package cz.zcu.pkozler.mkz.core.helpers;

import cz.zcu.pkozler.mkz.core.tokens.types.ConstantTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.FunctionTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.ITokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Knihovní třída poskytující regulární výrazy pro úpravu řetězcové reprezentace
 * zadaného matematického výrazu a jeho rozdělení na tokeny pro další zpracování.
 *
 * @author Petr Kozler
 */
public final class TokenParsing {
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
     * symbol proměnné v matematickém výrazu
     */
    public static final String VARIABLE_KEYWORD = "x";

    /**
     * standardní symbol záporného znaménka číselné hodnoty
     */
    public static final char DEF_SIGN_SYMBOL = '-';

    /**
     * zástupný symbol záporného znaménka pro odlišení od operátoru odčítání během zpracování výrazu
     */
    public static final char AUX_SIGN_SYMBOL = '_';

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
        VARIABLE_REGEX = Pattern.compile("^x");
        NUMBER_REGEX = Pattern.compile("^(_?[0-9]*\\.?[0-9]+(e[-+]?[0-9]+)?)");
        OPERATOR_REGEX = Pattern.compile("^(" + operators + ")");
        FUNCTION_REGEX = Pattern.compile("^(" + functions + ")");
        CONSTANT_REGEX = Pattern.compile("^(" + constants + ")");
        OTHER_REGEX = Pattern.compile("^(" + other + ")");

        // odstranění bílých znaků z výrazu
        TRANSCRIPTIONS.put("\\s+", "");
        // doplnění operace "1*" před proměnnou, konstantu, funkci nebo levou závorku se záporným znaménkem zleva
        TRANSCRIPTIONS.put("(-)(\\(|\" + VARIABLE_KEYWORD + \"|" + constants + "|" + functions + ")", "-1*$2");
        // doplnění znaku násobení "*" za čísla s bezprostředně následující proměnnou, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("([0-9])(\\(|" + VARIABLE_KEYWORD + "|" + constants + "|" + functions + ")", "$1*$2");
        // doplnění znaku násobení "*" za konstanty nebo pravé závorky s bezprostředně následující proměnnou, číslem, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("(\\)|" + constants + ")(\\(|[0-9]|" + VARIABLE_KEYWORD + "|" + constants + "|" + functions + ")", "$1*$2");
        // odstranění přebytečných kladných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(" + OperatorTokenType.ADD.toRegexString() + ")", "");
        // odstranění přebytečných kladných znamének uvnitř výrazu
        TRANSCRIPTIONS.put(OtherTokenType.LEFT_PARENTHESIS.toRegexString() + OperatorTokenType.ADD.toRegexString(), OtherTokenType.LEFT_PARENTHESIS.toString());
        // přeznačení záporných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(" + OperatorTokenType.SUB.toRegexString() + ")", "" + AUX_SIGN_SYMBOL);
        // přeznačení záporných znamének uvnitř výrazu
        TRANSCRIPTIONS.put(OtherTokenType.LEFT_PARENTHESIS.toRegexString() + OperatorTokenType.SUB.toRegexString(), OtherTokenType.LEFT_PARENTHESIS.toString() + AUX_SIGN_SYMBOL);
    }

}