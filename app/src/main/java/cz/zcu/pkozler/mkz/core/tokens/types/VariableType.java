package cz.zcu.pkozler.mkz.core.tokens.types;

/**
 * Výčtový typ reprezentující podporované tokeny matematického výrazu,
 * které jsou klasifikovány jako symboly označující proměnné.
 *
 * @author Petr Kozler
 */
public enum VariableType implements ITokenType {

    VARIABLE("x"); // proměnná x

    // klíčové slovo
    private final String KEYWORD;

    /**
     * Vytvoří typ tokenu představujícího proměnnou.
     *
     * @param keyword klíčové slovo
     */
    VariableType(String keyword) {
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
