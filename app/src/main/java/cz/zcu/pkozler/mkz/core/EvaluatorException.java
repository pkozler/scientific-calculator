package cz.zcu.pkozler.mkz.core;

/**
 * Třída představující výjimku, která může nastat v průběhu zpracování nebo vyhodnocení
 * matematického výrazu.
 * Uchovává identifikační kód chyby, podle kterého může být zobrazena odpovídající chybová hláška.
 *
 * @author Petr Kozler
 */
public class EvaluatorException extends Exception {

    /**
     * identifikační kód chyby
     */
    public final EvaluatorExceptionCode CODE;

    /**
     * Vytvoří výjimku se zadaným identifikačním kódem vzniklé chyby.
     *
     * @param code identifikační kód chyby
     */
    public EvaluatorException(EvaluatorExceptionCode code) {
        super();
        CODE = code;
    }
    
}
