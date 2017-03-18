package cz.zcu.pkozler.mkz.core.tokens;

import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.core.helpers.AuxiliaryMath;

/**
 * Třída představující funkce v matematickém výrazu.
 *
 * @author Petr Kozler
 */
public class Function extends Token {

    /// <summary>
    /// minimální povolený počet argumentů funkce
    /// </summary>
    public final int MIN_ARGC;

    /// <summary>
    /// maximální povolený počet argumentů funkce
    /// </summary>
    public final int MAX_ARGC;

    /// <summary>
    /// Vytvoří novou funkci a nastaví příslušný interval pro platný počet argumentů.
    /// </summary>
    /// <param name="str">textová reprezentace</param>
    public Function(String str) {
        super(str);

        if (FunctionType.YROOT.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 2;
        } else if (FunctionType.ACOSG.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ACOSH.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ASIND.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ASING.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ACOSD.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ATAND.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ATANG.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ATANH.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.FLOOR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ASINH.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.CBRT.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.TANG.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.SING.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.SINH.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.TANH.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ATANR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.FRAC.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.SQRT.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.SIND.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ACOSR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.TAND.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.COSH.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.COSG.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.COSD.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.ASINR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.CEIL.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.FACT.KEYWORD.equals(str)) {
            MIN_ARGC = 1;
            MAX_ARGC = 2;
        } else if (FunctionType.ABS.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.REC.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.POW.KEYWORD.equals(str)) {
            MIN_ARGC = 1;
            MAX_ARGC = 2;
        } else if (FunctionType.LOG.KEYWORD.equals(str)) {
            MIN_ARGC = 1;
            MAX_ARGC = 2;
        } else if (FunctionType.SGN.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.INT.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.TANR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.DMS.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.DEG.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.COSR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.SINR.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.EXP.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.LN.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.CB.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionType.SQ.KEYWORD.equals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else {
            MIN_ARGC = MAX_ARGC = 0;
        }
    }

    /// <summary>
    /// Provede příslušný výpočet nad předanými argumenty a vrátí nové číslo představující výsledek.
    /// </summary>
    /// <param name="args">seznam argumentů funkce</param>
    /// <returns>výsledek výpočtu</returns>
    public Number calc(Number... args) throws ExpressionException {
        if (FunctionType.YROOT.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.yroot(args[1].VALUE, args[0].VALUE));
        } else if (FunctionType.ACOSG.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.acosg((args[0].VALUE)));
        } else if (FunctionType.ACOSH.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.acosh(args[0].VALUE));
        } else if (FunctionType.ASIND.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.asind((args[0].VALUE)));
        } else if (FunctionType.ASING.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.asing((args[0].VALUE)));
        } else if (FunctionType.ACOSD.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.acosd((args[0].VALUE)));
        } else if (FunctionType.ATAND.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.atand((args[0].VALUE)));
        } else if (FunctionType.ATANG.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.atang((args[0].VALUE)));
        } else if (FunctionType.ATANH.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.atanh(args[0].VALUE));
        } else if (FunctionType.FLOOR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.floor(args[0].VALUE));
        } else if (FunctionType.ASINH.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.asinh(args[0].VALUE));
        } else if (FunctionType.CBRT.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.cbrt(args[0].VALUE));
        } else if (FunctionType.TANG.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.tang((args[0].VALUE)));
        } else if (FunctionType.SING.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.sing((args[0].VALUE)));
        } else if (FunctionType.SINH.KEYWORD.equals(str)) {
            return Number.createNumber(Math.sinh(args[0].VALUE));
        } else if (FunctionType.TANH.KEYWORD.equals(str)) {
            return Number.createNumber(Math.tanh(args[0].VALUE));
        } else if (FunctionType.ATANR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.atan(args[0].VALUE));
        } else if (FunctionType.FRAC.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.fraction(args[0].VALUE));
        } else if (FunctionType.SQRT.KEYWORD.equals(str)) {
            return Number.createNumber(Math.sqrt(args[0].VALUE));
        } else if (FunctionType.SIND.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.sind((args[0].VALUE)));
        } else if (FunctionType.ACOSR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.acos(args[0].VALUE));
        } else if (FunctionType.TAND.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.tand((args[0].VALUE)));
        } else if (FunctionType.COSH.KEYWORD.equals(str)) {
            return Number.createNumber(Math.cosh(args[0].VALUE));
        } else if (FunctionType.COSG.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.acosg((args[0].VALUE)));
        } else if (FunctionType.COSD.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.cosd((args[0].VALUE)));
        } else if (FunctionType.ASINR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.asin(args[0].VALUE));
        } else if (FunctionType.CEIL.KEYWORD.equals(str)) {
            return Number.createNumber(Math.ceil(args[0].VALUE));
        } else if (FunctionType.FACT.KEYWORD.equals(str)) {
            if (args.length == 1) {
                return Number.createNumber(AuxiliaryMath.factorial(args[0].VALUE));
            } else {
                return Number.createNumber(AuxiliaryMath.factorial(args[1].VALUE, args[0].VALUE));
            }
        } else if (FunctionType.ABS.KEYWORD.equals(str)) {
            return Number.createNumber(Math.abs(args[0].VALUE));
        } else if (FunctionType.REC.KEYWORD.equals(str)) {
            return Number.createNumber(1 / args[0].VALUE);
        } else if (FunctionType.POW.KEYWORD.equals(str)) {
            if (args.length == 1) {
                return Number.createNumber(Math.pow(args[0].VALUE, 10));
            } else {
                return Number.createNumber(Math.pow(args[1].VALUE, args[0].VALUE));
            }
        } else if (FunctionType.LOG.KEYWORD.equals(str)) {
            if (args.length == 1) {
                return Number.createNumber(Math.log10(args[0].VALUE));
            } else {
                return Number.createNumber(Math.log(args[1].VALUE) / Math.log(args[0].VALUE));
            }
        } else if (FunctionType.SGN.KEYWORD.equals(str)) {
            return Number.createNumber(Math.signum(args[0].VALUE));
        } else if (FunctionType.INT.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.integer(args[0].VALUE));
        } else if (FunctionType.TANR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.tan(args[0].VALUE));
        } else if (FunctionType.DMS.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.dms(args[0].VALUE));
        } else if (FunctionType.DEG.KEYWORD.equals(str)) {
            return Number.createNumber(AuxiliaryMath.deg(args[0].VALUE));
        } else if (FunctionType.COSR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.cos(args[0].VALUE));
        } else if (FunctionType.SINR.KEYWORD.equals(str)) {
            return Number.createNumber(Math.sin(args[0].VALUE));
        } else if (FunctionType.EXP.KEYWORD.equals(str)) {
            return Number.createNumber(Math.exp(args[0].VALUE));
        } else if (FunctionType.LN.KEYWORD.equals(str)) {
            return Number.createNumber(Math.log(args[0].VALUE));
        } else if (FunctionType.CB.KEYWORD.equals(str)) {
            return Number.createNumber(args[0].VALUE * args[0].VALUE * args[0].VALUE);
        } else if (FunctionType.SQ.KEYWORD.equals(str)) {
            return Number.createNumber(args[0].VALUE * args[0].VALUE);
        }

        return null;
    }
}
