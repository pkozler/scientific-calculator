package cz.zcu.pkozler.mkz.core.tokens;

import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;

/**
 * Třída představující operátory v matematickém výrazu.
 *
 * @author Petr Kozler
 */
public class Operator extends Token {

    /**
     * informace o asociativitě operátoru
     **/
    private final boolean LEFT_ASSOC;

    /**
     * informace o prioritě operátoru
     **/
    public final int PRECEDENCE;

    /**
     * Vytvoří nový operátor a nastaví mu příslušnou asociativitu a prioritu.
     * 
     * @param str textová reprezentace
     **/
    public Operator(String str) {
        super(str);

        if (OperatorTokenType.ADD.stringEquals(str)
                || OperatorTokenType.SUB.stringEquals(str)) {
            LEFT_ASSOC = true;
            PRECEDENCE = 1;
        } else if (OperatorTokenType.MUL.stringEquals(str)
                || OperatorTokenType.DIV.stringEquals(str)
                || OperatorTokenType.MOD.stringEquals(str)) {
            LEFT_ASSOC = true;
            PRECEDENCE = 2;
        } else if (OperatorTokenType.POW.stringEquals(str)) {
            LEFT_ASSOC = false;
            PRECEDENCE = 3;
        } else {
            LEFT_ASSOC = false;
            PRECEDENCE = 0;
        }
    }

    /**
     * Určí, zda je operátor asociativní zleva.
     * 
     * @return TRUE, pokud je token asociativní zleva, jinak FALSE
     **/
    public boolean isLeftAssoc() {
        return LEFT_ASSOC;
    }

    /**
     * Určí, zda je operátor asociativní zprava.
     * 
     * @return TRUE, pokud je token asociativní zprava, jinak FALSE
     **/
    public boolean isRightAssoc() {
        return !LEFT_ASSOC;
    }

    /**
     * Provede příslušný výpočet nad předanými operandy a vrátí nové číslo představující výsledek.
     * 
     * @param a levý operand
     * @param b pravý operand
     * @return výsledek výpočtu
     **/
    public Number calc(Number a, Number b) {
        if (OperatorTokenType.ADD.stringEquals(str)) {
            return Number.createNumber(a.VALUE + b.VALUE);
        }

        if (OperatorTokenType.SUB.stringEquals(str)) {
            return Number.createNumber(a.VALUE - b.VALUE);
        }

        if (OperatorTokenType.MUL.stringEquals(str)) {
            return Number.createNumber(a.VALUE * b.VALUE);
        }

        if (OperatorTokenType.DIV.stringEquals(str)) {
            return Number.createNumber(a.VALUE / b.VALUE);
        }

        if (OperatorTokenType.POW.stringEquals(str)) {
            return Number.createNumber(Math.pow(a.VALUE, b.VALUE));
        }

        if (OperatorTokenType.MOD.stringEquals(str)) {
            return Number.createNumber(a.VALUE % b.VALUE);
        }

        return null;
    }
}
