package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.customviews.PlotView;
import cz.zcu.pkozler.mkz.handlers.ActiveTextFieldChanger;
import cz.zcu.pkozler.mkz.handlers.CalculatorChanger;

public class PlotActivity extends BaseActivity {

    private EditText inputText;
    private TextView outputTextView;
    private PlotView plotView;

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

        initializeEvaluator();

        inputText = (EditText)findViewById(R.id.plotInputText);
        outputTextView = (TextView)findViewById(R.id.plotOutputTextView);
        plotView = (PlotView)findViewById(R.id.plotView);
        plotView.addDragListener(this);
        plotView.addDoubleTapListener(this);
        plotView.addScaleGestureListener(this);

        CalculatorChanger calculatorChanger = (CalculatorChanger)getApplication();
        createOnFocusChangeListener(calculatorChanger.getActiveTextFieldChanger(), inputText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.plotGridLayout);
        CalculatorChanger calculatorChanger = (CalculatorChanger)getApplication();
        calculatorChanger.setVariableMode(true);
        ActiveTextFieldChanger activeTextFieldChanger = calculatorChanger.getActiveTextFieldChanger();
        activeTextFieldChanger.setActiveTextField(inputText);
        calculatorChanger.getButtonGridLayoutChanger().setGridLayout(this, gridLayout);
    }

    public void drawPlot(View v) {
        plotView.draw(inputText.getText().toString(), expression, outputTextView);
    }

}
