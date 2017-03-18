package cz.zcu.pkozler.mkz.core;

/**
 *
 * @author Petr Kozler
 */
public abstract class AExpressionException extends Exception {
    
    public final String MESSAGE;
    
    public AExpressionException(String message) {
        super();
        MESSAGE = message;
    }
    
}
