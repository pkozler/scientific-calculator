package cz.zcu.pkozler.mkz.core.tokens.types;

/**
 * Výčtový typ reprezentující podporované tokeny matematického výrazu,
 * které jsou klasifikovány jako matematické funkce.
 *
 * @author Petr Kozler
 */
public enum FunctionTokenType implements ITokenType {

    YROOT("yroot"), // libovolná odmocnina
    ACOSG("acosg"), // arkus kosinus v gradech
    ACOSH("acosh"), // argument hyperbolického kosinu
    ASIND("asind"), // arkus sinus ve stupních
    ASING("asing"), // arkus sinus v gradech
    ACOSD("acosd"), // arkus kosinus ve stupních
    ATAND("atand"), // arkus tangens ve stupních
    ATANG("atang"), // arkus tangens v gradech
    ATANH("atanh"), // argument hyperbolického tangentu
    FLOOR("floor"), // dolní celá část
    ASINH("asinh"), // argument hyperbolického sinu
    CBRT("cbrt"), // třetí odmocnina
    TANG("tang"), // tangens úhlu v gradech
    SING("sing"), // sinus úhlu v gradech
    SINH("sinh"), //  hyperbolický kosinus
    TANH("tanh"), // hyperbolický tangens
    ATANR("atan"), // arkus tangens ve v radiánech
    FRAC("frac"), // desetinná část
    SQRT("sqrt"), // druhá odmocnina
    SIND("sind"), // sinus úhlu ve stupních
    ACOSR("acos"), // arkus kosinus v radiánech
    TAND("tand"), // tangens úhlu ve stupních
    COSH("cosh"), // hyperbolický kosinus
    COSG("cosg"), // kosinus úhlu v gradech
    COSD("cosd"), // kosinus úhlu ve stupních
    ASINR("asin"), // arkus sinus v radiánech
    CEIL("ceil"), // horní celá část
    FACT("fact"), // faktoriál
    ABS("abs"), // absolutní hodnota
    REC("rec"), // převrácená hodnota
    POW("pow"), // libovolná mocnina
    LOG("log"), // logaritmus o libovolném základu
    SGN("sgn"), // signum
    INT("int"), // celá část
    TANR("tan"), // tangens úhlu v radiánech
    DMS("dms"), // převod do šedesátkové soustavy
    DEG("deg"), // převod ze šedesátkové soustavy
    COSR("cos"), // kosinus úhlu v radiánech
    SINR("sin"), // sinus úhlu v radiánech
    EXP("exp"), // mocnina čísla "e"
    LN("ln"), // logaritmus o základu "e"
    CB("cb"), // třetí mocnina
    SQ("sq"); // druhá mocnina

    // klíčové slovo
    private final String KEYWORD;

    /**
     * Vytvoří typ tokenu představujícího funkci.
     *
     * @param keyword klíčové slovo
     */
    FunctionTokenType(String keyword) {
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
