package cz.zcu.pkozler.mkz.core.tokens;

/**
 * Třída představující hodnoty (čísla) v matematickém výrazu, které lze vytvořit
 * podle zadané číselné hodnoty nebo textové reprezentace včetně zkratky pro
 * název konstanty.
 *
 * @author Petr Kozler
 */
public class Number extends Token {

    /// <summary>
    /// číselná hodnota
    /// </summary>
    public final double VALUE;

    /// <summary>
    /// Vytvoří nové číslo podle jeho textové reprezentace.
    /// </summary>
    /// <param name="str">textová reprezentace</param>
    public Number(String str) {
        super(str);

        if (ConstantType.PI.KEYWORD.equals(str)) {
            VALUE = Math.PI;
        } else if (ConstantType.E.KEYWORD.equals(str)) {
            VALUE = Math.E;
        } else {
            // přeznačení záporných čísel zpět na znaménko "-" po zpracování
            VALUE = Double.parseDouble(
                    str.charAt(0) == '_' ? ("-" + str.substring(1)) : str);
        }
    }

    /// <summary>
    /// Vrátí nové číslo vytvořené na základě zadané hodnoty.
    /// </summary>
    /// <param name="value">číselná hodnota</param>
    /// <returns>číslo</returns>
    public static Number createNumber(double value) {
        return new Number(Double.toString(value));
    }
}
