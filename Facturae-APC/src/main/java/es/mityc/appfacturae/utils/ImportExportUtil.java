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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.facturae.Facturae;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.dialogs.MessageDialog;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.GenericFacturae;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.UnmarshallerUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.XMLUtil;

public class ImportExportUtil {
	
	private static Log logger = LogFactory.getLog(ImportExportUtil.class);
	private static String[] filenames = new String[]{"FacturaeDB.script", "FacturaeDB.properties"};
    
	public static void importDB(MainWindow mw, String importFile, boolean saveDrafts) throws ImportExportException, DatabaseOperationException {
    	if (importFile != null && !"".equals(importFile.trim())) {
    		File f = new File(importFile);
    		if (f.exists()) {
    			if (f.getName().endsWith(".fedb")) {
    				if (mw != null) 
    					mw.refreshProgressBar(10);
    				MessageDialog rdw = new MessageDialog(mw,true,Constants.LANG.getString("Import"),Constants.LANG.getString("ImportarBDMessage"),new javax.swing.ImageIcon(ImportExportUtil.class.getResource("/images/warning_message.gif")));
    				rdw.setVisible(true);
    				Vector<Facturae> v = new Vector<Facturae>();
    				Vector<String> vversion = new Vector<String>();
    				if (rdw.getResult() == MessageDialog.OK) {
    					// The drafts have to be saved. They're saved as Facturae objects
    					if (saveDrafts) {
	    					SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT FACTURAE_ID FROM FACTURAE WHERE FACTURAE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE STATE=0 AND CLASS=0)");
	    					List<?> ls = s.list();
	    					if (ls != null && ls.size() > 0) {
	    						int draftsCount = ls.size();
	    						SQLQuery s2 = null;
	    						List<?> ls2 = null,ls3 = null;
	    						java.sql.Blob b = null;
	    						Facturae fe = null;
	    						File tempFile = null;
	    						String tempFileName = "draftTemp.xml";
	    						for (int i = 0; i < draftsCount; i++) {
	    							if (mw != null) 
	    		    					mw.refreshProgressBar( (int)(i / (draftsCount * 1.) * 20) + 10 );
	    							s2 = FacturaeManager.getInstance().executeQuery("SELECT XML FROM FACTURAE WHERE FACTURAE_ID = '" + ls.get(i).toString()+ "'");
	    							ls2 = s2.list();
	    							s2 = FacturaeManager.getInstance().executeQuery("SELECT VERSION FROM INVOICE WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM FACTURAE_INVOICES WHERE FACTURAE_ID = '" + ls.get(i).toString()+ "')");
	    							ls3 = s2.list();
	    							b = (java.sql.Blob)ls2.get(0);
	    							try {
										XMLUtil.writeXml2File(StreamUtil.createDoc(b.getBinaryStream()),tempFileName);
										tempFile = new File(tempFileName);
										
										UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeUtil.getVersionOb(ls3.get(0).toString()));
										GenericFacturae gf = unmarshallerUtil.unmarshal(tempFile);
		    							fe = IntermediaryUtil.getApplicationFacturae(gf);
		    							
		    							v.add(fe);
		    							vversion.add(ls3.get(0).toString());
	    							} catch (FileNotFoundException e) {
	    								logger.error("Draft file not found error trying to import the data base: " + e.getMessage());
										MessageDialog j = new MessageDialog(mw, true, Constants.LANG.getString("SaveDraft"), Constants.LANG.getString("NOOKImportSaveDraft"), new ImageIcon(ImportExportUtil.class.getResource("/images/question_message.gif")));
							        	j.setVisible(true);
							        	int res = j.getResult();
							    		if (MessageDialog.CANCEL == res)
							    			return;
	    							} catch (UnmarshalException e) {
	    								logger.error("Unmarshalling a draft, an error happened during data base import: " + e.getMessage());
										MessageDialog j = new MessageDialog(mw, true, Constants.LANG.getString("SaveDraft"), Constants.LANG.getString("NOOKImportSaveDraft"), new ImageIcon(ImportExportUtil.class.getResource("/images/question_message.gif")));
							        	j.setVisible(true);
							        	int res = j.getResult();
							    		if (MessageDialog.CANCEL == res)
							    			return;
									} catch (ValidationException e) {
										logger.error("Draft validation error trying to import the data base: " + e.getMessage());
										MessageDialog j = new MessageDialog(mw, true, Constants.LANG.getString("SaveDraft"), Constants.LANG.getString("NOOKImportSaveDraft"), new ImageIcon(ImportExportUtil.class.getResource("/images/question_message.gif")));
							        	j.setVisible(true);
							        	int res = j.getResult();
							    		if (MessageDialog.CANCEL == res)
							    			return;
									} catch (SQLException e) {
										logger.error("Saving a draft a SQL error occurred during daba base import: " + e.getMessage());
										MessageDialog j = new MessageDialog(mw, true, Constants.LANG.getString("SaveDraft"), Constants.LANG.getString("NOOKImportSaveDraft"), new ImageIcon(ImportExportUtil.class.getResource("/images/question_message.gif")));
							        	j.setVisible(true);
							        	int res = j.getResult();
							    		if (MessageDialog.CANCEL == res)
							    			return;
									} finally {
										if (tempFile != null && tempFile.exists())
											tempFile.delete();
									}
	    						}
							}
    					}
    					// The data base is released
    					FacturaeManager.reset();
    					
    					// The old data base files are renamed
    					boolean renameResult = true;
    					for (int i = 0; i < filenames.length; i++) {
    						File fileDB = new File(filenames[i]);
    						if (fileDB.exists()) {
    							// Check if file to rename exists
    							File fileDBold = new File(filenames[i]+".OLD");
        						if (fileDBold.exists())
        							fileDBold.delete();
    							if (fileDB.renameTo(new File(fileDB.getName()+".OLD")) == false)
    								renameResult = false;
    						}
    					}
    					if (mw != null) 
	    					mw.refreshProgressBar(40);
    					
    					// The data base files to import are set
    					if (renameResult) {
    						ZipInputStream in = null;
    						OutputStream out = null;
    						try {
    							// Open the ZIP file
    							in = new ZipInputStream(new FileInputStream(importFile));
    							// Get the first entry
    							ZipEntry entry = in.getNextEntry();
    							while (entry != null) {
    								// Open the output file
        							out = new FileOutputStream(entry.getName());
        							// Transfer bytes from the ZIP file to the output file
        							byte[] buf = new byte[1024];
        							int len;
        							while ((len = in.read(buf)) > 0) {
        								out.write(buf, 0, len);
        							}
        							entry = in.getNextEntry();
        							out.close();
    							}
    							
    							
    						} catch (IOException e) {
    							throw new ImportExportException(Constants.LANG.getString("NOOKErrorDuringExport"));
    						} finally {
    							// Close the stream
    							if (in != null)
									try {
										in.close();
									} catch (IOException e) {}
    							if (out != null)
									try {
										out.close();
									} catch (IOException e) {}
    						}
    					}
    					
    					if (mw != null) 
	    					mw.refreshProgressBar(60);
    					
    					// Once the new files are stored, the configuration parameters have to be updated
        				
    					SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT MAX(CAST(NUMBER AS INTEGER)) FROM INVOICE WHERE STATE=0 AND CLASS=0");
    					List<?> ls = s.list();
    					if (ls != null && ls.size() > 0 && ls.get(0) != null)
    						Constants.CONFIG_PROP.setProperty("draftId", String.valueOf(Integer.parseInt(ls.get(0).toString()) + 1));
    					else
    						Constants.CONFIG_PROP.setProperty("draftId", String.valueOf(1));
    					if (mw != null) 
	    					mw.refreshProgressBar(65);
    					s = FacturaeManager.getInstance().executeQuery("SELECT MAX(CAST(NUMBER AS INTEGER)) FROM INVOICE WHERE (STATE=1 OR STATE=2) AND CLASS=0");
    					ls = s.list();
    					if (ls != null && ls.size() > 0 && ls.get(0) != null)
    						Constants.CONFIG_PROP.setProperty("issuedId", String.valueOf(Integer.parseInt(ls.get(0).toString())+1));
    					else
    						Constants.CONFIG_PROP.setProperty("issuedId", String.valueOf(1));
    					if (mw != null) 
	    					mw.refreshProgressBar(70);
    					s = FacturaeManager.getInstance().executeQuery("SELECT MAX(CAST(NUMBER AS INTEGER)) FROM INVOICE WHERE STATE=3 AND CLASS=0");
    					ls = s.list();
    					if (ls != null && ls.size() > 0 && ls.get(0) != null)
    						Constants.CONFIG_PROP.setProperty("receivedId", String.valueOf(Integer.parseInt(ls.get(0).toString())+1));
    					else
    						Constants.CONFIG_PROP.setProperty("receivedId", String.valueOf(1));
        				if (mw != null) 
	    					mw.refreshProgressBar(75);
        				s = FacturaeManager.getInstance().executeQuery("SELECT MAX(CAST(NUMBER AS INTEGER)) FROM INVOICE WHERE STATE=0 AND CLASS=1");
        				ls = s.list();
        				if (ls != null && ls.size() > 0 && ls.get(0) != null)
        					Constants.CONFIG_PROP.setProperty("c-draftId", String.valueOf(Integer.parseInt(ls.get(0).toString())+1));
        				else
        					Constants.CONFIG_PROP.setProperty("c-draftId", String.valueOf(1));
            			if (mw != null) 
	    					mw.refreshProgressBar(80);
            			s = FacturaeManager.getInstance().executeQuery("SELECT MAX(CAST(NUMBER AS INTEGER)) FROM INVOICE WHERE (STATE=1 OR STATE=2) AND CLASS=1");
            			ls = s.list();
            			if (ls != null && ls.size() > 0 && ls.get(0) != null)
            				Constants.CONFIG_PROP.setProperty("c-issuedId", String.valueOf(Integer.parseInt(ls.get(0).toString())+1));
            			else
            				Constants.CONFIG_PROP.setProperty("c-issuedId", String.valueOf(1));
                		if (mw != null) 
	    					mw.refreshProgressBar(85);
            			try {
            				Constants.CONFIG_PROP.store(new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file"))), new String("receivedId property updated"));
						} catch (FileNotFoundException e) {
							throw new ImportExportException(Constants.LANG.getString("NOOKErrorDuringExport"));
						} catch (IOException e) {
							throw new ImportExportException(Constants.LANG.getString("NOOKErrorDuringExport"));
						}
            			
            			// Finally, the old drafts are saved if the corresponding checkbox is selected
            			if (saveDrafts) {
        					for (int i = 0; i < v.size(); i++) {
        						v.get(i).getInvoices().getInvoice().get(0).getInvoiceHeader().setInvoiceNumber(Constants.CONFIG_PROP.getProperty("draftId"));
        						if (mw != null) 
    		    					mw.refreshProgressBar( (int)(i / (v.size() * 1.) * 15) + 85 );
        						try{
        							((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(vversion.get(i))+"Util").newInstance()).saveDraft(v.get(i), new ArrayList<AttachedDocument>(), false, null);
        						} catch (InstantiationException e) {
        							Constants.DIALOG.showErrorSaveDraft();
        						} catch (IllegalAccessException e) {
        							Constants.DIALOG.showErrorSaveDraft();
        						} catch (ClassNotFoundException e) {
        							Constants.DIALOG.showErrorSaveDraft();
        						} catch (ValidationException e) {
        							Constants.DIALOG.showErrorSaveDraft();
								}
        					}
        				}
            			
            			FacturaeManager.getInstance().saveAction(EnumOperationType.ImpBBDD, null); 
            			// The table content is removed
                    	int numRows = ((NoEdiTableModel)MainWindow.getInstance().getJTableInvoices().getModel()).getRowCount();
                    	for (int i = numRows; i > 0; i--)
                    		((NoEdiTableModel)MainWindow.getInstance().getJTableInvoices().getModel()).removeRow(i - 1);
            			MainWindow.getInstance().loadData();
                	}
    				else
    					throw new ImportExportException();
    			}
    			else
    				throw new ImportExportException(Constants.LANG.getString("NOOKMessageImportNoExtension"));
    		}
    		else
    			throw new ImportExportException(Constants.LANG.getString("NOOKMessageImportNoExist"));
    	}
    	else
    		throw new ImportExportException(Constants.LANG.getString("NOOKMessageImportEmptyFile"));
    }

    public static void exportDB(String exportFile) throws ImportExportException {
    	
    	// Before starting the data base is released (the log file is flushed)
		FacturaeManager.reset();
		FacturaeManager.getInstance().beginTransaction();
		FileOutputStream f = null;
		ZipOutputStream out = null;
		FileInputStream in = null;
		try {
			f = new FileOutputStream(exportFile);
			// Create a buffer for reading the files
			byte[] buf = new byte[1024];
			try {
				// Create the ZIP file
				out = new ZipOutputStream(f);
				// Compress the files
				for (int i = 0; i < filenames.length; i++) {
					in = new FileInputStream(filenames[i]);
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(filenames[i]));
					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0)
						out.write(buf, 0, len);

					// Complete the entry
					out.closeEntry();
					in.close();
				}
				// Complete the ZIP file
				out.close();
				try {
					FacturaeManager.getInstance().saveAction(EnumOperationType.ExpBBDD, null);
				} catch (DatabaseOperationException e) {
					// The user is not informed about action not saved event. An error log has been created previously.
				}
			} catch (IOException e) {
				throw new ImportExportException(Constants.LANG.getString("NOOKFileNotFoundExport"));
			} 
		} catch (FileNotFoundException e) {
			throw new ImportExportException(Constants.LANG.getString("NOOKErrorDuringExport"));
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException e) {}
				if (out != null)
					try {
						out.close();
					} catch (IOException e) {}
					if (in != null)
						try {
							in.close();
						} catch (IOException e) {}
		}
    }
}

