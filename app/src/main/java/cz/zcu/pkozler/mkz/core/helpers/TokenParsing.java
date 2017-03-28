package cz.zcu.pkozler.mkz.core.helpers;

import cz.zcu.pkozler.mkz.core.tokens.types.ConstantType;
import cz.zcu.pkozler.mkz.core.tokens.types.FunctionType;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Knihovní třída poskytující regulární výrazy pro předzpracování
 * matematického výrazu a rozdělení na tokeny.
 * 
 * @author Petr Kozler
 */
public final class TokenParsing
{
    /// <summary>
    /// regulární výraz popisující proměnné na začátku aktuálního podřetězce
    /// </summary>
    public static final Pattern VARIABLE_REGEX;

    /// <summary>
    /// regulární výraz popisující čísla na začátku aktuálního podřetězce
    /// </summary>
    public static final Pattern NUMBER_REGEX;

    /// <summary>
    /// regulární výraz popisující operátory na začátku aktuálního podřetězce
    /// </summary>
    public static final Pattern OPERATOR_REGEX;

    /// <summary>
    /// regulární výraz popisující funkce na začátku aktuálního podřetězce
    /// </summary>
    public static final Pattern FUNCTION_REGEX;

    /// <summary>
    /// regulární výraz popisující konstanty na začátku aktuálního podřetězce
    /// </summary>
    public static final Pattern CONSTANT_REGEX;

    /// <summary>
    /// regulární výraz popisující ostatní znaky (závorky a oddělovač argumentů funkcí) na začátku aktuálního podřetězce
    /// </summary>
    public static final Pattern OTHER_REGEX;

    /// <summary>
    /// slovník přepisovacích pravidel pro předzpracování matematického výrazu
    /// </summary>
    public static final Map<String, String> TRANSCRIPTIONS = new HashMap<>();
    
    private static String joinFunctions() {
        FunctionType[] functionTypes = FunctionType.values();
        
        if (functionTypes.length < 1) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder(functionTypes[0].KEYWORD);

        for (int i = 1; i < functionTypes.length; i++) {
            sb.append("|").append(functionTypes[i].KEYWORD);
        }

        return sb.toString();
    }
    
    private static String joinOperators() {
        OperatorType[] operatorTypes = OperatorType.values();
        
        if (operatorTypes.length < 1) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        
        if (operatorTypes[0].IS_SPECIAL) {
            sb.append("\\");
        }
        
        sb.append(operatorTypes[0].KEYWORD);

        for (int i = 1; i < operatorTypes.length; i++) {
            sb.append("|");
            
            if (operatorTypes[i].IS_SPECIAL) {
                sb.append("\\");
            }
            
            sb.append(operatorTypes[i].KEYWORD);
        }

        return sb.toString();
    }
    
    private static String joinConstants() {
        ConstantType[] constantTypes = ConstantType.values();
        
        if (constantTypes.length < 1) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder(constantTypes[0].KEYWORD);

        for (int i = 1; i < constantTypes.length; i++) {
            sb.append("|").append(constantTypes[i].KEYWORD);
        }

        return sb.toString();
    }
    
    private static String joinOther() {
        OtherTokenType[] otherTypes = OtherTokenType.values();
        
        if (otherTypes.length < 1) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();

        if (otherTypes[0].IS_SPECIAL) {
            sb.append("\\");
        }
        
        sb.append(otherTypes[0].KEYWORD);

        for (int i = 1; i < otherTypes.length; i++) {
            sb.append("|");
            
            if (otherTypes[i].IS_SPECIAL) {
                sb.append("\\");
            }
            
            sb.append(otherTypes[i].KEYWORD);
        }

        return sb.toString();
    }
    
    static {
        String functions = joinFunctions();
        String operators = joinOperators();
        String constants = joinConstants();
        String other = joinOther();
        
        VARIABLE_REGEX = Pattern.compile("^x");
        NUMBER_REGEX = Pattern.compile("^(_?[0-9]*\\.?[0-9]+(e[-+]?[0-9]+)?)");
        OPERATOR_REGEX = Pattern.compile("^(" + operators + ")");
        FUNCTION_REGEX = Pattern.compile("^(" + functions + ")");
        CONSTANT_REGEX = Pattern.compile("^(" + constants + ")");
        OTHER_REGEX = Pattern.compile("^(" + other + ")");
        
        // odstranění bílých znaků z výrazu
        TRANSCRIPTIONS.put("\\s+", "");
        // doplnění operace "1*" před proměnnou, konstantu, funkci nebo levou závorku se záporným znaménkem zleva
        TRANSCRIPTIONS.put("(-)(\\(|x|" + constants + "|" + functions + ")", "-1*$2");
        // doplnění znaku násobení "*" za čísla s bezprostředně následující proměnnou, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("([0-9])(\\(|x|" + constants + "|" + functions + ")", "$1*$2");
        // doplnění znaku násobení "*" za konstanty nebo pravé závorky s bezprostředně následující proměnnou, číslem, konstantou, funkcí nebo levou závorkou
        TRANSCRIPTIONS.put("(\\)|" + constants + ")(\\(|[0-9]|x|" + constants + "|" + functions + ")", "$1*$2");
        // odstranění přebytečných kladných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(\\+)", "");
        // odstranění přebytečných kladných znamének uvnitř výrazu
        TRANSCRIPTIONS.put("\\(\\+", "(");
        // přeznačení záporných znamének na začátku výrazu
        TRANSCRIPTIONS.put("^(\\-)", "_");
        // přeznačení záporných znamének uvnitř výrazu
        TRANSCRIPTIONS.put("\\(\\-", "(_");
    }
}