package cz.zcu.pkozler.mkz.core.tokens.types;

/**
 * Rozhraní představující typ tokenu v matematickém výrazu, který poskytuje funkcionalitu
 * pro získání své textové reprezentace upravené do podoby pro použití v regulárním výrazu,
 * a dále funkcionalitu pro testování shody textové reprezentace se zadaným řetězcem.
 */
public interface ITokenType {

    /**
     * Otestuje, zda se textová reprezentace shoduje s předaným řetězcem.
     *
     * @param str řetězec
     * @return true, pokud se řětězce shodují, jinak false
     */
    boolean stringEquals(String str);

    /**
     * Vrátí textovou reprezentaci typu tokenu upravenou pro použití v regulárním výrazu.
     * Úprava spočívá v doplnění escapovacího znaku, je-li klíčové slovo, resp. symbol představující
     * typ tokenu zároveň znakem, který má v regulárních výrazech speciální význam.
     * Nutnost této úpravy indikuje uchovávaný příznak zadaný při vytvoření typu.
     *
     * @return upravená textová reprezentace
     */
    String toRegexString();

}
