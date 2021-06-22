package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.course.boundary.LosResource;
import eu.erasmuswithoutpaper.discovery.boundary.ManifestResource;
import eu.erasmuswithoutpaper.echo.boundary.EchoResource;
import eu.erasmuswithoutpaper.iia.boundary.IiaResource;
import eu.erasmuswithoutpaper.imobility.boundary.IncomingMobilityResource;
import eu.erasmuswithoutpaper.omobility.boundary.OutgoingMobilityResource;
import eu.erasmuswithoutpaper.organization.boundary.InstitutionResource;
import eu.erasmuswithoutpaper.organization.boundary.OrganizationUnitResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class RestJAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(EchoResource.class);
        resources.add(ManifestResource.class);
        resources.add(InstitutionResource.class);
        //resources.add(OrganizationUnitResource.class);
        //resources.add(LosResource.class);
        //resources.add(OutgoingMobilityResource.class);
        //resources.add(IncomingMobilityResource.class);
        resources.add(IiaResource.class);
        return resources;
    }
}
