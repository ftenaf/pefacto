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
package es.mityc.appfacturae.ui.components;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import es.mityc.appfacturae.ui.renderers.InvoicesTreeRenderer;
import es.mityc.appfacturae.utils.constants.Constants;

public class InvoicesTree implements TreeSelectionListener {
	
	private JTree tree = new JTree();
    
    public InvoicesTree() {
    	
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(Constants.LANG.getString("Invoices"));
        DefaultMutableTreeNode bor = new DefaultMutableTreeNode(Constants.LANG.getString("DraftStr"));
        DefaultMutableTreeNode emi = new DefaultMutableTreeNode(Constants.LANG.getString("Issued"));
        DefaultMutableTreeNode env = new DefaultMutableTreeNode(Constants.LANG.getString("Sent"));
        DefaultMutableTreeNode rev = new DefaultMutableTreeNode(Constants.LANG.getString("ReceivedStr"));
        DefaultMutableTreeNode rec = new DefaultMutableTreeNode(Constants.LANG.getString("Corrective"));
        DefaultMutableTreeNode rbo = new DefaultMutableTreeNode(Constants.LANG.getString("CorrectiveDraft"));
        DefaultMutableTreeNode rem = new DefaultMutableTreeNode(Constants.LANG.getString("CorrectiveIssued"));
        DefaultMutableTreeNode ren = new DefaultMutableTreeNode(Constants.LANG.getString("CorrectiveSent"));

        top.add(bor);
        top.add(emi);
        top.add(env);
        top.add(rev);
        top.add(rec);
        
        rec.add(rbo);
        rec.add(rem);
        rec.add(ren);
        
        tree = new JTree(top);
        
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setOpaque(false);
        tree.addTreeSelectionListener(this);
        tree.setCellRenderer(new InvoicesTreeRenderer());
   }

    public void valueChanged(TreeSelectionEvent e) {
        // DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();       
    }
 
    public TreeModel getModel(){
        return tree.getModel();
    }
}