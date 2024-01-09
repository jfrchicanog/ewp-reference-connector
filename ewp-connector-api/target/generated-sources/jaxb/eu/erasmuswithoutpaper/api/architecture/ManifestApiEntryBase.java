//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.09 um 01:16:35 PM CET 
//


package eu.erasmuswithoutpaper.api.architecture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.discovery.Discovery;
import eu.erasmuswithoutpaper.api.echo.Echo;
import eu.erasmuswithoutpaper.api.factsheet.Factsheet;
import eu.erasmuswithoutpaper.api.iias.Iias;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApproval;
import eu.erasmuswithoutpaper.api.iias.approval.cnr.IiaApprovalCnr;
import eu.erasmuswithoutpaper.api.iias.cnr.IiaCnr;
import eu.erasmuswithoutpaper.api.imobilities.Imobilities;
import eu.erasmuswithoutpaper.api.imobilities.cnr.ImobilityCnr;
import eu.erasmuswithoutpaper.api.imobilities.tors.ImobilityTors;
import eu.erasmuswithoutpaper.api.imobilities.tors.cnr.ImobilityTorCnr;
import eu.erasmuswithoutpaper.api.institutions.Institutions;
import eu.erasmuswithoutpaper.api.omobilities.Omobilities;
import eu.erasmuswithoutpaper.api.omobilities.cnr.OmobilityCnr;
import eu.erasmuswithoutpaper.api.omobilities.las.OmobilityLas;
import eu.erasmuswithoutpaper.api.ounits.OrganizationalUnits;
import eu.erasmuswithoutpaper.api.registry.Registry;


/**
 * 
 *                 A common base type for children of the `apis-implemented` element of the
 *                 manifest file. We declare it here (as opposed to declaring in the Discovery
 *                 API's namespace) because it is shared between all the APIs - we want it to
 *                 stay backwards-compatible when new releases of the Discovery API are published.
 * 
 *                 IMPORTANT: Clients MUST NOT assume that all children of `apis-implemented` will
 *                 "inherit" these properties. It is true that most EWP-related APIs do, but
 *                 manifest files may contain references to *any* APIs.
 *             
 * 
 * <p>Java-Klasse für ManifestApiEntryBase complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ManifestApiEntryBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-email" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-notes" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;pattern value="[0-9]+\.[0-9]+\.[0-9]+"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestApiEntryBase", propOrder = {
    "adminEmail",
    "adminNotes"
})
@XmlSeeAlso({
    ImobilityTorCnr.class,
    ImobilityTors.class,
    ImobilityCnr.class,
    Imobilities.class,
    OmobilityLas.class,
    OmobilityCnr.class,
    Omobilities.class,
    Factsheet.class,
    IiaApprovalCnr.class,
    IiasApproval.class,
    IiaCnr.class,
    Iias.class,
    OrganizationalUnits.class,
    Institutions.class,
    Registry.class,
    Echo.class,
    Discovery.class
})
public class ManifestApiEntryBase
    implements Serializable
{

    @XmlElement(name = "admin-email")
    protected List<String> adminEmail;
    @XmlElement(name = "admin-notes")
    protected MultilineString adminNotes;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * 
     *                         RECOMMENDED element. Address of a developer or server administrator who may
     *                         be contacted in case of problems *with this particular API* (e.g. malformed
     *                         responses, etc.). Multiple `admin-email` elements may be provided.
     *                     Gets the value of the adminEmail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adminEmail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdminEmail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAdminEmail() {
        if (adminEmail == null) {
            adminEmail = new ArrayList<String>();
        }
        return this.adminEmail;
    }

    /**
     * 
     *                         Additional information provided by host developers for the client developers.
     * 
     *                         E.g. "We are currently not delivering <description> elements because our
     *                         model is incompatible with the `1.1.3` schema. We will start to deliver them
     *                         once we upgrade to the `1.2.0` schema."
     *                     
     * 
     * @return
     *     possible object is
     *     {@link MultilineString }
     *     
     */
    public MultilineString getAdminNotes() {
        return adminNotes;
    }

    /**
     * Legt den Wert der adminNotes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilineString }
     *     
     */
    public void setAdminNotes(MultilineString value) {
        this.adminNotes = value;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Legt den Wert der version-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
