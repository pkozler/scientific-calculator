package cz.zcu.pkozler.mkz.core.helpers;

/**
 * Knihovní třída obsahující základní řetězcové konstanty pro účely zpracovávání
 * textové reprezentace matematických výrazů.
 *
 * @author Petr Kozler
 */
public final class ExpressionConfig {

    /**
     * část regulárního výrazu představující zápis čísla ve vědecké notaci
     */
    public static final String SCIENTIFIC_NOTATION_STRING = "e[-+]";

    /**
     * část regulárního výrazu představující opačnou hodnotu čísla
     */
    public static final String MINUX_ONE_STRING = "-1";

    /**
     * část regulárního výrazu představující výčet čísel
     */
    public static final String DIGITS_STRING = "[0-9]";

    /**
     * zástupný symbol záporného znaménka pro odlišení od operátoru odčítání během zpracování výrazu
     */
    public static final char AUX_SIGN_SYMBOL = '_';

    /**
     * standardní symbol záporného znaménka číselné hodnoty
     */
    public static final char DEF_DECIMAL_SEPARATOR = '.';

    /**
     * maximální povolená hodnota pro výpočet faktoriálu
     **/
    public static final int MAX_FACT_ARG = 1000;

}
