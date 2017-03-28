package cz.zcu.pkozler.mkz.ui.handlers;

import android.widget.Button;
import android.widget.GridLayout;

import cz.zcu.pkozler.mkz.BaseActivity;
import cz.zcu.pkozler.mkz.ui.handlers.buttons.InputButtonType;
import cz.zcu.pkozler.mkz.ui.modes.AngleMode;
import cz.zcu.pkozler.mkz.ui.modes.FunctionInputMode;
import cz.zcu.pkozler.mkz.ui.modes.InputMode;

/**
 *
 * @author Petr Kozler
 */
public class ButtonGridLayoutHandler {

    private GridLayout gridLayout;
    private BaseActivity activity;
    private InputMode inputMode;
    private FunctionInputMode functionInputMode;
    private AngleMode angleMode;
    private boolean hasVariable;

    public ButtonGridLayoutHandler(InputMode inputMode, FunctionInputMode functionInputMode, AngleMode angleMode, boolean hasVariable) {
        this.inputMode = inputMode;
        this.functionInputMode = functionInputMode;
        this.angleMode = angleMode;
        this.hasVariable = hasVariable;
    }

    public void setGridLayout(BaseActivity activity, GridLayout gridLayout) {
        this.activity = activity;
        this.gridLayout = gridLayout;
        updateButtonGrid();
    }

    public void setVariableMode(boolean hasVariable) {
        this.hasVariable = hasVariable;
        updateButtonGrid();
    }

    public void setAngleMode(AngleMode angleMode) {
        this.angleMode = angleMode;
        updateButtonGrid();
    }

    public void setFunctionInputMode(FunctionInputMode functionInputMode) {
        this.functionInputMode = functionInputMode;
        updateButtonGrid();
    }

    public void setInputMode(InputMode inputMode) {
        this.inputMode = inputMode;
        updateButtonGrid();
    }

    private void updateButtonGrid() {
        InputButtonType[][] buttons = new InputButtonType[InputButtonType.BTN_GRID_ROW_COUNT][InputButtonType.BTN_GRID_COL_COUNT];

        for (InputButtonType buttonType : InputButtonType.values()) {
            if ((buttonType.INPUT_MODE == null || buttonType.INPUT_MODE == inputMode)
                    && (buttonType.FUNCTION_INPUT_MODE == null || buttonType.FUNCTION_INPUT_MODE == functionInputMode)
                    && (buttonType.ANGLE_MODE == null || buttonType.ANGLE_MODE == angleMode)
                    && (buttonType.HAS_VARIABLE == null || buttonType.HAS_VARIABLE == hasVariable)) {
                buttons[buttonType.ROW][buttonType.COL] = buttonType;
            }
        }

        if (gridLayout == null) {
            return;
        }

        gridLayout.removeAllViews();

        if (buttons == null) {
            return;
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = createButton(activity, buttons[i][j]);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                button.setLayoutParams(params);
                gridLayout.addView(button);
            }
        }
    }

    private static Button createButton(BaseActivity activity, InputButtonType type) {
        if (activity == null) {
            return null;
        }

        Button button = new Button(activity);
        button.setMinHeight(0);
        button.setMinimumHeight(0);
        button.setAllCaps(false);

        if (type != null) {
            button.setText(type.getText());
            button.setOnClickListener(type.getListener());
        }
        else {
            button.setText("");
            button.setEnabled(false);
        }

        return button;
    }

}
