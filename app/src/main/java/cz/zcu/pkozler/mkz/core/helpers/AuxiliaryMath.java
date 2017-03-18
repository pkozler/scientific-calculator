package cz.zcu.pkozler.mkz.core.helpers;

import java.math.BigDecimal;

/**
 * Knihovní třída rozšiřující vestavěnou matematickou knihovnu. Poskytuje
 * dodatečné funkce pro výpočty.
 *
 * @author Petr Kozler
 */
public final class AuxiliaryMath {

    /// <summary>
    /// maximální povolená hodnota pro výpočet faktoriálu
    /// </summary>
    private static final double MAX_FACT_ARG = 1000;

    /// <summary>
    /// Vypočítá 1. faktoriál ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>faktoriál</returns>
    public static double factorial(double a) {
        return AuxiliaryMath.factorial(a, 1);
    }

    /// <summary>
    /// Vypočítá k-tý multifaktoriál ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <param name="b">"stupeň" faktoriálu</param>
    /// <returns>k-tý faktoriál</returns>
    public static double factorial(double a, double b) {
        if (a > MAX_FACT_ARG) {
            a = MAX_FACT_ARG;
        }

        if (a != AuxiliaryMath.integer(a) || b != AuxiliaryMath.integer(b)) {
            throw new IllegalArgumentException("Parametry funkce faktoriál musí být celá čísla.");
        }

        double result = 1;

        for (double d = 1; d <= a; d += b) {
            result *= d;
        }

        return result;
    }
    
    /// <summary>
    /// Vrátí celou část zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>celá část</returns>
    public static double integer(double a) {
        BigDecimal bd = new BigDecimal(a);
        bd = bd.setScale(0, BigDecimal.ROUND_DOWN);
        
        return bd.doubleValue();
    }
    
    /// <summary>
    /// Vrátí desetinnou část zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>desetinná část</returns>
    public static double fraction(double a) {
        return a - AuxiliaryMath.integer(a);
    }

    /// <summary>
    /// Vypočítá reprezentaci zadané hodnoty v šedesátkové soustavě (stupně, minuty, vteřiny).
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>převod na stupně, minuty a vteřiny</returns>
    public static double dms(double a) {
        double decdeg = a;
        double minsec = (decdeg - AuxiliaryMath.integer(decdeg)) * 60;
        double sec = (minsec - AuxiliaryMath.integer(minsec)) * 60;
        double degminsec = (AuxiliaryMath.integer(sec) / 10000)
                + (AuxiliaryMath.integer(minsec) / 100) + AuxiliaryMath.integer(decdeg);

        return degminsec;
    }

    /// <summary>
    /// Vypočítá desítkovou reprezentaci zadané hodnoty v šedesátkové soustavě.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>převod na desítkovou reprezentaci</returns>
    public static double deg(double a) {
        double degminsec = a;
        double decsec = (degminsec * 100 - AuxiliaryMath.integer(degminsec * 100)) / .6;
        double degmin = (AuxiliaryMath.integer(degminsec * 100) + decsec) / 100;
        double deg = AuxiliaryMath.integer(degmin);
        double decdeg = deg + (degmin - deg) / .6;

        return decdeg;
    }

    /// <summary>
    /// Vypočítá třetí odmocninu ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>třetí odmocnina</returns>
    public static double cbrt(double a) {
        return Math.pow(a, 1.0 / 3.0);
    }

    /// <summary>
    /// Vypočítá y-tou odmocninu ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <param name="b">odmocnina</param>
    /// <returns>y-tá odmocnina</returns>
    public static double yroot(double a, double b) {
        return Math.pow(a, 1.0 / b);
    }

    /// <summary>
    /// Vypočítá argument hyperbolického sinu ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>argument hyperbolického sinu</returns>
    public static double asinh(double a) {
        return Math.log(a + Math.sqrt(a * a + 1));
    }

    /// <summary>
    /// Vypočítá argument hyperbolického kosinu ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>argument hyperbolického kosinu</returns>
    public static double acosh(double a) {
        return Math.log(a + Math.sqrt(a * a - 1));
    }

