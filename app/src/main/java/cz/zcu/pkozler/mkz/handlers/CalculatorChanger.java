package cz.zcu.pkozler.mkz.handlers;

import android.app.Application;
import android.view.View;

import cz.zcu.pkozler.mkz.core.tokens.ConstantType;
import cz.zcu.pkozler.mkz.core.tokens.FunctionType;
import cz.zcu.pkozler.mkz.core.tokens.OperatorType;

/**
 *
 * @author Petr Kozler
 */
public class CalculatorChanger extends Application {

    private ButtonGridLayoutChanger buttonGridLayoutChanger;
    private ActiveTextFieldChanger activeTextFieldChanger;
    private InputMode inputMode;
    private FunctionInputMode functionInputMode;
    private AngleMode angleMode;
    private boolean hasVariable;

    public CalculatorChanger() {
        buttonGridLayoutChanger = new ButtonGridLayoutChanger();
        activeTextFieldChanger = new ActiveTextFieldChanger();

        createButtonListeners();

        inputMode = InputMode.DIGIT_OPERATOR_MODE;
        functionInputMode = FunctionInputMode.FUNCTION_MODE_1;
        angleMode = AngleMode.RAD_MODE;
        hasVariable = false;
    }

    public ButtonGridLayoutChanger getButtonGridLayoutChanger() {
        return buttonGridLayoutChanger;
    }

    public ActiveTextFieldChanger getActiveTextFieldChanger() {
        return activeTextFieldChanger;
    }

    public void setVariableMode(boolean hasVariable) {
        this.hasVariable = hasVariable;
        changeButtonPanel();
    }

    private void setAngleMode(AngleMode angleMode) {
        this.angleMode = angleMode;
        changeButtonPanel();
    }

    private void setFunctionInputMode(FunctionInputMode functionInputMode) {
        this.functionInputMode = functionInputMode;
        changeButtonPanel();
    }

    private void setInputMode(InputMode inputMode) {
        this.inputMode = inputMode;
        changeButtonPanel();
    }

    private void changeButtonPanel() {
        InputButtonType[][] buttons = new InputButtonType[InputButtonType.BTN_GRID_ROW_COUNT][InputButtonType.BTN_GRID_COL_COUNT];

        for (InputButtonType buttonType : InputButtonType.values()) {
            if ((buttonType.INPUT_MODE == null || buttonType.INPUT_MODE == inputMode)
                    && (buttonType.FUNCTION_INPUT_MODE == null || buttonType.FUNCTION_INPUT_MODE == functionInputMode)
                    && (buttonType.ANGLE_MODE == null || buttonType.ANGLE_MODE == angleMode)
                    && (buttonType.HAS_VARIABLE == null || buttonType.HAS_VARIABLE == hasVariable)) {
                buttons[buttonType.ROW][buttonType.COL] = buttonType;
            }
        }

        buttonGridLayoutChanger.setCurrentButtons(buttons);
    }

    private void createInputButton(InputButtonType type, String text, View.OnClickListener listener) {
        type.setTitleAndListener(text, listener);
    }

