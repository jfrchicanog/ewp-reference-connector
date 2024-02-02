//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 11:52:32 AM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="academic-year-la-stats" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="receiving-academic-year-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v2}AcademicYearId"/&gt;
 *                   &lt;element name="la-outgoing-total" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="la-outgoing-not-modified-after-approval" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="la-outgoing-modified-after-approval" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="la-outgoing-latest-version-approved" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="la-outgoing-latest-version-rejected" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="la-outgoing-latest-version-awaiting" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
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
    "academicYearLaStats"
})
@XmlRootElement(name = "las-outgoing-stats-response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd")
public class LasOutgoingStatsResponse
    implements Serializable
{

    @XmlElement(name = "academic-year-la-stats", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd")
    protected List<LasOutgoingStatsResponse.AcademicYearLaStats> academicYearLaStats;

    /**
     * Gets the value of the academicYearLaStats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the academicYearLaStats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcademicYearLaStats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LasOutgoingStatsResponse.AcademicYearLaStats }
     * 
     * 
     */
    public List<LasOutgoingStatsResponse.AcademicYearLaStats> getAcademicYearLaStats() {
        if (academicYearLaStats == null) {
            academicYearLaStats = new ArrayList<LasOutgoingStatsResponse.AcademicYearLaStats>();
        }
        return this.academicYearLaStats;
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
     *         &lt;element name="receiving-academic-year-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v2}AcademicYearId"/&gt;
     *         &lt;element name="la-outgoing-total" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="la-outgoing-not-modified-after-approval" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="la-outgoing-modified-after-approval" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="la-outgoing-latest-version-approved" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="la-outgoing-latest-version-rejected" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="la-outgoing-latest-version-awaiting" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
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
        "receivingAcademicYearId",
        "laOutgoingTotal",
        "laOutgoingNotModifiedAfterApproval",
        "laOutgoingModifiedAfterApproval",
        "laOutgoingLatestVersionApproved",
        "laOutgoingLatestVersionRejected",
        "laOutgoingLatestVersionAwaiting"
    })
    public static class AcademicYearLaStats
        implements Serializable
    {

        @XmlElement(name = "receiving-academic-year-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected String receivingAcademicYearId;
        @XmlElement(name = "la-outgoing-total", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected BigInteger laOutgoingTotal;
        @XmlElement(name = "la-outgoing-not-modified-after-approval", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected BigInteger laOutgoingNotModifiedAfterApproval;
        @XmlElement(name = "la-outgoing-modified-after-approval", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected BigInteger laOutgoingModifiedAfterApproval;
        @XmlElement(name = "la-outgoing-latest-version-approved", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected BigInteger laOutgoingLatestVersionApproved;
        @XmlElement(name = "la-outgoing-latest-version-rejected", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected BigInteger laOutgoingLatestVersionRejected;
        @XmlElement(name = "la-outgoing-latest-version-awaiting", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/stats-response.xsd", required = true)
        protected BigInteger laOutgoingLatestVersionAwaiting;

        /**
         * Ruft den Wert der receivingAcademicYearId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReceivingAcademicYearId() {
            return receivingAcademicYearId;
        }

        /**
         * Legt den Wert der receivingAcademicYearId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReceivingAcademicYearId(String value) {
            this.receivingAcademicYearId = value;
        }

        /**
         * Ruft den Wert der laOutgoingTotal-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLaOutgoingTotal() {
            return laOutgoingTotal;
        }

        /**
         * Legt den Wert der laOutgoingTotal-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLaOutgoingTotal(BigInteger value) {
            this.laOutgoingTotal = value;
        }

        /**
         * Ruft den Wert der laOutgoingNotModifiedAfterApproval-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLaOutgoingNotModifiedAfterApproval() {
            return laOutgoingNotModifiedAfterApproval;
        }

        /**
         * Legt den Wert der laOutgoingNotModifiedAfterApproval-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLaOutgoingNotModifiedAfterApproval(BigInteger value) {
            this.laOutgoingNotModifiedAfterApproval = value;
        }

        /**
         * Ruft den Wert der laOutgoingModifiedAfterApproval-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLaOutgoingModifiedAfterApproval() {
            return laOutgoingModifiedAfterApproval;
        }

        /**
         * Legt den Wert der laOutgoingModifiedAfterApproval-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLaOutgoingModifiedAfterApproval(BigInteger value) {
            this.laOutgoingModifiedAfterApproval = value;
        }

        /**
         * Ruft den Wert der laOutgoingLatestVersionApproved-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLaOutgoingLatestVersionApproved() {
            return laOutgoingLatestVersionApproved;
        }

        /**
         * Legt den Wert der laOutgoingLatestVersionApproved-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLaOutgoingLatestVersionApproved(BigInteger value) {
            this.laOutgoingLatestVersionApproved = value;
        }

        /**
         * Ruft den Wert der laOutgoingLatestVersionRejected-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLaOutgoingLatestVersionRejected() {
            return laOutgoingLatestVersionRejected;
        }

        /**
         * Legt den Wert der laOutgoingLatestVersionRejected-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLaOutgoingLatestVersionRejected(BigInteger value) {
            this.laOutgoingLatestVersionRejected = value;
        }

        /**
         * Ruft den Wert der laOutgoingLatestVersionAwaiting-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLaOutgoingLatestVersionAwaiting() {
            return laOutgoingLatestVersionAwaiting;
        }

        /**
         * Legt den Wert der laOutgoingLatestVersionAwaiting-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLaOutgoingLatestVersionAwaiting(BigInteger value) {
            this.laOutgoingLatestVersionAwaiting = value;
        }

    }

}
