package cz.zcu.pkozler.mkz.core.tokens;

/**
 *
 * @author Petr Kozler
 */
public enum OperatorType {
    
    ADD("+", true), SUB("-", true), MUL("*", true),
    DIV("/", false), POW("^", true), MOD("%", false);
        
    public final String KEYWORD;
    public final boolean IS_SPECIAL;

    private OperatorType(String keyword, boolean isSpecial) {
        KEYWORD = keyword;
        IS_SPECIAL = isSpecial;
    }
    
}
