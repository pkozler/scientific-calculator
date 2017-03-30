package cz.zcu.pkozler.mkz.core.tokens.types;

/**
 * Výčtový typ reprezentující podporované tokeny matematického výrazu,
 * které jsou klasifikovány jako běžně používané aritmetické operátory.
 *
 * @author Petr Kozler
 */
public enum OperatorTokenType implements ITokenType {
    
    ADD("+", true), // sčítání
    SUB("-", true), // odčítání
    MUL("*", true), // násobení
    DIV("/", false), // dělení
    POW("^", true), // umocňování
    MOD("%", false); // modulo

    // klíčové slovo
    private final String KEYWORD;
    // příznak nutnosti úpravy pro regulární výraz
    private final boolean IS_SPECIAL;

    /**
     * Vytvoří typ tokenu představujícího operátor.
     *
     * @param keyword klíčové slovo
     * @param isSpecial příznak nutnosti úpravy pro regulární výraz
     */
    OperatorTokenType(String keyword, boolean isSpecial) {
        KEYWORD = keyword;
        IS_SPECIAL = isSpecial;
    }

    public boolean stringEquals(String str) {
        return KEYWORD.equals(str);
    }

    public String toRegexString() {
        return IS_SPECIAL ? "\\" + KEYWORD : KEYWORD;
    }

    @Override
    public String toString() {
        return KEYWORD;
    }

}
