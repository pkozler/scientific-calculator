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
 *
 * @author Petr Kozler
 */
public class PlotActivity extends BaseActivity {

    private EditText inputText;
    private TextView outputTextView;
    private PlotView plotView;
    private PlotView.TouchListener touchListener;

    public PlotActivity() {
        super(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputText = (EditText)findViewById(R.id.plotInputText);
        outputTextView = (TextView)findViewById(R.id.plotOutputTextView);
        plotView = (PlotView)findViewById(R.id.plotView);

        createErrorMessages();
        createOnFocusChangeListener(getCalculatorContext().getActiveEditTextHandler(), inputText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.plotGridLayout);
        getCalculatorContext().getButtonGridLayoutHandler().setVariableMode(true);
        ActiveEditTextHandler activeEditTextHandler = getCalculatorContext().getActiveEditTextHandler();
        activeEditTextHandler.setActiveTextField(inputText);
        getCalculatorContext().getButtonGridLayoutHandler().setGridLayout(this, gridLayout);

        Button zoomInButton = (Button)findViewById(R.id.zoomInButton);
        Button zoomOutButton = (Button)findViewById(R.id.zoomOutButton);
        touchListener = plotView.createGraphListeners(zoomInButton, zoomOutButton);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        String result = outputTextView.getText().toString();
        savedInstanceState.putString("Result", result == null ? "" : result);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String result = savedInstanceState.getString("Result");
        outputTextView.setText(result);
        plotView.draw(inputText.getText().toString(), getCalculatorContext().getEvaluator(),
                outputTextView, touchListener, errorMessages);
    }

    public void drawPlot(View v) {
        String input = inputText.getText().toString();

        if (input.isEmpty()) {
            outputTextView.setText(R.string.plot_empty_output);

            return;
        }

        plotView.draw(input, getCalculatorContext().getEvaluator(),
                outputTextView, touchListener, errorMessages);
    }

}
