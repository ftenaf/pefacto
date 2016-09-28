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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.StringUtil;

public class InputExtensionDialog extends JDialog {

	public InputExtensionDialog (Frame parent, boolean modal,String content,String error) {
		super(parent, modal);
	    init(content,error);
		setLocationRelativeTo(parent);
	}

	private void init(String content, String error) {
		mainPanel = new JPanel();
		jScroll = new JScrollPane();
		jScroll2 = new JScrollPane();
		jButtonReturn = new JButton();
		
		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);

		mainPanel.setLayout(new GridBagLayout());
		
		// Extension
		jLabelExtension = new JLabel(Constants.LANG.getString("Extension")+" (XML):");
		jLabelExtension.setForeground(Constants.FONT_COLOR);
	    jLabelExtension.setFont(Constants.FONT_PLAIN);
	    jLabelExtension.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelExtension.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints jLabelExtensionConstraints = new GridBagConstraints();
	    jLabelExtensionConstraints.gridx = 1;
	    jLabelExtensionConstraints.gridy = 2;
	    jLabelExtensionConstraints.weightx = 0.3;
	    jLabelExtensionConstraints.anchor = GridBagConstraints.WEST;
	    jLabelExtensionConstraints.insets = new Insets(10,10,10,10);
	    mainPanel.add(jLabelExtension, jLabelExtensionConstraints);
	    
	    // TextArea Error
		jTextAreaExtensionError = new JTextArea();
		jTextAreaExtensionError.setFont(Constants.FONT_PLAIN);
		jTextAreaExtensionError.setForeground(Constants.ERROR_MSG_COLOR);
		jTextAreaExtensionError.setBackground(Constants.BKG_MAIN_COLOR);
		jTextAreaExtensionError.setEditable(false);
		jTextAreaExtensionError.setText(StringUtil.wordWrap(error, 70));
		
		jTextAreaExtensionError.setWrapStyleWord(true);
		
		jScroll2.setViewportView(jTextAreaExtensionError);
		
		GridBagConstraints jTextAreaErrorConstraints = new GridBagConstraints();
		jTextAreaErrorConstraints.gridx = 1;
		jTextAreaErrorConstraints.gridy = 1;
		jTextAreaErrorConstraints.gridwidth = 3;
		jTextAreaErrorConstraints.weightx = 0.9;
		jTextAreaErrorConstraints.insets = new Insets(5,10,5,10);
		jTextAreaErrorConstraints.fill = GridBagConstraints.BOTH;
		jTextAreaErrorConstraints.ipady = 40;
		mainPanel.add(jScroll2, jTextAreaErrorConstraints);		
	    
	    // TextArea
		jTextAreaExtension = new JTextArea();
		jTextAreaExtension.setText(content);
		jTextAreaExtension.setFont(Constants.FONT_PLAIN);
		jTextAreaExtension.setForeground(Constants.FONT_COLOR);
		jTextAreaExtension.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
		
		jScroll.setViewportView(jTextAreaExtension);
		jScroll.setBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true));
		
		GridBagConstraints jTextAreaConstraints = new GridBagConstraints();
		jTextAreaConstraints.gridx = 1;
		jTextAreaConstraints.gridy = 3;
		jTextAreaConstraints.gridwidth = 3;
		jTextAreaConstraints.weightx = 0.9;
		jTextAreaConstraints.insets = new Insets(0,10,0,10);
		jTextAreaConstraints.fill = GridBagConstraints.BOTH;
		jTextAreaConstraints.ipady = 330;
		mainPanel.add(jScroll, jTextAreaConstraints);		
	    
		// JButton
		jButtonReturn.setBorderPainted(false);
		jButtonReturn.setContentAreaFilled(false);
		jButtonReturn.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg")));
		jButtonReturn.setToolTipText(Constants.LANG.getString("Return"));
		jButtonReturn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonReturn.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonReturn.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
		jButtonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonReturnActionPerformed();
			}
		});
		
		GridBagConstraints buttonReturnConstraints = new GridBagConstraints();
		buttonReturnConstraints.gridx = 3;
		buttonReturnConstraints.gridy = 4;
		buttonReturnConstraints.weightx = 0.2;
		buttonReturnConstraints.anchor = GridBagConstraints.EAST;
		buttonReturnConstraints.insets = new Insets(10,10,10,10);
		mainPanel.add(jButtonReturn, buttonReturnConstraints);
		
		add(mainPanel);
		
		setResizable(false);
		setSize(380, 550);
		setTitle(Constants.LANG.getString("Extension"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonReturnActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
	}
	
	private void jButtonReturnActionPerformed() {
		setVisible(false);
	}
	
	public String getValues() {
		return jTextAreaExtension.getText();
	}

	private JLabel jLabelExtension = null;
    
    private JTextArea jTextAreaExtension = null;
    
    private JPanel mainPanel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonReturn = null;
    
    private JScrollPane jScroll2 = null;
    private JTextArea jTextAreaExtensionError = null;
}
