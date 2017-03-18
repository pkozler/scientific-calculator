package cz.zcu.pkozler.mkz.core.tokens;

/**
 *
 * @author Petr Kozler
 */
public enum FunctionType {

    YROOT("yroot"),
    ACOSG("acosg"),
    ACOSH("acosh"),
    ASIND("asind"),
    ASING("asing"),
    ACOSD("acosd"),
    ATAND("atand"),
    ATANG("atang"),
    ATANH("atanh"),
    FLOOR("floor"),
    ASINH("asinh"),
    CBRT("cbrt"),
    TANG("tang"),
    SING("sing"),
    SINH("sinh"),
    TANH("tanh"),
    ATANR("atan"),
    FRAC("frac"),
    SQRT("sqrt"),
    SIND("sind"),
    ACOSR("acos"),
    TAND("tand"),
    COSH("cosh"),
    COSG("cosg"),
    COSD("cosd"),
    ASINR("asin"),
    CEIL("ceil"),
    FACT("fact"),
    ABS("abs"),
    REC("rec"),
    POW("pow"),
    LOG("log"),
    SGN("sgn"),
    INT("int"),
    TANR("tan"),
    DMS("dms"),
    DEG("deg"),
    COSR("cos"),
    SINR("sin"),
    EXP("exp"),
    LN("ln"),
    CB("cb"),
    SQ("sq");
        
    public final String KEYWORD;

    private FunctionType(String keyword) {
        KEYWORD = keyword;
    }
    
}
