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
package es.mityc.appfacturae.ui.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.StringUtil;

public class GUIUtils {
	
	int wordWrap = Integer.parseInt(Constants.APP_PROP.getProperty("word_wrap"));
	JOptionPane optionPane = null;
	JDialog dialog = null;
	
	private void initErrorOptionPane(String msg){
		optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
	    optionPane.setOptions(new Object[] { getButtonOK(optionPane,"",Constants.OK_ICON) });
	    optionPane.setMessage(msg);
	}
	
	private void initInfoOptionPane(String msg){
		optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
	    optionPane.setOptions(new Object[] { getButtonOK(optionPane,"",Constants.OK_ICON) });
	    optionPane.setMessage(msg);
	}
	
	private void initWarningOptionPane(String msg){
		optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
	    optionPane.setOptions(new Object[] { getButtonOK(optionPane,"",Constants.OK_ICON) });
	    optionPane.setMessage(msg);
	}
	
	private void initConfirmOptionPane(String msg){
		optionPane = new JOptionPane();
		optionPane.setOptionType(JOptionPane.YES_NO_OPTION);
	    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
	    optionPane.setOptions(new Object[] { getButtonOK(optionPane,"",Constants.OK_ICON), getButtonCancel(optionPane,"",Constants.CANCEL_ICON) });
		optionPane.setMessage(msg);
	}
	
	private void showDialog(String title){
		JFrame frame = new JFrame();
	    dialog = optionPane.createDialog(frame, title);
	    dialog.setVisible(true);
	}
	
	// Error dialogs
	
