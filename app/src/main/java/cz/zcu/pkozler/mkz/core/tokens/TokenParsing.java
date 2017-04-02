package cz.zcu.pkozler.mkz.core.tokens;

import cz.zcu.pkozler.mkz.core.helpers.ExpressionConfig;
import cz.zcu.pkozler.mkz.core.tokens.types.ConstantTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.FunctionTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.ITokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.VariableType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Třída, poskytující regulární výrazy pro úpravu řetězcové reprezentace
 * zadaného matematického výrazu a jeho rozdělení na tokeny pro další zpracování.
 *
 * @author Petr Kozler
 */
public class TokenParsing {
    /**
     * regulární výraz popisující proměnné na začátku aktuálního podřetězce
     **/
    private final Pattern VARIABLE_REGEX;

    /**
     * regulární výraz popisující čísla na začátku aktuálního podřetězce
     **/
    private final Pattern NUMBER_REGEX;

    /**
     * regulární výraz popisující operátory na začátku aktuálního podřetězce
     **/
    private final Pattern OPERATOR_REGEX;

    /**
     * regulární výraz popisující funkce na začátku aktuálního podřetězce
     **/
    private final Pattern FUNCTION_REGEX;

    /**
     * regulární výraz popisující konstanty na začátku aktuálního podřetězce
     **/
    private final Pattern CONSTANT_REGEX;

    /**
     * regulární výraz popisující ostatní znaky (závorky a oddělovač argumentů funkcí) na začátku aktuálního podřetězce
     **/
    private final Pattern OTHER_REGEX;

    /**
     * slovník přepisovacích pravidel pro předzpracování matematického výrazu, kde jako klíče slouží
     * textové reprezentace regulárních výrazů popisujících dané podřetězce, které mají být přepsány,
     * hodnoty jsou pak řetězce, kterými mají být odpovídající podřetězce nahrazeny
     **/
    private final Map<String, String> TRANSCRIPTIONS = new HashMap<>();

    public TokenParsing() {
        // sestavení textových reprezentací regulárních výrazů pro popis jednotlivých typů tokenů
        String functions = joinTokensToRegex(FunctionTokenType.values());
        String operators = joinTokensToRegex(OperatorTokenType.values());
        String constants = joinTokensToRegex(ConstantTokenType.values());
        String other = joinTokensToRegex(OtherTokenType.values());

        // kompilace regulárních výrazů ze sestavených textových reprezentací
        NUMBER_REGEX = Pattern.compile("^(" + Character.toString(ExpressionConfig.AUX_SIGN_SYMBOL) + "?" + ExpressionConfig.DIGITS_STRING + "*\\.?" + ExpressionConfig.DIGITS_STRING + "+(" + ExpressionConfig.SCIENTIFIC_NOTATION_STRING + "?" + ExpressionConfig.DIGITS_STRING + "+)?)");
        VARIABLE_REGEX = Pattern.compile("^" + VariableType.VARIABLE.toString());
        OPERATOR_REGEX = Pattern.compile("^(" + operators + ")");
        FUNCTION_REGEX = Pattern.compile("^(" + functions + ")");
        CONSTANT_REGEX = Pattern.compile("^(" + constants + ")");
        OTHER_REGEX = Pattern.compile("^(" + other + ")");

        // odstranění bílých znaků z výrazu
        TRANSCRIPTIONS.put("\\s+", "");
        // doplnění operace "1*" před proměnnou, konstantu, funkci nebo levou závorku se záporným znaménkem zleva
        TRANSCRIPTIONS.put("(" + OperatorTokenType.SUB.toRegexString() + ")(\\(|" + VariableType.VARIABLE.toString() + "|" + constants + "|" + functions + ")", ExpressionConfig.MINUX_ONE_STRING + OperatorTokenType.MUL.toRegexString() + "$2");
        // doplnění znaku násobení "*" za čísla s bezprostředně následující proměnnou, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("(" + ExpressionConfig.DIGITS_STRING + ")(\\(|" + VariableType.VARIABLE.toString() + "|" + constants + "|" + functions + ")", "$1" + OperatorTokenType.MUL.toRegexString() + "$2");
        // doplnění znaku násobení "*" za konstanty nebo pravé závorky s bezprostředně následující proměnnou, číslem, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("(\\)|" + constants + ")(\\(|" + ExpressionConfig.DIGITS_STRING + "|" + VariableType.VARIABLE.toString() + "|" + constants + "|" + functions + ")", "$1" + OperatorTokenType.MUL.toRegexString() + "$2");
        // odstranění přebytečných kladných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(" + OperatorTokenType.ADD.toRegexString() + ")", "");
        // odstranění přebytečných kladných znamének uvnitř výrazu
        TRANSCRIPTIONS.put(OtherTokenType.LEFT_PARENTHESIS.toRegexString() + OperatorTokenType.ADD.toRegexString(), OtherTokenType.LEFT_PARENTHESIS.toString());
        // přeznačení záporných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(" + OperatorTokenType.SUB.toRegexString() + ")", "" + ExpressionConfig.AUX_SIGN_SYMBOL);
        // přeznačení záporných znamének uvnitř výrazu
        TRANSCRIPTIONS.put(OtherTokenType.LEFT_PARENTHESIS.toRegexString() + OperatorTokenType.SUB.toRegexString(), OtherTokenType.LEFT_PARENTHESIS.toString() + ExpressionConfig.AUX_SIGN_SYMBOL);
    }

