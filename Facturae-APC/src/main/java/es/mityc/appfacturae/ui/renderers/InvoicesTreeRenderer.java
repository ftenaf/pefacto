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
package es.mityc.appfacturae.ui.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;

public class InvoicesTreeRenderer extends JLabel implements TreeCellRenderer{		

	private static Log logger = LogFactory.getLog(InvoicesTreeRenderer.class);

	private ImageIcon invoices,draft,emitted,sent,received,rectificated,rdraft,remitted,rsent;

	private boolean bSelected;  

    @Override
    public void setOpaque(boolean isOpaque) {
       super.setOpaque(isOpaque);
    }

    public InvoicesTreeRenderer() 
    {
        
        /** Loading images */
        invoices = new ImageIcon(getClass().getResource("/images/invoices.jpg"));
        draft = new ImageIcon(getClass().getResource("/images/draft.jpg"));
        emitted = new ImageIcon(getClass().getResource("/images/issued.jpg"));
        sent = new ImageIcon(getClass().getResource("/images/sent.jpg"));
        received = new ImageIcon(getClass().getResource("/images/received.jpg"));
        rectificated = new ImageIcon(getClass().getResource("/images/rectificated.jpg"));
        rdraft= new ImageIcon(getClass().getResource("/images/draft.jpg"));
        remitted= new ImageIcon(getClass().getResource("/images/issued.jpg"));
        rsent= new ImageIcon(getClass().getResource("/images/sent.jpg"));        
    }

    public Component getTreeCellRendererComponent( JTree tree,Object value, boolean bSelected, boolean bExpanded, boolean bLeaf, int iRow, boolean bHasFocus )
    {
        // Find out which node we are rendering and get its text
    	Properties appProperties = null;
        try {
            InputStream f = this.getClass().getResourceAsStream(Constants.APP_CONFIG_PATH);
            appProperties = new Properties();
            appProperties.load(f);
            f.close(); 
        }
        catch(Exception e) {
            logger.error("An exception occurred when loading the properties of the application file: " + e.getMessage());
        }
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String labelText = (String)node.getUserObject();

		this.bSelected = bSelected;
		this.setOpaque(false);
		
        // Setting the tree font
        Font font = new Font(appProperties.getProperty("Font"), Integer.valueOf(appProperties.getProperty("Fontmodifier")), Integer.valueOf(appProperties.getProperty("Fontsize")));
        this.setFont(font);

        // Determine the correct icon to display
        if( labelText.equals(Constants.LANG.getString("Invoices")) )
            setIcon(invoices);
        else if( labelText.equals(Constants.LANG.getString("DraftStr")) ) {
            if (node.getLevel() > 1)
                setIcon(rdraft);
            else
                setIcon(draft);
        }
        else if( labelText.equals(Constants.LANG.getString("Issued")) ) {
            if (node.getLevel() > 1)
                setIcon(remitted);
            else
                setIcon(emitted);
        }
	 	else if( labelText.equals(Constants.LANG.getString("Sent")) ) {
	            if (node.getLevel() > 1)
	                setIcon(rsent);
	            else
	                setIcon(sent);
	    }
		else if( labelText.equals(Constants.LANG.getString("ReceivedStr")) )
	            setIcon(received);
		else if( labelText.equals(Constants.LANG.getString("Corrective")) )
	            setIcon(rectificated);
	    else if( labelText.equals(Constants.LANG.getString("CorrectiveDraft")) )
	            setIcon(rdraft);
	    else if( labelText.equals(Constants.LANG.getString("CorrectiveIssued")) )
	            setIcon(remitted);
	    else if( labelText.equals(Constants.LANG.getString("CorrectiveSent")) )
	            setIcon(rsent);
	
		// Add the text to the cell
	    if(!bSelected) {
	        setForeground(Color.black);
	        setText("<html><font color=\"135B87\">" + labelText + "</font></html>");
	        setBackground(Constants.BKG_MAIN_COLOR);
	    }
		else {
	        setForeground(Color.white);
	        setText("<html><font color=\"135B87\">" + labelText + "</font></html>");
	        setBackground(Constants.SELECTION_COLOR);
	    }
		setOpaque(true);
	        
	    return this;
    }

    /** This is a hack to paint the background.  Normally a JLabel can
     * paint its own background, but due to an apparent bug or
     * limitation in the TreeCellRenderer, the paint method is
     * required to handle this.
     */
    @Override
    public void paint(Graphics g)
    {
    	Color bColor = null;

    	// Set the correct background color
    	bColor = bSelected ? SystemColor.textHighlight : Color.white;
    	g.setColor(bColor);

    	// Draw a rectangle in the background of the cell
    	g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );

    	super.paint(g);
    }
}