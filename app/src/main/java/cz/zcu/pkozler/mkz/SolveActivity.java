package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.handlers.ActiveTextFieldChanger;
import cz.zcu.pkozler.mkz.handlers.CalculatorChanger;
import cz.zcu.pkozler.mkz.handlers.EquationSolver;

public class SolveActivity extends BaseActivity {

    private EditText leftInputText;
    private EditText rightInputText;
    private EditText lowerBoundaryInputText;
    private EditText upperBoundaryInputText;
    private EditText stepCountInputText;
    private TextView outputTextView;
    private ListView listView;
    private ArrayAdapter<Double> adapter;
    private List<Double> list;

    public SolveActivity() {
        super(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeEvaluator();

        leftInputText = (EditText)findViewById(R.id.solveLeftInputText);
        rightInputText = (EditText)findViewById(R.id.solveRightInputText);
        lowerBoundaryInputText = (EditText)findViewById(R.id.solveLowerBoundaryText);
        upperBoundaryInputText = (EditText)findViewById(R.id.solveUpperBoundaryTextField);
        stepCountInputText = (EditText)findViewById(R.id.solveStepCountText);
        outputTextView = (TextView)findViewById(R.id.solveOutputTextView);
        listView = (ListView)findViewById(R.id.solveListView);

        CalculatorChanger calculatorChanger = (CalculatorChanger)getApplication();
        createOnFocusChangeListener(calculatorChanger.getActiveTextFieldChanger(),
                leftInputText, rightInputText, lowerBoundaryInputText, upperBoundaryInputText, stepCountInputText);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<Double>(this, R.layout.simple_list_item, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.solveGridLayout);
        CalculatorChanger calculatorChanger = (CalculatorChanger)getApplication();
        calculatorChanger.setVariableMode(true);
        ActiveTextFieldChanger activeTextFieldChanger = calculatorChanger.getActiveTextFieldChanger();
        selectTextFieldToActivate(activeTextFieldChanger, leftInputText,
                leftInputText, rightInputText, lowerBoundaryInputText, upperBoundaryInputText, stepCountInputText);
        calculatorChanger.getButtonGridLayoutChanger().setGridLayout(this, gridLayout);
    }

    private void selectTextFieldToActivate(ActiveTextFieldChanger activeTextFieldChanger,
                                           EditText defaultEditText, EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (activeTextFieldChanger.getActiveTextField() == editText) {
                activeTextFieldChanger.setActiveTextField(editText);

                return;
            }
        }

        activeTextFieldChanger.setActiveTextField(defaultEditText);
    }

    public void findSolution(View v) {
        EquationSolver equationSolver = new EquationSolver(expression, adapter);

        String left = leftInputText.getText().toString();
        String right = rightInputText.getText().toString();
        double lowerBoundary = Double.parseDouble(lowerBoundaryInputText.getText().toString());
        double upperBoundary = Double.parseDouble(upperBoundaryInputText.getText().toString());
        int stepCount = Integer.parseInt(stepCountInputText.getText().toString());

        try {
            List<Double> solutions = equationSolver.solve(left, right, lowerBoundary, upperBoundary, stepCount);
            outputTextView.setText("x = ");
            list.addAll(solutions);
            adapter.notifyDataSetChanged();
        }
        catch (ExpressionException e) {
            outputTextView.setText(e.getMessage());
        }
    }

}
