package cz.zcu.pkozler.mkz.core.tokens;

/**
 * Třída představující proměnné v matematickém výrazu, které uchovávají
 * informaci o svém indexu, na základě níž dojde při vyhodnocování výrazu k
 * dosazení příslušné zadané číselné hodnoty.
 *
 * @author Petr Kozler
 */
public class Variable extends Token {

    /// <summary>
    /// Vytvoří novou proměnnou.
    /// </summary>
    /// <param name="str">textová reprezentace</param>
    public Variable(String str) {
        super(str);
    }

}
