package lib;

import javax.swing.*; 
import javax.swing.text.*;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;

public class JIntegerTextField extends JTextField {
   
	private static final long serialVersionUID = 1L;
	private Toolkit toolkit;
    private NumberFormat integerFormatter;
    
	public JIntegerTextField(int value) {
        toolkit = Toolkit.getDefaultToolkit();
		integerFormatter = NumberFormat.getIntegerInstance();
        integerFormatter.setParseIntegerOnly(true);
        setValue(value);
    }
    
    public int getValue() {
        int retVal = 0;
        try {
            retVal = integerFormatter.parse(getText()).intValue();
        } catch (ParseException e) {
            toolkit.beep();
        }
        return retVal;
    }

    public void setValue(int value) {
        setText(integerFormatter.format(value));
    }

    protected Document createDefaultModel() {
        return new IntegerTextFieldDocument();
    }

    protected class IntegerTextFieldDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;

		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;
            for (int i = 0; i < result.length; i++) {
                if (Character.isDigit(source[i]))
                    result[j++] = source[i];
                else {
                    toolkit.beep();
					JOptionPane.showMessageDialog(null,"Escriba un número entero","Error Tipo de Dato",JOptionPane.ERROR_MESSAGE);
                }
            }
            super.insertString(offs, new String(result, 0, j), a);
        }
    }
}
