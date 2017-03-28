package cz.zcu.pkozler.mkz.core.tokens;

import cz.zcu.pkozler.mkz.core.tokens.types.OperatorType;

/**
 * Třída představující operátory v matematickém výrazu.
 *
 * @author Petr Kozler
 */
public class Operator extends Token {

    /// <summary>
    /// informace o asociativitě operátoru
    /// </summary>
    private final boolean LEFT_ASSOC;

    /// <summary>
    /// informace o prioritě operátoru
    /// </summary>
    public final int PRECEDENCE;

    /// <summary>
    /// Vytvoří nový operátor a nastaví mu příslušnou asociativitu a prioritu.
    /// </summary>
    /// <param name="str">textová reprezentace</param>
    public Operator(String str) {
        super(str);

        if (OperatorType.ADD.KEYWORD.equals(str)
                || OperatorType.SUB.KEYWORD.equals(str)) {
            LEFT_ASSOC = true;
            PRECEDENCE = 1;
        } else if (OperatorType.MUL.KEYWORD.equals(str)
                || OperatorType.DIV.KEYWORD.equals(str)
                || OperatorType.MOD.KEYWORD.equals(str)) {
            LEFT_ASSOC = true;
            PRECEDENCE = 2;
        } else if (OperatorType.POW.KEYWORD.equals(str)) {
            LEFT_ASSOC = false;
            PRECEDENCE = 3;
        } else {
            LEFT_ASSOC = false;
            PRECEDENCE = 0;
        }
    }

    /// <summary>
    /// Určí, zda je operátor asociativní zleva.
    /// </summary>
    /// <returns>TRUE, pokud je token asociativní zleva, jinak FALSE</returns>
    public boolean isLeftAssoc() {
        return LEFT_ASSOC;
    }

    /// <summary>
    /// Určí, zda je operátor asociativní zprava.
    /// </summary>
    /// <returns>TRUE, pokud je token asociativní zprava, jinak FALSE</returns>
    public boolean isRightAssoc() {
        return !LEFT_ASSOC;
    }

    /// <summary>
    /// Provede příslušný výpočet nad předanými operandy a vrátí nové číslo představující výsledek.
    /// </summary>
    /// <param name="a">levý operand</param>
    /// <param name="b">pravý operand</param>
    /// <returns>výsledek výpočtu</returns>
    public Number calc(Number a, Number b) {
        if (OperatorType.ADD.KEYWORD.equals(str)) {
            return Number.createNumber(a.VALUE + b.VALUE);
        }

        if (OperatorType.SUB.KEYWORD.equals(str)) {
            return Number.createNumber(a.VALUE - b.VALUE);
        }

        if (OperatorType.MUL.KEYWORD.equals(str)) {
            return Number.createNumber(a.VALUE * b.VALUE);
        }

        if (OperatorType.DIV.KEYWORD.equals(str)) {
            return Number.createNumber(a.VALUE / b.VALUE);
        }

        if (OperatorType.POW.KEYWORD.equals(str)) {
            return Number.createNumber(Math.pow(a.VALUE, b.VALUE));
        }

        if (OperatorType.MOD.KEYWORD.equals(str)) {
            return Number.createNumber(a.VALUE % b.VALUE);
        }

        return null;
    }
}
