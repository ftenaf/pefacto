/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
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
package es.mityc.appfacturae.ui.components;

import java.sql.Date;

public class MainTableRow {
	
	protected String VERSION;
	protected String IDENTIFIER;
	protected Date ISSUE_DATE;
	protected int PARTY_ID_SELLER;
	protected int PARTY_ID_BUYER;
	protected double TOTAL_EXECUTABLE;
	
	public String getVERSION() {
		return VERSION;
	}
	public void setVERSION(String version) {
		this.VERSION = version;
	}
	public String getIDENTIFIER() {
		return IDENTIFIER;
	}
	public void setIDENTIFIER(String identifier) {
		this.IDENTIFIER = identifier;
	}
	public Date getISSUE_DATE() {
		return ISSUE_DATE;
	}
	public void setISSUE_DATE(Date issueDate) {
		this.ISSUE_DATE = issueDate;
	}
	public int getPARTY_ID_SELLER() {
		return PARTY_ID_SELLER;
	}
	public void setPARTY_ID_SELLER(int sellerId) {
		this.PARTY_ID_SELLER = sellerId;
	}
	public int getPARTY_ID_BUYER() {
		return PARTY_ID_BUYER;
	}
	public void setPARTY_ID_BUYER(int buyerId) {
		this.PARTY_ID_BUYER = buyerId;
	}
	public double getTOTAL_EXECUTABLE() {
		return TOTAL_EXECUTABLE;
	}
	public void setTOTAL_EXECUTABLE(double amount) {
		this.TOTAL_EXECUTABLE = amount;
	}
	
}
