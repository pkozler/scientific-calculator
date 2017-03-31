package cz.zcu.pkozler.mkz.ui.buttons;

import android.view.View;

import cz.zcu.pkozler.mkz.ui.modes.AngleMode;
import cz.zcu.pkozler.mkz.ui.modes.FunctionInputMode;
import cz.zcu.pkozler.mkz.ui.modes.InputMode;

/**
 * Výčtový typ reprezentující jednotlivá tlačítka na klávesnici kalkulačky, která slouží
 * pro zadávání vstupu do zvoleného textového pole aktuálně zobrazené aktivity, a pro která
 * jsou uchovávány všechny potřebné informace, jako pozice na klávesnici, popisek, režim
 * kalkulačky, ve kterém jsou zobrazena, a referenci na objekt posluchače stisku tlačítka.
 * Data pro všechna tlačítka jsou inicializována a uložena najednou po spuštění aplikace,
 * díky čemuž není nutné je při přepnutí režimu kalkulačky inicializovat znova, pouze jsou
 * vystřídána tlačítka na odpovídajících pozicích.
 *
 * @author Petr Kozler
 */
public enum InputButtonType {
    
    CLEAR_BTN(0, 0, null, null, null, null),
    
    DIGIT_MODE_BTN(0, 1, InputMode.FUNCTION_MODE, null, null, null),
    FUNCTION_MODE_BTN(0, 1, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    
    O_MOD_BTN(0, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, false),
    VAR_BTN(0, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, true),
    FUN_MODE_1_BTN(0, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    FUN_MODE_2_BTN(0, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    DELETE_BTN(0, 3, null, null, null, null),
    
    D_7_BTN(1, 0, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_SQ_BTN(1, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_SIND_BTN(1, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    F_SINR_BTN(1, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    F_SING_BTN(1, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    
    D_8_BTN(1, 1, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_CB_BTN(1, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_COSD_BTN(1, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    F_COSR_BTN(1, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    F_COSG_BTN(1, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    
    D_9_BTN(1, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_POW_BTN(1, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_TAND_BTN(1, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    F_TANR_BTN(1, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    F_TANG_BTN(1, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    
    O_POW_BTN(1, 3, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_INT_BTN(1, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    RAD_MODE_BTN(1, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    GRAD_MODE_BTN(1, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    DEG_MODE_BTN(1, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    
    D_4_BTN(2, 0, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_SQRT_BTN(2, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_ASIND_BTN(2, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    F_ASINR_BTN(2, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    F_ASING_BTN(2, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    
    D_5_BTN(2, 1, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_CBRT_BTN(2, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_ACOSD_BTN(2, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    F_ACOSR_BTN(2, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    F_ACOSG_BTN(2, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    
    D_6_BTN(2, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_YROOT_BTN(2, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_ATAND_BTN(2, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.DEG_MODE, null),
    F_ATANR_BTN(2, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.RAD_MODE, null),
    F_ATANG_BTN(2, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, AngleMode.GRAD_MODE, null),
    
    O_DIV_BTN(2, 3, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_FRAC_BTN(2, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_DMS_BTN(2, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    D_1_BTN(3, 0, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_REC_BTN(3, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_SINH_BTN(3, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    D_2_BTN(3, 1, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_LN_BTN(3, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_COSH_BTN(3, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    D_3_BTN(3, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_LOG_BTN(3, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_TANH_BTN(3, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    O_MUL_BTN(3, 3, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_FLOOR_BTN(3, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_DEG_BTN(3, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    C_PI_BTN(4, 0, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_ABS_BTN(4, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_ASINH_BTN(4, 0, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    D_0_BTN(4, 1, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_EXP_BTN(4, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_ACOSH_BTN(4, 1, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    DEC_SEPARATOR_BTN(4, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_FACT_BTN(4, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    F_ATANH_BTN(4, 2, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_2, null, null),
    
    O_SUB_BTN(4, 3, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_CEIL_BTN(4, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null),
    
    P_LEFT_BTN(5, 0, null, null, null, null),
    
    P_RIGHT_BTN(5, 1, null, null, null, null),
    
    C_EU_BTN(5, 2, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    ARG_SEPARATOR_BTN(5, 2, InputMode.FUNCTION_MODE, null, null, null),
    
    O_ADD_BTN(5, 3, InputMode.DIGIT_OPERATOR_MODE, null, null, null),
    F_SGN_BTN(5, 3, InputMode.FUNCTION_MODE, FunctionInputMode.FUNCTION_MODE_1, null, null);

    /**
     * počet řádek klávesnice s tlačítky
     */
    public static final int BTN_GRID_ROW_COUNT = 6;

    /**
     * počet sloupců klávesnice s tlačítky
     */
    public static final int BTN_GRID_COL_COUNT = 4;

    /**
     * index řádky klávesnice, na které je tlačítko umístěno
     */
    public final int ROW;

    /**
     * index sloupce klávesnice, ve kterém je tlačítko umístěno
     */
    public final int COL;

    /**
     * základní režim kalkulačky, při kterém je tlačítko zobrazeno
     */
    public final InputMode INPUT_MODE;

    /**
     * režim matematických funkcí, při kterém je tlačítko zobrazeno
     */
    public final FunctionInputMode FUNCTION_INPUT_MODE;

    /**
     * režim úhlových jednotek, při kterém je tlačítko zobrazeno
     */
    public final AngleMode ANGLE_MODE;

    /**
     * příznak zobrazení tlačítka v aktivitě pro práci s výrazy, které obsahují proměnné
     */
    public final Boolean HAS_VARIABLE;

    /**
     * popis tlačítka
     */
    private String text;

    /**
     * posluchač události stisku tlačítka
     */
    private View.OnClickListener listener;

    /**
     * Vytvoří nový typ tlačítka s inicializací základních informací potřebných pro jeho vytvoření.
     *
     * @param row index řádky klávesnice, na které je tlačítko umístěno
     * @param col index sloupce klávesnice, ve kterém je tlačítko umístěno
     * @param inputMode základní režim kalkulačky, při kterém je tlačítko zobrazeno
     * @param functionInputMode režim matematických funkcí, při kterém je tlačítko zobrazeno
     * @param angleMode režim úhlových jednotek, při kterém je tlačítko zobrazeno
     * @param hasVariable příznak zobrazení tlačítka v aktivitě pro práci s výrazy, které obsahují proměnné
     */
    InputButtonType(int row, int col, InputMode inputMode,
            FunctionInputMode functionInputMode, AngleMode angleMode, Boolean hasVariable) {
        ROW = row;
        COL = col;
        INPUT_MODE = inputMode;
        FUNCTION_INPUT_MODE = functionInputMode;
        ANGLE_MODE = angleMode;
        HAS_VARIABLE = hasVariable;
    }

    /**
     * Nastaví danému typu tlačítka dodatečné informace potřebné pro zobrazení a správnou funkci tlačítka.
     *
     * @param text popis tlačítka
     * @param listener posluchač události stisku tlačítka
     */
    public void setAdditionalProperties(String text, View.OnClickListener listener) {
        this.text = text;
        this.listener = listener;
    }

    /**
     * Vrátí popis tlačítka.
     *
     * @return popis tlačítka
     */
    public String getText() {
        return text;
    }

    /**
     * Vrátí posluchač události stisku tlačítka.
     *
     * @return posluchač události stisku tlačítka
     */
    public View.OnClickListener getListener() {
        return listener;
    }

}
