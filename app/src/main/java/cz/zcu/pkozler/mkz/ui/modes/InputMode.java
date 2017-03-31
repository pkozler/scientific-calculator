package cz.zcu.pkozler.mkz.ui.modes;

/**
 * Výčtový typ reprezentující základní režimy klávesnice kalkulačky, tj. možné základní
 * konfigurace zobrazených tlačítek pro zadávání vstupu.
 *
 * @author Petr Kozler
 */
public enum InputMode {
    DIGIT_OPERATOR_MODE, // režim zadávání čísel a operátorů
    FUNCTION_MODE // režim zadávání funkcí (dále rozdělen na 2 možné sekundární konfigurace)
}
