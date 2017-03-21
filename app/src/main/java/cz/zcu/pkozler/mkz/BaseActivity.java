package cz.zcu.pkozler.mkz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import cz.zcu.pkozler.mkz.core.Expression;
import cz.zcu.pkozler.mkz.core.ExpressionExceptionCode;
import cz.zcu.pkozler.mkz.handlers.ActiveTextFieldChanger;

public abstract class BaseActivity extends AppCompatActivity {

    protected HashMap<ExpressionExceptionCode, String> errorMessages;
    protected Expression expression;
    private final boolean SHOW_MENU;

    public BaseActivity(boolean showMenu) {
        SHOW_MENU = showMenu;
    }

    protected void initializeEvaluator() {
        errorMessages = createErrorMessages();
        expression = new Expression();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!SHOW_MENU) {
            return true;
        }

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!SHOW_MENU) {
            return super.onOptionsItemSelected(item);
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_calc) {
            calcActivity(null);
            return true;
        }

        if (id == R.id.action_plot) {
            plotActivity(null);
            return true;
        }

        if (id == R.id.action_solve) {
            solveActivity(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void calcActivity(View v) {
        Intent i = new Intent(this, CalcActivity.class);
        startActivity(i);
    }

    public void plotActivity(View v) {
        Intent i = new Intent(this, PlotActivity.class);
        startActivity(i);
    }

    public void solveActivity(View v) {
        Intent i = new Intent(this, SolveActivity.class);
        startActivity(i);
    }

    protected void createOnFocusChangeListener(final ActiveTextFieldChanger activeTextFieldChanger, final EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        activeTextFieldChanger.setActiveTextField((EditText) v);
                    }
                }
            });
        }
    }

    private HashMap<ExpressionExceptionCode, String> createErrorMessages() {
        HashMap<ExpressionExceptionCode, String> messages = new HashMap<>();

        messages.put(null, "");
        messages.put(ExpressionExceptionCode.INVALID_SYMBOLS, getString(R.string.error_invalid_symbols));
        messages.put(ExpressionExceptionCode.MISPLACED_ARG_SEPARATOR, getString(R.string.error_misplaced_arg_separator));
        messages.put(ExpressionExceptionCode.MISSING_LEFT_PARENTHESES, getString(R.string.error_missing_left_parentheses));
        messages.put(ExpressionExceptionCode.MISSING_RIGHT_PARENTHESES, getString(R.string.error_missing_right_parentheses));
        messages.put(ExpressionExceptionCode.MISSING_OPERANDS, getString(R.string.error_missing_operands));
        messages.put(ExpressionExceptionCode.MISSING_ARGS, getString(R.string.error_missing_args));
        messages.put(ExpressionExceptionCode.TOO_MANY_VALUES, getString(R.string.error_too_many_values));
        messages.put(ExpressionExceptionCode.ILLEGAL_VAR_SYMBOL, getString(R.string.error_illegal_var_symbol));
        messages.put(ExpressionExceptionCode.NOT_INT_FACTORIAL_ARG, getString(R.string.error_not_int_factorial_arg));

        return messages;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
