package cz.zcu.pkozler.mkz.core.tokens;

/**
 *
 * @author Petr Kozler
 */
public enum ConstantType {
    
    PI("pi"), E("eu");
        
    public final String KEYWORD;

    private ConstantType(String keyword) {
        KEYWORD = keyword;
    }
    
}
