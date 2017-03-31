package cz.zcu.pkozler.mkz.ui.handlers;

import android.widget.Button;
import android.widget.GridLayout;

import cz.zcu.pkozler.mkz.BaseActivity;
import cz.zcu.pkozler.mkz.ui.buttons.InputButtonType;
import cz.zcu.pkozler.mkz.ui.modes.AngleMode;
import cz.zcu.pkozler.mkz.ui.modes.FunctionInputMode;
import cz.zcu.pkozler.mkz.ui.modes.InputMode;

/**
 * Třída, sloužící jako obal nad klávesnicí kalkulačky s tlačítky pro zadávání vstupů.
 * Poskytuje rozhraní pro přepínání režimů kalkulačky a v případě přepnutí režimu
 * nebo zobrazení nové aktivity provádí výměnu tlačítek a jejich umístění na klávesnici
 * v právě zobrazené aktivitě.
 *
 * @author Petr Kozler
 */
public class ButtonGridLayoutHandler {

    /**
     * právě aktivní komponenta pro umístění klávesnice
     */
    private GridLayout gridLayout;

    /**
     * právě zobrazená aktivita
     */
    private BaseActivity activity;

    /**
     * základní režim klávesnice
     */
    private InputMode inputMode;

    /**
     * režim matematických funkcí
     */
    private FunctionInputMode functionInputMode;

    /**
     * režim úhlových jednotek
     */
    private AngleMode angleMode;

    /**
     * příznak výpočtů s proměnnou v právě zobrazené aktivitě
     */
    private boolean hasVariable;

    /**
     * Vytvoří nový objekt pro manipulaci s klávesnicí kalkulačky.
     *
     * @param inputMode základní režim klávesnice
     * @param functionInputMode režim matematických funkcí
     * @param angleMode režim úhlových jednotek
     * @param hasVariable příznak výpočtů s proměnnou v právě zobrazené aktivitě
     */
    public ButtonGridLayoutHandler(InputMode inputMode, FunctionInputMode functionInputMode, AngleMode angleMode, boolean hasVariable) {
        this.inputMode = inputMode;
        this.functionInputMode = functionInputMode;
        this.angleMode = angleMode;
        this.hasVariable = hasVariable;
    }

    /**
     * Nastaví právě aktivní komponenta pro umístění klávesnice
     * v právě zobrazené aktivitě.
     *
     * @param activity právě zobrazená aktivita
     * @param gridLayout právě aktivní komponenta pro umístění klávesnice
     */
    public void setGridLayout(BaseActivity activity, GridLayout gridLayout) {
        this.activity = activity;
        this.gridLayout = gridLayout;
        updateButtonGrid();
    }

    /**
     * Nastaví příznak výpočtů s proměnnou v právě zobrazené aktivitě.
     *
     * @param hasVariable příznak výpočtů s proměnnou v právě zobrazené aktivitě
     */
    public void setVariableMode(boolean hasVariable) {
        this.hasVariable = hasVariable;
        updateButtonGrid();
    }

    /**
     * Nastaví režim úhlových jednotek.
     *
     * @param angleMode režim úhlových jednotek
     */
    public void setAngleMode(AngleMode angleMode) {
        this.angleMode = angleMode;
        updateButtonGrid();
    }

    /**
     * Nastaví režim matematických funkcí.
     *
     * @param functionInputMode režim matematických funkcí
     */
    public void setFunctionInputMode(FunctionInputMode functionInputMode) {
        this.functionInputMode = functionInputMode;
        updateButtonGrid();
    }

    /**
     * Nastaví základní režim klávesnice.
     *
     * @param inputMode základní režim klávesnice
     */
    public void setInputMode(InputMode inputMode) {
        this.inputMode = inputMode;
        updateButtonGrid();
    }

    /**
     * Provede aktualizaci právě aktivní komponenty představující klávesnici
     * při změně režimu klávesnice nebo přechodu do jiné aktivity.
     */
    private void updateButtonGrid() {
        if (gridLayout == null) {
            return;
        }

        // vytvoření matice pro uspořádání nové konfigurace tlačítek do mřížky
        InputButtonType[][] buttons = new InputButtonType[InputButtonType.BTN_GRID_ROW_COUNT][InputButtonType.BTN_GRID_COL_COUNT];

        for (InputButtonType buttonType : InputButtonType.values()) {
            if ((buttonType.INPUT_MODE == null || buttonType.INPUT_MODE == inputMode)
                    && (buttonType.FUNCTION_INPUT_MODE == null || buttonType.FUNCTION_INPUT_MODE == functionInputMode)
                    && (buttonType.ANGLE_MODE == null || buttonType.ANGLE_MODE == angleMode)
                    && (buttonType.HAS_VARIABLE == null || buttonType.HAS_VARIABLE == hasVariable)) {
                // uložení typu tlačítka odpovídajícího aktuálně zvolenému režimu kalkulačky na příslušnou pozici v matici
                buttons[buttonType.ROW][buttonType.COL] = buttonType;
            }
        }

        gridLayout.removeAllViews();

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                // vytvoření grafické komponenty tlačítka a její umístění na pozici v mřížce, která odpovídá indexu v matici
                Button button = createButton(activity, buttons[i][j]);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                button.setLayoutParams(params);
                gridLayout.addView(button);
            }
        }
    }

    /**
     * Vytvoří komponentu tlačítka zadaného typu.
     *
     * @param activity právě zobrazená aktivita
     * @param type typ tlačítka
     * @return komponenta tlačítka
     */
    private static Button createButton(BaseActivity activity, InputButtonType type) {
        if (activity == null) {
            return null;
        }

        // nastavení vzhledu tlačítka
        Button button = new Button(activity);
        button.setMinHeight(0);
        button.setMinimumHeight(0);
        button.setAllCaps(false);

        if (type != null) {
            // nastavení popisku tlačítka a posluchače stisku
            button.setText(type.getText());
            button.setOnClickListener(type.getListener());
        }
        else {
            // deaktivace tlačítka, pokud byla jako typ předána hodnota null
            button.setText("");
            button.setEnabled(false);
        }

        return button;
    }

}
