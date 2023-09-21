//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 12:39:18 PM CEST 
//


package eu.emrex.elmo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Notes for server implementers:
 * 
 *                 The attachements MUST contain a human-readable version of all the crucial data
 *                 contained within the reports. This also includes all the data which may help to
 *                 prove the identity of the student (such as birth date). You MAY include all
 *                 this data in a single file, or you MAY split it into several ones (e.g. one PDF
 *                 per report) - we're leaving this decision to individual server implementers.
 * 
 *                 The attachments SHOULD be in PDF format. All other types of attachments MAY
 *                 be ignored by the clients.
 * 
 *                 If you wonder why attachements are required, then you should remember, that
 *                 some clients MAY ignore all learningOpportunitySpecification elements
 *                 (even if they are 100% valid) and simply choose to forward the attached
 *                 transcript of records to a staff member for manual processing (this is a
 *                 valid EMREX ELMO use case and all servers need to support it).
 * 
 *                 Try to order your attachments by importance (we'll leave it for the implementer
 *                 to decide which attachments are more important).
 * 
 *                 Also, try to keep them lightweight - some clients will need to store your
 *                 EMREX ELMO files for some time. Take their storage space into account when you
 *                 think about embedding logos or fonts inside your PDFs!
 * 
 *                 Notes for client implementers:
 * 
 *                 You will probably need these attachments in case when something goes wrong, and
 *                 you want this response to be imported or verified manually.
 * 
 *                 You MAY also use the attachment to view a human-readable "preview" of the ELMO
 *                 data for the student.
 *             
 * 
 * <p>Java-Klasse für Attachment complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Attachment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="type" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *               &lt;enumeration value="Diploma"/&gt;
 *               &lt;enumeration value="Diploma Supplement"/&gt;
 *               &lt;enumeration value="Transcript of Records"/&gt;
 *               &lt;enumeration value="EMREX transcript"/&gt;
 *               &lt;enumeration value="Micro Credential"/&gt;
 *               &lt;enumeration value="Letter of Nomination"/&gt;
 *               &lt;enumeration value="Certificate of Training"/&gt;
 *               &lt;enumeration value="Learning Agreement"/&gt;
 *               &lt;enumeration value="Other"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="content" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attachment", propOrder = {
    "identifier",
    "title",
    "type",
    "description",
    "content",
    "extension"
})
public class Attachment
    implements Serializable
{

    protected List<Attachment.Identifier> identifier;
    protected List<TokenWithOptionalLang> title;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    protected List<PlaintextMultilineStringWithOptionalLang> description;
    protected List<TokenWithOptionalLang> content;
    protected CustomExtensionsContainer extension;

    /**
     * Gets the value of the identifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attachment.Identifier }
     * 
     * 
     */
    public List<Attachment.Identifier> getIdentifier() {
        if (identifier == null) {
            identifier = new ArrayList<Attachment.Identifier>();
        }
        return this.identifier;
    }

    /**
     * Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TokenWithOptionalLang }
     * 
     * 
     */
    public List<TokenWithOptionalLang> getTitle() {
        if (title == null) {
            title = new ArrayList<TokenWithOptionalLang>();
        }
        return this.title;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlaintextMultilineStringWithOptionalLang }
     * 
     * 
     */
    public List<PlaintextMultilineStringWithOptionalLang> getDescription() {
        if (description == null) {
            description = new ArrayList<PlaintextMultilineStringWithOptionalLang>();
        }
        return this.description;
    }

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TokenWithOptionalLang }
     * 
     * 
     */
    public List<TokenWithOptionalLang> getContent() {
        if (content == null) {
            content = new ArrayList<TokenWithOptionalLang>();
        }
        return this.content;
    }

    /**
     * Ruft den Wert der extension-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CustomExtensionsContainer }
     *     
     */
    public CustomExtensionsContainer getExtension() {
        return extension;
    }

    /**
     * Legt den Wert der extension-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomExtensionsContainer }
     *     
     */
    public void setExtension(CustomExtensionsContainer value) {
        this.extension = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Identifier
        implements Serializable
    {

        @XmlValue
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String value;
        @XmlAttribute(name = "type", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String type;

        /**
         * Ruft den Wert der value-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Legt den Wert der value-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Ruft den Wert der type-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Legt den Wert der type-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

    }

}
