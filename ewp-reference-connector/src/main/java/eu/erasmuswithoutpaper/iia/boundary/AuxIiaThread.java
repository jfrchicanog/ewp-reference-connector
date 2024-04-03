/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;

import java.util.*;
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

        Iia localIia = null;

        IiasGetResponse.Iia sendIia = responseEnity.getIia().get(0);

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

            iiasEJB.insertReceivedIia(sendIia, newIia);

            LOG.fine("AuxIiaThread_ADDEDIT: After seting id");

            CompletableFuture.runAsync(() -> {

                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e) {
                    LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                }

                ClientResponse cnrResponse = notifyPartner(heiId, newIia.getId());

                LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse!= null?cnrResponse.getStatusCode():"NULL"));
            });
        } else {
            LOG.fine("AuxIiaThread_ADDEDIT: Found existing iia");
            if (localIia.getHashPartner() == null) {
                LOG.fine("AuxIiaThread_ADDEDIT: Not containing other HASH");
                iiasEJB.updateWithPartnerIDs(localIia, sendIia, iiaId, heiId);
                LOG.fine("AuxIiaThread_ADDEDIT: Merged");

                String localId = localIia.getId();
                CompletableFuture.runAsync(() -> {

                    try {
                        Thread.sleep(5000);
                    }catch (InterruptedException e) {
                        LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                    }

                    ClientResponse cnrResponse = notifyPartner(heiId, localId);

                    LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse!= null?cnrResponse.getStatusCode():"NULL"));
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

                    LOG.fine("AuxIiaThread_ADDEDIT: CNR URL: " + url);

                    String localId = localIia.getId();
                    CompletableFuture.runAsync(() -> {

                        try {
                            Thread.sleep(5000);
                        }catch (InterruptedException e) {
                            LOG.fine("AuxIiaThread_ADDEDIT: Error sleeping");
                        }

                        ClientResponse cnrResponse = notifyPartner(heiId, localId);

                        LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + (cnrResponse!= null?cnrResponse.getStatusCode():"NULL"));
                    });

                }
            }
        }

    }

    public void modify(String heiId, String iiaId, Iia approvedVersion) {
        LOG.fine("AuxIiaThread_MODIFY: Empezando GET tras CNR");
        LOG.fine("AuxIiaThread_MODIFY: IIA ID: " + iiaId);
        LOG.fine("AuxIiaThread_MODIFY: HEI ID: " + heiId);
        LOG.fine("AuxIiaThread_MODIFY: APPROVED_IIA ID: " + approvedVersion.getId());
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
}
