//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 11:39:43 AM CEST 
//


package eu.emrex.elmo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Groups is a way of organizing and sorting over groups of LOI's.
 *             
 * 
 * <p>Java-Klasse für Groups complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Groups"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="groupType" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="group" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="sortingKey" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
@XmlType(name = "Groups", propOrder = {
    "groupType"
})
public class Groups
    implements Serializable
{

    protected List<Groups.GroupType> groupType;

    /**
     * Gets the value of the groupType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Groups.GroupType }
     * 
     * 
     */
    public List<Groups.GroupType> getGroupType() {
        if (groupType == null) {
            groupType = new ArrayList<Groups.GroupType>();
        }
        return this.groupType;
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
     *         &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
     *         &lt;element name="group" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="sortingKey" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "title",
        "group"
    })
    public static class GroupType
        implements Serializable
    {

        @XmlElement(required = true)
        protected List<TokenWithOptionalLang> title;
        protected List<Groups.GroupType.Group> group;
        @XmlAttribute(name = "id")
        protected String id;

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
         * Gets the value of the group property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the group property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Groups.GroupType.Group }
         * 
         * 
         */
        public List<Groups.GroupType.Group> getGroup() {
            if (group == null) {
                group = new ArrayList<Groups.GroupType.Group>();
            }
            return this.group;
        }

        /**
         * Ruft den Wert der id-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Legt den Wert der id-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
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
         *         &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="sortingKey" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "title"
        })
        public static class Group
            implements Serializable
        {

            @XmlElement(required = true)
            protected List<TokenWithOptionalLang> title;
            @XmlAttribute(name = "id")
            protected String id;
            @XmlAttribute(name = "sortingKey")
            protected String sortingKey;

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
             * Ruft den Wert der id-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            /**
             * Ruft den Wert der sortingKey-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSortingKey() {
                return sortingKey;
            }

            /**
             * Legt den Wert der sortingKey-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSortingKey(String value) {
                this.sortingKey = value;
            }

        }

    }

}
