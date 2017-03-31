package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.customviews.PlotView;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;

/**
 * Třída aktivity představující sekci pro zobrazování průběhu matematických funkcí do grafu.
 *
 * @author Petr Kozler
 */
public class PlotActivity extends BaseActivity {

    /**
     * vstupní textové pole pro zadávání funkce
     */
    private EditText inputText;

    /**
     * výstupní textové pole pro výpis výsledku
     */
    private TextView outputTextView;

    /**
     * komponenta pro vykreslení grafu
     */
    private PlotView plotView;

    /**
     * posluchač události pohybu po obrazovce nad komponentou grafu
     */
    private PlotView.TouchListener touchListener;

    public PlotActivity() {
        super(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        // nastavení toolbaru
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // uložení referencí na prvky uživatelského rozhraní
        inputText = (EditText)findViewById(R.id.plotInputText);
        outputTextView = (TextView)findViewById(R.id.plotOutputTextView);
        plotView = (PlotView)findViewById(R.id.plotView);

        createErrorMessages();

        // nastavení posluchačů pro ovládací prvky
        createOnFocusChangeListener(getCalculatorContext().getActiveEditTextHandler(), inputText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // nastavení příznaku provádění operací s proměnnými
        GridLayout gridLayout = (GridLayout)findViewById(R.id.plotGridLayout);
        getCalculatorContext().getButtonGridLayoutHandler().setVariableMode(true);

        // přepnutí odkazu na právě aktivní vstupní textové pole v obalové komponentě
        ActiveEditTextHandler activeEditTextHandler = getCalculatorContext().getActiveEditTextHandler();
        activeEditTextHandler.setActiveTextField(inputText);

        // přepnutí odkazu na panel pro zobrazení klávesnice kalkulačky v obalové komponentě
        getCalculatorContext().getButtonGridLayoutHandler().setGridLayout(this, gridLayout);

        // vytvoření posluchače pohybu v grafu a posluchačů stisku tlačítek pro změnu měřítka grafu
        Button zoomInButton = (Button)findViewById(R.id.zoomInButton);
        Button zoomOutButton = (Button)findViewById(R.id.zoomOutButton);
        touchListener = plotView.createGraphListeners(zoomInButton, zoomOutButton);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // uložení aktuálně zobrazených výsledků vizualizace funkce před změnou orientace obrazovky
        String result = outputTextView.getText().toString();
        savedInstanceState.putString("Result", result == null ? "" : result);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // obnovení posledních zobrazených výsledků vizualizace funkce po změně orientace obrazovky
        String result = savedInstanceState.getString("Result");
        outputTextView.setText(result);
        plotView.draw(inputText.getText().toString(), getCalculatorContext().getEvaluator(),
                outputTextView, touchListener, errorMessages);
    }

    /**
     * Ověří vstup v textovém poli pro zadání matematické funkce a zavolá funkci pro spuštění
     * vizualizace grafu se zobrazením průběhu zadané matematické funkce.
     *
     * @param v
     */
    public void startDrawing(View v) {
        String input = inputText.getText().toString();

        // test vyplnění vstupního textového pole
        if (input.isEmpty()) {
            outputTextView.setText(R.string.plot_empty_output);

            return;
        }

        // spuštění vizualizace grafu
        plotView.draw(input, getCalculatorContext().getEvaluator(),
                outputTextView, touchListener, errorMessages);
    }

}
