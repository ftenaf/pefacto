/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por la Comisión Europea– versiones posteriores de la EUPL (la Licencia);
 * Solo podrá usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
 * Véase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia.
 */
package es.mityc.facturae32;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FileHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FileHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SchemaVersion" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}SchemaVersionType"/>
 *         &lt;element name="Modality" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}ModalityType"/>
 *         &lt;element name="InvoiceIssuerType" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}InvoiceIssuerTypeType"/>
 *         &lt;element name="ThirdParty" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}ThirdPartyType" minOccurs="0"/>
 *         &lt;element name="Batch" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}BatchType"/>
 *         &lt;element name="FactoringAssignmentData" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}FactoringAssignmentDataType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileHeaderType", propOrder = {
    "schemaVersion",
    "modality",
    "invoiceIssuerType",
    "thirdParty",
    "batch",
    "factoringAssignmentData"
})
public class FileHeaderType {

    @XmlElement(name = "SchemaVersion", required = true, defaultValue = "3.2")
    protected String schemaVersion;
    @XmlElement(name = "Modality", required = true)
    protected ModalityType modality;
    @XmlElement(name = "InvoiceIssuerType", required = true)
    protected InvoiceIssuerTypeType invoiceIssuerType;
    @XmlElement(name = "ThirdParty")
    protected ThirdPartyType thirdParty;
    @XmlElement(name = "Batch", required = true)
    protected BatchType batch;
    @XmlElement(name = "FactoringAssignmentData")
    protected FactoringAssignmentDataType factoringAssignmentData;

    /**
     * Gets the value of the schemaVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Sets the value of the schemaVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaVersion(String value) {
        this.schemaVersion = value;
    }

    /**
     * Gets the value of the modality property.
     * 
     * @return
     *     possible object is
     *     {@link ModalityType }
     *     
     */
    public ModalityType getModality() {
        return modality;
    }

    /**
     * Sets the value of the modality property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalityType }
     *     
     */
    public void setModality(ModalityType value) {
        this.modality = value;
    }

    /**
     * Gets the value of the invoiceIssuerType property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceIssuerTypeType }
     *     
     */
    public InvoiceIssuerTypeType getInvoiceIssuerType() {
        return invoiceIssuerType;
    }

    /**
     * Sets the value of the invoiceIssuerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceIssuerTypeType }
     *     
     */
    public void setInvoiceIssuerType(InvoiceIssuerTypeType value) {
        this.invoiceIssuerType = value;
    }

    /**
     * Gets the value of the thirdParty property.
     * 
     * @return
     *     possible object is
     *     {@link ThirdPartyType }
     *     
     */
    public ThirdPartyType getThirdParty() {
        return thirdParty;
    }

    /**
     * Sets the value of the thirdParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThirdPartyType }
     *     
     */
    public void setThirdParty(ThirdPartyType value) {
        this.thirdParty = value;
    }

    /**
     * Gets the value of the batch property.
     * 
     * @return
     *     possible object is
     *     {@link BatchType }
     *     
     */
    public BatchType getBatch() {
        return batch;
    }

    /**
     * Sets the value of the batch property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchType }
     *     
     */
    public void setBatch(BatchType value) {
        this.batch = value;
    }

    /**
     * Gets the value of the factoringAssignmentData property.
     * 
     * @return
     *     possible object is
     *     {@link FactoringAssignmentDataType }
     *     
     */
    public FactoringAssignmentDataType getFactoringAssignmentData() {
        return factoringAssignmentData;
    }

    /**
     * Sets the value of the factoringAssignmentData property.
     * 
     * @param value
     *     allowed object is
     *     {@link FactoringAssignmentDataType }
     *     
     */
    public void setFactoringAssignmentData(FactoringAssignmentDataType value) {
        this.factoringAssignmentData = value;
    }

}
