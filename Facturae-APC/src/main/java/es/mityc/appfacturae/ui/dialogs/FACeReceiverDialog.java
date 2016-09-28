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
package es.mityc.appfacturae.ui.dialogs;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.mityc.appfacturae.utils.constants.Constants;

public class FACeReceiverDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6691649045917435510L;

	private int returnOption = JOptionPane.NO_OPTION;
	
	public static int showFACeReceiverDialog(Frame f) {

		FACeReceiverDialog dialog = new FACeReceiverDialog(f, true);
		dialog.setVisible(true);
		return dialog.returnOption;
	}
	
	private FACeReceiverDialog (Frame f, boolean modal) {
		super(f, modal);
		initComponents();
	}
	
	private void initComponents() {
		panelPrin = new JPanel();
		jButtonYes = new JButton();
		jButtonNo = new JButton();
		
		jLabelMessage = new JLabel(Constants.LANG.getString("MsgIsFACeReceiver"));
	    jLabelMessage.setForeground(Constants.FONT_COLOR);
	    jLabelMessage.setFont(Constants.FONT_PLAIN);
	    jLabelMessage.setHorizontalAlignment(SwingConstants.LEFT);
		
	    jLabelLink = new JLabel(Constants.LANG.getString("LinkFACe"));
	    jLabelLink.setForeground(Constants.LINK_COLOR);
	    jLabelLink.setFont(Constants.FONT_LINK);
	    jLabelLink.setHorizontalAlignment(SwingConstants.LEFT);
	    jLabelLink.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent evt) {
        		jLabelLink.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent evt) {
        		jLabelLink.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	}
        	public void mouseReleased(MouseEvent e) {
        		jLabelLinkActionPerformed();
        	}
        	
        });	

	    jButtonYes.setBorderPainted(false);
	    jButtonYes.setContentAreaFilled(false);
	    jButtonYes.setHorizontalAlignment(SwingConstants.CENTER);
        jButtonYes.setIcon(new ImageIcon(getClass().getResource("/images/button_accept.jpg")));
        jButtonYes.setToolTipText(Constants.LANG.getString("Yes"));
        jButtonYes.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent evt) {
        		jButtonYes.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent evt) {
        		jButtonYes.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	}
        });	
        jButtonYes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		jButtonYesActionPerformed();
        	}
        });	

        jButtonNo.setBorderPainted(false);
        jButtonNo.setContentAreaFilled(false);
        jButtonNo.setHorizontalAlignment(SwingConstants.CENTER);
        jButtonNo.setIcon(new ImageIcon(getClass().getResource("/images/button_cancel.jpg")));
        jButtonNo.setToolTipText(Constants.LANG.getString("No"));
        jButtonNo.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent evt) {
        		jButtonNo.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent evt) {
        		jButtonNo.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	}
        });	
        jButtonNo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		jButtonNoActionPerformed();
        	}
        });	

	    
	    
		// The Main Panel is built
		panelPrin.setBackground(Constants.BKG_MAIN_COLOR);
		panelPrin.setLayout(new GridBagLayout());

	    GridBagConstraints jLabelMessageConstraints = new GridBagConstraints();
	    jLabelMessageConstraints.gridx = 0;
	    jLabelMessageConstraints.gridy = 0;
	    //jLabelMessageConstraints.fill = GridBagConstraints.BOTH;
	    jLabelMessageConstraints.gridwidth = 2;
	    jLabelMessageConstraints.gridheight = 2;
	    //jLabelMessageConstraints.weightx = 0.01;
	    jLabelMessageConstraints.anchor = GridBagConstraints.WEST;
	    jLabelMessageConstraints.insets = new Insets(3,3,6,10);
	    panelPrin.add(jLabelMessage, jLabelMessageConstraints);		

	    GridBagConstraints jLabelLinkConstraints = new GridBagConstraints();
	    jLabelLinkConstraints.gridx = 0;
	    jLabelLinkConstraints.gridy = 4;
	    //jLabelLinkConstraints.fill = GridBagConstraints.HORIZONTAL;
	    //jLabelLinkConstraints.weightx = 0.01;
	    jLabelLinkConstraints.gridwidth = 2;
	    jLabelLinkConstraints.anchor = GridBagConstraints.WEST;
	    jLabelLinkConstraints.insets = new Insets(3,3,6,10);
	    panelPrin.add(jLabelLink, jLabelLinkConstraints);		

	    GridBagConstraints jButtonYesConstraints = new GridBagConstraints();
	    jButtonYesConstraints.gridx = 0;
	    jButtonYesConstraints.gridy = 5;
	    //jButtonYesConstraints.weightx = 0.01;
	    jButtonYesConstraints.anchor = GridBagConstraints.EAST;
	    jButtonYesConstraints.insets = new Insets(3,3,3,3);
	    panelPrin.add(jButtonYes, jButtonYesConstraints);		

	    GridBagConstraints jButtonNoConstraints = new GridBagConstraints();
	    jButtonNoConstraints.gridx = 1;
	    jButtonNoConstraints.gridy = 5;
	    //jButtonNoConstraints.weightx = 0.01;
	    jButtonNoConstraints.anchor = GridBagConstraints.CENTER;
	    jButtonNoConstraints.insets = new Insets(3,3,3,10);
	    panelPrin.add(jButtonNo, jButtonNoConstraints);		

	    add(panelPrin);

        this.setSize(400, 300);
        this.setTitle(Constants.LANG.getString("NewReceiver"));
        this.setResizable(false);
        this.setLocationRelativeTo(null);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonNoActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
		
	}
	
	private void jButtonYesActionPerformed() {
		returnOption = JOptionPane.YES_OPTION;
		this.setVisible(false);
		this.dispose();
	}

	private void jButtonNoActionPerformed() {
		returnOption = JOptionPane.NO_OPTION;
		this.setVisible(false);
		this.dispose();
	}
	
	private void jLabelLinkActionPerformed() {
		try {
			Desktop.getDesktop().browse(new URI(Constants.FACE_URL));
        } catch (Exception ex) {
		}
	}
	
	private JLabel jLabelMessage = null;
	private JLabel jLabelLink = null;
	private JButton jButtonYes = null;
	private JButton jButtonNo = null;
	private JPanel panelPrin = null;
}
