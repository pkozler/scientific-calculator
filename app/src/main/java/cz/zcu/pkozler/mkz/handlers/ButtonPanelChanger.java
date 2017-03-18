package cz.zcu.pkozler.mkz.handlers;

import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.pkozler.mkz.BaseActivity;

/**
 *
 * @author Petr Kozler
 */
public class ButtonPanelChanger {

    private InputButtonType[][] currentButtons;
    private GridLayout gridLayout;
    private BaseActivity activity;

    public void setCurrentButtons(InputButtonType[][] buttons) {
        currentButtons = buttons;
        
        if (gridLayout == null) {
            return;
        }
        
        gridLayout.removeAllViews();
        
        if (buttons == null) {
            return;
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = InputButtonType.createButton(activity, buttons[i][j]);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                button.setLayoutParams(params);
                gridLayout.addView(button);
            }
        }
    }

    public void setGridLayout(BaseActivity activity, GridLayout gridLayout) {
        this.activity = activity;
        this.gridLayout = gridLayout;
        setCurrentButtons(currentButtons);
    }

}
