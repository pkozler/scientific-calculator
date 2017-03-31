package cz.zcu.pkozler.mkz.ui.modes;

/**
 * Výčtový typ reprezentující možné konfigurace zobrazených tlačítek na klávesnici kalkulačky,
 * je-li jako základní režim právě aktivní režim zobrazení tlačítek pro zadávání funkcí
 * a zároveň je aktivní konfigurace tlačítek pro zadávání goniometrických funkcí.
 *
 * @author Petr Kozler
 */
public enum AngleMode {
    DEG_MODE, // režim funkcí pro výpočty s úhly ve stupních
    RAD_MODE, // režim funkcí pro výpočty s úhly v radiánech
    GRAD_MODE // režim funkcí pro výpočty s úhly v gradech
}
