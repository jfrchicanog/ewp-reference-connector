package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.echo.boundary.GuiEchoResource;
import eu.erasmuswithoutpaper.factsheet.boundary.GuiFactsheetResource;
import eu.erasmuswithoutpaper.home.boundary.GuiHomeResource;
import eu.erasmuswithoutpaper.iia.boundary.GuiIiaPartnerResource;
import eu.erasmuswithoutpaper.iia.boundary.GuiIiaResource;
import eu.erasmuswithoutpaper.monitoring.TestMonitoringEndpoint;
import eu.erasmuswithoutpaper.notification.boundary.GuiNotificationResource;
import eu.erasmuswithoutpaper.omobility.boundary.GuiOutgoingMobilityResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiContactResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiInstitutionResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiPersonResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiMobilityParticipantResource;
import eu.erasmuswithoutpaper.organization.boundary.GuiOUnitResource;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("algoria")
public class GuiJAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(GuiHomeResource.class);
        resources.add(GuiInstitutionResource.class);
        resources.add(GuiPersonResource.class);
        resources.add(GuiContactResource.class);
        resources.add(GuiIiaPartnerResource.class);
        resources.add(GuiIiaResource.class);
        resources.add(GuiEchoResource.class);
        resources.add(GuiOutgoingMobilityResource.class);
        resources.add(GuiMobilityParticipantResource.class);
        resources.add(GuiNotificationResource.class);
        resources.add(GuiFactsheetResource.class);
        resources.add(GuiOUnitResource.class);
        return resources;
    }
}
