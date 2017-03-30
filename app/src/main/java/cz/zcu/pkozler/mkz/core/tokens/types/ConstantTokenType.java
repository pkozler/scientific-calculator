package cz.zcu.pkozler.mkz.core.tokens.types;

/**
 * Výčtový typ reprezentující podporované tokeny matematického výrazu,
 * které jsou klasifikovány jako symboly označující běžně používané konstanty.
 *
 * @author Petr Kozler
 */
public enum ConstantTokenType implements ITokenType {
    
    PI("pi"), // Ludolfovo číslo
    E("eu"); // Eulerovo číslo

    // klíčové slovo
    private final String KEYWORD;

    /**
     * Vytvoří typ tokenu představujícího konstantu.
     *
     * @param keyword klíčové slovo
     */
    ConstantTokenType(String keyword) {
        KEYWORD = keyword;
    }

    public boolean stringEquals(String str) {
        return KEYWORD.equals(str);
    }

    public String toRegexString() {
        return KEYWORD;
    }

    @Override
    public String toString() {
        return KEYWORD;
    }

}
