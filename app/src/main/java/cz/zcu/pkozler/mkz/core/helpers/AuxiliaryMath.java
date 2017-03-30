package cz.zcu.pkozler.mkz.core.helpers;

import java.math.BigDecimal;

import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.core.EvaluatorExceptionCode;

/**
 * Knihovní třída rozšiřující vestavěnou matematickou knihovnu.
 * Poskytuje dodatečné funkce pro výpočty.
 *
 * @author Petr Kozler
 */
public final class AuxiliaryMath {

    /**
     * maximální povolená hodnota pro výpočet faktoriálu
     **/
    private static final double MAX_FACT_ARG = 1000;

    /**
     * Vypočítá 1. faktoriál ze zadané hodnoty.
     *
     * @param a argument
     * @return faktoriál
     * @throws EvaluatorException
     **/
    public static double factorial(double a) throws EvaluatorException {
        return AuxiliaryMath.factorial(a, 1);
    }

    /**
     * Vypočítá k-tý multifaktoriál ze zadané hodnoty. Maximální hodnota argumentu je omezena
     * z důvodu příliš vysoké výpočetní náročnosti při větších hodnotách.
     *
     * @param a         argument
     * @param b"stupeň" faktoriálu
     * @return k-tý faktoriál
     * @throws EvaluatorException
     **/
    public static double factorial(double a, double b) throws EvaluatorException {
        if (a > MAX_FACT_ARG) {
            a = MAX_FACT_ARG;
        }

        double nA = Math.abs(AuxiliaryMath.integer(a));
        double nB = Math.abs(AuxiliaryMath.integer(b));

        if (a != nA || b != nB) {
            throw new EvaluatorException(EvaluatorExceptionCode.ILLEGAL_FACTORIAL_ARG);
        }

        double result = 1;

        for (double d = 1; d <= a; d += b) {
            result *= d;
        }

        return result;
    }

    /**
     * Vrátí celou část zadané hodnoty.
     *
     * @param a argument
     * @return celá část
     **/
    public static double integer(double a) {
        BigDecimal bd = new BigDecimal(a);
        bd = bd.setScale(0, BigDecimal.ROUND_DOWN);

        return bd.doubleValue();
    }

    /**
     * Vrátí desetinnou část zadané hodnoty.
     *
     * @param a argument
     * @return desetinná část
     **/
    public static double fraction(double a) {
        return a - AuxiliaryMath.integer(a);
    }

    /**
     * Vypočítá reprezentaci zadané hodnoty v šedesátkové soustavě (stupně, minuty, vteřiny).
     *
     * @param a argument
     * @return převod na stupně, minuty a vteřiny
     **/
    public static double dms(double a) {
        double decdeg = a;
        double minsec = (decdeg - AuxiliaryMath.integer(decdeg)) * 60;
        double sec = (minsec - AuxiliaryMath.integer(minsec)) * 60;
        double degminsec = (AuxiliaryMath.integer(sec) / 10000)
                + (AuxiliaryMath.integer(minsec) / 100) + AuxiliaryMath.integer(decdeg);

        return degminsec;
    }

    /**
     * Vypočítá desítkovou reprezentaci zadané hodnoty v šedesátkové soustavě.
     *
     * @param a argument
     * @return převod na desítkovou reprezentaci
     **/
    public static double deg(double a) {
        double degminsec = a;
        double decsec = (degminsec * 100 - AuxiliaryMath.integer(degminsec * 100)) / .6;
        double degmin = (AuxiliaryMath.integer(degminsec * 100) + decsec) / 100;
        double deg = AuxiliaryMath.integer(degmin);
        double decdeg = deg + (degmin - deg) / .6;

        return decdeg;
    }

    /**
     * Vypočítá třetí odmocninu ze zadané hodnoty.
     *
     * @param a argument
     * @return třetí odmocnina
     **/
    public static double cbrt(double a) {
        return Math.pow(a, 1.0 / 3.0);
    }

    /**
     * Vypočítá y-tou odmocninu ze zadané hodnoty.
     *
     * @param a argument
     * @param b odmocnina
     * @return y-tá odmocnina
     **/
    public static double yroot(double a, double b) {
        return Math.pow(a, 1.0 / b);
    }

    /**
     * Vypočítá argument hyperbolického sinu ze zadané hodnoty.
     *
     * @param a argument
     * @return argument hyperbolického sinu
     **/
    public static double asinh(double a) {
        return Math.log(a + Math.sqrt(a * a + 1));
    }

