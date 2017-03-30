package cz.zcu.pkozler.mkz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import cz.zcu.pkozler.mkz.core.EvaluatorExceptionCode;
import cz.zcu.pkozler.mkz.ui.CalculatorContext;
import cz.zcu.pkozler.mkz.ui.handlers.ActiveEditTextHandler;

/**
 *
 * @author Petr Kozler
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected HashMap<EvaluatorExceptionCode, String> errorMessages;
    private final boolean SHOW_MENU;

    public BaseActivity(boolean showMenu) {
        SHOW_MENU = showMenu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!SHOW_MENU) {
            return true;
        }

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!SHOW_MENU) {
            return super.onOptionsItemSelected(item);
        }

        int id = item.getItemId();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected CalculatorContext getCalculatorContext() {
        return (CalculatorContext)getApplication();
    }

    protected void createOnFocusChangeListener(final ActiveEditTextHandler activeEditTextHandler, final EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        activeEditTextHandler.setActiveTextField((EditText) v);
                    }
                }
            });
        }
    }

    protected void createErrorMessages() {
        errorMessages = new HashMap<>();
        errorMessages.put(null, "");
        errorMessages.put(EvaluatorExceptionCode.EMPTY_TOKEN, getString(R.string.error_empty_token));
        errorMessages.put(EvaluatorExceptionCode.INVALID_SYMBOLS, getString(R.string.error_invalid_symbols));
        errorMessages.put(EvaluatorExceptionCode.MISPLACED_ARG_SEPARATOR, getString(R.string.error_misplaced_arg_separator));
        errorMessages.put(EvaluatorExceptionCode.MISSING_LEFT_PARENTHESES, getString(R.string.error_missing_left_parentheses));
        errorMessages.put(EvaluatorExceptionCode.MISSING_RIGHT_PARENTHESES, getString(R.string.error_missing_right_parentheses));
        errorMessages.put(EvaluatorExceptionCode.MISSING_OPERANDS, getString(R.string.error_missing_operands));
        errorMessages.put(EvaluatorExceptionCode.MISSING_ARGS, getString(R.string.error_missing_args));
        errorMessages.put(EvaluatorExceptionCode.TOO_MANY_VALUES, getString(R.string.error_too_many_values));
        errorMessages.put(EvaluatorExceptionCode.WRONG_VAR_SYMBOL, getString(R.string.error_wrong_var_symbol));
        errorMessages.put(EvaluatorExceptionCode.ILLEGAL_FACTORIAL_ARG, getString(R.string.error_illegal_factorial_arg));
    }

}
