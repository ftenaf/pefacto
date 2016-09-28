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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.mityc.appfacturae.facturae.AdministrativeCentreType;
import es.mityc.appfacturae.facturae.AdministrativeCentresType;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.utils.AdmCentreUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;

public class AdmCentresDialog extends JDialog{
	
	private int spainCode = -1;
	private String version = "";
	private boolean disabled = false; 
	
	public AdmCentresDialog(Frame f, boolean modal, String version, AdministrativeCentresType centres, boolean disabled){
		super(f,modal);
		this.version = version;
		spainCode = FacturaeUtil.getSpainCode();
		this.centres = centres;
		this.disabled = disabled;
		initComponents();
		if (disabled)
			setFieldsDisabled();
		loadCentres();
				
		this.setTitle(Constants.LANG.getString("AdministrativeCentres"));
		this.setSize(585, 165);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
	}

	private void initComponents() {
	
	jPanelAdmCentres = new JPanel();
    jScrollPaneAdmCentres = new JScrollPane();
    jTableAdmCentres = new JTable();
    
    fd = new FadingCanvas();
    fd.setFont(Constants.TITLE_FONT);
    fd.setForeground(Constants.FONT_COLOR);
    
    jButtonAddAdmCentre = new JButton();
	
    jButtonAddAdmCentre.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
    jButtonAddAdmCentre.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus_disabled.jpg")));
    jButtonAddAdmCentre.setToolTipText(Constants.LANG.getString("Add")); 
    jButtonAddAdmCentre.setBorderPainted(false);
    jButtonAddAdmCentre.setContentAreaFilled(false);
    jButtonAddAdmCentre.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
        	AdmCentresDialog.this.mouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	AdmCentresDialog.this.mouseExited(evt);
        }
    });
    jButtonAddAdmCentre.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        	addCentre(evt);
        }
    });
    
    jButtonDelAdmCentre = new JButton();
	
    jButtonDelAdmCentre.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
    jButtonDelAdmCentre.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus_disabled.jpg")));
    jButtonDelAdmCentre.setToolTipText(Constants.LANG.getString("Remove")); 
    jButtonDelAdmCentre.setBorderPainted(false);
    jButtonDelAdmCentre.setContentAreaFilled(false);
    jButtonDelAdmCentre.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
        	AdmCentresDialog.this.mouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	AdmCentresDialog.this.mouseExited(evt);
        }
    });
    jButtonDelAdmCentre.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        	delCentre(evt);
        }
    });
    
    jButtonReturn = new JButton();
    jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
    jButtonReturn.setToolTipText(Constants.LANG.getString("Return")); 
    jButtonReturn.setBorderPainted(false);
    jButtonReturn.setContentAreaFilled(false);
    jButtonReturn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
	    	dispose();
		}
    });
    jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
        	AdmCentresDialog.this.mouseEntered(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	AdmCentresDialog.this.mouseExited(evt);
        }
    });
    
    jTableAdmCentres.setModel(new NoEdiTableModel(
        new Object [][] {

        },
        new String [] {
        		Constants.LANG.getString("CentreCode"), 				
        		Constants.LANG.getString("Role"), 			
        		Constants.LANG.getString("Name"),  
        		Constants.LANG.getString("FirstSurname"), 			
        		Constants.LANG.getString("SecondSurname")
        },
        new int[]{}
    ));
    
    for (int i = 0 ; i < jTableAdmCentres.getColumnCount() ; i++){
    	jTableAdmCentres.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
    }
    jTableAdmCentres.setGridColor(Constants.SELECTION_COLOR);
    jScrollPaneAdmCentres.setViewportView(jTableAdmCentres);
    jTableAdmCentres.getTableHeader().setReorderingAllowed(false);
    jTableAdmCentres.getColumnModel().getColumn(0).setResizable(false);
    jTableAdmCentres.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
    //jTableAdmCentres.getColumnModel().getColumn(0).setPreferredWidth(100);
    jTableAdmCentres.getColumnModel().getColumn(1).setResizable(false);
    jTableAdmCentres.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
    //jTableAdmCentres.getColumnModel().getColumn(1).setPreferredWidth(100);
    jTableAdmCentres.getColumnModel().getColumn(2).setResizable(false);
    jTableAdmCentres.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
    //jTableAdmCentres.getColumnModel().getColumn(2).setPreferredWidth(100);
    jTableAdmCentres.getColumnModel().getColumn(3).setResizable(false);
    jTableAdmCentres.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
    //jTableAdmCentres.getColumnModel().getColumn(3).setPreferredWidth(100);
    jTableAdmCentres.getColumnModel().getColumn(4).setResizable(false);
    jTableAdmCentres.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
    //jTableAdmCentres.getColumnModel().getColumn(4).setPreferredWidth(100);

    jTableAdmCentres.addMouseListener(new MouseAdapter(){
	    public void mouseClicked(MouseEvent e){
	    	int row = jTableAdmCentres.getSelectedRow();
	        if (e.getClickCount() == 2){
	        	if(row >= 0){
	        		InputAdmCentreDialog iacd = new InputAdmCentreDialog (null, true, centres.getAdministrativeCentre().get(row),disabled,version);
	        		iacd.setVisible(true);
	        		if (!disabled){
	        			String[] values = iacd.getValues();
	        			if (!"".equals(values[7])){
	        				
	        				// *** Cambios adaptación a 3.2 ***
	        				
	        				AdministrativeCentreType act = AdmCentreUtil.makeCentre(values,spainCode,version);
	        				
		        			centres.getAdministrativeCentre().set(row,act);
		        			((NoEdiTableModel)jTableAdmCentres.getModel()).removeRow(row);
		        			String rolePresent = "";
		        			if (!"".equals(act.getRoleTypeCode()) && act.getRoleTypeCode() != null){
		        				if ("3.2".equals(version))
		        					rolePresent = Constants.LANG.getString(Constants.APP_PROP.getProperty("roles32").split(";")[Integer.parseInt(act.getRoleTypeCode())]);
		        				else
		        					rolePresent = Constants.LANG.getString(Constants.APP_PROP.getProperty("roles").split(";")[Integer.parseInt(act.getRoleTypeCode())]);
		        			}
		        			((NoEdiTableModel)jTableAdmCentres.getModel()).insertRow(row, new Object[]{act.getCentreCode(),rolePresent,act.getName(),act.getFirstSurname(),act.getSecondSurname()});
	        			}
	        		}
	        	}
	        }
	    }
	 });
    
    jScrollPaneAdmCentres.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
    
    org.jdesktop.layout.GroupLayout jPanelLayout = new org.jdesktop.layout.GroupLayout(jPanelAdmCentres);
    jPanelAdmCentres.setLayout(jPanelLayout);
    jPanelLayout.setHorizontalGroup(
    	jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)	
	        .add(jPanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .add(jScrollPaneAdmCentres, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	            .add(5, 5, 5)
	            .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
	                .add(jButtonAddAdmCentre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
	                .add(jButtonDelAdmCentre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
	            .addContainerGap())
	        .add(jPanelLayout.createSequentialGroup()
	        	.addContainerGap()
	        	.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 400, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	        	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	        	.add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	        	.addContainerGap()
	        )
    );
    jPanelLayout.setVerticalGroup(
        jPanelLayout.createSequentialGroup()
        	.addContainerGap()
            .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            	.add(jScrollPaneAdmCentres, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            	.add(jPanelLayout.createSequentialGroup()
                    .add(jButtonAddAdmCentre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jButtonDelAdmCentre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            )     
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            	.add(fd)
                .add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap());
    
    add(jPanelAdmCentres);
    this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    
	}

	private void addCentre(java.awt.event.ActionEvent evt) {
		InputAdmCentreDialog iacd = new InputAdmCentreDialog(null,true,null,disabled,version);
		iacd.setVisible(true);
		
		String[] result = iacd.getValues();
		
		if (!"".equals(result[7])){
			
			AdministrativeCentreType act = AdmCentreUtil.makeCentre(result,spainCode,version);
			
			if (centres == null)
				centres = new AdministrativeCentresType();
			
			centres.getAdministrativeCentre().add(act);
			String rolePresent = "";
			if (!"".equals(result[1])){
				if ("3.2".equals(version))
					rolePresent = Constants.LANG.getString(Constants.APP_PROP.getProperty("roles32").split(";")[Integer.parseInt(result[1])]);
				else
					rolePresent = Constants.LANG.getString(Constants.APP_PROP.getProperty("roles").split(";")[Integer.parseInt(result[1])]);
			}
			((NoEdiTableModel)jTableAdmCentres.getModel()).addRow(new Object[]{result[0],rolePresent,result[2],result[3],result[4]});
			fd.showMessage(Constants.LANG.getString("OKMessageAdmCentreAdded"), Constants.OK_MSG_COLOR);
		}
	}
	
	
	private void delCentre(java.awt.event.ActionEvent evt) {
		int selRow = jTableAdmCentres.getSelectedRow();
		if (selRow >= 0){
			centres.getAdministrativeCentre().remove(selRow);
			((NoEdiTableModel)jTableAdmCentres.getModel()).removeRow(selRow);
			fd.showMessage(Constants.LANG.getString("OKMessageAdmCentreDeleted"), Constants.OK_MSG_COLOR);
		}
		else{
			fd.showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
		}
	}
	
	private void loadCentres() {
		AdministrativeCentreType act = null;
		Object[] ob = new Object [5];
		if (centres != null && centres.getAdministrativeCentre() != null && centres.getAdministrativeCentre().size() > 0){
			for (int i = 0 ; i < centres.getAdministrativeCentre().size() ; i++){
				act = centres.getAdministrativeCentre().get(i);
				if (!"".equals(act.getCentreCode()))
					ob[0] = act.getCentreCode();
				else
					ob[0] = "";
				if (!"".equals(act.getRoleTypeCode()) && act.getRoleTypeCode() != null){
					if ("3.2".equals(version))
						ob[1] = Constants.LANG.getString(Constants.APP_PROP.getProperty("roles32").split(";")[Integer.parseInt(act.getRoleTypeCode())]);
					else
						ob[1] = Constants.LANG.getString(Constants.APP_PROP.getProperty("roles").split(";")[Integer.parseInt(act.getRoleTypeCode())]);
				}
				else
					ob[1] = "";
				if (!"".equals(act.getName()))
					ob[2] = act.getName();
				else
					ob[2] = "";
				if (!"".equals(act.getFirstSurname()))
					ob[3] = act.getFirstSurname();
				else
					ob[3] = "";
				if (!"".equals(act.getSecondSurname()))
					ob[4] = act.getSecondSurname();
				else
					ob[4] = "";
				((NoEdiTableModel)jTableAdmCentres.getModel()).addRow(ob);
			}
		}
	}
	
	public AdministrativeCentresType getCentres(){
		return centres;
	}
	
	public void setFieldsDisabled() {
		jButtonAddAdmCentre.setEnabled(false);
		jButtonDelAdmCentre.setEnabled(false);
	}
	
	/** Action on mouse entered*/
	private void mouseEntered(java.awt.event.MouseEvent evt) {
		jButtonAddAdmCentre.getTopLevelAncestor().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	}
	
	/** Action on mouse exited*/
	private void mouseExited(java.awt.event.MouseEvent evt) {
		jButtonAddAdmCentre.getTopLevelAncestor().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	}

	private JPanel jPanelAdmCentres = null;
	private JScrollPane jScrollPaneAdmCentres = null;
	private JTable jTableAdmCentres;
	private JButton jButtonAddAdmCentre = null;
	private JButton jButtonDelAdmCentre = null;
	private JButton jButtonReturn = null;
	
	private FadingCanvas fd;
	
	private AdministrativeCentresType centres;
}
