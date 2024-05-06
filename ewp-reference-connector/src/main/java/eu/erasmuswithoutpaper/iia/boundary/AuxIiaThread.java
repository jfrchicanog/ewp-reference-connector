/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.iia.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.common.IiaTaskEnum;
import eu.erasmuswithoutpaper.iia.common.IiaTaskService;
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 * @author Moritz Baader
 */
public class AuxIiaThread {

    @EJB
    IiasEJB iiasEJB;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    SendMonitoringService sendMonitoringService;

    @Inject
    IiaConverter iiaConverter;

    private static final Logger LOG = Logger.getLogger(AuxIiaThread.class.getCanonicalName());

    public void addEditIiaBeforeApproval(String heiId, String iiaId) throws Exception {
        String localHeiId = iiasEJB.getHeiId();

        LOG.fine("AuxIiaThread_ADDEDIT: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        LOG.fine("AuxIiaThread_ADDEDIT: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        LOG.fine("AuxIiaThread_ADDEDIT: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            if (clientResponse.getStatusCode() <= 599 && clientResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), clientResponse.getErrorMessage(), null);
            } else if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), clientResponse.getErrorMessage(), "Error");
            }
            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Respuesta raw: " + clientResponse.getRawResponse());

        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        if (responseEnity.getIia() == null || responseEnity.getIia().isEmpty()) {
            delete(heiId, localHeiId, iiaId);
            execNotificationToAlgoria(iiaId, heiId, IiaTaskEnum.DELETED, "Deleted");
            return;
        }

        Iia localIia = null;

        IiasGetResponse.Iia sendIia = responseEnity.getIia().get(0);

        if (sendIia.getCooperationConditions().isTerminatedAsAWhole() != null) {
            sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), "Terminated before approval", null);

            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: SendIia found HEIID: " + sendIia.getPartner().stream().map(p -> (p.getHeiId() == null ? "" : p.getHeiId())).collect(Collectors.toList()));
        LOG.fine("AuxIiaThread_ADDEDIT: SendIia found IIAID: " + sendIia.getPartner().stream().map(p -> (p.getIiaId() == null ? "" : p.getIiaId())).collect(Collectors.toList()));

        for (IiasGetResponse.Iia.Partner partner : sendIia.getPartner()) {
            LOG.fine("AuxIiaThread_ADDEDIT: Partner heiID: " + partner.getHeiId());
            LOG.fine("AuxIiaThread_ADDEDIT: Partner ID: " + partner.getIiaId());
            if (localHeiId.equals(partner.getHeiId())) {
                LOG.fine("AuxIiaThread_ADDEDIT: Own localeId found: " + (partner.getIiaId() == null ? "" : partner.getIiaId()));
                if (partner.getIiaId() != null) {
                    List<Iia> iia = iiasEJB.findByIdList(partner.getIiaId());
                    LOG.fine("AuxIiaThread_ADDEDIT: Find local iias: " + (iia == null ? "0" : iia.size()));
                    if (iia != null && !iia.isEmpty()) {
                        localIia = iia.get(0);
                        LOG.fine("AuxIiaThread_ADDEDIT: Foind local iia: " + localIia.getId());
                        break;
                    }
                }
            }
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Busqueda en bbdd " + (localIia != null));
        if (localIia == null) {
            Iia newIia = new Iia();
            iiaConverter.convertToIia(sendIia, newIia, iiasEJB.findAllInstitutions());

            LOG.fine("AuxIiaThread_ADDEDIT: Iia convertsed with conditions: " + newIia.getCooperationConditions().size());

            Iia persisted = iiasEJB.insertReceivedIia(sendIia, newIia);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(persisted);
            execNotificationToAlgoria(newIia.getId(), heiId, IiaTaskEnum.CREATED, json);

            LOG.fine("AuxIiaThread_ADDEDIT: After seting id");

            CompletableFuture.runAsync(() -> {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                }

                ClientResponse cnrResponse = notifyPartner(heiId, newIia.getId());

                LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse != null ? cnrResponse.getStatusCode() : "NULL"));
            });
        } else {
            LOG.fine("AuxIiaThread_ADDEDIT: Found existing iia");
            if (localIia.getHashPartner() == null) {
                LOG.fine("AuxIiaThread_ADDEDIT: Not containing other HASH");
                iiasEJB.updateWithPartnerIDs(localIia, sendIia, iiaId, heiId);
                execNotificationToAlgoria(localIia.getId(), heiId, IiaTaskEnum.UPDATED, "Updated hash y id del partner");
                LOG.fine("AuxIiaThread_ADDEDIT: Merged");

                String localId = localIia.getId();
                CompletableFuture.runAsync(() -> {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                    }

                    ClientResponse cnrResponse = notifyPartner(heiId, localId);

                    LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse != null ? cnrResponse.getStatusCode() : "NULL"));
                });

            } else {
                String beforeHash = localIia.getConditionsHash();
                LOG.fine("AuxIiaThread_ADDEDIT: Before hash: " + beforeHash);
                Iia modifIia = new Iia();
                iiaConverter.convertToIia(sendIia, modifIia, iiasEJB.findAllInstitutions());

                String afterHash = iiasEJB.updateIia(modifIia, localIia, sendIia.getIiaHash());

                LOG.fine("AuxIiaThread_ADDEDIT: After merging changes");

                LOG.fine("AuxIiaThread_ADDEDIT: Compare hashes: " + beforeHash + " " + afterHash);

                if (!beforeHash.equals(afterHash)) {

                    iiasEJB.deleteAssociatedIiaApprovals(localIia.getId());
                    execNotificationToAlgoria(localIia.getId(), heiId, IiaTaskEnum.UPDATED, "Update");

                    LOG.fine("AuxIiaThread_ADDEDIT: CNR URL: " + url);

                    String localId = localIia.getId();
                    CompletableFuture.runAsync(() -> {

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                        }

                        ClientResponse cnrResponse = notifyPartner(heiId, localId);

                        LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse != null ? cnrResponse.getStatusCode() : "NULL"));
                    });

                }
            }
        }

    }

    public void modify(String heiId, String iiaId, Iia approvedVersion) throws Exception {
        String localHeiId = iiasEJB.getHeiId();

        LOG.fine("AuxIiaThread_MODIFY: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return;
        }

        LOG.fine("AuxIiaThread_MODIFY: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return;
        }

        LOG.fine("AuxIiaThread_MODIFY: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        LOG.fine("AuxIiaThread_MODIFY: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        LOG.fine("AuxIiaThread_MODIFY: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            if (clientResponse.getStatusCode() <= 599 && clientResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), clientResponse.getErrorMessage(), null);
            } else if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), clientResponse.getErrorMessage(), "Error");
            }
            return;
        }

        LOG.fine("AuxIiaThread_MODIFY: Respuesta raw: " + clientResponse.getRawResponse());

        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        Iia localIia = null;

        IiasGetResponse.Iia sendIia = responseEnity.getIia().get(0);

        LOG.fine("AuxIiaThread_MODIFY: SendIia found HEIID: " + sendIia.getPartner().stream().map(p -> (p.getHeiId() == null ? "" : p.getHeiId())).collect(Collectors.toList()));
        LOG.fine("AuxIiaThread_MODIFY: SendIia found IIAID: " + sendIia.getPartner().stream().map(p -> (p.getIiaId() == null ? "" : p.getIiaId())).collect(Collectors.toList()));

        for (IiasGetResponse.Iia.Partner partner : sendIia.getPartner()) {
            LOG.fine("AuxIiaThread_MODIFY: Partner heiID: " + partner.getHeiId());
            LOG.fine("AuxIiaThread_MODIFY: Partner ID: " + partner.getIiaId());
            if (localHeiId.equals(partner.getHeiId())) {
                LOG.fine("AuxIiaThread_MODIFY: Own localeId found: " + (partner.getIiaId() == null ? "" : partner.getIiaId()));
                if (partner.getIiaId() != null) {
                    List<Iia> iia = iiasEJB.findByIdList(partner.getIiaId());
                    LOG.fine("AuxIiaThread_MODIFY: Find local iias: " + (iia == null ? "0" : iia.size()));
                    if (iia != null && !iia.isEmpty()) {
                        localIia = iia.get(0);
                        LOG.fine("AuxIiaThread_MODIFY: Foind local iia: " + localIia.getId());
                        break;
                    }
                }
            }
        }

        LOG.fine("AuxIiaThread_MODIFY: Busqueda en bbdd " + (localIia != null));
        if (localIia == null) {
            return;
        }

        if (sendIia.getCooperationConditions().isTerminatedAsAWhole() != null && sendIia.getCooperationConditions().isTerminatedAsAWhole()) {
            LOG.fine("AuxIiaThread_MODIFY: CNR for termination");
            if (localIia.getConditionsTerminatedAsAWhole() != null && localIia.getConditionsTerminatedAsAWhole()) {
                LOG.fine("AuxIiaThread_MODIFY: Already terminated");
                return;
            }

            if (sendIia.getIiaHash().equals(approvedVersion.getHashPartner())
                    && sendIia.getCooperationConditions().isTerminatedAsAWhole() == approvedVersion.getConditionsTerminatedAsAWhole()) {
                LOG.fine("AuxIiaThread_MODIFY: Revert before termination");
                iiasEJB.revertIia(localIia.getId(), approvedVersion.getId());
                execNotificationToAlgoria(localIia.getId(), heiId, IiaTaskEnum.REVERTED, "Revert");
            } else {
                LOG.fine("AuxIiaThread_MODIFY: Terminate");
                iiasEJB.terminateIia(localIia.getId());
                execNotificationToAlgoria(localIia.getId(), heiId, IiaTaskEnum.TERMINATED, "Terminated");
            }

            Iia finalLocalIia = localIia;
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                }

                ClientResponse cnrResponse = notifyPartner(heiId, finalLocalIia.getId());

                LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse != null ? cnrResponse.getStatusCode() : "NULL"));
            });

            return;
        } else if (sendIia.getCooperationConditions().isTerminatedAsAWhole() != null) {
            sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), "Terminated attribute is false", null);
            return;
        }

        if (sendIia.getIiaHash().equals(approvedVersion.getHashPartner())) {
            if (sendIia.getIiaHash().equals(localIia.getHashPartner()) && localIia.getConditionsTerminatedAsAWhole() == null) {
                LOG.fine("AuxIiaThread_MODIFY: Hashes are equal");
                return;
            }

            LOG.fine("AuxIiaThread_MODIFY: Revert detected");
            iiasEJB.revertIia(localIia.getId(), approvedVersion.getId());
            execNotificationToAlgoria(localIia.getId(), heiId, IiaTaskEnum.REVERTED, "Revert");

            Iia finalLocalIia = localIia;
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                }

                ClientResponse cnrResponse = notifyPartner(heiId, finalLocalIia.getId());

                LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse != null ? cnrResponse.getStatusCode() : "NULL"));
            });

            return;
        }

        iiasEJB.updateHashPartner(localIia.getId(), sendIia.getIiaHash());

        Iia modifIia = new Iia();
        iiaConverter.convertToIia(sendIia, modifIia, iiasEJB.findAllInstitutions());

        modifIia.getCooperationConditions().sort((cc1, cc2) -> cc1.getSendingPartner().getInstitutionId().equals(localHeiId) ? 1 : -1);

        modifIia.getCooperationConditions().forEach(cc -> System.out.println(cc.getSendingPartner().getInstitutionId() + " " + cc.getReceivingPartner().getInstitutionId()));

        String sendHash = HashCalculationUtility.calculateSha256(iiaConverter.convertToIias(localHeiId, Arrays.asList(modifIia)).get(0));
        if (sendHash.equals(localIia.getConditionsHash())) {
            LOG.fine("AuxIiaThread_MODIFY: Hashes are equal");
            return;
        }
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(sendIia);
        execNotificationToAlgoria(localIia.getId(), heiId, IiaTaskEnum.MODIFY, json);
        LOG.fine("AuxIiaThread_MODIFY: notify algoria");

    }

    private ClientResponse notifyPartner(String heiId, String iiaId) {
        String localHeiId = iiasEJB.getHeiId();
        Map<String, String> map = registryClient.getIiaCnrHeiUrls(heiId);

        if (map == null) {
            return null;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: MAP 2 ENCONTRADO");

        String url = (new ArrayList<>(map.values())).get(0);
        if (url == null) {
            return null;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Url CNR encontrada: " + url);

        ClientRequest cnrRequest = new ClientRequest();
        cnrRequest.setUrl(url);
        cnrRequest.setHeiId(heiId);
        cnrRequest.setMethod(HttpMethodEnum.POST);
        cnrRequest.setHttpsec(true);

        Map<String, List<String>> paramsMapCNR = new HashMap<>();
        paramsMapCNR.put("iia_id", Arrays.asList(iiaId));
        ParamsClass paramsClassCNR = new ParamsClass();
        paramsClassCNR.setUnknownFields(paramsMapCNR);
        cnrRequest.setParams(paramsClassCNR);

        return restClient.sendRequest(cnrRequest, Empty.class);
    }

    public void delete(String heiId, String localHeiId, String iiaId) {
        List<Iia> iias = iiasEJB.getByPartnerId(heiId, iiaId);
        if (iias == null || iias.isEmpty()) {
            LOG.fine("AuxIiaThread_ADDEDIT: No iia found to delete");
            return;
        }
        String iiaIdToDelete = iias.get(0).getId();
        iiasEJB.deleteIia(iias.get(0));

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
            }

            ClientResponse cnrResponse = notifyPartner(heiId, iiaIdToDelete);

            LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse != null ? cnrResponse.getStatusCode() : "NULL"));
        });

    }

    private void execNotificationToAlgoria(String iiaId, String notifierHeiId, IiaTaskEnum iiaTaskService, String description) {

        Callable<String> callableTask = IiaTaskService.createTask(iiaId, iiaTaskService, notifierHeiId, description);

        //Put the task in the queue
        IiaTaskService.addTask(callableTask);
    }
}
