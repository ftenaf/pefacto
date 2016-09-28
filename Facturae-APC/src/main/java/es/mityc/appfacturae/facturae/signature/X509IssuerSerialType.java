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
 
import java.math.BigInteger;

public class X509IssuerSerialType {
    
	protected String x509IssuerName;
    protected BigInteger x509SerialNumber;
    
	public String getX509IssuerName() {
        return x509IssuerName;
    }
    
	public void setX509IssuerName(String value) {
        this.x509IssuerName = value;
    }
    
	public BigInteger getX509SerialNumber() {
        return x509SerialNumber;
    }
    
	public void setX509SerialNumber(BigInteger value) {
        this.x509SerialNumber = value;
    }
}