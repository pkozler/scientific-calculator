package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.core.Expression;
import cz.zcu.pkozler.mkz.core.ExpressionExceptionCode;

public class CalcActivity extends BaseActivity {

    public CalcActivity() {
        super(true);
    }

    private EditText inputText;
    private TextView outputTextView;

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
        inputText.addTextChangedListener(createTextWatcher());
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {

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
        };
    }

}
