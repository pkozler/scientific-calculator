package cz.zcu.pkozler.mkz.core.tokens;

import java.util.Objects;

import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;

/**
 * Třída představující obecné tokeny v matematickém výrazu zpracovávaném
 * algoritmem Shunting-Yard. Takový token může být číslo, proměnná, konstanta,
 * závorka, operátor, funkce nebo oddělovač argumentů funkce.
 *
 * @author Petr Kozler
 */
public class Token {

    /// <summary>
    /// textová reprezentace tokenu
    /// </summary>
    protected String str;

    /// <summary>
    /// Vytvoří nový token.
    /// </summary>
    /// <param name="str">textová reprezentace</param>
    public Token(String str) {
        this.str = str;
    }

    /// <summary>
    /// Určí, zda je token levá závorka.
    /// </summary>
    /// <returns>TRUE, pokud je token levá závorka, jinak FALSE</returns>
    public boolean isLeftParenthesis() {
        return OtherTokenType.LEFT_PARENTHESIS.KEYWORD.equals(str);
    }

    /// <summary>
    /// Určí, zda je token pravá závorka.
    /// </summary>
    /// <returns>TRUE, pokud je token pravá závorka, jinak FALSE</returns>
    public boolean isRightParenthesis() {
        return OtherTokenType.RIGHT_PARENTHESIS.KEYWORD.equals(str);
    }

    /// <summary>
    /// Určí, zda je token oddělovač argumentů funkce (čárka).
    /// </summary>
    /// <returns>TRUE, pokud je token čárka, jinak FALSE</returns>
    public boolean isArgumentSeparator() {
        return OtherTokenType.ARG_SEPARATOR.KEYWORD.equals(str);
    }

    /// <summary>
    /// Vrátí textovou reprezentaci tokenu.
    /// </summary>
    /// <returns>textová reprezentace</returns>
    @Override
    public String toString() {
        return str;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.str);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if (!Objects.equals(this.str, other.str)) {
            return false;
        }
        return true;
    }
    
}
