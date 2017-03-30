package cz.zcu.pkozler.mkz.support;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cz.zcu.pkozler.mkz.R;
import cz.zcu.pkozler.mkz.core.Evaluator;
import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.core.EvaluatorExceptionCode;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorTokenType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;

/**
 *
 * @author Petr Kozler
 */
public class EquationSolver {

    private static final double ACCURACY_COEFFICIENT = 10e18;
    protected HashMap<EvaluatorExceptionCode, String> errorMessages;
    private Evaluator evaluator;
    private List<Double> list;
    private ArrayAdapter<Double> adapter;
    private TextView outputTextView;

    public EquationSolver(Evaluator evaluator, List<Double> list, ArrayAdapter<Double> adapter,
                          TextView outputTextView, HashMap<EvaluatorExceptionCode, String> errorMessages) {
        this.evaluator = evaluator;
        this.list = list;
        this.adapter = adapter;
        this.outputTextView = outputTextView;
        this.errorMessages = errorMessages;
    }

    public void solve(String leftSideStr, String rightSideStr,
            double lowerBoundary, double upperBoundary, int stepCount) {
        // ošetření mezí a určení délky jednoho podintervalu z počtu kroků a celkové délky
        double minX = Math.min(lowerBoundary, upperBoundary);
        double maxX = Math.max(lowerBoundary, upperBoundary);
        double xStep = (maxX - minX) / stepCount;

        String funcStr = arrangeFunction(leftSideStr, rightSideStr);

        try {
            int answer;
            SolutionContainer solutionContainer = findSolutions(funcStr, minX, maxX, xStep);

            if (solutionContainer.hasNoSolution()) {
                answer = R.string.solve_output_no_solution;
            }
            else if (solutionContainer.hasInfiniteManySolutions()) {
                answer = R.string.solve_output_inf_solutions;
            }
            else {
                answer = R.string.solve_output;
            }

            solutionContainer.getSolutions(list);
            adapter.notifyDataSetChanged();
            outputTextView.setText(answer);
        }
        catch (EvaluatorException e) {
            outputTextView.setText(errorMessages.get(e.CODE));
        }
    }

    private String arrangeFunction(String leftSideStr, String rightSideStr) {
        StringBuilder sb = new StringBuilder();

        sb.append(OtherTokenType.LEFT_PARENTHESIS.KEYWORD)
                .append(leftSideStr).append(OtherTokenType.RIGHT_PARENTHESIS.KEYWORD)
                .append(OperatorTokenType.SUB.KEYWORD).append(OtherTokenType.LEFT_PARENTHESIS.KEYWORD)
                .append(rightSideStr).append(OtherTokenType.RIGHT_PARENTHESIS.KEYWORD);

        return sb.toString();
    }

    private SolutionContainer findSolutions(String funcStr,
                                            double minX, double maxX, double xStep) throws EvaluatorException {
        evaluator.parse(funcStr);
        SolutionContainer solutionContainer = new SolutionContainer();

        // určení tolerance pro nalezenou hodnotu řešení podle délky jednoho podintervalu
        double epsilon = xStep / ACCURACY_COEFFICIENT;

        for (double x = minX; x < maxX; x += xStep) {
            // hledání řešení pro aktuální vypočtený podinterval
            findSolutionInInterval(solutionContainer, x, x + xStep, epsilon);
        }

        return solutionContainer;
    }

    private void findSolutionInInterval(SolutionContainer solutionContainer, double a, double b,
                                        double epsilon) throws EvaluatorException {
        double fA = evaluator.evaluate(a);
        double fB = evaluator.evaluate(b);

        // po obou stranách intervalu je funkční hodnota přibližně 0 - pravděpodobně nekonečný počet řešení
        if (Math.abs(fA) < epsilon && Math.abs(fB) < epsilon) {
            solutionContainer.setInfiniteManySolutions();

            return;
        }

        // součin funkčních hodnot po stranách intervalu je kladný - interval neobsahuje žádné řešení
        if (fA * fB >= 0) {
            return;
        }

        // interval obsahuje jedno řešení - hledání pomocí metody bisekce
        while (Math.abs(a - b) > epsilon) {
            double c = (a + b) / 2;
            double fC = evaluator.evaluate(c);
            
            if (fC * fA == 0 || fC * fB == 0) {
                solutionContainer.addSolution(c);

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

        solutionContainer.addSolution((a + b) / 2);
    }

}