	public void showErrorDuplicatedInvoice() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("ChangeConfigurationMessage"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorSignProccess(String msg) {
		if ("".equals(msg))
	    	initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMesaggeSigning"), wordWrap));
		else
			initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMesagge") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}

	public void showErrorSave(String msg) {
		if ("".equals(msg))
			initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageSave"), wordWrap));
		else
			initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMesagge") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}

	public void showErrorSaveDraft() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageSavingDraft"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorSaveDraft(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageSavingDraft")+ ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorDelete() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageDelete"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorSaveAttachment() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMesaggeAttachAdded"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorDeleteAttachment() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMesaggeAttachDeleted"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorSend(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("SendUtilException") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorImport(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageImportProcess") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorExport(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageExportProcess") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }

	public void showErrorSeeCorrections() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageSeeCorrections"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorSeeCorrections(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageSeeCorrections")+": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorChargeInvoice(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageChargeInvoice") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorConfigurationSafe(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("ConfigurationWindowException5") + " " + msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorSeeXML() {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageSeeXMLError"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
    }
	
	public void showErrorHelp(){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("HelpWindowException"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorAttachmentSize(){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("MessageIncludeAttachTooManyData"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));	
	}
	
	public void showErrorDataBaseBusy(JFrame frame){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("MainWindowException4"), wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showErrorGeneratingInvoice(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOOKMessageGeneratingInvoice")+": "+msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	// Error dialogs - store selection
	
	public void showErrorConfJavaStore(Component jd){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.JavaMsg3"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showErrorOpeningFile(String fichAyudaPerfil, String message){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.MozillaMsg2") + " " + fichAyudaPerfil + "\n" + Constants.LANG.getString("Store.MozillaMsg3") + " " + message, wordWrap));
		showDialog(Constants.LANG.getString("Store.MozillaMsg4") + " " + fichAyudaPerfil);
	}
	
	public void showErrorStoreNotSupported(Component jd){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.StoreNotSupported"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showErrorStoreNoInicialize(Component jp){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.NoInicializeMsg"), wordWrap));
		showDialog(Constants.LANG.getString("Store.ParamsNeeded"));
	}
	
	public void showErrorAssPrivateKeyNeeded(Component jp){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.AssPrivateKeyNeeded"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showErrorCertEncodingError(Component jtp){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.CertEncodingError"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showErrorStoreFileNotFound(Component jtp){
		initErrorOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.FileNotFound"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}

	public void showErrorFACe(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));		
	}
    
	// OK dialogs

	public void showOKExport(String msg) {
		initInfoOptionPane(StringUtil.wordWrap(Constants.LANG.getString("OKExportProcess") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("OK"));
    }
	
	public void showOKImport(String msg) {
		initInfoOptionPane(StringUtil.wordWrap(Constants.LANG.getString("OKImportProcess") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("OK"));
    }
	
	public void showOKSave(String msg) {
		if ("".equals(msg))
			initInfoOptionPane(StringUtil.wordWrap(Constants.LANG.getString("OKMessageSave"), wordWrap));
		else
			initInfoOptionPane(StringUtil.wordWrap(Constants.LANG.getString("OKMessageSave") + ": " + msg, wordWrap));
		showDialog(Constants.LANG.getString("OK"));
    }	

	public void showError(String msg) {
		initErrorOptionPane(StringUtil.wordWrap(msg, wordWrap));
		showDialog(Constants.LANG.getString("Error"));
	}
	
	public void showInfo(String msg) {
		initInfoOptionPane(StringUtil.wordWrap(msg, wordWrap));
		showDialog(Constants.LANG.getString("Information"));
	}

	// Warning dialogs
	
	public void showWarnLanguage(){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("MsgResetApp"), wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
	}
	
	public void showWarnNoCertificates(){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("NOCertsForSign"), wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
	}
	
	public void showWarnDraftCreated(){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("DraftCreated"), wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
	}
	
	// Warning dialogs - store selection
	
	public void showWarnProtectedWithPass(Component jp, String message){
		initWarningOptionPane(StringUtil.wordWrap(message, wordWrap));
		showDialog(Constants.LANG.getString("Store.PwdProtect"));
	}
	
	public void showWarnInvalidPass(Component jp){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.InvalidPwd"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showWarnEmptyContainer(Component jp){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.EmptyP12Container"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showWarnInvalidPass2(Component jp){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.InvalidPwd2"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showWarnExpiredCert(Component jp){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.ExpiredCert"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	public void showWarnYetNotValidCert(Component jp){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.YetNotValidCert"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Error"));
	}
	
	public void showWarnConfigFileRequired(Component jd){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.JavaMsg6"), wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
	}
	
	public void showWarnGeneratingInvoice(){
		initWarningOptionPane(StringUtil.wordWrap(Constants.LANG.getString("ErrorGeneratingInvoice"), wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
	}
	
	// Confirm dialogs
	
	public int showConfirmJavaStoreUsed(Component jd, String storeURL){
		initConfirmOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.JavaMsg5.1")+storeURL+Constants.LANG.getString("Store.JavaMsg5.2"),wordWrap));
		showDialog(Constants.LANG.getString("Store.Warning"));
		int result = JOptionPane.CLOSED_OPTION;
		try{
			if (optionPane.getValue() != null)
				result = Integer.valueOf(optionPane.getValue().toString());
		}
		catch(NumberFormatException e){}
	    return result;
	}
	
	public int showConfirmCreateConfigFile(Component jd){
		initConfirmOptionPane(StringUtil.wordWrap(Constants.LANG.getString("Store.JavaMsg8"), wordWrap));
		showDialog(Constants.LANG.getString("Store.Warning"));
		int result = JOptionPane.CLOSED_OPTION;
		try{
			if (optionPane.getValue() != null)
				result = Integer.valueOf(optionPane.getValue().toString());
		}
		catch(NumberFormatException e){}
	    return result;
	}
	
	public int showConfirmSendToFACe() {
		initConfirmOptionPane(StringUtil.wordWrap(Constants.LANG.getString("MsgConfirmFACeSend"), wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
		int result = JOptionPane.CLOSED_OPTION;
		try{
			if (optionPane.getValue() != null)
				result = Integer.valueOf(optionPane.getValue().toString());
		}
		catch(NumberFormatException e){}
	    return result;
	}
	
	public int showConfirmFileExists(String fileName) {
		if ("".equals(fileName))
			initConfirmOptionPane(StringUtil.wordWrap(Constants.LANG.getString("MsgConfirmOvewriteFile"), wordWrap));
		else
			initConfirmOptionPane(StringUtil.wordWrap(Constants.LANG.getString("MsgConfirmOvewriteFile") + ": "
				+ fileName, wordWrap));
		showDialog(Constants.LANG.getString("Store.Warning"));
		int result = JOptionPane.CLOSED_OPTION;
		try {
			if (optionPane.getValue() != null)
				result = Integer.valueOf(optionPane.getValue().toString());
		} catch (NumberFormatException e) {
		}
		return result;
	}
	
	public int showConfirmDeleteInvoice(String idInvoice) {
		String msg = MessageFormat.format(Constants.LANG.getString("MsgCheckDeleteInvoice"), idInvoice);
		initConfirmOptionPane(StringUtil.wordWrap(msg, wordWrap));
		showDialog(Constants.LANG.getString("Warning"));
		int result = JOptionPane.CLOSED_OPTION;
		try {
			if (optionPane.getValue() != null)
				result = Integer.valueOf(optionPane.getValue().toString());
		} catch (NumberFormatException e) {
		}
	    return result;
	}	
	
	// Close app
	
	public int showCloseAppDialog(){
		initConfirmOptionPane(Constants.LANG.getString("MsgConfirmExit"));
		showDialog(Constants.LANG.getString("Exit"));
		int result = JOptionPane.CLOSED_OPTION;
		try{
			result = Integer.valueOf(optionPane.getValue().toString());
		}
		catch(NumberFormatException e){}
	    return result;
	}
	
	
	private JButton createButton(String text, Icon icon){
		 JButton b = new JButton(text, icon);
		 b.setBorderPainted(false);
		 b.setContentAreaFilled(false);
		 b.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseEntered(java.awt.event.MouseEvent evt) {
				 GUIUtils.this.mouseEntered(evt);
			 }
			 public void mouseExited(java.awt.event.MouseEvent evt) {
				 GUIUtils.this.mouseExited(evt);
			 }
		 });
		 return b;
	}
	
	public JButton getButtonOK(JOptionPane jop, String text, Icon icon) {
		final JButton button = createButton(text, icon);
	    ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
	    	((JDialog)((JButton)actionEvent.getSource()).getTopLevelAncestor()).dispose();
	    	((JOptionPane)((JButton)actionEvent.getSource()).getParent().getParent()).setValue(JOptionPane.OK_OPTION);
	      }
	    };
	    button.addActionListener(actionListener);
	    button.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseEntered(java.awt.event.MouseEvent evt) {
				 GUIUtils.this.mouseEntered(evt);
			 }
			 public void mouseExited(java.awt.event.MouseEvent evt) {
				 GUIUtils.this.mouseExited(evt);
			 }
		 });
	    return button;
	}
	
	public JButton getButtonCancel(JOptionPane jop, String text, Icon icon) {
		final JButton button = createButton(text, icon);
	    ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
	        ((JDialog)((JButton)actionEvent.getSource()).getTopLevelAncestor()).dispose();
	    	((JOptionPane)((JButton)actionEvent.getSource()).getParent().getParent()).setValue(JOptionPane.CANCEL_OPTION);
	      }
	    };
	    button.addActionListener(actionListener);
	    button.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseEntered(java.awt.event.MouseEvent evt) {
				 GUIUtils.this.mouseEntered(evt);
			 }
			 public void mouseExited(java.awt.event.MouseEvent evt) {
				 GUIUtils.this.mouseExited(evt);
			 }
		 });
	    return button;
	}
	
	private void mouseEntered(java.awt.event.MouseEvent evt) {
		dialog.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
    	dialog.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

}
