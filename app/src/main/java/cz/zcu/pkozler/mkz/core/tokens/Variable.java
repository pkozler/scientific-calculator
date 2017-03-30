package cz.zcu.pkozler.mkz.core.tokens;

/**
 * Třída představující symboly proměnné v matematickém výrazu,
 * za které je dosazena zadaná číselná hodnota v průběhu vyhodnocování tohoto výrazu.
 *
 * @author Petr Kozler
 */
public class Variable extends Token {

    /**
     * Vytvoří nový symbol proměnné.
     * 
     * @param str textová reprezentace
     **/
    public Variable(String str) {
        super(str);
    }

}
