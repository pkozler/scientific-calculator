package cz.zcu.pkozler.mkz;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * Třída aktivity představující úvodní obrazovku aplikace s tlačítky pro přepnutí
 * do požadované sekce pro výpočty.
 *
 * @author Petr Kozler
 */
public class MainActivity extends BaseActivity {

    public MainActivity() {
        super(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nastavení toolbaru
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
