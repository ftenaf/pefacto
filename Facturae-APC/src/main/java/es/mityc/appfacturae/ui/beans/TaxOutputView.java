/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
package es.mityc.appfacturae.ui.beans;

import javax.swing.table.TableModel;
import es.mityc.appfacturae.utils.constants.Constants;

public class TaxOutputView {
	private String idImpuesto="";
	private String tipoImpositivo="";
	private String baseImponible="";
	private String cuota="";
	private String baseImponibleEspecial="";
	private String cuotaEspecial="";
	private String recargoEquivalencia="";
	private String importeRecargoEquivalencia="";
	
	public TaxOutputView ()
	{
		
	}
	public String getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(String idImpuesto) {
		this.idImpuesto = idImpuesto;
	}
	public String getTipoImpositivo() {
		return tipoImpositivo;
	}
	public void setTipoImpositivo(String tipoImpositivo) {
		this.tipoImpositivo = tipoImpositivo;
	}
	public String getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}
	public String getCuota() {
		return cuota;
	}
	public void setCuota(String cuota) {
		this.cuota = cuota;
	}
	public String getBaseImponibleEspecial() {
		return baseImponibleEspecial;
	}
	public void setBaseImponibleEspecial(String baseImponibleEspecial) {
		this.baseImponibleEspecial = baseImponibleEspecial;
	}
	public String getCuotaEspecial() {
		return cuotaEspecial;
	}
	public void setCuotaEspecial(String cuotaEspecial) {
		this.cuotaEspecial = cuotaEspecial;
	}
	public String getRecargoEquivalencia() {
		return recargoEquivalencia;
	}
	public void setRecargoEquivalencia(String recargoEquivalencia) {
		this.recargoEquivalencia = recargoEquivalencia;
	}
	public String getImporteRecargoEquivalencia() {
		return importeRecargoEquivalencia;
	}
	public void setImporteRecargoEquivalencia(String importeRecargoEquivalencia) {
		this.importeRecargoEquivalencia = importeRecargoEquivalencia;
	}
	
    public static TaxOutputView getElementFromTable(TableModel tabla, int fila)
    {
    	TaxOutputView taxOutputView = new TaxOutputView();
    	if (tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_ID_IMPUESTO) == null )
    		taxOutputView.setIdImpuesto("");
    	else
    		taxOutputView.setIdImpuesto(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_ID_IMPUESTO).toString());
    	
    	if ( tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_TIPO_IMPOSITIVO) == null )
    		taxOutputView.setTipoImpositivo("");
    	else
    		taxOutputView.setTipoImpositivo(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_TIPO_IMPOSITIVO).toString());
    	
    	if (tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE) == null )
    			taxOutputView.setBaseImponible("");
    	else
    		taxOutputView.setBaseImponible(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE).toString());
    	
    	if (tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_CUOTA) == null )
    			taxOutputView.setCuota("");
    	else
    		taxOutputView.setCuota(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_CUOTA).toString());
    	
    	if (tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE_ESPECIAL) == null)
    		taxOutputView.setBaseImponibleEspecial("");
    	else
    		taxOutputView.setBaseImponibleEspecial(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE_ESPECIAL).toString());
    	
    	if (  tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_CUOTA_ESPECIAL) == null )
    		taxOutputView.setCuotaEspecial("");
    	else
    		taxOutputView.setCuotaEspecial(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_CUOTA_ESPECIAL).toString());
    	if ( tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_RECARGO_EQUIVALENCIA) == null )
    		taxOutputView.setRecargoEquivalencia("");
    	else
    		taxOutputView.setRecargoEquivalencia(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_RECARGO_EQUIVALENCIA).toString());
    	if (tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_IMPORTE_RECARGO_EQUIVALENCIA) == null )
    		taxOutputView.setImporteRecargoEquivalencia("");
    	else
    		taxOutputView.setImporteRecargoEquivalencia(tabla.getValueAt(fila, Constants.POS_GRID_REPERCUTIDOS_IMPORTE_RECARGO_EQUIVALENCIA).toString());
    	
    	return taxOutputView;
    }
    public static TaxOutputView getElementFromResult(String[] result)
    {
    	TaxOutputView taxOutputView = new TaxOutputView();
    	
    	if (result[Constants.POS_GRID_REPERCUTIDOS_ID_IMPUESTO] == null )
    		taxOutputView.setIdImpuesto("");
    	else
    		taxOutputView.setIdImpuesto(result[Constants.POS_GRID_REPERCUTIDOS_ID_IMPUESTO].toString());
    	
    	if ( result[Constants.POS_GRID_REPERCUTIDOS_TIPO_IMPOSITIVO] == null )
    		taxOutputView.setTipoImpositivo("");
    	else
    		taxOutputView.setTipoImpositivo(result[Constants.POS_GRID_REPERCUTIDOS_TIPO_IMPOSITIVO].toString());
    	
    	if (result[Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE] == null )
    			taxOutputView.setBaseImponible("");
    	else
    		taxOutputView.setBaseImponible(result[Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE].toString());
    	
    	if (result[Constants.POS_GRID_REPERCUTIDOS_CUOTA] == null )
    			taxOutputView.setCuota("");
    	else
    		taxOutputView.setCuota(result[Constants.POS_GRID_REPERCUTIDOS_CUOTA].toString());
    	
    	if (result[Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE_ESPECIAL]== null)
    		taxOutputView.setBaseImponibleEspecial("");
    	else
    		taxOutputView.setBaseImponibleEspecial(result[Constants.POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE_ESPECIAL].toString());
    	
    	if ( result[Constants.POS_GRID_REPERCUTIDOS_CUOTA_ESPECIAL] == null )
    		taxOutputView.setCuotaEspecial("");
    	else
    		taxOutputView.setCuotaEspecial(result[Constants.POS_GRID_REPERCUTIDOS_CUOTA_ESPECIAL].toString());
    	if ( result[Constants.POS_GRID_REPERCUTIDOS_RECARGO_EQUIVALENCIA] == null )
    		taxOutputView.setRecargoEquivalencia("");
    	else
    		taxOutputView.setRecargoEquivalencia(result[Constants.POS_GRID_REPERCUTIDOS_RECARGO_EQUIVALENCIA].toString());
    	if (result[Constants.POS_GRID_REPERCUTIDOS_IMPORTE_RECARGO_EQUIVALENCIA] == null )
    		taxOutputView.setImporteRecargoEquivalencia("");
    	else
    		taxOutputView.setImporteRecargoEquivalencia(result[Constants.POS_GRID_REPERCUTIDOS_IMPORTE_RECARGO_EQUIVALENCIA].toString());    	
    	
    	    	
    	return taxOutputView;    	
    }
}
