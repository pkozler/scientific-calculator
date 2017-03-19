package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.handlers.ActiveTextFieldChanger;
import cz.zcu.pkozler.mkz.handlers.CalculatorChanger;

public class CalcActivity extends BaseActivity {

    private EditText inputText;
    private TextView outputTextView;

    public CalcActivity() {
        super(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeEvaluator();

        inputText = (EditText)findViewById(R.id.calcInputText);
        outputTextView = (TextView)findViewById(R.id.calcOutputTextView);

        CalculatorChanger calculatorChanger = (CalculatorChanger)getApplication();
        createOnFocusChangeListener(calculatorChanger.getActiveTextFieldChanger(), inputText);
        createTextWatcher(inputText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.calcGridLayout);
        CalculatorChanger calculatorChanger = (CalculatorChanger)getApplication();
        calculatorChanger.setVariableMode(false);
        ActiveTextFieldChanger activeTextFieldChanger = calculatorChanger.getActiveTextFieldChanger();
        activeTextFieldChanger.setActiveTextField(inputText);
        calculatorChanger.getButtonGridLayoutChanger().setGridLayout(this, gridLayout);
    }

    private void createTextWatcher(final EditText inputText) {
        inputText.addTextChangedListener(new TextWatcher() {

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
                    double outputVal = expression.eval(inputStr, null);

                    outputTextView.setText(" = " + outputVal);
                }
                catch (ExpressionException ex) {
                    outputTextView.setText(errorMessages.get(ex.CODE));
                }
            }
        });
    }

}
