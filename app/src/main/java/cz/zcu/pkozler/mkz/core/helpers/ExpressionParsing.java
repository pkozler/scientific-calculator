package cz.zcu.pkozler.mkz.core.helpers;

import cz.zcu.pkozler.mkz.core.tokens.TokenConfig;
import cz.zcu.pkozler.mkz.core.tokens.types.ConstantTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.FunctionTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.ITokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.VariableType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Knihovní třída poskytující regulární výrazy pro úpravu řetězcové reprezentace
 * zadaného matematického výrazu a jeho rozdělení na tokeny pro další zpracování.
 *
 * @author Petr Kozler
 */
public final class ExpressionParsing {
    /**
     * regulární výraz popisující proměnné na začátku aktuálního podřetězce
     **/
    public static final Pattern VARIABLE_REGEX;

    /**
     * regulární výraz popisující čísla na začátku aktuálního podřetězce
     **/
    public static final Pattern NUMBER_REGEX;

    /**
     * regulární výraz popisující operátory na začátku aktuálního podřetězce
     **/
    public static final Pattern OPERATOR_REGEX;

    /**
     * regulární výraz popisující funkce na začátku aktuálního podřetězce
     **/
    public static final Pattern FUNCTION_REGEX;

    /**
     * regulární výraz popisující konstanty na začátku aktuálního podřetězce
     **/
    public static final Pattern CONSTANT_REGEX;

    /**
     * regulární výraz popisující ostatní znaky (závorky a oddělovač argumentů funkcí) na začátku aktuálního podřetězce
     **/
    public static final Pattern OTHER_REGEX;

    /**
     * slovník přepisovacích pravidel pro předzpracování matematického výrazu, kde jako klíče slouží
     * textové reprezentace regulárních výrazů popisujících dané podřetězce, které mají být přepsány,
     * hodnoty jsou pak řetězce, kterými mají být odpovídající podřetězce nahrazeny
     **/
    public static final Map<String, String> TRANSCRIPTIONS = new HashMap<>();

    /**
     * Spojí výčet textových reprezentací tokenů jednoho daného typu do řetězce představujícího
     * regulární výraz použitý k identifikaci typu tokenu v řetězcové reprezentaci
     * matematického výrazu ve fázi zpracování.
     *
     * @param values výčet všech řetězců představujících tokeny daného typu
     * @return regulární výraz popisující všechny tokeny daného typu
     */
    private static String joinTokensToRegex(ITokenType[] values) {
        if (values.length < 1) {
            return "";
        }

        StringBuilder sb = new StringBuilder(values[0].toRegexString());

        for (int i = 1; i < values.length; i++) {
            sb.append("|");

            sb.append(values[i].toRegexString());
        }

        return sb.toString();
    }

    static {
        // sestavení textových reprezentací regulárních výrazů pro popis jednotlivých typů tokenů
        String functions = joinTokensToRegex(FunctionTokenType.values());
        String operators = joinTokensToRegex(OperatorTokenType.values());
        String constants = joinTokensToRegex(ConstantTokenType.values());
        String other = joinTokensToRegex(OtherTokenType.values());

        // kompilace regulárních výrazů ze sestavených textových reprezentací
        NUMBER_REGEX = Pattern.compile("^(" + Character.toString(TokenConfig.AUX_SIGN_SYMBOL) + "?" + TokenConfig.DIGITS_STRING + "*\\.?\" + DIGITS_STRING + \"+(e[-+]?\" + DIGITS_STRING + \"+)?)");
        VARIABLE_REGEX = Pattern.compile("^" + VariableType.VARIABLE.toString());
        OPERATOR_REGEX = Pattern.compile("^(" + operators + ")");
        FUNCTION_REGEX = Pattern.compile("^(" + functions + ")");
        CONSTANT_REGEX = Pattern.compile("^(" + constants + ")");
        OTHER_REGEX = Pattern.compile("^(" + other + ")");

        // odstranění bílých znaků z výrazu
        TRANSCRIPTIONS.put("\\s+", "");
        // doplnění operace "1*" před proměnnou, konstantu, funkci nebo levou závorku se záporným znaménkem zleva
        TRANSCRIPTIONS.put("(-)(\\(|" + VariableType.VARIABLE.toString() + "|" + constants + "|" + functions + ")", "-1*$2");
        // doplnění znaku násobení "*" za čísla s bezprostředně následující proměnnou, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("(" + TokenConfig.DIGITS_STRING + ")(\\(|" + VariableType.VARIABLE.toString() + "|" + constants + "|" + functions + ")", "$1*$2");
        // doplnění znaku násobení "*" za konstanty nebo pravé závorky s bezprostředně následující proměnnou, číslem, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("(\\)|" + constants + ")(\\(|" + TokenConfig.DIGITS_STRING + "|" + VariableType.VARIABLE.toString() + "|" + constants + "|" + functions + ")", "$1*$2");
        // odstranění přebytečných kladných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(" + OperatorTokenType.ADD.toRegexString() + ")", "");
        // odstranění přebytečných kladných znamének uvnitř výrazu
        TRANSCRIPTIONS.put(OtherTokenType.LEFT_PARENTHESIS.toRegexString() + OperatorTokenType.ADD.toRegexString(), OtherTokenType.LEFT_PARENTHESIS.toString());
        // přeznačení záporných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(" + OperatorTokenType.SUB.toRegexString() + ")", "" + TokenConfig.AUX_SIGN_SYMBOL);
        // přeznačení záporných znamének uvnitř výrazu
        TRANSCRIPTIONS.put(OtherTokenType.LEFT_PARENTHESIS.toRegexString() + OperatorTokenType.SUB.toRegexString(), OtherTokenType.LEFT_PARENTHESIS.toString() + TokenConfig.AUX_SIGN_SYMBOL);
    }

}