    /**
     * Vypočítá argument hyperbolického kosinu ze zadané hodnoty.
     *
     * @param a argument
     * @return argument hyperbolického kosinu
     **/
    public static double acosh(double a) {
        return Math.log(a + Math.sqrt(a * a - 1));
    }

    /**
     * Vypočítá argument hyperbolického tangentu ze zadané hodnoty.
     *
     * @param a argument
     * @return argument hyperbolického tangentu
     **/
    public static double atanh(double a) {
        return Math.log((1 + a) / (1 - a)) / 2;
    }

    /**
     * Vypočítá sinus ze zadaného úhlu ve stupních.
     *
     * @param a argument
     * @return sinus úhlu ve stupních
     **/
    public static double sind(double a) {
        return Math.sin(degToRad(a));
    }

    /**
     * Vypočítá kosinus ze zadaného úhlu ve stupních.
     *
     * @param a argument
     * @return kosinus úhlu ve stupních
     **/
    public static double cosd(double a) {
        return Math.cos(degToRad(a));
    }

    /**
     * Vypočítá tangens ze zadaného úhlu ve stupních.
     *
     * @param a argument
     * @return tangens úhlu ve stupních
     **/
    public static double tand(double a) {
        return Math.tan(degToRad(a));
    }

    /**
     * Vypočítá úhel ve stupních ze zadaného sinu.
     *
     * @param a argument
     * @return úhel ve stupních ze sinu
     **/
    public static double asind(double a) {
        return radToDeg(Math.asin(a));
    }

    /**
     * Vypočítá úhel ve stupních ze zadaného kosinu.
     *
     * @param a argument
     * @return úhel ve stupních z kosinu
     **/
    public static double acosd(double a) {
        return radToDeg(Math.acos(a));
    }

    /**
     * Vypočítá úhel ve stupních ze zadaného tangentu.
     *
     * @param a argument
     * @return úhel ve stupních z tangentu
     **/
    public static double atand(double a) {
        return radToDeg(Math.atan(a));
    }

    /**
     * Vypočítá sinus ze zadaného úhlu v gradech.
     *
     * @param a argument
     * @return sinus úhlu v gradech
     **/
    public static double sing(double a) {
        return Math.sin(gradToRad(a));
    }

    /**
     * Vypočítá kosinus ze zadaného úhlu v gradech.
     *
     * @param a argument
     * @return kosinus úhlu v gradech
     **/
    public static double cosg(double a) {
        return Math.cos(gradToRad(a));
    }

    /**
     * Vypočítá tangens ze zadaného úhlu v gradech.
     *
     * @param a argument
     * @return tangens úhlu v gradech
     **/
    public static double tang(double a) {
        return Math.tan(gradToRad(a));
    }

    /**
     * Vypočítá úhel v gradech ze zadaného sinu.
     *
     * @param a argument
     * @return úhel v gradech ze sinu
     **/
    public static double asing(double a) {
        return radToGrad(Math.asin(a));
    }

    /**
     * Vypočítá úhel v gradech ze zadaného kosinu.
     *
     * @param a argument
     * @return úhel v gradech z kosinu
     **/
    public static double acosg(double a) {
        return radToGrad(Math.acos(a));
    }

    /**
     * Vypočítá úhel v gradech ze zadaného tangentu.
     *
     * @param a argument
     * @return úhel v gradech z tangentu
     **/
    public static double atang(double a) {
        return radToGrad(Math.atan(a));
    }

    /**
     * Převede zadaný úhel ve stupních na úhel v radiánech.
     *
     * @param a stupně
     * @return radiány
     **/
    private static double degToRad(double a) {
        return (Math.PI / 180) * a;
    }

    /**
     * Převede zadaný úhel v gradech na úhel v radiánech.
     *
     * @param a grady
     * @return radiány
     **/
    private static double gradToRad(double a) {
        return (Math.PI / 200) * a;
    }

    /**
     * Převede zadaný úhel v radiánech na úhel ve stupních.
     *
     * @param a radiány
     * @return stupně
     **/
    private static double radToDeg(double a) {
        return (180 / Math.PI) * a;
    }

    /**
     * Převede zadaný úhel v radiánech na úhel v gradech.
     *
     * @param a radiány
     * @return grady
     **/
    private static double radToGrad(double a) {
        return (200 / Math.PI) * a;
    }
}
