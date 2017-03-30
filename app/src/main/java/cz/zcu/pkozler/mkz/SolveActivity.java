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

import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;
import cz.zcu.pkozler.mkz.support.EquationSolver;

/**
 *
 * @author Petr Kozler
 */
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

        leftInputText = (EditText)findViewById(R.id.solveLeftInputText);
        rightInputText = (EditText)findViewById(R.id.solveRightInputText);
        lowerBoundaryInputText = (EditText)findViewById(R.id.solveLowerBoundaryText);
        upperBoundaryInputText = (EditText)findViewById(R.id.solveUpperBoundaryTextField);
        stepCountInputText = (EditText)findViewById(R.id.solveStepCountText);
        outputTextView = (TextView)findViewById(R.id.solveOutputTextView);
        listView = (ListView)findViewById(R.id.solveListView);

        createErrorMessages();
        createOnFocusChangeListener(getCalculatorContext().getActiveEditTextHandler(),
                leftInputText, rightInputText, lowerBoundaryInputText, upperBoundaryInputText, stepCountInputText);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, R.id.textView, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.solveGridLayout);
        getCalculatorContext().getButtonGridLayoutHandler().setVariableMode(true);
        ActiveEditTextHandler activeEditTextHandler = getCalculatorContext().getActiveEditTextHandler();
        selectTextFieldToActivate(activeEditTextHandler, leftInputText,
                leftInputText, rightInputText);
        getCalculatorContext().getButtonGridLayoutHandler().setGridLayout(this, gridLayout);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        String result = outputTextView.getText().toString();
        savedInstanceState.putString("Result", result == null ? "" : result);
        savedInstanceState.putSerializable("Solutions", (Serializable) list);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String result = savedInstanceState.getString("Result");
        outputTextView.setText(result);
        List<Double> solutions = (List<Double>) savedInstanceState.getSerializable("Solutions");

        if (solutions != null) {
            list.addAll(solutions);
        }
    }

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

    public void findSolution(View v) {
        String left = leftInputText.getText().toString();

        if (left.isEmpty()) {
            outputTextView.setText(R.string.solve_empty_output_left);

            return;
        }

        String right = rightInputText.getText().toString();

        if (right.isEmpty()) {
            outputTextView.setText(R.string.solve_empty_output_right);

            return;
        }

        double lowerBoundary;

        try {
            lowerBoundary = Double.parseDouble(lowerBoundaryInputText.getText().toString());
        }
        catch (NumberFormatException e) {
            outputTextView.setText(R.string.solve_empty_output_lower_boundary);

            return;
        }

        double upperBoundary;

        try {
            upperBoundary = Double.parseDouble(upperBoundaryInputText.getText().toString());
        }
        catch (NumberFormatException e) {
            outputTextView.setText(R.string.solve_empty_output_upper_boundary);

            return;
        }

        int stepCount;

        try {
            stepCount = Integer.parseInt(stepCountInputText.getText().toString());
        }
        catch (NumberFormatException e) {
            outputTextView.setText(R.string.solve_empty_output_step_count);

            return;
        }

        EquationSolver equationSolver = new EquationSolver(getCalculatorContext().getEvaluator(),
                list, adapter, outputTextView, errorMessages);
        equationSolver.solve(left, right, lowerBoundary, upperBoundary, stepCount);
    }

}
