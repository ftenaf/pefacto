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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.mityc.appfacturae.facturae.AttachmentFormatType;
import es.mityc.appfacturae.ui.components.CustomFileFilter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FileUtil;

public class InputAttachmentDialog extends JDialog{
	private static final long serialVersionUID = 809480790679404863L;

	private FadingCanvas fd = null;
	private ArrayList<String> prevDesc = null;
	private int includedFilesSize = 0;
	
	// Singelton pattern
	private static InputAttachmentDialog iad = null;
	
	/**
	 * Window form for insert a new attached file 
	 * @param parent .- Parent window for set the screen position
	 * @param prevDesc .- All previous description for this invoice
	 * @param modal .- Set window modal
	 */
	public static InputAttachmentDialog getInstance(Frame parent, ArrayList<String> prevDesc, boolean modal, int includedFilesSize) {
		if (iad == null)
			iad = new InputAttachmentDialog(parent, prevDesc, modal, includedFilesSize);
		
		return iad;
	}
	
	private InputAttachmentDialog (Frame parent, ArrayList<String> prevDesc, boolean modal, int includedFilesSize) {
		super(parent, modal);
	        
	    this.prevDesc = prevDesc;
	    this.includedFilesSize = includedFilesSize;
		init();
		setLocationRelativeTo(parent);
	}

