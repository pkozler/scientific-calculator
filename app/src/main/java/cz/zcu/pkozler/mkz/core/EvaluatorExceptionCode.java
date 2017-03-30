package cz.zcu.pkozler.mkz.core;

/**
 * Výčtový typ představující identifikační kódy jednotlivých typů chyb, které mohou nastat
 * v průběhu zpracování nebo vyhodnocení matematického výrazu.
 *
 * @author Petr Kozler
 */
public enum EvaluatorExceptionCode {

    EMPTY_TOKEN, // vytvořen token bez textové reprezentace
    INVALID_SYMBOLS, // nalezen neplatný symbol ve výrazu
    MISPLACED_ARG_SEPARATOR, // nalezen oddělovač argumentů funkce na nesprávném místě
    MISSING_LEFT_PARENTHESES, // zjištěna chybějící levá závorka
    MISSING_RIGHT_PARENTHESES, // zjištěna chybějící pravá závorka
    WRONG_VAR_SYMBOL, // nalezen nepodporovaný symbol proměnné
    MISSING_OPERANDS, // zjištěn chybějící operátor
    MISSING_ARGS, // zjištěny chybějící argumenty funkce
    TOO_MANY_VALUES, // nalezena přebývající hodnota
    ILLEGAL_FACTORIAL_ARG, // zjištěny neplatné argumenty funkce "faktoriál"

}
