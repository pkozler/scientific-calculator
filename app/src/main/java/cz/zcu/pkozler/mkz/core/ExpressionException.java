package cz.zcu.pkozler.mkz.core;

/**
 *
 * @author Petr Kozler
 */
public class ExpressionException extends Exception {

    public final ExpressionExceptionCode CODE;

    public ExpressionException(ExpressionExceptionCode code) {
        super();
        CODE = code;
    }
    
}
