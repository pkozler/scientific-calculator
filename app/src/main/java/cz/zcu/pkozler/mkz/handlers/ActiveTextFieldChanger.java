package cz.zcu.pkozler.mkz.handlers;

import android.text.InputType;
import android.widget.EditText;

/**
 *
 * @author Petr Kozler
 */
public class ActiveTextFieldChanger {
    
    private EditText activeTextField;

    public EditText getActiveTextField() {
        return activeTextField;
    }

    public void setActiveTextField(EditText textField) {
        activeTextField = textField;
    }
    
    public void clearString() {
        activeTextField.setText("");
    }
    
    public void removeChar() {
        String str = activeTextField.getText().toString();
        
        if (str.length() < 1) {
            return;
        }
        
        activeTextField.setText(str.substring(0, str.length() - 1));
    }
    
    public void addDigit(Character digit) {
        if (digit == null) {
            addSubString(".");
            
            return;
        }
        
        addSubString(digit.toString());
    }
    
    public void addOperator(String operator) {
        addSubString(" " + operator + " ");
    }
    
    public void addFunction(String function) {
        addSubString(function + "(");
    }
    
    public void addConstant(String constant) {
        addSubString(constant);
    }
    
    public void addVariable() {
        addSubString("x");
    }
    
    public void addParenthesis(boolean isEnding) {
        if (isEnding) {
            addSubString(")");
            
            return;
        }
        
        addSubString("(");
    }
    
    public void addArgumentSeparator() {
        addSubString(",");
    }
    
    private void addSubString(String subStr) {
        String str = activeTextField.getText().toString();
        activeTextField.setText(str + subStr);
    }
    
}
