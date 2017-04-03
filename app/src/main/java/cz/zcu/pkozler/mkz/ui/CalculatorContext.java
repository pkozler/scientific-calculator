package cz.zcu.pkozler.mkz.ui;

import android.app.Application;

import cz.zcu.pkozler.mkz.core.Evaluator;
import cz.zcu.pkozler.mkz.core.tokens.TokenParsing;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;
import cz.zcu.pkozler.mkz.ui.handlers.ButtonGridLayoutHandler;
import cz.zcu.pkozler.mkz.ui.buttons.InputButtonTypeInitializer;
import cz.zcu.pkozler.mkz.ui.modes.AngleMode;
import cz.zcu.pkozler.mkz.ui.modes.FunctionInputMode;
import cz.zcu.pkozler.mkz.ui.modes.InputMode;

/**
 * Třída, představující aplikační kontext. Uchovává komponenty, ke kterým je přistupováno "globálně"
 * z různých aktivit a jsou používány po většinu doby běhu programu.
 *
 * @author Petr Kozler
 */
public class CalculatorContext extends Application {

    /**
     * objekt pro manipulaci s klávesnicí kalkulačky
     */
    private ButtonGridLayoutHandler buttonGridLayoutHandler;

    /**
     * objekt pro manipulaci s aktivním textovým polem
     */
    private ActiveEditTextHandler activeEditTextHandler;

    /**
     * objekt pro zpracovávání a vyhodnocování zadaných výrazů
     */
    private Evaluator evaluator;

    /**
     * Vytvoří nový kontejner pro globálně přístupné komponenty aplikace.
     */
    public CalculatorContext() {
        // inicializace jednotlivých komponent
        TokenParsing tokenParsing = new TokenParsing();
        evaluator = new Evaluator(tokenParsing);
        buttonGridLayoutHandler = new ButtonGridLayoutHandler(
                InputMode.DIGIT_OPERATOR_MODE, FunctionInputMode.FUNCTION_MODE_1, AngleMode.RAD_MODE, false);
        activeEditTextHandler = new ActiveEditTextHandler();
        InputButtonTypeInitializer.initialize(activeEditTextHandler, buttonGridLayoutHandler);
    }

    /**
     * Vrátí objekt pro zpracovávání a vyhodnocování zadaných výrazů.
     *
     * @return objekt pro zpracovávání a vyhodnocování zadaných výrazů
     */
    public Evaluator getEvaluator() {
        return evaluator;
    }

    /**
     * Vrátí objekt pro manipulaci s klávesnicí kalkulačky.
     *
     * @return objekt pro manipulaci s klávesnicí kalkulačky
     */
    public ButtonGridLayoutHandler getButtonGridLayoutHandler() {
        return buttonGridLayoutHandler;
    }

    /**
     * Vrátí objekt pro manipulaci s aktivním textovým polem.
     *
     * @return objekt pro manipulaci s aktivním textovým polem
     */
    public ActiveEditTextHandler getActiveEditTextHandler() {
        return activeEditTextHandler;
    }

}
