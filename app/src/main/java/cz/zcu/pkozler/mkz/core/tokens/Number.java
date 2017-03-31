package cz.zcu.pkozler.mkz.core.tokens;

import cz.zcu.pkozler.mkz.core.helpers.ExpressionParsing;
import cz.zcu.pkozler.mkz.core.tokens.types.ConstantTokenType;

/**
 * Třída představující hodnoty (čísla) v matematickém výrazu, které lze vytvořit
 * podle zadané číselné hodnoty nebo textové reprezentace včetně zkratky pro
 * název konstanty.
 *
 * @author Petr Kozler
 */
public class Number extends Token {

    /**
     * číselná hodnota
     **/
    public final double VALUE;

    /**
     * Vytvoří nové číslo podle jeho textové reprezentace.
     * 
     * @param str textová reprezentace
     **/
    public Number(String str) {
        super(str);

        if (ConstantTokenType.PI.stringEquals(str)) {
            VALUE = Math.PI;
        } else if (ConstantTokenType.E.stringEquals(str)) {
            VALUE = Math.E;
        } else {
            // přeznačení záporných čísel zpět na znaménko "-" po zpracování
            VALUE = Double.parseDouble(
                    ExpressionParsing.AUX_SIGN_SYMBOL == str.charAt(0) ? (ExpressionParsing.DEF_SIGN_SYMBOL + str.substring(1)) : str);
        }
    }

    /**
     * Vrátí nové číslo se zadanou hodnotou a uloženou textovou reprezentací.
     * 
     * @param value číselná hodnota
     * @return číslo
     **/
    public static Number createNumber(double value) {
        return new Number(Double.toString(value));
    }
}
