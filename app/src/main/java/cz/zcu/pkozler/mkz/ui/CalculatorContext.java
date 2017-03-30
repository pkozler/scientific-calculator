package cz.zcu.pkozler.mkz.ui;

import android.app.Application;

import cz.zcu.pkozler.mkz.core.Evaluator;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;
import cz.zcu.pkozler.mkz.ui.handlers.ButtonGridLayoutHandler;
import cz.zcu.pkozler.mkz.ui.handlers.buttons.InputButtonTypeInitializer;
import cz.zcu.pkozler.mkz.ui.modes.AngleMode;
import cz.zcu.pkozler.mkz.ui.modes.FunctionInputMode;
import cz.zcu.pkozler.mkz.ui.modes.InputMode;

/**
 *
 * @author Petr Kozler
 */
public class CalculatorContext extends Application {

    private ButtonGridLayoutHandler buttonGridLayoutHandler;
    private ActiveEditTextHandler activeEditTextHandler;
    private Evaluator evaluator;

    public CalculatorContext() {
        evaluator = new Evaluator();
        buttonGridLayoutHandler = new ButtonGridLayoutHandler(
                InputMode.DIGIT_OPERATOR_MODE, FunctionInputMode.FUNCTION_MODE_1, AngleMode.RAD_MODE, false);
        activeEditTextHandler = new ActiveEditTextHandler();
        InputButtonTypeInitializer.initialize(activeEditTextHandler, buttonGridLayoutHandler);
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public ButtonGridLayoutHandler getButtonGridLayoutHandler() {
        return buttonGridLayoutHandler;
    }

    public ActiveEditTextHandler getActiveEditTextHandler() {
        return activeEditTextHandler;
    }

}
