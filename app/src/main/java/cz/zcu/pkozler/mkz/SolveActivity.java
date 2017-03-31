package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.solving.EquationSolutionContainer;
import cz.zcu.pkozler.mkz.solving.solvers.BisectionEquationSolver;
import cz.zcu.pkozler.mkz.solving.solvers.AEquationSolver;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;

/**
 * Třída aktivity představující sekci pro hledání řešení rovnic pomocí numerických metod.
 *
 * @author Petr Kozler
 */
public class SolveActivity extends BaseActivity {

    /**
     * vstupní textové pole pro zadávání levé strany rovnice
     */
    private EditText leftInputText;

    /**
     * vstupní textové pole pro zadávání pravé strany rovnice
     */
    private EditText rightInputText;

    /**
     * vstupní pole pro zadávání desetinných čísel představujících dolní mez intervalu hledání řešení
     */
    private EditText lowerBoundaryInputText;

    /**
     * vstupní pole pro zadávání desetinných čísel představujících horní mez intervalu hledání řešení
     */
    private EditText upperBoundaryInputText;

    /**
     * vstupní pole pro zadávání celých čísel představujících počet kroků v hledání řešení
     */
    private EditText stepCountInputText;

    /**
     * výstupní textové pole pro výpis výsledku
     */
    private TextView outputTextView;

    /**
     * komponenta pro zobrazení seznamu nalezených řešení
     */
    private ListView listView;

    /**
     * adaptér propojující seznam hodnot představujících řešení s grafickou komponentou seznamu
     */
    private ArrayAdapter<Double> adapter;

    /**
     * seznam číselných hodnot představujících nalezená řešení
     */
    private List<Double> list;

    public SolveActivity() {
        super(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        // nastavení toolbaru
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // uložení referencí na prvky uživatelského rozhraní
        leftInputText = (EditText)findViewById(R.id.solveLeftInputText);
        rightInputText = (EditText)findViewById(R.id.solveRightInputText);
        lowerBoundaryInputText = (EditText)findViewById(R.id.solveLowerBoundaryText);
        upperBoundaryInputText = (EditText)findViewById(R.id.solveUpperBoundaryTextField);
        stepCountInputText = (EditText)findViewById(R.id.solveStepCountText);
        outputTextView = (TextView)findViewById(R.id.solveOutputTextView);
        listView = (ListView)findViewById(R.id.solveListView);

        createErrorMessages();

        // nastavení posluchačů pro ovládací prvky
        createOnFocusChangeListener(getCalculatorContext().getActiveEditTextHandler(),
                leftInputText, rightInputText, lowerBoundaryInputText, upperBoundaryInputText, stepCountInputText);

        // vytvoření objektů pro seznam řešení
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, R.id.textView, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.solveGridLayout);

        // nastavení příznaku provádění operací s proměnnými
        getCalculatorContext().getButtonGridLayoutHandler().setVariableMode(true);

        // přepnutí odkazu na právě aktivní vstupní textové pole v obalové komponentě
        ActiveEditTextHandler activeEditTextHandler = getCalculatorContext().getActiveEditTextHandler();
        selectTextFieldToActivate(activeEditTextHandler, leftInputText,
                leftInputText, rightInputText);

        // přepnutí odkazu na panel pro zobrazení klávesnice kalkulačky v obalové komponentě
        getCalculatorContext().getButtonGridLayoutHandler().setGridLayout(this, gridLayout);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // uložení aktuálně zobrazených výsledků hledání řešení rovnice před změnou orientace obrazovky
        String result = outputTextView.getText().toString();
        savedInstanceState.putString("Result", result == null ? "" : result);
        savedInstanceState.putSerializable("Solutions", (Serializable) list);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // obnovení posledních zobrazených výsledků hledání řešení rovnice po změně orientace obrazovky
        String result = savedInstanceState.getString("Result");
        outputTextView.setText(result);
        List<Double> solutions = (List<Double>) savedInstanceState.getSerializable("Solutions");

        if (solutions != null) {
            list.addAll(solutions);
        }
    }

