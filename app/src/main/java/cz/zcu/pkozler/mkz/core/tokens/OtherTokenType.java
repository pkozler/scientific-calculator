package cz.zcu.pkozler.mkz.core.tokens;

/**
 *
 * @author Petr Kozler
 */
public enum OtherTokenType {
    
    LEFT_PARENTHESIS("(", true), RIGHT_PARENTHESIS(")", true), ARG_SEPARATOR(",", false);
        
    public final String KEYWORD;
    public final boolean IS_SPECIAL;

    private OtherTokenType(String keyword, boolean isSpecial) {
        KEYWORD = keyword;
        IS_SPECIAL = isSpecial;
    }
    
}
