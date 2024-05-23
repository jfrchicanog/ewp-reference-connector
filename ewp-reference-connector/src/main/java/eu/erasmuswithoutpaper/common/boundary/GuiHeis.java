package eu.erasmuswithoutpaper.common.boundary;

import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("heis")
public class GuiHeis {

    @Inject
    RegistryClient registryClient;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response institutionHeis() {
        List<HeiEntry> institutionsHeis = registryClient.getEwpInstanceHeisWithUrlsNew();

        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(institutionsHeis) {
        };
        return javax.ws.rs.core.Response.ok(entity).build();
    }
}
