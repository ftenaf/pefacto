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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import es.mityc.appfacturae.facturae.LanguageCodeType;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.windows.ReceiveInvoiceWindow;
import es.mityc.appfacturae.utils.constants.Constants;

public class InputLanguageDialog extends javax.swing.JDialog {

    
    private LanguageCodeType language = null;
    
    /** Creates new form Presentation Selection Dialog */
    public InputLanguageDialog(JFrame f, boolean b) {
    	super(f,b);
    	if (Constants.LANG == null)
    		Constants.LANG = ResourceBundle.getBundle("language/lang", new Locale("es"));
    	
    	initComponents();
    	this.setSize(390, 300);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    	this.setTitle(Constants.LANG.getString("PresentationLanguageSelection"));
    }
    
    
    private void initComponents() {

        buttonGroupFormat = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
       
        BufferedImage imgTopBar2 = null, imgSpanishFlag = null, imgCatalanFlag = null, imgBasqueFlag = null, imgGalicianFlag = null, imgEnglishFlag = null;
        try {
        	imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
        	imgSpanishFlag = ImageIO.read(getClass().getResourceAsStream("/images/spanishFlag.jpg"));
        	imgCatalanFlag = ImageIO.read(getClass().getResourceAsStream("/images/catalanFlag.jpg"));
        	imgBasqueFlag = ImageIO.read(getClass().getResourceAsStream("/images/basqueFlag.jpg"));
        	imgGalicianFlag = ImageIO.read(getClass().getResourceAsStream("/images/galicianFlag.jpg"));
        	imgEnglishFlag = ImageIO.read(getClass().getResourceAsStream("/images/englishFlag.jpg"));
        } catch (IOException ioe) {
        	Logger.getLogger(ReceiveInvoiceWindow.class.getName()).log(Level.SEVERE, null, ioe);
        }
        
        jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
        jLabelTopBar1 = new javax.swing.JLabel();
        jLabelTopBar2 = new javax.swing.JLabel();
        jPanelSpanish = new PicturedPanel(imgSpanishFlag, 1.00f);
        jPanelCatalan = new PicturedPanel(imgCatalanFlag, 1.00f);
        jPanelBasque = new PicturedPanel(imgBasqueFlag, 1.00f);
        jPanelGalician = new PicturedPanel(imgGalicianFlag, 1.00f);
        jPanelEnglish = new PicturedPanel(imgEnglishFlag, 1.00f);
        jButtonContinue = new javax.swing.JButton();
        jPanelLanguage = new javax.swing.JPanel();
        jRadioButtonSpanish = new javax.swing.JRadioButton();
        jRadioButtonCatalan = new javax.swing.JRadioButton();
        jRadioButtonBasque = new javax.swing.JRadioButton();
        jRadioButtonGalician = new javax.swing.JRadioButton();
        jRadioButtonEnglish = new javax.swing.JRadioButton();

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
        		jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelTopBarLayout.createSequentialGroup()
        				.addContainerGap(85, Short.MAX_VALUE)
        				.add(jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        						.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelTopBarLayout.createSequentialGroup()
        								.add(jLabelTopBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        								.add(13, 13, 13))
        						.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelTopBarLayout.createSequentialGroup()
        								.add(jLabelTopBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        								.add(18, 18, 18))))
        );
        jPanelTopBarLayout.setVerticalGroup(
        		jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelTopBarLayout.createSequentialGroup()
        				.add(4, 4, 4)
        				.add(jLabelTopBar1)
        				.add(3, 3, 3)
        				.add(jLabelTopBar2)
        				.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        		if(jButtonContinueActionPerformed()) {
        			setVisible(false);
					dispose();
        		}
        	}
        });
       jButtonContinue.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseEntered(java.awt.event.MouseEvent evt) {
        		InputLanguageDialog.this.mouseEntered(evt);
        	}
        	public void mouseExited(java.awt.event.MouseEvent evt) {
        		InputLanguageDialog.this.mouseExited(evt);
        	}
        });

        jRadioButtonSpanish.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioButtonSpanish.setSelected(true);
        buttonGroupFormat.add(jRadioButtonSpanish);
        jRadioButtonSpanish.setText(Constants.LANG.getString("Spanish"));

        jRadioButtonCatalan.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonCatalan);
        jRadioButtonCatalan.setText(Constants.LANG.getString("Catalan"));
        
        jRadioButtonBasque.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonBasque);
        jRadioButtonBasque.setText(Constants.LANG.getString("Basque"));
        
        jRadioButtonGalician.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonGalician);
        jRadioButtonGalician.setText(Constants.LANG.getString("Galician"));
        
        jRadioButtonEnglish.setBackground(Constants.BKG_MAIN_COLOR);
        buttonGroupFormat.add(jRadioButtonEnglish);
        jRadioButtonEnglish.setText(Constants.LANG.getString("English"));

        org.jdesktop.layout.GroupLayout jPanelFormatLayout = new org.jdesktop.layout.GroupLayout(jPanelLanguage);
        jPanelLanguage.setLayout(jPanelFormatLayout);
        jPanelFormatLayout.setHorizontalGroup(
        		jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelFormatLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(20,20,20)
        				.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                				.add(jPanelSpanish, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                				.add(jPanelBasque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                				.add(jPanelGalician, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                				.add(jPanelCatalan, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                				.add(jPanelEnglish, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        				.add(15,15,15)
                		.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        						.add(jRadioButtonSpanish)
        						.add(jRadioButtonBasque)
        						.add(jRadioButtonGalician)
        						.add(jRadioButtonCatalan)
        						.add(jRadioButtonEnglish))
                		.addContainerGap())
        );
        jPanelFormatLayout.setVerticalGroup(
        		jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelFormatLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        					.add(jPanelSpanish, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(jRadioButtonSpanish))
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        						.add(jPanelCatalan, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jRadioButtonCatalan))
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        						.add(jPanelBasque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jRadioButtonBasque))
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        						.add(jPanelGalician, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jRadioButtonGalician))
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jPanelFormatLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        						.add(jPanelEnglish, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jRadioButtonEnglish))
        				.addContainerGap())
        );
        
        jPanelLanguage.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelLanguage.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("PresentationLanguage"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
        		mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(mainPanelLayout.createSequentialGroup()
        				.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        					.add(mainPanelLayout.createSequentialGroup()
        							.add(5, 5, 5)
        							.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 375, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        					.add(jPanelLanguage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(jButtonContinue)
        				)
        		)
        );
        mainPanelLayout.setVerticalGroup(
        		mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(mainPanelLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(10,10,10)
        				.add(jPanelLanguage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        				.add(10,10,10)
        				.add(jButtonContinue)
        				.add(10,10,10)
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


    private boolean jButtonContinueActionPerformed() {  
    	/** Set the presentation language type**/
    	boolean buttonContinue = false;
    	if (jRadioButtonSpanish.isSelected()) {
    		language = LanguageCodeType.ES;
    		buttonContinue = true;
    	} else if(jRadioButtonCatalan.isSelected()) {
    		language = LanguageCodeType.CA;
    		buttonContinue = true;
    	} else if(jRadioButtonBasque.isSelected()) {
    		language = LanguageCodeType.EU;
    		buttonContinue = true;
    	} else if(jRadioButtonGalician.isSelected()) {
			language = LanguageCodeType.GL;
			buttonContinue = true;
		} else if(jRadioButtonEnglish.isSelected()) {
			language = LanguageCodeType.EN;
			buttonContinue = true;
		}
		    		
    	return buttonContinue;
    }                                        

	public String getValue() {
		return language.value();
	}
	
	public void setValue(String lan) {
		if (lan == null)
			jRadioButtonSpanish.setSelected(true);
		if (lan.equals(LanguageCodeType.ES.value()))
    		jRadioButtonSpanish.setSelected(true);
    	else if(lan.equals(LanguageCodeType.CA.value()))
    		jRadioButtonCatalan.setSelected(true);
    	else if(lan.equals(LanguageCodeType.EU.value()))
    		jRadioButtonBasque.setSelected(true);
    	else if(lan.equals(LanguageCodeType.GL.value()))
			jRadioButtonGalician.setSelected(true);
		else if(lan.equals(LanguageCodeType.EN.value()))
			jRadioButtonEnglish.setSelected(true);
		else
			jRadioButtonSpanish.setSelected(true);
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
    private javax.swing.JPanel jPanelSpanish;
    private javax.swing.JPanel jPanelCatalan;
    private javax.swing.JPanel jPanelBasque;
    private javax.swing.JPanel jPanelGalician;
    private javax.swing.JPanel jPanelEnglish;
    
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel jPanelTopBar;
    private javax.swing.JPanel jPanelLanguage;
    
    private javax.swing.JRadioButton jRadioButtonSpanish;
    private javax.swing.JRadioButton jRadioButtonCatalan;
    private javax.swing.JRadioButton jRadioButtonBasque;
    private javax.swing.JRadioButton jRadioButtonGalician;
    private javax.swing.JRadioButton jRadioButtonEnglish;
}