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

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.StreamUtil;


public class PDFUtil {

	
	private static Log logger = null;
	private Document pdfDocument = null;
	private static Paragraph newLine = null;
	private static Image logo = null;
	
	static {
		try {
			loadImages();
		} catch (Exception e) {
			logger.error("Error loading topbar and logo images to create the PDF file: " + e.getMessage());
		}
	}

	public PDFUtil(){

		logger = LogFactory.getLog(PDFUtil.class);
		pdfDocument = new Document(PageSize.A4.rotate());	
		newLine = new Paragraph(5f,"\n\n");
	}

	public boolean generatePDF(String pdfFile, java.util.List<Object[]> invoicesList,String dateFrom,String dateTo, int invoiceType, String finalTaxableBase, String finalTaxes, String finalTotalAmount, int columsNumber){

		try {

			PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfFile));
			pdfDocument.open();

			
			/** Set the Logo of the PDF Document **/
			addLogo(pdfDocument, 0);
			
			
			/** Set the Title of the PDF Document **/
			if(invoiceType == 0){
				addTitle(pdfDocument, Constants.LANG.getString("VATBookTotalInvoices") + " (" + Constants.LANG.getString("From") + " " + dateFrom + " " + Constants.LANG.getString("To") + " " + dateTo + ")", Constants.FONT_TITLE_BOLD);
			}else if(invoiceType == 1){
				addTitle(pdfDocument, Constants.LANG.getString("VATBookIssuedInvoices") + " (" + Constants.LANG.getString("From") + " " + dateFrom + " " + Constants.LANG.getString("To") + " " + dateTo + ")", Constants.FONT_TITLE_BOLD);
			}else if(invoiceType == 2){
				addTitle(pdfDocument, Constants.LANG.getString("VATBookReceivedInvoices") + " (" + Constants.LANG.getString("From") + " " + dateFrom + " " + Constants.LANG.getString("To") + " " + dateTo + ")", Constants.FONT_TITLE_BOLD);
			}


			/** Set the Header of the PDF Document **/
			addTableHeader(pdfDocument, columsNumber, Constants.FONT_TEXT_PLAIN, Constants.BKG_MAIN_COLOR);


			/** Set the table containg the VAT Book information **/
			java.util.List<Object[]>  vatBookList = invoicesList;
			for(int i = 0; i < vatBookList.size() ; i++){
				addLine(pdfDocument, vatBookList.get(i), columsNumber, Constants.FONT_TEXT_PLAIN, Constants.BKG_MAIN_COLOR);
			}

			
			/** Set the Summary of the PDF Document **/
			addSummary(pdfDocument, Constants.LANG.getString("TaxableBase") + ": " + finalTaxableBase + " "+Constants.LANG.getString("EuroSimbol")+"    " + Constants.LANG.getString("Taxes") + ": " + finalTaxes + " "+Constants.LANG.getString("EuroSimbol")+"    " + Constants.LANG.getString("TotalAmount") + ": " + finalTotalAmount + " "+Constants.LANG.getString("EuroSimbol") , Constants.FONT_TEXT_BOLD);
			

