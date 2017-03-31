package cz.zcu.pkozler.mkz.solving.solvers;

import cz.zcu.pkozler.mkz.core.Evaluator;
import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.solving.EquationSolutionContainer;

/**
 * Abstraktní třída představující komponentu pro hledání řešení zadané nelineární rovnice
 * nespecifikovanou metodou.
 */
public abstract class AEquationSolver {

    /**
     * objekt pro zpracovávání a vyhodnocování zadaných výrazů
     */
    protected Evaluator evaluator;

    /**
     * Vytvoří nový objekt pro hledání řešení rovnice.
     *
     * @param evaluator objekt pro zpracovávání a vyhodnocování zadaných výrazů
     */
    public AEquationSolver(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Spustí hledání řešení nelineární rovnice se zadanými textovými reprezentacemi
     * jednotlivých stran v rámci zadaného intervalu o zadaných mezích a se zadaným počtem kroků,
     * tj. podintervalů shodné délky vytvořených uvnitř celkového intervalu, přičemž pro každý
     * z nich má být algoritmus hledání řešení spuštěn zvlášť, aby bylo možné nalézt všechna řešení
     * rovnic, které mají více než jedno konkrétní řešení.
     *
     * @param leftSideStr textová reprezentace výrazu levé strany rovnice
     * @param rightSideStr textová reprezentace výrazu pravé strany rovnice
     * @param lowerBoundary dolní mez celkového intervalu pro hledání řešení
     * @param upperBoundary horní mez celkového intervalu pro hledání řešení
     * @param stepCount počet kroků hledání řešení
     * @return přepravka řešení rovnice
     * @throws EvaluatorException
     */
    public abstract EquationSolutionContainer solve(String leftSideStr, String rightSideStr,
                                           double lowerBoundary, double upperBoundary, int stepCount) throws EvaluatorException;

}
