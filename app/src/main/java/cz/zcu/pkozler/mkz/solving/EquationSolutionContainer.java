package cz.zcu.pkozler.mkz.solving;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída sloužící jako přepravka pro řešení rovnice, která byla nalezena pomocí numerické metody.
 * Umožňuje snadnou identifikaci singulárních případů (žádné nebo nekonečně mnoho řešení)
 * a poskytuje funkcionalitu pro odpovídající způsob aktualizace seznamu řešení určeného k zobrazení.
 *
 * @author Petr Kozler
 */
public class EquationSolutionContainer {

    /**
     * vnitřní seznam pro dočasné uchování nalezených konkrétních řešení aktuálně zadané rovnice
     */
    private List<Double> solutions = new ArrayList<>();

    /**
     * příznak detekce nekonečně mnoha řešení aktuálně zadané rovnice
     */
    private boolean infiniteManySolutions = false;

    /**
     * Přidá platnou číselnou hodnotu představující jedno nalezené konkrétní řešení
     * jako prvek vnitřního seznamu řešení.
     *
     * @param value nalezené řešení rovnice
     */
    public void addSolution(Double value) {
        if (value == null) {
            return;
        }

        solutions.add(value);
    }

    /**
     * Nastaví příznak detekce pravděpodobně nekonečného počtu řešení
     * v právě provedeném kroku hledání řešení.
     */
    public void setInfiniteManySolutions() {
        infiniteManySolutions = true;
    }

    /**
     * Identifikuje případ nulového počtu nalezených řešení pro aktuálně nastavené parametry
     * numerické metody pro hledání řešení.
     * Pro tento případ musí platit, že v žádném kroku hledání řešení nebyl detekován
     * nekonečný počet řešení, a že seznam konkrétních řešení po provedení všech kroků
     * zůstal prázdný.
     *
     * @return true při identifikaci nulového počtu řešení, jinak false
     */
    public boolean hasNoSolution() {
        return !infiniteManySolutions && solutions.isEmpty();
    }

    /**
     * Identifikuje případ nekonečného počtu nalezených řešení pro aktuálně nastavené parametry
     * numerické metody pro hledání řešení.
     * Pro tento případ musí platit, že seznam konkrétních řešení po provedení všech kroků
     * zůstal prázdný, a že alespoň v některém kroku hledání řešení byl detekován nekonečný
     * počet řešení.
     * Pokud byla nalezena konkrétní řešení, pak případná detekce nekonečně mnoha řešení
     * v některém z kroků je považována za chybu způsobenou nedostatečnou přesností
     * numerické metody hledání řešení s aktuálně nastavenými parametry.
     *
     * @return true při identifikaci nekonečného počtu řešení, jinak false
     */
    public boolean hasInfiniteManySolutions() {
        return solutions.isEmpty() && infiniteManySolutions;
    }

    /**
     * Přidá číselné hodnoty představující nalezená konkrétní řešení rovnice do předaného seznamu.
     * Stávající položky předaného seznamu jsou odstraněny.
     */
    public void getSolutions(List<Double> list) {
        if (list == null) {
            return;
        }

        list.clear();
        list.addAll(solutions);
    }

}
