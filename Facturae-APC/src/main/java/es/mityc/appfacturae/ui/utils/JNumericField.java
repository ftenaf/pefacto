/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Campo de texto numerico
 */
public class JNumericField extends JTextField {

	private static final long serialVersionUID = 1L;

	private static final char DOT = '.';
	public static final String FM_DECIMAL = "0123456789" + DOT;
	private int precision = 0;

	protected PlainDocument numberFieldFilter;

	public JNumericField() {
		numberFieldFilter = new JNumberFieldFilter();
		super.setDocument(numberFieldFilter);
	}

	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
	}

	public void setEditable(boolean enable) {
		super.setEditable(enable);
	}

	public void setPrecision(int iPrecision) {
		precision = iPrecision;
	}

	public int getPrecision() {
		return precision;
	}

	class JNumberFieldFilter extends PlainDocument {
		private static final long serialVersionUID = 1L;

		public JNumberFieldFilter() {
			super();
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			String text = getText(0, offset) + str + getText(offset, (getLength() - offset));

			if (str == null || text == null)
				return;

			int precisionLength = 0;
			int textLength = text.length();

			try {
				if (!(text.length() == 1))
					new Double(text);
				int dotIndex = text.indexOf(DOT);
				if (dotIndex != -1) {
					precisionLength = textLength - dotIndex -1;
					if (precisionLength > precision)
						return;
				}
			} catch (Exception ex) {
				return;
			}

			super.insertString(offset, str, attr);
		}
	}
}