    /**
     * Vybere aktivní vstupní textové pole za účelem předání jeho reference do objektu
     * pro manipulaci s jeho obsahem z posluchačů stisku tlačítek na klávesnici kalkulačky.
     *
     * @param activeEditTextHandler komponenta pro manipulaci s obsahem aktivního textového pole
     * @param defaultEditText výchozí aktivní vstupní pole
     * @param editTexts volitelná další vstupní pole, která lze aktivovat
     */
    private void selectTextFieldToActivate(ActiveEditTextHandler activeEditTextHandler,
                                           EditText defaultEditText, EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (activeEditTextHandler.getActiveTextField() == editText) {
                activeEditTextHandler.setActiveTextField(editText);

                return;
            }
        }

        activeEditTextHandler.setActiveTextField(defaultEditText);
    }

    /**
     * Ověří vstupy v textových polích pro zadání stran rovnice a parametrů pro algoritmus
     * hledání řešení, a následně zavolá funkci pro spuštění hledání řešení.
     *
     * @param v
     */
    public void startSolving(View v) {
        String left = leftInputText.getText().toString();

        // test vyplnění vstupního textového pole pro levou stranu rovnice
        if (left.isEmpty()) {
            outputTextView.setText(R.string.solve_empty_output_left);

            return;
        }

        String right = rightInputText.getText().toString();

        // test vyplnění vstupního textového pole pro pravou stranu rovnice
        if (right.isEmpty()) {
            outputTextView.setText(R.string.solve_empty_output_right);

            return;
        }

        double lowerBoundary;

        try {
            // test správného formátu obsahu vstupního pole pro dolní mez intervalu
            lowerBoundary = Double.parseDouble(lowerBoundaryInputText.getText().toString());
        }
        catch (NumberFormatException e) {
            outputTextView.setText(R.string.solve_empty_output_lower_boundary);

            return;
        }

        double upperBoundary;

        try {
            // test správného formátu obsahu vstupního pole pro horní mez intervalu
            upperBoundary = Double.parseDouble(upperBoundaryInputText.getText().toString());
        }
        catch (NumberFormatException e) {
            outputTextView.setText(R.string.solve_empty_output_upper_boundary);

            return;
        }

        int stepCount;

        try {
            // test správného formátu obsahu vstupního pole pro počet kroků hledání řešení
            stepCount = Integer.parseInt(stepCountInputText.getText().toString());
        }
        catch (NumberFormatException e) {
            outputTextView.setText(R.string.solve_empty_output_step_count);

            return;
        }

        // vytvoření objektu pro nalezení řešení
        AEquationSolver equationSolver = new BisectionEquationSolver(getCalculatorContext().getEvaluator());

        try {
            int answer;
            // spuštění hledání řešení
            EquationSolutionContainer equationSolutionContainer = equationSolver.solve(left, right, lowerBoundary, upperBoundary, stepCount);

            if (equationSolutionContainer.hasNoSolution()) {
                // nebylo nalezeno žádné řešení - bude zobrazena příslušná hláška a zobrazený seznam zůstane prázdný
                answer = R.string.solve_output_no_solution;
            }
            else if (equationSolutionContainer.hasInfiniteManySolutions()) {
                // byl zjištěn nekonečný počet řešení - bude zobrazena příslušná hláška a zobrazený seznam zůstane prázdný
                answer = R.string.solve_output_inf_solutions;
            }
            else {
                // byla nalezena jednoznačná řešení rovnice - bude zobrazena příslušná hláška a naplněn seznam řešení
                answer = R.string.solve_output;
            }

            // aktualizuje zobrazovaný seznam řešení podle posledních výsledků operace hledání řešení
            equationSolutionContainer.getSolutions(list);
            adapter.notifyDataSetChanged();
            outputTextView.setText(answer);
        }
        catch (EvaluatorException e) {
            outputTextView.setText(errorMessages.get(e.CODE));
        }
    }

}
