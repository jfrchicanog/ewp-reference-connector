//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 11:48:25 AM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Describes a list of components that make up various tables in LA.
 *             
 * 
 * <p>Java-Klasse für ListOf_Components complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ListOf_Components"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="components-studied" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ComponentList" minOccurs="0"/&gt;
 *         &lt;element name="components-recognized" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ComponentList" minOccurs="0"/&gt;
 *         &lt;element name="virtual-components" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ComponentList" minOccurs="0"/&gt;
 *         &lt;element name="blended-mobility-components" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ComponentList" minOccurs="0"/&gt;
 *         &lt;element name="short-term-doctoral-components" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ComponentList" minOccurs="0"/&gt;
 *         &lt;element name="student-signature" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}Signature"/&gt;
 *         &lt;element name="sending-hei-signature" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}Signature"/&gt;
 *         &lt;element name="receiving-hei-signature" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}Signature" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOf_Components", propOrder = {
    "componentsStudied",
    "componentsRecognized",
    "virtualComponents",
    "blendedMobilityComponents",
    "shortTermDoctoralComponents",
    "studentSignature",
    "sendingHeiSignature",
    "receivingHeiSignature"
})
@XmlSeeAlso({
    eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement.ChangesProposal.class
})
public class ListOfComponents
    implements Serializable
{

    @XmlElement(name = "components-studied")
    protected ComponentList componentsStudied;
    @XmlElement(name = "components-recognized")
    protected ComponentList componentsRecognized;
    @XmlElement(name = "virtual-components")
    protected ComponentList virtualComponents;
    @XmlElement(name = "blended-mobility-components")
    protected ComponentList blendedMobilityComponents;
    @XmlElement(name = "short-term-doctoral-components")
    protected ComponentList shortTermDoctoralComponents;
    @XmlElement(name = "student-signature", required = true)
    protected Signature studentSignature;
    @XmlElement(name = "sending-hei-signature", required = true)
    protected Signature sendingHeiSignature;
    @XmlElement(name = "receiving-hei-signature")
    protected Signature receivingHeiSignature;

    /**
     * Ruft den Wert der componentsStudied-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ComponentList }
     *     
     */
    public ComponentList getComponentsStudied() {
        return componentsStudied;
    }

    /**
     * Legt den Wert der componentsStudied-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentList }
     *     
     */
    public void setComponentsStudied(ComponentList value) {
        this.componentsStudied = value;
    }

    /**
     * Ruft den Wert der componentsRecognized-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ComponentList }
     *     
     */
    public ComponentList getComponentsRecognized() {
        return componentsRecognized;
    }

    /**
     * Legt den Wert der componentsRecognized-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentList }
     *     
     */
    public void setComponentsRecognized(ComponentList value) {
        this.componentsRecognized = value;
    }

    /**
     * Ruft den Wert der virtualComponents-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ComponentList }
     *     
     */
    public ComponentList getVirtualComponents() {
        return virtualComponents;
    }

    /**
     * Legt den Wert der virtualComponents-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentList }
     *     
     */
    public void setVirtualComponents(ComponentList value) {
        this.virtualComponents = value;
    }

    /**
     * Ruft den Wert der blendedMobilityComponents-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ComponentList }
     *     
     */
    public ComponentList getBlendedMobilityComponents() {
        return blendedMobilityComponents;
    }

    /**
     * Legt den Wert der blendedMobilityComponents-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentList }
     *     
     */
    public void setBlendedMobilityComponents(ComponentList value) {
        this.blendedMobilityComponents = value;
    }

    /**
     * Ruft den Wert der shortTermDoctoralComponents-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ComponentList }
     *     
     */
    public ComponentList getShortTermDoctoralComponents() {
        return shortTermDoctoralComponents;
    }

    /**
     * Legt den Wert der shortTermDoctoralComponents-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentList }
     *     
     */
    public void setShortTermDoctoralComponents(ComponentList value) {
        this.shortTermDoctoralComponents = value;
    }

    /**
     * Ruft den Wert der studentSignature-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getStudentSignature() {
        return studentSignature;
    }

    /**
     * Legt den Wert der studentSignature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setStudentSignature(Signature value) {
        this.studentSignature = value;
    }

    /**
     * Ruft den Wert der sendingHeiSignature-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSendingHeiSignature() {
        return sendingHeiSignature;
    }

    /**
     * Legt den Wert der sendingHeiSignature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSendingHeiSignature(Signature value) {
        this.sendingHeiSignature = value;
    }

    /**
     * Ruft den Wert der receivingHeiSignature-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getReceivingHeiSignature() {
        return receivingHeiSignature;
    }

    /**
     * Legt den Wert der receivingHeiSignature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setReceivingHeiSignature(Signature value) {
        this.receivingHeiSignature = value;
    }

}
