package cz.zcu.pkozler.mkz.handlers;

import android.view.View;
import android.widget.Button;

import cz.zcu.pkozler.mkz.BaseActivity;

/**
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
    
    public static final int BTN_GRID_ROW_COUNT = 6;
    public static final int BTN_GRID_COL_COUNT = 4;
    public final int ROW;
    public final int COL;
    public final InputMode INPUT_MODE;
    public final FunctionInputMode FUNCTION_INPUT_MODE;
    public final AngleMode ANGLE_MODE;
    public final Boolean HAS_VARIABLE;
    private View.OnClickListener listener;
    private String title;
    
    InputButtonType(int row, int col, InputMode inputMode,
            FunctionInputMode functionInputMode, AngleMode angleMode, Boolean hasVariable) {
        ROW = row;
        COL = col;
        INPUT_MODE = inputMode;
        FUNCTION_INPUT_MODE = functionInputMode;
        ANGLE_MODE = angleMode;
        HAS_VARIABLE = hasVariable;
    }

    public void setTitleAndListener(String title, View.OnClickListener listener) {
        this.title = title;
        this.listener = listener;
    }

    public static Button createButton(BaseActivity activity, InputButtonType type) {
        if (activity == null) {
            return null;
        }

        Button button = new Button(activity);
        button.setMinHeight(0);
        button.setMinimumHeight(0);

        if (type != null) {
            button.setText(type.title);
            button.setOnClickListener(type.listener);
        }
        else {
            button.setText("");
            button.setEnabled(false);
        }

        return button;
    }

}