    private void createButtonListeners() {
        createInputButton(InputButtonType.CLEAR_BTN, "C", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.clearString();
            }
        });

        createInputButton(InputButtonType.DELETE_BTN, "\u2190", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.removeChar();
            }
        });

        createInputButton(InputButtonType.DIGIT_MODE_BTN, "#", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputMode(InputMode.DIGIT_OPERATOR_MODE);
            }
        });

        createInputButton(InputButtonType.FUNCTION_MODE_BTN, "f(x)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputMode(InputMode.FUNCTION_MODE);
            }
        });

        createInputButton(InputButtonType.FUN_MODE_1_BTN, "1/2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFunctionInputMode(FunctionInputMode.FUNCTION_MODE_2);
            }
        });

        createInputButton(InputButtonType.FUN_MODE_2_BTN, "2/2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFunctionInputMode(FunctionInputMode.FUNCTION_MODE_1);
            }
        });

        createInputButton(InputButtonType.DEG_MODE_BTN, "DEG", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAngleMode(AngleMode.RAD_MODE);
            }
        });

        createInputButton(InputButtonType.RAD_MODE_BTN, "RAD", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAngleMode(AngleMode.GRAD_MODE);
            }
        });

        createInputButton(InputButtonType.GRAD_MODE_BTN, "GRAD", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAngleMode(AngleMode.DEG_MODE);
            }
        });

        createInputButton(InputButtonType.DEC_SEPARATOR_BTN, ".", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit(null);
            }
        });

        createInputButton(InputButtonType.D_0_BTN, "0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('0');
            }
        });

        createInputButton(InputButtonType.D_1_BTN, "1", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('1');
            }
        });

        createInputButton(InputButtonType.D_2_BTN, "2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('2');
            }
        });

        createInputButton(InputButtonType.D_3_BTN, "3", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('3');
            }
        });

        createInputButton(InputButtonType.D_4_BTN, "4", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('4');
            }
        });

        createInputButton(InputButtonType.D_5_BTN, "5", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('5');
            }
        });

        createInputButton(InputButtonType.D_6_BTN, "6", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('6');
            }
        });

        createInputButton(InputButtonType.D_7_BTN, "7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('7');
            }
        });

        createInputButton(InputButtonType.D_8_BTN, "8", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('8');
            }
        });

        createInputButton(InputButtonType.D_9_BTN, "9", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addDigit('9');
            }
        });

        createInputButton(InputButtonType.O_ADD_BTN, "+", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addOperator(OperatorType.ADD.KEYWORD);
            }
        });

        createInputButton(InputButtonType.O_SUB_BTN, "\u2212", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addOperator(OperatorType.SUB.KEYWORD);
            }
        });

        createInputButton(InputButtonType.O_MUL_BTN, "\u00D7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addOperator(OperatorType.MUL.KEYWORD);
            }
        });

        createInputButton(InputButtonType.O_DIV_BTN, "\u00F7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addOperator(OperatorType.DIV.KEYWORD);
            }
        });

        createInputButton(InputButtonType.O_POW_BTN, "^", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addOperator(OperatorType.POW.KEYWORD);
            }
        });

        createInputButton(InputButtonType.O_MOD_BTN, "mod", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addOperator(OperatorType.MOD.KEYWORD);
            }
        });

        createInputButton(InputButtonType.C_EU_BTN, "e", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addConstant(ConstantType.E.KEYWORD);
            }
        });

        createInputButton(InputButtonType.C_PI_BTN, "\u03C0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addConstant(ConstantType.PI.KEYWORD);
            }
        });

        createInputButton(InputButtonType.VAR_BTN, "var", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addVariable();
            }
        });

        createInputButton(InputButtonType.P_LEFT_BTN, "(", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addParenthesis(false);
            }
        });

        createInputButton(InputButtonType.P_RIGHT_BTN, ")", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addParenthesis(true);
            }
        });

        createInputButton(InputButtonType.ARG_SEPARATOR_BTN, ",", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addArgumentSeparator();
            }
        });

        createInputButton(InputButtonType.F_SQ_BTN, "x\u00B2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SQ.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_SQRT_BTN, "\u221Ax", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SQRT.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_CB_BTN, "x\u00B3", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.CB.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_CBRT_BTN, "\u221Bx", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.CBRT.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_POW_BTN, "x\u02B8", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.POW.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_YROOT_BTN, "\u02B8\u221Ax", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.YROOT.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_EXP_BTN, "e^x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.EXP.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_LN_BTN, "ln x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.LN.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_LOG_BTN, "log\u2090 x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.LOG.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_REC_BTN, "x\u207B\u00B9", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.REC.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ABS_BTN, "|x|", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ABS.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_INT_BTN, "[x]", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.INT.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_FRAC_BTN, "x\u2212[x]", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.FRAC.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_FLOOR_BTN, "\u230Ax\u230B", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.FLOOR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_CEIL_BTN, "\u2308x\u2309", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.CEIL.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_SGN_BTN, "sgn x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SGN.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_SIND_BTN, "sin x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SIND.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_COSD_BTN, "cos x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.COSD.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_TAND_BTN, "tan x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.TAND.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ASIND_BTN, "asin x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ASIND.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ACOSD_BTN, "acos x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ACOSD.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ATAND_BTN, "atan x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ATAND.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_SINR_BTN, "sin x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SINR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_COSR_BTN, "cos x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.COSR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_TANR_BTN, "tan x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.TANR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ASINR_BTN, "asin x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ASINR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ACOSR_BTN, "acos x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ACOSR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ATANR_BTN, "atan x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ATANR.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_SING_BTN, "sin x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SING.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_COSG_BTN, "cos x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.COSG.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_TANG_BTN, "tan x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.TANG.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ASING_BTN, "asin x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ASING.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ACOSG_BTN, "acos x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ACOSG.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ATANG_BTN, "atan x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ATANG.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_SINH_BTN, "sinh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.SINH.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_COSH_BTN, "cosh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.COSH.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_TANH_BTN, "tanh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.TANH.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ASINH_BTN, "asinh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ASINH.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ACOSH_BTN, "acosh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ACOSH.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_ATANH_BTN, "atanh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.ATANH.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_DMS_BTN, "dms", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.DMS.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_DEG_BTN, "deg", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.DEG.KEYWORD);
            }
        });

        createInputButton(InputButtonType.F_FACT_BTN, "n!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTextFieldChanger.addFunction(FunctionType.FACT.KEYWORD);
            }
        });
    }

}
