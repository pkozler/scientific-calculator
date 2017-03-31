package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;

/**
 * Třída aktivity představující sekci pro vyhodnocování zadaných matematických výrazů.
 *
 * @author Petr Kozler
 */
public class CalcActivity extends BaseActivity {

    /**
     * vstupní textové pole pro zadávání výrazu
     */
    private EditText inputText;

    /**
     * výstupní textové pole pro výpis výsledku
     */
    private TextView outputTextView;

    /**
     * Třída, představující posluchač události změny řetězce ve vstupním textovém poli.
     * Při každé změně řetězce je automaticky provedeno jeho zpracování a vyhodnocení
     * a zobrazen výsledek do výstupního textového pole, díky čemuž není nutné
     * potvrzování zadaného vstupu tlačítkem.
     */
    private class TextChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // žádná akce
        }

        @Override
        public void afterTextChanged(Editable s) {
            // žádná akce
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String inputStr = inputText.getText().toString();

            try {
                // zpracování a vyhodnocení zadaného výrazu
                getCalculatorContext().getEvaluator().parse(inputStr);
                double outputVal = getCalculatorContext().getEvaluator().evaluate(null);

                // výpis výsledku vyhodnocení v případě platného vstupu
                outputTextView.setText(" = " + outputVal);
            }
            catch (EvaluatorException e) {
                // výpis chybové hlášky v případě neplatného vstupu
                outputTextView.setText(errorMessages.get(e.CODE));
            }
        }

    }

    public CalcActivity() {
        super(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        // nastavení toolbaru
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // uložení referencí na prvky uživatelského rozhraní
        inputText = (EditText)findViewById(R.id.calcInputText);
        outputTextView = (TextView)findViewById(R.id.calcOutputTextView);

        createErrorMessages();

        // nastavení posluchačů pro ovládací prvky
        createOnFocusChangeListener(getCalculatorContext().getActiveEditTextHandler(), inputText);
        inputText.addTextChangedListener(new TextChangedListener());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // nastavení příznaku provádění operací s proměnnými
        GridLayout gridLayout = (GridLayout)findViewById(R.id.calcGridLayout);
        getCalculatorContext().getButtonGridLayoutHandler().setVariableMode(false);

        // přepnutí odkazu na právě aktivní vstupní textové pole v obalové komponentě
        ActiveEditTextHandler activeEditTextHandler = getCalculatorContext().getActiveEditTextHandler();
        activeEditTextHandler.setActiveTextField(inputText);

        // přepnutí odkazu na panel pro zobrazení klávesnice kalkulačky v obalové komponentě
        getCalculatorContext().getButtonGridLayoutHandler().setGridLayout(this, gridLayout);
    }

}
