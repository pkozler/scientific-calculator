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

    /**
     * textová reprezentace tokenu
     **/
    protected String str;

    /**
     * Vytvoří nový token.
     * 
     * @param str textová reprezentace
     **/
    public Token(String str) {
        this.str = str;
    }

    /**
     * Určí, zda je token levá závorka.
     * 
     * @return TRUE, pokud je token levá závorka, jinak FALSE
     **/
    public boolean isLeftParenthesis() {
        return OtherTokenType.LEFT_PARENTHESIS.stringEquals(str);
    }

    /**
     * Určí, zda je token pravá závorka.
     * 
     * @return TRUE, pokud je token pravá závorka, jinak FALSE
     **/
    public boolean isRightParenthesis() {
        return OtherTokenType.RIGHT_PARENTHESIS.stringEquals(str);
    }

    /**
     * Určí, zda je token oddělovač argumentů funkce (čárka).
     * 
     * @return TRUE, pokud je token čárka, jinak FALSE
     **/
    public boolean isArgumentSeparator() {
        return OtherTokenType.ARG_SEPARATOR.stringEquals(str);
    }

    /**
     * Vrátí textovou reprezentaci tokenu.
     * 
     * @return textová reprezentace
     */
    @Override
    public String toString() {
        return str;
    }

    /**
     * Vypočítá hashcode tokenu na základě jeho textové reprezentace.
     *
     * @return hashcode tokenu
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.str);
        return hash;
    }

    /**
     * Otestuje shodu s předaným tokenem. Tokeny jsou považovánny za shodné právě tehdy, mají-li
     * totožnou textovou reprezentaci.
     *
     * @param obj porovnávaný token
     * @return true, je-li předaný token shodný, jinak false
     */
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
        if (!this.str.equals(other.str)) {
            return false;
        }
        return true;
    }
    
}