			pdfDocument.close();
			return true;

			
		} catch (DocumentException de) {
			logger.error("Error while generating the VAT Book: " + de.getMessage());
			return false;
		} catch(FileNotFoundException fnfe)	{
			logger.error("Error while generating the VAT Book: " + fnfe.getMessage());
			return false;
		} catch (Exception e) {
			logger.error("Error while generating the VAT Book: " + e.getMessage());
			return false;
		}

	}


	private static void addTitle(Document pdfDocument, String title, Font font) throws DocumentException{
		Paragraph text = new Paragraph(title, font);
		pdfDocument.add(text);
		pdfDocument.add(newLine);
		pdfDocument.add(newLine);
	}

	
	private static void addLogo(Document pdfDocument, int align) throws DocumentException {
		int tries = 10;
		while (logo == null && tries > 0) {
			try {
				Thread.sleep(50);
				tries--;
			} catch (InterruptedException e) {}
		}
		
		logo.setAlignment(align);	
		pdfDocument.add(logo);
		pdfDocument.add(newLine);
	}

	
	private static void addTableHeader(Document pdfDocument, int columsNumber, Font font, Color backgroundColor) throws DocumentException{
		
		/** Creates the Table Header **/
		PdfPTable pdfPTable = new PdfPTable( columsNumber );
		pdfPTable.setWidths(new int[]{7,10,7,10,7,6,7,4,7,5,7});
		pdfPTable.setWidthPercentage(100f);

		/** Set Table Header properties **/
		PdfPCell pdfPCell = new PdfPCell( );
		pdfPCell.setBackgroundColor(backgroundColor);
		pdfPCell.setBorderWidth(0.8f);
		pdfPCell.setBorderColor(Constants.BKG_MAIN_COLOR);
		pdfPCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("InvoiceNumber"), font));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("Issuer"), font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("CIFNIFIssuer"), font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("Receiver"), font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("CIFNIFReceiver"), font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("IssueDate"), font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("TaxableBase") + " ("+Constants.LANG.getString("EuroSimbol")+")", font ));
		pdfPTable.addCell(pdfPCell);

		String[] vatTax = Constants.LANG.getString("VAT").split(" ");
		pdfPCell.setPhrase(new Phrase(vatTax[0] + " (%)", font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("Taxes") + " ("+Constants.LANG.getString("EuroSimbol")+")", font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("EquivalenceSurcharge") + " (%)", font ));
		pdfPTable.addCell(pdfPCell);

		pdfPCell.setPhrase(new Phrase(Constants.LANG.getString("EquivalenceSurchargeAmount") + " ("+Constants.LANG.getString("EuroSimbol")+")", font ));
		pdfPTable.addCell(pdfPCell);

		pdfDocument.add(pdfPTable);
		
	}

	
	private static void addLine(Document pdfDocument, Object[] invoiceLine, int columsNumber, Font font, Color backgroundColor) throws DocumentException{

		try {
			
			/** Creates the Table **/
			PdfPTable pdfPTable = new PdfPTable( columsNumber );
			pdfPTable.setWidths( new int[]{7,10,7,10,7,6,7,4,7,5,7});
			pdfPTable.setWidthPercentage( 100f );

			/** Set Table properties **/
			PdfPCell pdfPCell = new PdfPCell( );
			pdfPCell.setBorderWidth(0.5f);
			pdfPCell.setBorderColor(Constants.FONT_COLOR);
			pdfPCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			for(int i = 0; i< columsNumber; i++){
				if(invoiceLine[i].toString().equals("")){
					pdfPCell.setBackgroundColor(Color.white);
					pdfPCell.setPhrase( new Phrase(invoiceLine[i].toString(), font ));
				} else{
					pdfPCell.setBackgroundColor(backgroundColor);
					pdfPCell.setPhrase( new Phrase(invoiceLine[i].toString(), font ));
				}
				pdfPTable.addCell( pdfPCell );
			}

			pdfDocument.add ( pdfPTable );

		} catch (DocumentException e) {
			throw e;
		}	

	}
	
	private static void addSummary(Document pdfDocument, String title, Font font) throws DocumentException{
		pdfDocument.add(newLine);
		pdfDocument.add(newLine);		
		pdfDocument.add(newLine);
		pdfDocument.add(newLine);
		Paragraph text = new Paragraph(title, font);
		pdfDocument.add(text);
		pdfDocument.add(newLine);
		pdfDocument.add(newLine);
	}
	
	private static void loadImages() throws MalformedURLException, IOException, BadElementException {
		InputStream is = PDFUtil.class.getResourceAsStream("/images/topbar.jpg");
		byte[] imageBytes = new byte[is.available()];
		StreamUtil.readStream(is, imageBytes);
		logo = Image.getInstance(imageBytes);
		logo.scalePercent(77f,77f);
	}
}
