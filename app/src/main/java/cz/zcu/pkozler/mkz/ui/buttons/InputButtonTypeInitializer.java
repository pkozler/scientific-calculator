package cz.zcu.pkozler.mkz.ui.buttons;

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
 * Třída sloužící jako inicializátor informací pro jednotlivé typy tlačítek
 * na klávesnici kalkulačky.
 *
 * @author Petr Kozler
 */
public final class InputButtonTypeInitializer {

    /**
     * Provede inicializaci typů tlačítek na klávesnici kalkulačky.
     *
     * @param activeEditTextHandler objekt pro manipulaci se vstupními textovými poli
     * @param buttonGridLayoutHandler objekt pro manipulaci s tlačítky na klávesnici kalkulačky
     */
    public static void initialize(final ActiveEditTextHandler activeEditTextHandler,
                                  final ButtonGridLayoutHandler buttonGridLayoutHandler) {
        InputButtonType.CLEAR_BTN.setAdditionalProperties("C", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.clearString();
            }
        });

        InputButtonType.DELETE_BTN.setAdditionalProperties("\u2190", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.removeChar();
            }
        });

        InputButtonType.DIGIT_MODE_BTN.setAdditionalProperties("#", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setInputMode(InputMode.DIGIT_OPERATOR_MODE);
            }
        });

        InputButtonType.FUNCTION_MODE_BTN.setAdditionalProperties("f(x)", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setInputMode(InputMode.FUNCTION_MODE);
            }
        });

        InputButtonType.FUN_MODE_1_BTN.setAdditionalProperties("1/2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setFunctionInputMode(FunctionInputMode.FUNCTION_MODE_2);
            }
        });

        InputButtonType.FUN_MODE_2_BTN.setAdditionalProperties("2/2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setFunctionInputMode(FunctionInputMode.FUNCTION_MODE_1);
            }
        });

        InputButtonType.DEG_MODE_BTN.setAdditionalProperties("DEG", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setAngleMode(AngleMode.RAD_MODE);
            }
        });

        InputButtonType.RAD_MODE_BTN.setAdditionalProperties("RAD", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setAngleMode(AngleMode.GRAD_MODE);
            }
        });

        InputButtonType.GRAD_MODE_BTN.setAdditionalProperties("GRAD", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGridLayoutHandler.setAngleMode(AngleMode.DEG_MODE);
            }
        });

        InputButtonType.DEC_SEPARATOR_BTN.setAdditionalProperties(".", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit(null);
            }
        });

        InputButtonType.D_0_BTN.setAdditionalProperties("0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('0');
            }
        });

        InputButtonType.D_1_BTN.setAdditionalProperties("1", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('1');
            }
        });

        InputButtonType.D_2_BTN.setAdditionalProperties("2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('2');
            }
        });

        InputButtonType.D_3_BTN.setAdditionalProperties("3", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('3');
            }
        });

        InputButtonType.D_4_BTN.setAdditionalProperties("4", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('4');
            }
        });

        InputButtonType.D_5_BTN.setAdditionalProperties("5", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('5');
            }
        });

        InputButtonType.D_6_BTN.setAdditionalProperties("6", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('6');
            }
        });

        InputButtonType.D_7_BTN.setAdditionalProperties("7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('7');
            }
        });

        InputButtonType.D_8_BTN.setAdditionalProperties("8", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('8');
            }
        });

        InputButtonType.D_9_BTN.setAdditionalProperties("9", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addDigit('9');
            }
        });

        InputButtonType.O_ADD_BTN.setAdditionalProperties("+", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.ADD.toString());
            }
        });

        InputButtonType.O_SUB_BTN.setAdditionalProperties("\u2212", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.SUB.toString());
            }
        });

        InputButtonType.O_MUL_BTN.setAdditionalProperties("\u00D7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.MUL.toString());
            }
        });

        InputButtonType.O_DIV_BTN.setAdditionalProperties("\u00F7", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.DIV.toString());
            }
        });

        InputButtonType.O_POW_BTN.setAdditionalProperties("^", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.POW.toString());
            }
        });

        InputButtonType.O_MOD_BTN.setAdditionalProperties("mod", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addOperator(OperatorTokenType.MOD.toString());
            }
        });

        InputButtonType.C_EU_BTN.setAdditionalProperties("e", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addConstant(ConstantTokenType.E.toString());
            }
        });

        InputButtonType.C_PI_BTN.setAdditionalProperties("\u03C0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addConstant(ConstantTokenType.PI.toString());
            }
        });

        InputButtonType.VAR_BTN.setAdditionalProperties("var", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addVariable();
            }
        });

        InputButtonType.P_LEFT_BTN.setAdditionalProperties("(", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addParenthesis(false);
            }
        });

        InputButtonType.P_RIGHT_BTN.setAdditionalProperties(")", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addParenthesis(true);
            }
        });

        InputButtonType.ARG_SEPARATOR_BTN.setAdditionalProperties(",", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addArgumentSeparator();
            }
        });

        InputButtonType.F_SQ_BTN.setAdditionalProperties("x\u00B2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SQ.toString());
            }
        });

        InputButtonType.F_SQRT_BTN.setAdditionalProperties("\u221Ax", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SQRT.toString());
            }
        });

        InputButtonType.F_CB_BTN.setAdditionalProperties("x\u00B3", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.CB.toString());
            }
        });

        InputButtonType.F_CBRT_BTN.setAdditionalProperties("\u221Bx", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.CBRT.toString());
            }
        });

        InputButtonType.F_POW_BTN.setAdditionalProperties("x\u02B8", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.POW.toString());
            }
        });

        InputButtonType.F_YROOT_BTN.setAdditionalProperties("\u02B8\u221Ax", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.YROOT.toString());
            }
        });

        InputButtonType.F_EXP_BTN.setAdditionalProperties("e^x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.EXP.toString());
            }
        });

        InputButtonType.F_LN_BTN.setAdditionalProperties("ln x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.LN.toString());
            }
        });

        InputButtonType.F_LOG_BTN.setAdditionalProperties("log\u2090 x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.LOG.toString());
            }
        });

        InputButtonType.F_REC_BTN.setAdditionalProperties("x\u207B\u00B9", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.REC.toString());
            }
        });

        InputButtonType.F_ABS_BTN.setAdditionalProperties("|x|", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ABS.toString());
            }
        });

        InputButtonType.F_INT_BTN.setAdditionalProperties("[x]", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.INT.toString());
            }
        });

        InputButtonType.F_FRAC_BTN.setAdditionalProperties("x\u2212[x]", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.FRAC.toString());
            }
        });

        InputButtonType.F_FLOOR_BTN.setAdditionalProperties("\u230Ax\u230B", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.FLOOR.toString());
            }
        });

        InputButtonType.F_CEIL_BTN.setAdditionalProperties("\u2308x\u2309", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.CEIL.toString());
            }
        });

        InputButtonType.F_SGN_BTN.setAdditionalProperties("sgn x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SGN.toString());
            }
        });

        InputButtonType.F_SIND_BTN.setAdditionalProperties("sin x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SIND.toString());
            }
        });

        InputButtonType.F_COSD_BTN.setAdditionalProperties("cos x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSD.toString());
            }
        });

        InputButtonType.F_TAND_BTN.setAdditionalProperties("tan x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TAND.toString());
            }
        });

        InputButtonType.F_ASIND_BTN.setAdditionalProperties("asin x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASIND.toString());
            }
        });

        InputButtonType.F_ACOSD_BTN.setAdditionalProperties("acos x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSD.toString());
            }
        });

        InputButtonType.F_ATAND_BTN.setAdditionalProperties("atan x\u00B0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATAND.toString());
            }
        });

        InputButtonType.F_SINR_BTN.setAdditionalProperties("sin x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SINR.toString());
            }
        });

        InputButtonType.F_COSR_BTN.setAdditionalProperties("cos x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSR.toString());
            }
        });

        InputButtonType.F_TANR_BTN.setAdditionalProperties("tan x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TANR.toString());
            }
        });

        InputButtonType.F_ASINR_BTN.setAdditionalProperties("asin x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASINR.toString());
            }
        });

        InputButtonType.F_ACOSR_BTN.setAdditionalProperties("acos x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSR.toString());
            }
        });

        InputButtonType.F_ATANR_BTN.setAdditionalProperties("atan x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATANR.toString());
            }
        });

        InputButtonType.F_SING_BTN.setAdditionalProperties("sin x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SING.toString());
            }
        });

        InputButtonType.F_COSG_BTN.setAdditionalProperties("cos x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSG.toString());
            }
        });

        InputButtonType.F_TANG_BTN.setAdditionalProperties("tan x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TANG.toString());
            }
        });

        InputButtonType.F_ASING_BTN.setAdditionalProperties("asin x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASING.toString());
            }
        });

        InputButtonType.F_ACOSG_BTN.setAdditionalProperties("acos x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSG.toString());
            }
        });

        InputButtonType.F_ATANG_BTN.setAdditionalProperties("atan x\u1D4D", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATANG.toString());
            }
        });

        InputButtonType.F_SINH_BTN.setAdditionalProperties("sinh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.SINH.toString());
            }
        });

        InputButtonType.F_COSH_BTN.setAdditionalProperties("cosh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.COSH.toString());
            }
        });

        InputButtonType.F_TANH_BTN.setAdditionalProperties("tanh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.TANH.toString());
            }
        });

        InputButtonType.F_ASINH_BTN.setAdditionalProperties("asinh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ASINH.toString());
            }
        });

        InputButtonType.F_ACOSH_BTN.setAdditionalProperties("acosh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ACOSH.toString());
            }
        });

        InputButtonType.F_ATANH_BTN.setAdditionalProperties("atanh x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.ATANH.toString());
            }
        });

        InputButtonType.F_DMS_BTN.setAdditionalProperties("dms", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.DMS.toString());
            }
        });

        InputButtonType.F_DEG_BTN.setAdditionalProperties("deg", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.DEG.toString());
            }
        });

        InputButtonType.F_FACT_BTN.setAdditionalProperties("n!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditTextHandler.addFunction(FunctionTokenType.FACT.toString());
            }
        });
    }

}