    /// <summary>
    /// Vypočítá argument hyperbolického tangentu ze zadané hodnoty.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>argument hyperbolického tangentu</returns>
    public static double atanh(double a) {
        return Math.log((1 + a) / (1 - a)) / 2;
    }

    /// <summary>
    /// Vypočítá sinus ze zadaného úhlu ve stupních.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>sinus úhlu ve stupních</returns>
    public static double sind(double a) {
        return Math.sin(degToRad(a));
    }

    /// <summary>
    /// Vypočítá kosinus ze zadaného úhlu ve stupních.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>kosinus úhlu ve stupních</returns>
    public static double cosd(double a) {
        return Math.cos(degToRad(a));
    }

    /// <summary>
    /// Vypočítá tangens ze zadaného úhlu ve stupních.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>tangens úhlu ve stupních</returns>
    public static double tand(double a) {
        return Math.tan(degToRad(a));
    }

    /// <summary>
    /// Vypočítá úhel ve stupních ze zadaného sinu.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>úhel ve stupních ze sinu</returns>
    public static double asind(double a) {
        return radToDeg(Math.asin(a));
    }

    /// <summary>
    /// Vypočítá úhel ve stupních ze zadaného kosinu.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>úhel ve stupních z kosinu</returns>
    public static double acosd(double a) {
        return radToDeg(Math.acos(a));
    }

    /// <summary>
    /// Vypočítá úhel ve stupních ze zadaného tangentu.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>úhel ve stupních z tangentu</returns>
    public static double atand(double a) {
        return radToDeg(Math.atan(a));
    }

    /// <summary>
    /// Vypočítá sinus ze zadaného úhlu v gradech.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>sinus úhlu v gradech</returns>
    public static double sing(double a) {
        return Math.sin(gradToRad(a));
    }

    /// <summary>
    /// Vypočítá kosinus ze zadaného úhlu v gradech.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>kosinus úhlu v gradech</returns>
    public static double cosg(double a) {
        return Math.cos(gradToRad(a));
    }

    /// <summary>
    /// Vypočítá tangens ze zadaného úhlu v gradech.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>tangens úhlu v gradech</returns>
    public static double tang(double a) {
        return Math.tan(gradToRad(a));
    }

    /// <summary>
    /// Vypočítá úhel v gradech ze zadaného sinu.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>úhel v gradech ze sinu</returns>
    public static double asing(double a) {
        return radToGrad(Math.asin(a));
    }

    /// <summary>
    /// Vypočítá úhel v gradech ze zadaného kosinu.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>úhel v gradech z kosinu</returns>
    public static double acosg(double a) {
        return radToGrad(Math.acos(a));
    }

    /// <summary>
    /// Vypočítá úhel v gradech ze zadaného tangentu.
    /// </summary>
    /// <param name="a">argument</param>
    /// <returns>úhel v gradech z tangentu</returns>
    public static double atang(double a) {
        return radToGrad(Math.atan(a));
    }

    /// <summary>
    /// Převede zadaný úhel ve stupních na úhel v radiánech.
    /// </summary>
    /// <param name="a">stupně</param>
    /// <returns>radiány</returns>
    private static double degToRad(double a) {
        return (Math.PI / 180) * a;
    }

    /// <summary>
    /// Převede zadaný úhel v gradech na úhel v radiánech.
    /// </summary>
    /// <param name="a">grady</param>
    /// <returns>radiány</returns>
    private static double gradToRad(double a) {
        return (Math.PI / 200) * a;
    }

    /// <summary>
    /// Převede zadaný úhel v radiánech na úhel ve stupních.
    /// </summary>
    /// <param name="a">radiány</param>
    /// <returns>stupně</returns>
    private static double radToDeg(double a) {
        return (180 / Math.PI) * a;
    }

    /// <summary>
    /// Převede zadaný úhel v radiánech na úhel v gradech.
    /// </summary>
    /// <param name="a">radiány</param>
    /// <returns>grady</returns>
    private static double radToGrad(double a) {
        return (200 / Math.PI) * a;
    }
}
