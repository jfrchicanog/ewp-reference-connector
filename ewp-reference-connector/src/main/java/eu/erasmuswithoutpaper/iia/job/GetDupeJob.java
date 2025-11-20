package eu.erasmuswithoutpaper.iia.job;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Stateless
public class GetDupeJob {

    @EJB IiasEJB iiasEJB;
    @EJB JobRegistry jobs;

    RegistryClient registryClient;

    RestClient restClient;

    public Map<String, Map<String, List<String>>> counts;

    @Asynchronous
    public void run(String jobId, RegistryClient registryClient, RestClient restClient) {
        this.registryClient = registryClient;
        this.restClient = restClient;
        List<Iia> iias = iiasEJB.findAllNoneApproved();

        String localHeiId = iiasEJB.getHeiId();

        counts = new HashMap<>();

        for (Iia iia : iias) {
            IiaPartner partner = null;
            if (iia.getCooperationConditions() != null) {
                for (CooperationCondition condition : iia.getCooperationConditions()) {
                    if (condition.getSendingPartner().getInstitutionId() != null
                            && !condition.getSendingPartner().getInstitutionId().equals(localHeiId)) {
                        partner = condition.getSendingPartner();
                    }
                    if (condition.getReceivingPartner().getInstitutionId() != null
                            && !condition.getReceivingPartner().getInstitutionId().equals(localHeiId)) {
                        partner = condition.getReceivingPartner();
                    }
                }
            }

            if (partner != null) {
                if (!counts.containsKey(partner.getInstitutionId())) {
                    Map<String, List<String>> iiaList = new HashMap<>();
                    List<String> iiaIds = new ArrayList<>();
                    iiaIds.add(iia.getId());
                    iiaList.put(partner.getIiaId(), iiaIds);
                    counts.put(partner.getInstitutionId(), iiaList);
                } else {
                    Map<String, List<String>> iiaList = counts.get(partner.getInstitutionId());
                    if (iiaList.containsKey(partner.getIiaId())) {
                        iiaList.get(partner.getIiaId()).add(iia.getId());
                    } else {
                        List<String> iiaIds = new ArrayList<>();
                        iiaIds.add(iia.getId());
                        iiaList.put(partner.getIiaId(), iiaIds);
                    }

                }
            }
        }

        //filter only duplicated or if key is null
        counts.entrySet().removeIf(entry -> {
            Map<String, List<String>> iiaMap = entry.getValue();
            iiaMap.entrySet().removeIf(e -> e.getValue().size() < 2);
            iiaMap.entrySet().removeIf(e -> {
                String k = e.getKey();
                return k == null || k.trim().isEmpty() || k.trim().equalsIgnoreCase("null");
            });
            return iiaMap.isEmpty();
        });

        //iterate for each send get to partener
        counts.forEach((partnerHeiId, iiaMap) -> {
            iiaMap.forEach((partnerIiaId, ourIiaIds) -> {
                //send get to partnerHeiId with partnerIiaId
                try {
                    IiasGetResponse.Iia remoteIia = sendGet(partnerHeiId, partnerIiaId);
                    if (remoteIia != null) {
                        AtomicReference<String> ourIdFromPartner = new AtomicReference<>();
                        remoteIia.getPartner().forEach(p -> {
                            if (p.getHeiId().equals(localHeiId)) {
                                ourIdFromPartner.set(p.getIiaId());
                            }
                        });
                        //mark the correct one with an *
                        if (ourIdFromPartner.get() != null) {
                            ourIiaIds.forEach(ourIiaId -> {
                                if (ourIiaId.equals(ourIdFromPartner.get())) {
                                    ourIiaIds.set(ourIiaIds.indexOf(ourIiaId), ourIiaId + "*");
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                }
            });
        });

    }

    private IiasGetResponse.Iia sendGet(String heiId, String iiaId) {
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return null;
        }


        String url = map.get("get-url");
        if (url == null) {
            return null;
        }


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


        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);


        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        return responseEnity.getIia().get(0);
    }

    public Map<String, Map<String, List<String>>> getCounts() {
        return counts;
    }
}

