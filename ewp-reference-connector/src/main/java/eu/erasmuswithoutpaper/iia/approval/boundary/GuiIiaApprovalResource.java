package eu.erasmuswithoutpaper.iia.approval.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.Approval;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.approval.control.IiaApprovalConverter;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;

@Stateless
@Path("iiaApproval")
public class GuiIiaApprovalResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;
    
    @Inject
    IiaApprovalConverter converter;

    @GET
    @Path("get_all")
    public Response getAll() {
        List<IiaApproval> iiaApprovalList = em.createNamedQuery(IiaApproval.findAll).getResultList();
        
        List<Approval> approvals = converter.convertToIiasApproval(null, iiaApprovalList);
        GenericEntity<List<Approval>> entity = new GenericEntity<List<Approval>>(approvals) {};
        
        return Response.ok(entity).build();
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(IiaApproval iiaApproval) {
        em.persist(iiaApproval);
    }

    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiaHeis() {
        List<HeiEntry> heis = registryClient.getIiaApprovalHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("iias_approval")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasApproval(ClientRequest clientRequest) {
        ClientResponse iiaApprovalResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
        return javax.ws.rs.core.Response.ok(iiaApprovalResponse).build();
    }
}
