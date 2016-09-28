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
package es.mityc.appfacturae.facturae.signature;
 
public class DSAKeyValueType {
    
	protected byte[] p;
    protected byte[] q;
    protected byte[] g;
    protected byte[] y;
    protected byte[] j;
    protected byte[] seed;
    protected byte[] pgenCounter;
    
	public byte[] getP() {
        return p;
    }
    
	public void setP(byte[] value) {
        this.p = ((byte[]) value);
    }
    
	public byte[] getQ() {
        return q;
    }
    
	public void setQ(byte[] value) {
        this.q = ((byte[]) value);
    }
    
	public byte[] getG() {
        return g;
    }
    
	public void setG(byte[] value) {
        this.g = ((byte[]) value);
    }
    
	public byte[] getY() {
        return y;
    }
    
	public void setY(byte[] value) {
        this.y = ((byte[]) value);
    }
    
	public byte[] getJ() {
        return j;
    }
    
	public void setJ(byte[] value) {
        this.j = ((byte[]) value);
    }
    
	public byte[] getSeed() {
        return seed;
    }
    
	public void setSeed(byte[] value) {
        this.seed = ((byte[]) value);
    }
    
	public byte[] getPgenCounter() {
        return pgenCounter;
    }
    
	public void setPgenCounter(byte[] value) {
        this.pgenCounter = ((byte[]) value);
    }

}