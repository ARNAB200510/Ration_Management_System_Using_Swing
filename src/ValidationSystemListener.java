import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ValidationSystemListener implements DocumentListener {
    private JComponent field; //Field to be validated 
    private Pattern pattern; //Pattern for validation 
    private JLabel label; //Label to map the result 
    public static HashMap<JLabel, Pair<Boolean, JLabel>> labelFieldMap;
    ValidationSystemListener() {
        this.field = null;
        this.pattern = null;
        this.label = null;

    }
    ValidationSystemListener(JLabel label, JComponent field, Pattern pattern) {
        this.field = field;
        this.pattern = pattern;
        this.label = label;
    }
    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            validateField();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            validateField();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    @Override
    public void changedUpdate(DocumentEvent e) {
        return;
    }
    
    private void validateField() throws Exception {
        String text = "";
        if (field instanceof JTextField) {
            text = ((JTextField) field).getText();
        } else if (field instanceof JPasswordField) {
            text = new String(((JPasswordField) field).getPassword());
        } else {
            throw new Exception("TypeMismatch in field");
        }
        boolean isValid = pattern.matcher(text).matches();
        labelFieldMap.get(label).setOne(isValid);
        
    }


}