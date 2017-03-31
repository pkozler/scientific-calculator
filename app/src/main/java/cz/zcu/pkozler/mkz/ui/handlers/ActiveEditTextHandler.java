package cz.zcu.pkozler.mkz.ui.handlers;

import android.widget.EditText;

import cz.zcu.pkozler.mkz.core.tokens.TokenConfig;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.VariableType;

/**
 * Třída, sloužící jako obal nad textovými poli pro zadávání vstupů. Zprostředkovává přístup
 * posluchačů stisků kláves k aktuálně vybranému textovému poli v právě zobrazené aktivitě.
 * Díky tomu není při přepínání režimů kalkulačky nutné provádět změny v reakcích
 * na stisky kláves.
 *
 * @author Petr Kozler
 */
public class ActiveEditTextHandler {

    /**
     * právě aktivní textové pole
     */
    private EditText activeTextField;

    /**
     * Vrátí právě aktivní textové pole.
     *
     * @return právě aktivní textové pole
     */
    public EditText getActiveTextField() {
        return activeTextField;
    }

    /**
     * Nastaví právě aktivní textové pole.
     *
     * @param textField právě aktivní textové pole
     */
    public void setActiveTextField(EditText textField) {
        activeTextField = textField;
    }

    /**
     * Smaže celý obsah textového pole.
     */
    public void clearString() {
        activeTextField.setText("");
    }

    /**
     * Odstraní z konce řetězce v textovém poli všechny bílé znaky až do prvního nebílého včetně.
     */
    public void removeChar() {
        String str = activeTextField.getText().toString();
        str = str.replaceAll("\\s+$", "");

        if (str.length() < 1) {
            return;
        }

        activeTextField.setText(str.substring(0, str.length() - 1));
        moveCursor();
    }

    /**
     * Vloží číslici nebo desetinný oddělovač.
     *
     * @param digit
     */
    public void addDigit(Character digit) {
        if (digit == null) {
            addSubString(TokenConfig.DEF_DECIMAL_SEPARATOR);
            
            return;
        }
        
        addSubString(digit.toString());
    }

    /**
     * Vloží operátor.
     *
     * @param operator
     */
    public void addOperator(String operator) {
        addSubString(" " + operator + " ");
    }

    /**
     * Vloží funkci.
     *
     * @param function
     */
    public void addFunction(String function) {
        addSubString(function + OtherTokenType.LEFT_PARENTHESIS.toString());
    }

    /**
     * Vloží konstantu.
     *
     * @param constant
     */
    public void addConstant(String constant) {
        addSubString(constant);
    }

    /**
     * Vloží symbol proměnné.
     */
    public void addVariable() {
        addSubString(VariableType.VARIABLE.toString());
    }

    /**
     * Vloží závorku.
     *
     * @param isEnding true, pokud se jedná o ukončovací závorku, jinak false
     */
    public void addParenthesis(boolean isEnding) {
        if (isEnding) {
            addSubString(OtherTokenType.RIGHT_PARENTHESIS.toString());
            
            return;
        }
        
        addSubString(OtherTokenType.LEFT_PARENTHESIS.toString());
    }

    /**
     * Vloží oddělovač argumentů funkce.
     */
    public void addArgumentSeparator() {
        addSubString(OtherTokenType.ARG_SEPARATOR.toString());
    }

    /**
     * Připojí předaný podřetězec ke stávajícímu řetězci v textovém poli.
     *
     * @param subStr podřetězec
     */
    private void addSubString(String subStr) {
        String str = activeTextField.getText().toString();
        activeTextField.setText(str + subStr);
        moveCursor();
    }

    /**
     * Připojí předaný znak jako podřetězec ke stávajícímu řetězci v textovém poli.
     *
     * @param ch znak
     */
    private void addSubString(Character ch) {
        addSubString(Character.toString(ch));
    }

    /**
     * Přesune kurzor na konec textového pole.
     */
    private void moveCursor() {
        activeTextField.setSelection(activeTextField.getText().length());
    }

}
