package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.core.AExpressionException;
import cz.zcu.pkozler.mkz.core.Expression;

public class CalcActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView outputTextView;
    private Expression expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expression = new Expression();
        inputText = (EditText)findViewById(R.id.calcInputText);
        outputTextView = (TextView)findViewById(R.id.calcOutputTextView);

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
                catch (AExpressionException ex) {
                    outputTextView.setText(ex.MESSAGE);
                }
            }

        });
    }

}
