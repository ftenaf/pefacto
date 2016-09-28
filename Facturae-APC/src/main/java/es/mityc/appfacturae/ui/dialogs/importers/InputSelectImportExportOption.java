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
package es.mityc.appfacturae.ui.dialogs.importers;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.utils.constants.Constants;

public class InputSelectImportExportOption extends javax.swing.JDialog {
    
    private static Log logger = LogFactory.getLog(InputSelectImportExportOption.class);
    
    /** Creates new form Presentation Selection Dialog */
    public InputSelectImportExportOption(JFrame f, boolean b) {
    	super(f,b);

    	initComponents();
    	this.setSize(390, 300);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jRadioButtonNone.setSelected(true);
        		setVisible(false);
        		dispose();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
    	this.setTitle(Constants.LANG.getString("ImportExportOptionSelectionTitle"));
    }
    
    
    private void initComponents() {

        buttonGroupFormat = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
       
        BufferedImage imgTopBar2 = null;
        try {
        	imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
        } catch (IOException ioe) {
        	logger.error("Error during image charge: " + ioe.getMessage());
        }
        
        jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
        jLabelTopBar1 = new javax.swing.JLabel();
        jLabelTopBar2 = new javax.swing.JLabel();
        jButtonContinue = new javax.swing.JButton();
        jPanelOptions = new javax.swing.JPanel();

        jRadioButtonImportInvoices = new javax.swing.JRadioButton();
        jRadioButtonImportDataBase = new javax.swing.JRadioButton();
        jRadioButtonExportInvoices = new javax.swing.JRadioButton();
        jRadioButtonExportDataBase = new javax.swing.JRadioButton();
        jRadioButtonNone = new javax.swing.JRadioButton();

        mainPanel.setBackground(Constants.BKG_MAIN_COLOR);

        jPanelTopBar.setBackground(Color.white);
        jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

        jLabelTopBar1.setFont(Constants.TITLE_FONT_ITALIC_LITTLE);
        jLabelTopBar1.setForeground(Constants.FONT_COLOR);
        jLabelTopBar1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBar1.setText(Constants.LANG.getString("TopBarMessage1")); 

        jLabelTopBar2.setFont(Constants.TITLE_FONT_ITALIC_LITTLE);
        jLabelTopBar2.setForeground(Constants.FONT_COLOR);
        jLabelTopBar2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBar2.setText(Constants.LANG.getString("TopBarMessage2")); 

        org.jdesktop.layout.GroupLayout jPanelTopBarLayout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
        jPanelTopBar.setLayout(jPanelTopBarLayout);
        jPanelTopBarLayout.setHorizontalGroup(
        		jPanelTopBarLayout.createSequentialGroup()
        			.addContainerGap()
        			.add(jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
        				.add(jLabelTopBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(jLabelTopBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.addContainerGap()
        );
        jPanelTopBarLayout.setVerticalGroup(
        		jPanelTopBarLayout.createSequentialGroup()
        				.add(5,5,5)
        				.add(jLabelTopBar1)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jLabelTopBar2)
        				.add(5,5,5)
        );

        jButtonContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
        jButtonContinue.setFont(Constants.TITLE_FONT);
        jButtonContinue.setForeground(Constants.FONT_COLOR);
        jButtonContinue.setText(Constants.LANG.getString("Continue")); 
        jButtonContinue.setToolTipText(Constants.LANG.getString("Continue")); 
        jButtonContinue.setBorderPainted(false);
        jButtonContinue.setContentAreaFilled(false);
        jButtonContinue.setDefaultCapable(true);
        jButtonContinue.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		dispose();
        	}
        });
       jButtonContinue.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseEntered(java.awt.event.MouseEvent evt) {
        		InputSelectImportExportOption.this.mouseEntered(evt);
        	}
        	public void mouseExited(java.awt.event.MouseEvent evt) {
        		InputSelectImportExportOption.this.mouseExited(evt);
        	}
        });

        jRadioButtonImportInvoices.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioButtonImportInvoices.setSelected(true);
        buttonGroupFormat.add(jRadioButtonImportInvoices);
        jRadioButtonImportInvoices.setText(Constants.LANG.getString("ImportInvoices"));

        jRadioButtonImportDataBase.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonImportDataBase);
        jRadioButtonImportDataBase.setText(Constants.LANG.getString("ImportDB"));
        
        jRadioButtonExportInvoices.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonExportInvoices);
        jRadioButtonExportInvoices.setText(Constants.LANG.getString("ExportInvoices"));
        
        jRadioButtonExportDataBase.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonExportDataBase);
        jRadioButtonExportDataBase.setText(Constants.LANG.getString("ExportDB"));

        buttonGroupFormat.add(jRadioButtonNone);
        
        org.jdesktop.layout.GroupLayout jPanelFormatLayout = new org.jdesktop.layout.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelFormatLayout);
        jPanelFormatLayout.setHorizontalGroup(
        		jPanelFormatLayout.createSequentialGroup()
        		.addContainerGap()
        		.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        				.add(jRadioButtonImportInvoices)
        				.add(jRadioButtonExportInvoices)
        				.add(jRadioButtonImportDataBase)
        				.add(jRadioButtonExportDataBase))
        				.addContainerGap()
        );
        jPanelFormatLayout.setVerticalGroup(
        		jPanelFormatLayout.createSequentialGroup()
        		.addContainerGap()
        		.add(jRadioButtonImportInvoices)
        		.add(jRadioButtonImportDataBase)
        		.add(jRadioButtonExportInvoices)
        		.add(jRadioButtonExportDataBase)
        		.addContainerGap()
        );
        
        jPanelOptions.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("PresentationLanguage"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
        		mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(mainPanelLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        					.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 365, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(jPanelOptions, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(jButtonContinue)
        				)
        				.addContainerGap()
        		)
        );
        mainPanelLayout.setVerticalGroup(
        		mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(mainPanelLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jPanelOptions, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jButtonContinue)
        				.addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
        		layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 390, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
        		layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    public int getSelectedOption() {  
    	if (jRadioButtonNone.isSelected()) {
    		return -1;
    	} else if (jRadioButtonImportInvoices.isSelected()) {
    		return 0;
    	} else if(jRadioButtonImportDataBase.isSelected()) {
    		return 1;
    	} else if(jRadioButtonExportInvoices.isSelected()) {
    		return 2;
		} else if(jRadioButtonExportDataBase.isSelected()) {
			return 3;
		}
		return -1;
    }                                        

	private void mouseEntered(java.awt.event.MouseEvent evt) {                                      
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }                                     

    private void mouseExited(java.awt.event.MouseEvent evt) {                                     
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }            
    
    private javax.swing.ButtonGroup buttonGroupFormat;
    private javax.swing.JButton jButtonContinue;
    
    private javax.swing.JLabel jLabelTopBar1;
    private javax.swing.JLabel jLabelTopBar2;
    
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel jPanelTopBar;
    private javax.swing.JPanel jPanelOptions;
    
    private javax.swing.JRadioButton jRadioButtonImportInvoices;
    private javax.swing.JRadioButton jRadioButtonImportDataBase;
    private javax.swing.JRadioButton jRadioButtonExportInvoices;
    private javax.swing.JRadioButton jRadioButtonExportDataBase;
    private javax.swing.JRadioButton jRadioButtonNone;
}