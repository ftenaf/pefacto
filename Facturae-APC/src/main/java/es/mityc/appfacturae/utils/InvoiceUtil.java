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
package es.mityc.appfacturae.utils;

import java.util.ArrayList;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.SignInvoiceException;
import es.mityc.appfacturae.facturae.Facturae;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.facturae.utils.MarshalException;
import es.mityc.facturae.utils.SigningException;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.ValidationException;

public interface InvoiceUtil {

	/**
     * Charges an invoice stored in a File to screen
     * 
     * Argument openType.- 
     * 					true  .- See invoice  (all disabled, no dialog)
     * 					false .- Corrective Invoice (nothing disabled, a dialog is shown)
     * 					null  .- Edit invoice (nothing disabled, no dialog)
	 * @throws UnmarshalException 
     */
	public void chargeInvoice(final Boolean openType, String id) throws UnmarshalException;

	/** Sign an invoice loading its attached documents stored in data base 
     * 
     *  @argument id.- Serie + number for selected invoice
     *  @argument rectIn.- It sets signed invoice as corrective invoice
     *  @argument MainWindow.- MainWindow instance for progress bar (could be null) 
     *  @return .- New id for signed invoice stored in DB
     * @throws DatabaseOperationException If a database error happens
	 * @throws ValidationException 
     * @throws UnmarshalException 
     * @throws SigningException 
     * @throws MarshalException 
     */
	public String signInvoice(String id, boolean rectIn, MainWindow mw) throws DatabaseOperationException, SignInvoiceException, ValidationException;

    /** This method invokes the invoice signature process
     * @param f .- Facturae object to sign
     * @param ads .- Attachment objects to store with
     * @param rectIn .- Flag for Rectified invoices
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return .- Id for signed invoice
     * @throws DatabaseOperationException If a database error happens
     * @throws ValidationException 
     * @throws MarshalException 
     * @throws SigningException 
     */
    public String signInvoice(Facturae f, ArrayList<AttachedDocument> ads, boolean rectIn, MainWindow mw) throws DatabaseOperationException, SignInvoiceException, ValidationException;
    
    /** This method invokes the save draft process
     * 
     * @param f .- Facturae object to save
     * @param ads .- Attachment objects to store with
     * @param rectIn .- Flag for Rectified invoices
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return .- Id for saved invoice
     * @throws DatabaseOperationException If a database error happens
     * @throws ValidationException 
     */
    public String saveDraft(Facturae f, ArrayList<AttachedDocument> ads, boolean rectIn, MainWindow mw) throws DatabaseOperationException, ValidationException;
 
    /** This method invokes the save received process
     * 
     * @param f .- Facturae object to save
     * @param ads .- Attachment objects to store with
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return .- Id for saved invoice
     * @throws DatabaseOperationException If a database error happens
     * @throws ValidationException 
     */    
    public String saveReceived(Facturae f, ArrayList<AttachedDocument> ads, MainWindow mw) throws DatabaseOperationException, ValidationException;
    
    /**
     * Show a Corrective element
     * @param id.- Id for invoice corrective element to be shown
     */
    public void seeCorrections(String id);

    /**
     * Send a Facturae object using simple mail transfer protocol
     * @param id.- Identifier for invoice to be send
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return boolean.- True if it was sent
     */
    public boolean sendInvoice(String id, MainWindow mw);

}