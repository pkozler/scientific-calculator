package cz.zcu.pkozler.mkz.core.tokens;

/**
 * Knihovní třída obsahující základní řetězcové konstanty pro účely zpracovávání
 * textové reprezentace matematických výrazů.
 *
 * @author Petr Kozler
 */
public final class TokenConfig {

    /**
     * část regulárního výrazu představující výčet čísel
     */
    public static final String DIGITS_STRING = "[0-9]";

    /**
     * standardní symbol záporného znaménka číselné hodnoty
     */
    public static final char DEF_DECIMAL_SEPARATOR = '.';

    /**
     * zástupný symbol záporného znaménka pro odlišení od operátoru odčítání během zpracování výrazu
     */
    public static final char AUX_SIGN_SYMBOL = '_';

}
