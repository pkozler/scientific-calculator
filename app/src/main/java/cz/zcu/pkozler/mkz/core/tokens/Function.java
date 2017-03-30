package cz.zcu.pkozler.mkz.core.tokens;

import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.core.helpers.AuxiliaryMath;
import cz.zcu.pkozler.mkz.core.tokens.types.FunctionTokenType;

/**
 * Třída představující funkce v matematickém výrazu.
 *
 * @author Petr Kozler
 */
public class Function extends Token {

    /**
     * minimální povolený počet argumentů funkce
     **/
    public final int MIN_ARGC;

    /**
     * maximální povolený počet argumentů funkce
     **/
    public final int MAX_ARGC;

    /**
     * Vytvoří novou funkci a nastaví příslušný interval pro platný počet argumentů.
     * 
     * @param str textová reprezentace
     **/
    public Function(String str) {
        super(str);

        if (FunctionTokenType.YROOT.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 2;
        } else if (FunctionTokenType.ACOSG.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ACOSH.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ASIND.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ASING.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ACOSD.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ATAND.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ATANG.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ATANH.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.FLOOR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ASINH.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.CBRT.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.TANG.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.SING.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.SINH.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.TANH.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ATANR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.FRAC.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.SQRT.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.SIND.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ACOSR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.TAND.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.COSH.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.COSG.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.COSD.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.ASINR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.CEIL.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.FACT.stringEquals(str)) {
            MIN_ARGC = 1;
            MAX_ARGC = 2;
        } else if (FunctionTokenType.ABS.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.REC.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.POW.stringEquals(str)) {
            MIN_ARGC = 1;
            MAX_ARGC = 2;
        } else if (FunctionTokenType.LOG.stringEquals(str)) {
            MIN_ARGC = 1;
            MAX_ARGC = 2;
        } else if (FunctionTokenType.SGN.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.INT.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.TANR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.DMS.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.DEG.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.COSR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.SINR.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.EXP.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.LN.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.CB.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else if (FunctionTokenType.SQ.stringEquals(str)) {
            MIN_ARGC = MAX_ARGC = 1;
        } else {
            MIN_ARGC = MAX_ARGC = 0;
        }
    }

    /**
     * Provede příslušný výpočet nad předanými argumenty a vrátí nové číslo představující výsledek.
     * 
     * @param args seznam argumentů funkce
     * @return výsledek výpočtu
     **/
    public Number calc(Number... args) throws EvaluatorException {
        if (FunctionTokenType.YROOT.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.yroot(args[1].VALUE, args[0].VALUE));
        } else if (FunctionTokenType.ACOSG.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.acosg((args[0].VALUE)));
        } else if (FunctionTokenType.ACOSH.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.acosh(args[0].VALUE));
        } else if (FunctionTokenType.ASIND.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.asind((args[0].VALUE)));
        } else if (FunctionTokenType.ASING.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.asing((args[0].VALUE)));
        } else if (FunctionTokenType.ACOSD.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.acosd((args[0].VALUE)));
        } else if (FunctionTokenType.ATAND.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.atand((args[0].VALUE)));
        } else if (FunctionTokenType.ATANG.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.atang((args[0].VALUE)));
        } else if (FunctionTokenType.ATANH.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.atanh(args[0].VALUE));
        } else if (FunctionTokenType.FLOOR.stringEquals(str)) {
            return Number.createNumber(Math.floor(args[0].VALUE));
        } else if (FunctionTokenType.ASINH.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.asinh(args[0].VALUE));
        } else if (FunctionTokenType.CBRT.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.cbrt(args[0].VALUE));
        } else if (FunctionTokenType.TANG.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.tang((args[0].VALUE)));
        } else if (FunctionTokenType.SING.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.sing((args[0].VALUE)));
        } else if (FunctionTokenType.SINH.stringEquals(str)) {
            return Number.createNumber(Math.sinh(args[0].VALUE));
        } else if (FunctionTokenType.TANH.stringEquals(str)) {
            return Number.createNumber(Math.tanh(args[0].VALUE));
        } else if (FunctionTokenType.ATANR.stringEquals(str)) {
            return Number.createNumber(Math.atan(args[0].VALUE));
        } else if (FunctionTokenType.FRAC.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.fraction(args[0].VALUE));
        } else if (FunctionTokenType.SQRT.stringEquals(str)) {
            return Number.createNumber(Math.sqrt(args[0].VALUE));
        } else if (FunctionTokenType.SIND.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.sind((args[0].VALUE)));
        } else if (FunctionTokenType.ACOSR.stringEquals(str)) {
            return Number.createNumber(Math.acos(args[0].VALUE));
        } else if (FunctionTokenType.TAND.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.tand((args[0].VALUE)));
        } else if (FunctionTokenType.COSH.stringEquals(str)) {
            return Number.createNumber(Math.cosh(args[0].VALUE));
        } else if (FunctionTokenType.COSG.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.acosg((args[0].VALUE)));
        } else if (FunctionTokenType.COSD.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.cosd((args[0].VALUE)));
        } else if (FunctionTokenType.ASINR.stringEquals(str)) {
            return Number.createNumber(Math.asin(args[0].VALUE));
        } else if (FunctionTokenType.CEIL.stringEquals(str)) {
            return Number.createNumber(Math.ceil(args[0].VALUE));
        } else if (FunctionTokenType.FACT.stringEquals(str)) {
            if (args.length == 1) {
                return Number.createNumber(AuxiliaryMath.factorial(args[0].VALUE));
            } else {
                return Number.createNumber(AuxiliaryMath.factorial(args[1].VALUE, args[0].VALUE));
            }
        } else if (FunctionTokenType.ABS.stringEquals(str)) {
            return Number.createNumber(Math.abs(args[0].VALUE));
        } else if (FunctionTokenType.REC.stringEquals(str)) {
            return Number.createNumber(1 / args[0].VALUE);
        } else if (FunctionTokenType.POW.stringEquals(str)) {
            if (args.length == 1) {
                return Number.createNumber(Math.pow(args[0].VALUE, 10));
            } else {
                return Number.createNumber(Math.pow(args[1].VALUE, args[0].VALUE));
            }
        } else if (FunctionTokenType.LOG.stringEquals(str)) {
            if (args.length == 1) {
                return Number.createNumber(Math.log10(args[0].VALUE));
            } else {
                return Number.createNumber(Math.log(args[1].VALUE) / Math.log(args[0].VALUE));
            }
        } else if (FunctionTokenType.SGN.stringEquals(str)) {
            return Number.createNumber(Math.signum(args[0].VALUE));
        } else if (FunctionTokenType.INT.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.integer(args[0].VALUE));
        } else if (FunctionTokenType.TANR.stringEquals(str)) {
            return Number.createNumber(Math.tan(args[0].VALUE));
        } else if (FunctionTokenType.DMS.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.dms(args[0].VALUE));
        } else if (FunctionTokenType.DEG.stringEquals(str)) {
            return Number.createNumber(AuxiliaryMath.deg(args[0].VALUE));
        } else if (FunctionTokenType.COSR.stringEquals(str)) {
            return Number.createNumber(Math.cos(args[0].VALUE));
        } else if (FunctionTokenType.SINR.stringEquals(str)) {
            return Number.createNumber(Math.sin(args[0].VALUE));
        } else if (FunctionTokenType.EXP.stringEquals(str)) {
            return Number.createNumber(Math.exp(args[0].VALUE));
        } else if (FunctionTokenType.LN.stringEquals(str)) {
            return Number.createNumber(Math.log(args[0].VALUE));
        } else if (FunctionTokenType.CB.stringEquals(str)) {
            return Number.createNumber(args[0].VALUE * args[0].VALUE * args[0].VALUE);
        } else if (FunctionTokenType.SQ.stringEquals(str)) {
            return Number.createNumber(args[0].VALUE * args[0].VALUE);
        }

        return null;
    }
}