    /**
     * Vrátí množinu prvků slovníku přepisovacích pravidel pro předzpracování matematického výrazu.
     *
     * @return množina prvků slovníku přepisovacích pravidel
     */
    public Set<Map.Entry<String, String>> getTranscriptionsEntrySet() {
        return TRANSCRIPTIONS.entrySet();
    }

    /**
     * Vrátí objekt pro nalezení proměnné v podřetězci.
     *
     * @param str podřetězec
     * @return objekt pro nalezení proměnné
     */
    public Matcher getVariableMatcher(String str) {
        return VARIABLE_REGEX.matcher(str);
    }

    /**
     * Vrátí objekt pro nalezení čísla v podřetězci.
     *
     * @param str podřetězec
     * @return objekt pro nalezení čísla
     */
    public Matcher getNumberMatcher(String str) {
        return NUMBER_REGEX.matcher(str);
    }

    /**
     * Vrátí objekt pro nalezení operátoru v podřetězci.
     *
     * @param str podřetězec
     * @return objekt pro nalezení operátoru
     */
    public Matcher getOperatorMatcher(String str) {
        return OPERATOR_REGEX.matcher(str);
    }

    /**
     * Vrátí objekt pro nalezení funkce v podřetězci.
     *
     * @param str podřetězec
     * @return objekt pro nalezení funkce
     */
    public Matcher getFunctionMatcher(String str) {
        return FUNCTION_REGEX.matcher(str);
    }

    /**
     * Vrátí objekt pro nalezení konstanty v podřetězci.
     *
     * @param str podřetězec
     * @return objekt pro nalezení konstanty
     */
    public Matcher getConstantMatcher(String str) {
        return CONSTANT_REGEX.matcher(str);
    }

    /**
     * Vrátí objekt pro nalezení speciálního matematického symbolu v podřetězci.
     *
     * @param str podřetězec
     * @return objekt pro nalezení speciálního matematického symbolu
     */
    public Matcher getOtherMatcher(String str) {
        return OTHER_REGEX.matcher(str);
    }

    /**
     * Spojí výčet textových reprezentací tokenů jednoho daného typu do řetězce představujícího
     * regulární výraz použitý k identifikaci typu tokenu v řetězcové reprezentaci
     * matematického výrazu ve fázi zpracování.
     *
     * @param values výčet všech řetězců představujících tokeny daného typu
     * @return regulární výraz popisující všechny tokeny daného typu
     */
    private String joinTokensToRegex(ITokenType[] values) {
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

}