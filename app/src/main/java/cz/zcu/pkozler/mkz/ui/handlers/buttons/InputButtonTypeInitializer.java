package cz.zcu.pkozler.mkz.ui.handlers.buttons;

import android.view.View;

import cz.zcu.pkozler.mkz.core.tokens.types.ConstantTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.FunctionTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;
import cz.zcu.pkozler.mkz.ui.handlers.ButtonGridLayoutHandler;
import cz.zcu.pkozler.mkz.ui.modes.AngleMode;
import cz.zcu.pkozler.mkz.ui.modes.FunctionInputMode;
import cz.zcu.pkozler.mkz.ui.modes.InputMode;

/**
 *
 * @author Petr Kozler
 */
public class InputButtonTypeInitializer {

    public static void initialize(final ActiveEditTextHandler activeEditTextHandler,
                                  final ButtonGridLayoutHandler buttonGridLayoutHandler) {
        InputButtonType.setAdditionalProperties(InputButtonType.CLEAR_BTN, "C", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.clearString();
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.DELETE_BTN, "\u2190", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.removeChar();
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.DIGIT_MODE_BTN, "#", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setInputMode(InputMode.DIGIT_OPERATOR_MODE);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.FUNCTION_MODE_BTN, "f(x)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setInputMode(InputMode.FUNCTION_MODE);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.FUN_MODE_1_BTN, "1/2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setFunctionInputMode(FunctionInputMode.FUNCTION_MODE_2);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.FUN_MODE_2_BTN, "2/2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setFunctionInputMode(FunctionInputMode.FUNCTION_MODE_1);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.DEG_MODE_BTN, "DEG", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setAngleMode(AngleMode.RAD_MODE);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.RAD_MODE_BTN, "RAD", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setAngleMode(AngleMode.GRAD_MODE);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.GRAD_MODE_BTN, "GRAD", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setAngleMode(AngleMode.DEG_MODE);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.DEC_SEPARATOR_BTN, ".", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit(null);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_0_BTN, "0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('0');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_1_BTN, "1", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('1');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_2_BTN, "2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('2');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_3_BTN, "3", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('3');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_4_BTN, "4", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('4');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_5_BTN, "5", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('5');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_6_BTN, "6", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('6');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_7_BTN, "7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('7');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_8_BTN, "8", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('8');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.D_9_BTN, "9", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('9');
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.O_ADD_BTN, "+", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.ADD.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.O_SUB_BTN, "\u2212", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.SUB.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.O_MUL_BTN, "\u00D7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.MUL.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.O_DIV_BTN, "\u00F7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.DIV.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.O_POW_BTN, "^", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.POW.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.O_MOD_BTN, "mod", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.MOD.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.C_EU_BTN, "e", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addConstant(ConstantTokenType.E.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.C_PI_BTN, "\u03C0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addConstant(ConstantTokenType.PI.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.VAR_BTN, "var", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addVariable();
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.P_LEFT_BTN, "(", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addParenthesis(false);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.P_RIGHT_BTN, ")", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addParenthesis(true);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.ARG_SEPARATOR_BTN, ",", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addArgumentSeparator();
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SQ_BTN, "x\u00B2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SQ.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SQRT_BTN, "\u221Ax", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SQRT.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_CB_BTN, "x\u00B3", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.CB.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_CBRT_BTN, "\u221Bx", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.CBRT.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_POW_BTN, "x\u02B8", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.POW.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_YROOT_BTN, "\u02B8\u221Ax", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.YROOT.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_EXP_BTN, "e^x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.EXP.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_LN_BTN, "ln x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.LN.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_LOG_BTN, "log\u2090 x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.LOG.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_REC_BTN, "x\u207B\u00B9", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.REC.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ABS_BTN, "|x|", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ABS.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_INT_BTN, "[x]", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.INT.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_FRAC_BTN, "x\u2212[x]", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.FRAC.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_FLOOR_BTN, "\u230Ax\u230B", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.FLOOR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_CEIL_BTN, "\u2308x\u2309", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.CEIL.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SGN_BTN, "sgn x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SGN.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SIND_BTN, "sin x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SIND.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_COSD_BTN, "cos x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSD.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_TAND_BTN, "tan x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TAND.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ASIND_BTN, "asin x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASIND.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ACOSD_BTN, "acos x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSD.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ATAND_BTN, "atan x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATAND.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SINR_BTN, "sin x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SINR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_COSR_BTN, "cos x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_TANR_BTN, "tan x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TANR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ASINR_BTN, "asin x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASINR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ACOSR_BTN, "acos x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ATANR_BTN, "atan x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATANR.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SING_BTN, "sin x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SING.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_COSG_BTN, "cos x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSG.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_TANG_BTN, "tan x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TANG.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ASING_BTN, "asin x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASING.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ACOSG_BTN, "acos x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSG.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ATANG_BTN, "atan x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATANG.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_SINH_BTN, "sinh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SINH.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_COSH_BTN, "cosh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSH.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_TANH_BTN, "tanh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TANH.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ASINH_BTN, "asinh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASINH.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ACOSH_BTN, "acosh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSH.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_ATANH_BTN, "atanh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATANH.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_DMS_BTN, "dms", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.DMS.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_DEG_BTN, "deg", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.DEG.KEYWORD);
            }
        });

        InputButtonType.setAdditionalProperties(InputButtonType.F_FACT_BTN, "n!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.FACT.KEYWORD);
            }
        });
    }

}
