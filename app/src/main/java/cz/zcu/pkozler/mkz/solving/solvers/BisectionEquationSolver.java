package cz.zcu.pkozler.mkz.solving.solvers;

import cz.zcu.pkozler.mkz.core.Evaluator;
import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;
import cz.zcu.pkozler.mkz.solving.EquationSolutionContainer;

/**
 * Třída představující komponentu pro hledání řešení zadané nelineární rovnice
 * pomocí metody bisekce (půlení intervalu).
 */
public class BisectionEquationSolver extends AEquationSolver {

    /**
     * koeficient pro určení povolené odchylky od přesného řešení na základě délky podintervalů
     */
    private static final double ACCURACY_COEFFICIENT = 10e18;

    /**
     * Vytvoří nový objekt pro hledání řešení rovnice metodou půlení intervalu.
     *
     * @param evaluator objekt pro zpracovávání a vyhodnocování zadaných výrazů
     */
    public BisectionEquationSolver(Evaluator evaluator) {
        super(evaluator);
    }

    public EquationSolutionContainer solve(String leftSideStr, String rightSideStr,
                                           double lowerBoundary, double upperBoundary, int stepCount) throws EvaluatorException {
        // ošetření mezí a určení délky jednoho podintervalu z počtu kroků a celkové délky
        double minX = Math.min(lowerBoundary, upperBoundary);
        double maxX = Math.max(lowerBoundary, upperBoundary);
        double xStep = (maxX - minX) / stepCount;

        // předzpracování rovnice a hledání řešení
        String funcStr = preprocessEquation(leftSideStr, rightSideStr);
        EquationSolutionContainer equationSolutionContainer = new EquationSolutionContainer();
        findSolutions(equationSolutionContainer, funcStr, minX, maxX, xStep);

        return equationSolutionContainer;
    }

    /**
     * Převede rovnici v obecném tvaru "L(x)=R(x)" do tvaru určeného ke zpracování metodou bisekce "L(x)-R(x)=0".
     *
     * @param leftSideStr řetězcová reprezentace levé strany rovnice
     * @param rightSideStr řetězcová reprezentace pravé strany rovnice
     * @return řetězcová reprezentace odečtení pravé strany od levé (na pravé straně je 0)
     */
    private String preprocessEquation(String leftSideStr, String rightSideStr) {
        StringBuilder sb = new StringBuilder();

        // sestavení textové reprezentace odečtení pravé strany od levé, která je určena k pozdějšímu vyhodnocování
        sb.append(OtherTokenType.LEFT_PARENTHESIS.toString())
                .append(leftSideStr).append(OtherTokenType.RIGHT_PARENTHESIS.toString())
                .append(OperatorTokenType.SUB.toString()).append(OtherTokenType.LEFT_PARENTHESIS.toString())
                .append(rightSideStr).append(OtherTokenType.RIGHT_PARENTHESIS.toString());

        return sb.toString();
    }

    /**
     * Provede průchod celkovým intervalem hledání řešení po krocích o délce určené z jejich zadaného
     * počtu a rozdílu zadaných mezí celkového intervalu, a pro každý z takto vzniklých podintervalů
     * spustí hledání řešení metodou bisekce. Seznam nalezených řešení ze všech podintervalů
     * jsou ukládána do přepravky.
     *
     * @param equationSolutionContainer přepravka pro ukládání nalezených řešení rovnice
     * @param funcStr řetězcová reprezentace odečtení pravé strany rovnice od levé
     * @param minX dolní mez celkového intervalu hledání řešení
     * @param maxX horní mez celkového intervalu hledání řešení
     * @param xStep délka jednoho podintervalu
     * @throws EvaluatorException
     */
    private void findSolutions(EquationSolutionContainer equationSolutionContainer, String funcStr,
                                                    double minX, double maxX, double xStep) throws EvaluatorException {
        evaluator.parse(funcStr);

        // určení povolené odchylky od přesného řešení podle délky podintervalů (v menších je vyžadována větší přesnost)
        double epsilon = xStep / ACCURACY_COEFFICIENT;

        for (double x = minX; x < maxX; x += xStep) {
            // hledání řešení pro aktuální vypočtený podinterval
            findSolutionInInterval(equationSolutionContainer, x, x + xStep, epsilon);
        }
    }

    /**
     * Nalezne řešení rovnice (nebo jeho odhad nepřesahující povolenou odchylku) v aktuálním podintervalu
     * o zadaných mezích pomocí metody bisekce a přidá toto řešení do přepravky pro uchovávání výsledků.
     * Nemají-li funkční hodnoty v hraničních bodech podintervalu rozdílná znaménka, tento podinterval
     * nemůže obsahovat žádné řešení a algoritmus bisekce není spuštěn.
     * Jsou-li funkční hodnoty obou hraničních bodů velmi blízké nule (menší, než stanovená odchylka),
     * je považováno za pravděpodobné, že rovnice má nekonečný počet řešení. V takovém případě je
     * přepravce pro uchování výsledků nastaven odpovídající příznak a algoritmus bisekce není spuštěn.
     *
     * @param equationSolutionContainer přepravka pro ukládání nalezených řešení rovnice
     * @param a dolní mez podintervalu v aktuálním kroku hledání řešení
     * @param b horní mez podintervalu v aktuálním kroku hledání řešení
     * @param epsilon povolená odchylka od přesného řešení určená podle délky podintervalu
     * @throws EvaluatorException
     */
    private void findSolutionInInterval(EquationSolutionContainer equationSolutionContainer, double a, double b,
                                        double epsilon) throws EvaluatorException {
        double fA = evaluator.evaluate(a);
        double fB = evaluator.evaluate(b);

        if (Math.abs(fA) < epsilon && Math.abs(fB) < epsilon) {
            // po obou stranách intervalu je funkční hodnota přibližně 0 - pravděpodobně nekonečný počet řešení
            equationSolutionContainer.setInfiniteManySolutions();

            return;
        }

        if (fA * fB >= 0) {
            // součin funkčních hodnot po stranách intervalu je kladný - interval neobsahuje žádné řešení
            return;
        }

        // interval obsahuje jedno řešení - hledání hodnoty půlením intervalu
        while (Math.abs(a - b) > epsilon) {
            double c = (a + b) / 2;
            double fC = evaluator.evaluate(c);

            if (fC * fA == 0 || fC * fB == 0) {
                // funkční hodnota je rovna 0 - bylo nalezeno přesné řešení
                equationSolutionContainer.addSolution(c);

                return;
            }
            else if (fC * fA > 0) {
                a = c;
            }
            else {
                b = c;
            }

            fA = evaluator.evaluate(a);
            fB = evaluator.evaluate(b);
        }

        // bylo dosaženo povolené odchylky - bude vypočítán odhad řešení
        equationSolutionContainer.addSolution((a + b) / 2);
    }

}
