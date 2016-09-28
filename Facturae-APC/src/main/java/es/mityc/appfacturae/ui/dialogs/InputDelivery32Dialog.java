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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.wannawork.jcalendar.JCalendarComboBox;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.utils.constants.Constants;

public class InputDelivery32Dialog extends JDialog {
	
	private FadingCanvas fd;
	
	public InputDelivery32Dialog (Frame parent, boolean modal) {
		super(parent, modal);
	        
	    init();
		setLocationRelativeTo(parent);
	}

	private void init() {
		mainPanel = new JPanel();
		panel = new JPanel();
		jScroll = new JScrollPane();
		jButtonOk = new JButton();
		jButtonCancel = new JButton();
		fd = new FadingCanvas();

		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
		
		panel.setBackground(Constants.BKG_MAIN_COLOR);
		
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
				if ( idTextField.getText() == null || 
						"".equals(idTextField.getText().trim()) ) {								
					idTextField.requestFocus();  
					idTextField.setBackground(Constants.BKG_ERROR_COLOR);

					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
				} else if (idTextField.getText().length() > 30){
				// El identificador debe ser un String de 30 caracteres maximo
					idTextField.setText("");
					idTextField.requestFocus();  
					idTextField.setBackground(Constants.BKG_ERROR_COLOR);
					
					fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
				} else {
					setVisible(false);
					dispose();
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
				
		// Se construye el panel de entrada	
	    idLabel = new JLabel(Constants.LANG.getString("Identifier"));
	    idLabel.setForeground(Constants.FONT_COLOR);
	    idLabel.setFont(Constants.FONT_PLAIN);
	    idLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
	    idLabel.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
	    idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
	    dateLabel = new JLabel(Constants.LANG.getString("Date"));
	    dateLabel.setForeground(Constants.FONT_COLOR);
	    dateLabel.setFont(Constants.FONT_PLAIN);
	    dateLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
	    dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		idTextField = new JTextField();
		idTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		idTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		
		dateComboBox = new de.wannawork.jcalendar.JCalendarComboBox();
		((JFormattedTextField)((JSpinner)dateComboBox.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapterDate());
		dateComboBox.setVisible(false);
		
		jRadioButtonDate = new javax.swing.JRadioButton();
		jRadioButtonDate.setMargin(new Insets(0,0,0,10));
		jRadioButtonDate.setContentAreaFilled(false);
		jRadioButtonDate.addActionListener(new ActionListenerDate(dateComboBox));
    	
		// Se construye el panel Canvas
		fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
		
        // Se construye el panel principal
		mainPanel.setLayout(new GridBagLayout());
		
		org.jdesktop.layout.GroupLayout jPanelLayout = new org.jdesktop.layout.GroupLayout(panel);
		panel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(
				jPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        				.add(idLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(jPanelLayout.createSequentialGroup()
        					.add(jRadioButtonDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(dateLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			)
        			.add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        				.add(idTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, Short.MAX_VALUE)
        				.add(dateComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.addContainerGap()
        );
		jPanelLayout.setVerticalGroup(
				jPanelLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    					.add(idLabel)
    					.add(idTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    				.add(15,15,15)
    				.add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    					.add(jRadioButtonDate)
    					.add(dateLabel)
    					.add(dateComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    				.addContainerGap()
        );
		
		jScroll.setViewportView(panel);
		jScroll.setBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true));
		
		org.jdesktop.layout.GroupLayout jPanelMainLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
        		jPanelMainLayout.createSequentialGroup()
        			.addContainerGap()
        			.add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        				.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
        				.add(jScroll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.add(jPanelMainLayout.createSequentialGroup()
        					.add(jButtonOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(30,30,30)
        					.add(jButtonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			)
        			.addContainerGap()
        );
        jPanelMainLayout.setVerticalGroup(
        		jPanelMainLayout.createSequentialGroup()
    			.addContainerGap()
    			.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    			.add(jScroll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    			.add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jButtonOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.add(jButtonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.addContainerGap()
        );
		
        this.add(mainPanel);
        
		setResizable(false);
		setSize(370, 210);
		fd.setSize(getWidth(), 13);
		setTitle(Constants.LANG.getString("deliveryIdentifierAddTitle"));
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
	
    private class FocusAdapterDate extends FocusAdapter{
    	public void focusLost(FocusEvent evt){
			if ( !((JFormattedTextField)evt.getSource()).isEditValid() ) {
		   		fd.showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
		   	}
		}
    }
    
    private class ActionListenerDate implements java.awt.event.ActionListener{
    	JCalendarComboBox jccb;
    	public ActionListenerDate(JCalendarComboBox jccb){
    		this.jccb = jccb;
    	}
		public void actionPerformed(ActionEvent e) {
			if (((JRadioButton)e.getSource()).isSelected())
				jccb.setVisible(true);
	        else
	        	jccb.setVisible(false);
		}
    }
    
	private void jButtonCancelActionPerformed() {
		idTextField.setText(null);

		setVisible(false);
		dispose();
	}
	
	public String[] getValues() {
		String[] result = null;
		if (jRadioButtonDate.isSelected())
			result = new String[]{idTextField.getText(), Constants.DATE_FORMAT.format(dateComboBox.getCalendar().getTime())};
		else
			result = new String[]{idTextField.getText(), null};
		return result;
	}	
	
    private JLabel idLabel = null;
    private JLabel dateLabel = null;
    
    private JTextField idTextField = null;
    private JCalendarComboBox dateComboBox = null;
    private JRadioButton jRadioButtonDate = null;
    
    private JPanel mainPanel = null;
    private JPanel panel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
}
