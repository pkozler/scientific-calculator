package cz.zcu.pkozler.mkz.ui.modes;

/**
 * Výčtový typ reprezentující možné konfigurace zobrazených tlačítek na klávesnici kalkulačky,
 * je-li jako základní režim právě aktivní režim zobrazení tlačítek pro zadávání funkcí.
 *
 * @author Petr Kozler
 */
public enum FunctionInputMode {
    FUNCTION_MODE_1, // režim funkcí č. 1 (umožňuje mj. zadávání mocnin, odmocnin, logaritmů a abs. hodnoty)
    FUNCTION_MODE_2 // režim funkcí č. 2 (umožňuje mj. zadávání goniometrických a cyklometrických funkcí)
}
