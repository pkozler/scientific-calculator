package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;
import cz.zcu.pkozler.mkz.ui.CalculatorContext;

public class CalcActivity extends BaseActivity {

    private EditText inputText;
    private TextView outputTextView;

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
                getCalculatorContext().getExpression().parse(inputStr);
                double outputVal = getCalculatorContext().getExpression().evaluate(null);

                outputTextView.setText(" = " + outputVal);
            }
            catch (ExpressionException e) {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputText = (EditText)findViewById(R.id.calcInputText);
        outputTextView = (TextView)findViewById(R.id.calcOutputTextView);

        createErrorMessages();
        createOnFocusChangeListener(getCalculatorContext().getActiveEditTextHandler(), inputText);
        inputText.addTextChangedListener(new TextChangedListener());
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridLayout gridLayout = (GridLayout)findViewById(R.id.calcGridLayout);
        getCalculatorContext().getButtonGridLayoutHandler().setVariableMode(false);
        ActiveEditTextHandler activeEditTextHandler = getCalculatorContext().getActiveEditTextHandler();
        activeEditTextHandler.setActiveTextField(inputText);
        getCalculatorContext().getButtonGridLayoutHandler().setGridLayout(this, gridLayout);
    }

}
