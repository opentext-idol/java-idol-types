/*
 * Copyright 2026 Open Text.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Open Text and its affiliates
 * and licensors ("Open Text") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Open Text shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.opentext.idol.types.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.opentext.idol.types.xjc.DoubleAdapter;
import com.opentext.idol.types.xjc.LongAdapter;


/**
 * <p>Java class for valueDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="valueDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="values" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="value_sum" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *           &lt;element name="valuesum" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice&gt;
 *           &lt;element name="value_average" type="{http://schemas.autonomy.com/aci/}dateOrNumber"/&gt;
 *           &lt;element name="valueaverage" type="{http://schemas.autonomy.com/aci/}dateOrNumber"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice&gt;
 *           &lt;element name="value_min" type="{http://schemas.autonomy.com/aci/}dateOrNumber"/&gt;
 *           &lt;element name="valuemin" type="{http://schemas.autonomy.com/aci/}dateOrNumber"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice&gt;
 *           &lt;element name="value_max" type="{http://schemas.autonomy.com/aci/}dateOrNumber"/&gt;
 *           &lt;element name="valuemax" type="{http://schemas.autonomy.com/aci/}dateOrNumber"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="valuepercentile" type="{http://schemas.autonomy.com/aci/}dateOrNumberPercentile" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "valueDetails", propOrder = {
    "values",
    "valueSum",
    "valuesum",
    "valueAverage",
    "valueaverage",
    "valueMin",
    "valuemin",
    "valueMax",
    "valuemax",
    "valuepercentile"
})
public class ValueDetails
    implements Serializable
{

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(LongAdapter.class)
    @XmlSchemaType(name = "integer")
    protected Long values;
    @XmlElement(name = "value_sum")
    @XmlJavaTypeAdapter(DoubleAdapter.class)
    protected Double valueSum;
    protected Double valuesum;
    @XmlElement(name = "value_average")
    protected DateOrNumber valueAverage;
    protected DateOrNumber valueaverage;
    @XmlElement(name = "value_min")
    protected DateOrNumber valueMin;
    protected DateOrNumber valuemin;
    @XmlElement(name = "value_max")
    protected DateOrNumber valueMax;
    protected DateOrNumber valuemax;
    protected List<DateOrNumberPercentile> valuepercentile;

    /**
     * Gets the value of the values property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Long getValues() {
        return values;
    }

    /**
     * Sets the value of the values property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValues(Long value) {
        this.values = value;
    }

    /**
     * Gets the value of the valueSum property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValueSum() {
        return valueSum != null ? valueSum : valuesum;
    }

    /**
     * Sets the value of the valueSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValueSum(Double value) {
        this.valueSum = value;
        this.valuesum = value;
    }

    /**
     * Gets the value of the valuesum property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValuesum() {
        return getValueSum();
    }

    /**
     * Sets the value of the valuesum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValuesum(Double value) {
        setValueSum(value);
    }

    /**
     * Gets the value of the valueAverage property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrNumber }
     *     
     */
    public DateOrNumber getValueAverage() {
        return valueAverage != null ? valueAverage : valueaverage;
    }

    /**
     * Sets the value of the valueAverage property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrNumber }
     *     
     */
    public void setValueAverage(DateOrNumber value) {
        this.valueAverage = value;
        this.valueaverage = value;
    }

    /**
     * Gets the value of the valueaverage property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrNumber }
     *     
     */
    public DateOrNumber getValueaverage() {
        return getValueAverage();
    }

    /**
     * Sets the value of the valueaverage property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrNumber }
     *     
     */
    public void setValueaverage(DateOrNumber value) {
        setValueAverage(value);
    }

    /**
     * Gets the value of the valueMin property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrNumber }
     *     
     */
    public DateOrNumber getValueMin() {
        return valueMin != null ? valueMin : valuemin;
    }

    /**
     * Sets the value of the valueMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrNumber }
     *     
     */
    public void setValueMin(DateOrNumber value) {
        this.valueMin = value;
        this.valuemin = value;
    }

    /**
     * Gets the value of the valuemin property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrNumber }
     *     
     */
    public DateOrNumber getValuemin() {
        return getValueMin();
    }

    /**
     * Sets the value of the valuemin property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrNumber }
     *     
     */
    public void setValuemin(DateOrNumber value) {
        setValueMin(value);
    }

    /**
     * Gets the value of the valueMax property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrNumber }
     *     
     */
    public DateOrNumber getValueMax() {
        return valueMax != null ? valueMax : valuemax;
    }

    /**
     * Sets the value of the valueMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrNumber }
     *     
     */
    public void setValueMax(DateOrNumber value) {
        this.valueMax = value;
        this.valuemax = value;
    }

    /**
     * Gets the value of the valuemax property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrNumber }
     *     
     */
    public DateOrNumber getValuemax() {
        return getValueMax();
    }

    /**
     * Sets the value of the valuemax property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrNumber }
     *     
     */
    public void setValuemax(DateOrNumber value) {
        setValueMax(value);
    }

    /**
     * Gets the value of the valuepercentile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the valuepercentile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValuepercentile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateOrNumberPercentile }
     * 
     * 
     */
    public List<DateOrNumberPercentile> getValuepercentile() {
        if (valuepercentile == null) {
            valuepercentile = new ArrayList<DateOrNumberPercentile>();
        }
        return this.valuepercentile;
    }

}