	private void init() {
		panelPrin = new JPanel();
		panel = new JPanel();
		jScroll = new JScrollPane();
		jButtonOk = new JButton();
		jButtonCancel = new JButton();
		fd = new FadingCanvas();
		
		panelPrin.setBackground(Constants.BKG_MAIN_COLOR);
		panel.setBackground(Constants.BKG_MAIN_COLOR);
		panel.setLayout(new GridBagLayout());

		jButtonOk.setBorderPainted(false);
		jButtonOk.setContentAreaFilled(false);
		jButtonOk.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonOk.setIcon(new ImageIcon(getClass().getResource("/images/button_accept.jpg")));
		jButtonOk.setToolTipText(Constants.LANG.getString("Accept"));
		jButtonOk.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonOk.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonOk.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
		jButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean flag = false;

				if (!Constants.ERROR_MSG_COLOR.equals(jTextFieldDescription.getForeground())) {
	
					if ( jComboBoxFormat.getSelectedItem() == null || 
							"".equals(jComboBoxFormat.getSelectedItem().toString().trim()) ) {
						flag = true;
						jComboBoxFormat.requestFocus();  
						jComboBoxFormat.setBackground(Constants.BKG_ERROR_COLOR);						
					}					
					if ( jTextFieldDocument.getText() == null || 
							"".equals(jTextFieldDocument.getText().trim()) ) {
						flag = true;
						jTextFieldDocument.requestFocus();  
						jTextFieldDocument.setBackground(Constants.BKG_ERROR_COLOR);
					}

					if ( jTextFieldDescription.getText() == null || 
							"".equals(jTextFieldDescription.getText().trim()) ) {
						flag = true;
						jTextFieldDescription.requestFocus();  
						jTextFieldDescription.setBackground(Constants.BKG_ERROR_COLOR);						
					}
					
				} else {
					jTextFieldDescription.setForeground(Constants.FONT_COLOR);
					return;
				}

				if (flag) {
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
					return;
				} else {
					iad.setVisible(false);
					iad.dispose();
					iad = null;
				}
			}
		});

		jButtonCancel.setBorderPainted(false);
		jButtonCancel.setContentAreaFilled(false);
		jButtonCancel.setIcon(new ImageIcon(getClass().getResource("/images/button_cancel.jpg")));
		jButtonCancel.setToolTipText(Constants.LANG.getString("Cancel"));
		jButtonCancel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonCancel.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonCancel.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
		    	jButtonCancelActionPerformed();
			}
		});
				
		jScroll.setViewportView(panel);
		jScroll.setBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true));
		
		jLabelFormat = new JLabel("* "+Constants.LANG.getString("Format"));
	    GridBagConstraints jLabelFormatConstraints = new GridBagConstraints();
	    jLabelFormatConstraints.gridx = 1;
	    jLabelFormatConstraints.gridy = 1;
	    jLabelFormatConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelFormatConstraints.weightx = 0.01;
	    jLabelFormatConstraints.insets = new Insets(0,3,0,10);
	    jLabelFormat.setForeground(Constants.FONT_COLOR);
	    jLabelFormat.setFont(Constants.FONT_PLAIN);
	    jLabelFormat.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelFormat, jLabelFormatConstraints);
		
		jComboBoxFormat = new JComboBox();
		jComboBoxFormat.setBackground(Color.white);
		jComboBoxFormat.setModel(new javax.swing.DefaultComboBoxModel(AttachmentFormatType.values()));
        jComboBoxFormat.setBounds(200, 70, 80, 20);
        jComboBoxFormat.setSelectedIndex(0);
        jComboBoxFormat.setForeground(Constants.FONT_COLOR);
        jComboBoxFormat.setFont(Constants.FONT_PLAIN);
        jComboBoxFormat.setMaximumRowCount(4);
        this.add(jComboBoxFormat);
		jComboBoxFormat.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints jComboBoxFormatConstraints = new GridBagConstraints();
		jComboBoxFormatConstraints.gridx = 2;
		jComboBoxFormatConstraints.gridy = 1;
		jComboBoxFormatConstraints.fill = GridBagConstraints.HORIZONTAL;
		jComboBoxFormatConstraints.weightx = 1.0;
		jComboBoxFormatConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboBoxFormat, jComboBoxFormatConstraints);
		
		
		jLabelPath = new JLabel("* "+Constants.LANG.getString("Document"));
	    GridBagConstraints jLabelPathConstraints = new GridBagConstraints();
	    jLabelPathConstraints.gridx = 1;
	    jLabelPathConstraints.gridy = 2;
	    jLabelPathConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelPathConstraints.weightx = 0.01;
	    jLabelPathConstraints.insets = new Insets(0,3,0,10);
	    jLabelPath.setForeground(Constants.FONT_COLOR);
	    jLabelPath.setFont(Constants.FONT_PLAIN);
	    jLabelPath.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelPath.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelPath, jLabelPathConstraints);
	    	    
		
		jTextFieldDocument=new JTextField(10);
		jTextFieldDocument.setForeground(Constants.FONT_COLOR);
		jTextFieldDocument.setFont(Constants.FONT_PLAIN);
		jTextFieldDocument.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldDocument.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints jTextFieldDocumentConstraints = new GridBagConstraints();
		jTextFieldDocumentConstraints.gridx = 2;
		jTextFieldDocumentConstraints.gridy = 2;
		jTextFieldDocumentConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldDocumentConstraints.weightx = 1.0;
		jTextFieldDocumentConstraints.insets = new Insets(0,0,6,5);
		panel.add(jTextFieldDocument, jTextFieldDocumentConstraints);
		
		
		jButtonFileChooser=new JButton();
		jButtonFileChooser.setBorderPainted(false);
		jButtonFileChooser.setContentAreaFilled(false);
		jButtonFileChooser.setIcon(new ImageIcon(getClass().getResource("/images/button_middle_folder.jpg")));
		jButtonFileChooser.setToolTipText(Constants.LANG.getString("Examine"));
		jButtonFileChooser.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonFileChooser.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonFileChooser.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				fc = new JFileChooser();
				CustomFileFilter filter = new CustomFileFilter();
		        filter.addExtension(jComboBoxFormat.getSelectedItem().toString());
		        
		        if("xml".equalsIgnoreCase(jComboBoxFormat.getSelectedItem().toString())){
		        	filter.addExtension("xsig");
		        }
		        
		        filter.setDescription(Constants.LANG.getString("InvoicesWithExtension"));
		        fc.setFileFilter(filter);

		        int returnVal = fc.showOpenDialog(iad);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        		
		        		jTextFieldDocument.setBackground(Color.white);
		                File file = fc.getSelectedFile();
		                extension = FileUtil.getExtension(file);
		                if (extension.equalsIgnoreCase(jComboBoxFormat.getSelectedItem().toString()) || ("xml".equalsIgnoreCase(jComboBoxFormat.getSelectedItem().toString()) && extension.equalsIgnoreCase("xsig"))) {
		                	jTextFieldDocument.setText(file.getAbsolutePath());
		                	jTextFieldDocument.setForeground(Constants.FONT_COLOR);
		                	jTextFieldDocument.setFont(Constants.FONT_PLAIN);
		                }
		                
		                // Included data length may not exceed "Constants.AttIncludedLimit" Mb
		                if (includedFilesSize + file.length() > (Constants.AttIncludedLimit)) {
		                	//jCheckBoxIncludingIntoXML.setState(false);
		                	//jCheckBoxIncludingIntoXML.setEnabled(false);
		                	Thread th = new Thread(new Runnable() { public void run() {
		                		try {
									Thread.sleep(100);
								} catch (InterruptedException e) {}
		                		fd.showMessage(Constants.LANG.getString("MessageIncludeAttachHigher"), Constants.ERROR_MSG_COLOR);
		                	}});
		                	th.start();
		                }

		        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
		        	jTextFieldDocument.setText("");
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					jTextFieldDocument.setText("");
				} 
				
		    	jButtonFileChooser.getTopLevelAncestor().setVisible(true);
			}
		});
		
		GridBagConstraints jButtonFileChooserConstraints = new GridBagConstraints();
		jButtonFileChooserConstraints.gridx = 3;
		jButtonFileChooserConstraints.gridy = 2;
		jButtonFileChooserConstraints.fill = GridBagConstraints.HORIZONTAL;
		jButtonFileChooserConstraints.weightx = 0.01;
		jButtonFileChooserConstraints.insets = new Insets(0,0,6,5);
		panel.add(jButtonFileChooser, jButtonFileChooserConstraints);
		

		jLabelDocument = new JLabel("* "+Constants.LANG.getString("Description"));
	    GridBagConstraints jLabelDocumentConstraints = new GridBagConstraints();
	    jLabelDocumentConstraints.gridx = 1;
	    jLabelDocumentConstraints.gridy = 3;
	    jLabelDocumentConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelDocumentConstraints.weightx = 0.01;
	    jLabelDocumentConstraints.insets = new Insets(0,3,0,10);
	    jLabelDocument.setForeground(Constants.FONT_COLOR);
	    jLabelDocument.setFont(Constants.FONT_PLAIN);
	    jLabelDocument.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelDocument.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelDocument, jLabelDocumentConstraints);

		
		jTextFieldDescription = new JTextField();
		jTextFieldDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldDescription.setForeground(Constants.FONT_COLOR);
		jTextFieldDescription.setFont(Constants.FONT_PLAIN);
		jTextFieldDescription.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);			
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});
		GridBagConstraints jTextFieldDescriptionConstraints = new GridBagConstraints();
		jTextFieldDescriptionConstraints.gridx = 2;
		jTextFieldDescriptionConstraints.gridy = 3;
		jTextFieldDescriptionConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldDescriptionConstraints.weightx = 1.0;
		jTextFieldDescriptionConstraints.insets = new Insets(0,0,6,5);
		DescriptionValidator av = new DescriptionValidator();
		jTextFieldDescription.addFocusListener(av);
		panel.add(jTextFieldDescription, jTextFieldDescriptionConstraints);
		
		GridBagConstraints jCheckBoxIncludingIntoXMLConstraints = new GridBagConstraints();
		jCheckBoxIncludingIntoXMLConstraints.gridx = 2;
		jCheckBoxIncludingIntoXMLConstraints.gridy = 4;
		jCheckBoxIncludingIntoXMLConstraints.gridwidth = 2;
		jCheckBoxIncludingIntoXMLConstraints.fill = GridBagConstraints.HORIZONTAL;
		jCheckBoxIncludingIntoXMLConstraints.weightx = 1;
		jCheckBoxIncludingIntoXMLConstraints.insets = new Insets(0,0,6,5);
		
		
		jTextAreaIncludingInfo = new JTextArea();
		jTextAreaIncludingInfo.setSize(100, 20);
		jTextAreaIncludingInfo.setText(Constants.LANG.getString("MessageIncludeAttachmentXML"));
		jTextAreaIncludingInfo.setFont(Constants.FONT_ITALIC);
		jTextAreaIncludingInfo.setForeground(Constants.FONT_COLOR);
		jTextAreaIncludingInfo.setEditable(false);
		jTextAreaIncludingInfo.setBackground(Constants.BKG_MAIN_COLOR);
		jTextAreaIncludingInfo.setFocusable(false);
		jTextAreaIncludingInfo.setVisible(true);

		GridBagConstraints jTextAreaIncludingInfoConstraints = new GridBagConstraints();
		jTextAreaIncludingInfoConstraints.gridx = 2;
		jTextAreaIncludingInfoConstraints.gridy = 5;
		jTextAreaIncludingInfoConstraints.gridwidth = 4;
		jTextAreaIncludingInfoConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextAreaIncludingInfoConstraints.weightx = 1;
		jTextAreaIncludingInfoConstraints.insets = new Insets(0,0,6,5);
		panel.add(jTextAreaIncludingInfo, jTextAreaIncludingInfoConstraints);
		
		// Canvas Panel is built
		fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
		
		// The Main Panel is built
		panelPrin.setLayout(new GridBagLayout());
		
		GridBagConstraints canvasConstraints = new GridBagConstraints();
		canvasConstraints.gridx = 0;
		canvasConstraints.gridy = 0;
		canvasConstraints.fill = GridBagConstraints.HORIZONTAL;
		canvasConstraints.weightx = 1.0;
		canvasConstraints.ipady = 10;
		canvasConstraints.gridwidth = 4;
		canvasConstraints.insets = new Insets(0,2,0,2);
		canvasConstraints.anchor = GridBagConstraints.CENTER;
		panelPrin.add(fd, canvasConstraints);
		
		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 1;
		panelConstraints.fill = GridBagConstraints.BOTH;
		panelConstraints.weightx = 1.0;
		panelConstraints.weighty = 1.0;
		panelConstraints.gridwidth = 4;
		panelConstraints.insets = new Insets(8,10,10,10);
		panelPrin.add(jScroll, panelConstraints);
		
		JPanel relleno = new JPanel();
		relleno.setBackground(panelPrin.getBackground());
		GridBagConstraints rellenoConstraints = new GridBagConstraints();
		rellenoConstraints.gridx = 0;
		rellenoConstraints.gridy = 2;
		rellenoConstraints.fill = GridBagConstraints.HORIZONTAL;
		rellenoConstraints.weightx = 0.3;
		panelPrin.add(relleno, rellenoConstraints);
		
		GridBagConstraints buttonAceptConstraints = new GridBagConstraints();
		buttonAceptConstraints.gridx = 1;
		buttonAceptConstraints.gridy = 2;
		buttonAceptConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonAceptConstraints.weightx = 0.2;
		buttonAceptConstraints.insets = new Insets(1,1,8,1);
		panelPrin.add(jButtonOk, buttonAceptConstraints);
		
		GridBagConstraints buttonCancelConstraints = new GridBagConstraints();
		buttonCancelConstraints.gridx = 2;
		buttonCancelConstraints.gridy = 2;
		buttonCancelConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonCancelConstraints.weightx = 0.2;
		buttonCancelConstraints.insets = new Insets(1,1,8,1);
		panelPrin.add(jButtonCancel, buttonCancelConstraints);

		JPanel relleno2 = new JPanel();
		relleno2.setBackground(panelPrin.getBackground());
		rellenoConstraints.gridx = 3;
		panelPrin.add(relleno2, rellenoConstraints);
		
		add(panelPrin);
		
		setResizable(false);
		setSize(390, 335);
		fd.setSize(getWidth(), 13);
		setTitle(Constants.LANG.getString("AttachedAddTitle"));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonCancelActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
	}
	
	private void jButtonCancelActionPerformed() {
		jComboBoxFormat.setSelectedItem(null);
    	jTextFieldDocument.setText(null);
    	jTextFieldDescription.setText(null);
		iad.setVisible(false);
		iad.dispose();
		iad = null;
	}
	
	public String[] getValues() {
		
		String[] result = new String[4];		
		
		if (jComboBoxFormat.getSelectedItem() == null)
			return result;
		
		result[0] = jComboBoxFormat.getSelectedItem().toString();
		if(extension.equalsIgnoreCase("xsig"))
			result[0] ="XSIG";
			
    	result[1] = jTextFieldDocument.getText();
    	
    	if (result[1] == null || "".equals(result[1].trim()))
    		return null;
    	
    	result[2] = jTextFieldDescription.getText();
   		result[3] = Constants.LANG.getString("Yes");
		
		return result;
	}
	
	private class DescriptionValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String value = ((JTextField)evt.getSource()).getText();
			if (value == null || "".equals(value))
				return;
			// Description may be a 2500 character length max.
			if (value.length() > 2500){
				((JTextField)evt.getSource()).setText("");
				fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
				return;
			}
			// Description may be unique (for this invoice)
			for (int i = 0; i < prevDesc.size(); ++i) {
				if (value.equals(prevDesc.get(i))) {
					// If the element is contained in previous description added to JTable, it already exists
					((JTextField)evt.getSource()).setText("");
					((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
					fd.showMessage(Constants.LANG.getString("NOOKParamAlreadyExists"), Constants.ERROR_MSG_COLOR);
				}
			}
		}
	}	

    private JLabel jLabelFormat = null;
    private JLabel jLabelPath = null;
    private JLabel jLabelDocument = null;

    private JComboBox jComboBoxFormat = null;
    
    private JTextField jTextFieldDocument = null;
    private JTextField jTextFieldDescription = null;
    private JFileChooser fc = null;
    
    private JTextArea jTextAreaIncludingInfo = null;
    
    private JPanel panelPrin = null;
    private JPanel panel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
    private JButton jButtonFileChooser=null;
    private String extension=null;
	
}
