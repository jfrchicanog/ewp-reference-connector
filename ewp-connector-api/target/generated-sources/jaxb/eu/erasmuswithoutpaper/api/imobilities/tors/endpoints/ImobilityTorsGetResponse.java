//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 01:43:29 PM CET 
//


package eu.erasmuswithoutpaper.api.imobilities.tors.endpoints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.emrex.elmo.Elmo;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tor" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="omobility-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *                   &lt;element ref="{https://github.com/emrex-eu/elmo-schemas/tree/v1}elmo"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tor"
})
@XmlRootElement(name = "imobility-tors-get-response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-imobility-tors/blob/stable-v1/endpoints/get-response.xsd")
public class ImobilityTorsGetResponse
    implements Serializable
{

    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-imobility-tors/blob/stable-v1/endpoints/get-response.xsd")
    protected List<ImobilityTorsGetResponse.Tor> tor;

    /**
     * Gets the value of the tor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImobilityTorsGetResponse.Tor }
     * 
     * 
     */
    public List<ImobilityTorsGetResponse.Tor> getTor() {
        if (tor == null) {
            tor = new ArrayList<ImobilityTorsGetResponse.Tor>();
        }
        return this.tor;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="omobility-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
     *         &lt;element ref="{https://github.com/emrex-eu/elmo-schemas/tree/v1}elmo"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "omobilityId",
        "elmo"
    })
    public static class Tor
        implements Serializable
    {

        @XmlElement(name = "omobility-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-imobility-tors/blob/stable-v1/endpoints/get-response.xsd", required = true)
        protected String omobilityId;
        @XmlElement(namespace = "https://github.com/emrex-eu/elmo-schemas/tree/v1", required = true)
        protected Elmo elmo;

        /**
         * Ruft den Wert der omobilityId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOmobilityId() {
            return omobilityId;
        }

        /**
         * Legt den Wert der omobilityId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOmobilityId(String value) {
            this.omobilityId = value;
        }

        /**
         * 
         *                                         The Transcript of Records for this mobility, in the EMREX ELMO format.
         * 
         *                                         ## Why we reuse EMREX ELMO format?
         * 
         *                                         It's worth taking note of some slight disadvantages of this approach - in
         *                                         particular, there's the fact that some features required by EMREX ELMO are
         *                                         quite unnecessary in EWP's use case.
         * 
         *                                         For example, there's no longer any reason for the ELMO element to be digitally
         *                                         signed (because in EWP we can rely on the transport layer for security, whereas
         *                                         in EMREX signing is required because the student acts as an intermediary).
         *                                         Similarly, we do not need `learner` and `issuer` elements, because we can
         *                                         determine their content by other means.
         * 
         *                                         Despite these, we have chosen to require EMREX ELMO XML format here, "as it
         *                                         is", without any changes. Why?
         * 
         *                                          1. Because some partners have already implemented EMREX, and it will simply be
         *                                             easier for them to reuse its output.
         *                                          2. Because we want to encourage EWP partners to implement EMREX too.
         *                                          3. Because we don't want to introduce a competing ToR format unless we have
         *                                             to. It seems better to promote existing formats.
         *                                          4. Because *some* clients might still make some use of the features which we
         *                                             might otherwise deem unnecessary (e.g. PDF attachments).
         * 
         *                                         ## Cross-referencing IDs
         * 
         *                                         EMREX ELMO allows implementers to attach an unlimited number of identifiers
         *                                         to many of its elements. In order to facilitate cross-referencing, we need to
         *                                         assign some explicit values for the `type` attribute used in the `identifier`
         *                                         elements:
         * 
         *                                         * In the `issuer` element, you SHOULD use the "schac" identifier type to refer
         *                                           to EWP's HEI IDs. E.g. <identifier type="schac">uw.edu.pl</identifier>
         *                                           https://github.com/erasmus-without-paper/ewp-specs-api-registry/#schac-identifiers
         * 
         *                                         * In the `learningOpportunitySpecification` elements, you SHOULD use the
         *                                           "ewp-los-id" identifier type to refer to LOS IDs introduced in EWP's Courses
         *                                           API: https://github.com/erasmus-without-paper/ewp-specs-api-courses#unique-identifiers
         * 
         *                                         * In the `learningOpportunityInstance` elements, you SHOULD use the
         *                                           "ewp-loi-id" identifier type to refer to LOI IDs introduced in EWP's Courses
         *                                           API: https://github.com/erasmus-without-paper/ewp-specs-api-courses#unique-identifiers
         * 
         *                                           (At the time of writing this, EMREX ELMO schema v1.1.0 does not allow specifying
         *                                           identifiers for LOI elements, but we have requested this change and expect it to be
         *                                           introduced in future versions: https://github.com/emrex-eu/elmo-schemas/issues/10)
         * 
         *                                         ## Signing the ELMO
         * 
         *                                         This API does not currently specify how the ELMO response needs to be signed.
         *                                         In EMREX, ELMO files need to be signed with a very specific certificate, but in
         *                                         EWP the certificate used does not (currently) matter, because clients use other ways
         *                                         of verifying that the response came from appropriate source. This means that clients
         *                                         MAY simply ignore the attached digital signature (it is okay to not verify it in any
         *                                         way).
         * 
         *                                         That said, it seems that the best certificate to use for this, would be the same
         *                                         certificate which was used to actually serve the ToRs API. (Clients SHOULD NOT rely
         *                                         on this recommendation however, because it MAY change in the future.)
         *                                     
         * 
         * @return
         *     possible object is
         *     {@link Elmo }
         *     
         */
        public Elmo getElmo() {
            return elmo;
        }

        /**
         * Legt den Wert der elmo-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Elmo }
         *     
         */
        public void setElmo(Elmo value) {
            this.elmo = value;
        }

    }

}
