//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.25 at 06:54:07 PM EET 
//


package com.sschudakov.xml.bin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for event complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="event">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="last_firs_middle_name" type="{sschudakov}last_firs_middle_name"/>
 *         &lt;element name="faculty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sub-faculty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="branch-of-study" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="time" type="{sschudakov}time"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "event", propOrder = {
    "lastFirsMiddleName",
    "faculty",
    "subFaculty",
    "branchOfStudy",
    "time"
})
public class Event {

    @XmlElement(name = "last_firs_middle_name", required = true)
    protected LastFirsMiddleName lastFirsMiddleName;
    @XmlElement(required = true)
    protected String faculty;
    @XmlElement(name = "sub-faculty", required = true)
    protected String subFaculty;
    @XmlElement(name = "branch-of-study", required = true)
    protected String branchOfStudy;
    @XmlElement(required = true)
    protected Time time;

    /**
     * Gets the value of the lastFirsMiddleName property.
     * 
     * @return
     *     possible object is
     *     {@link LastFirsMiddleName }
     *     
     */
    public LastFirsMiddleName getLastFirsMiddleName() {
        return lastFirsMiddleName;
    }

    /**
     * Sets the value of the lastFirsMiddleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LastFirsMiddleName }
     *     
     */
    public void setLastFirsMiddleName(LastFirsMiddleName value) {
        this.lastFirsMiddleName = value;
    }

    /**
     * Gets the value of the faculty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Sets the value of the faculty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaculty(String value) {
        this.faculty = value;
    }

    /**
     * Gets the value of the subFaculty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubFaculty() {
        return subFaculty;
    }

    /**
     * Sets the value of the subFaculty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubFaculty(String value) {
        this.subFaculty = value;
    }

    /**
     * Gets the value of the branchOfStudy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchOfStudy() {
        return branchOfStudy;
    }

    /**
     * Sets the value of the branchOfStudy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchOfStudy(String value) {
        this.branchOfStudy = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setTime(Time value) {
        this.time = value;
    }

}