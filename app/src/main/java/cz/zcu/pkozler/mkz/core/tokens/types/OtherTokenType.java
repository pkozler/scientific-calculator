package cz.zcu.pkozler.mkz.core.tokens.types;

/**
 * Výčtový typ reprezentující podporované tokeny matematického výrazu,
 * které nejsou klasifikovány jako funkce, operátory, proměnné nebo konstanty
 * (ani se nejedná o čísla).
 * Zahrnuje závorky a znak sloužící k oddělení hodnot představujících argumenty funkce.
 *
 * @author Petr Kozler
 */
public enum OtherTokenType implements ITokenType {
    
    LEFT_PARENTHESIS("(", true), // levá (otevírací) závorka
    RIGHT_PARENTHESIS(")", true), // pravá (uzavírací) závorka
    ARG_SEPARATOR(",", false); // oddělovač argumentů funkce

    // klíčové slovo
    private final String KEYWORD;
    // příznak nutnosti úpravy pro regulární výraz
    private final boolean IS_SPECIAL;

    /**
     * Vytvoří typ tokenu představujícího matematický symbol.
     *
     * @param keyword klíčové slovo
     * @param isSpecial příznak nutnosti úpravy pro regulární výraz
     */
    OtherTokenType(String keyword, boolean isSpecial) {
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
