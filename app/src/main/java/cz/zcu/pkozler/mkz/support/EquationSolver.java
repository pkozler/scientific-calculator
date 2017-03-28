package cz.zcu.pkozler.mkz.support;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.zcu.pkozler.mkz.R;
import cz.zcu.pkozler.mkz.core.Expression;
import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.core.ExpressionExceptionCode;
import cz.zcu.pkozler.mkz.core.tokens.types.OperatorType;
import cz.zcu.pkozler.mkz.core.tokens.types.OtherTokenType;

/**
 *
 * @author Petr Kozler
 */
public class EquationSolver {

    private static final double ACCURACY_COEFFICIENT = 10e18;
    protected HashMap<ExpressionExceptionCode, String> errorMessages;
    private Expression expression;
    private List<Double> list;
    private ArrayAdapter<Double> adapter;
    private TextView outputTextView;

    public EquationSolver(Expression expression, List<Double> list, ArrayAdapter<Double> adapter,
                          TextView outputTextView, HashMap<ExpressionExceptionCode, String> errorMessages) {
        this.expression = expression;
        this.list = list;
        this.adapter = adapter;
        this.outputTextView = outputTextView;
        this.errorMessages = errorMessages;
    }

    public void solve(String leftSideStr, String rightSideStr,
            double lowerBoundary, double upperBoundary, int stepCount) {
        double minX = Math.min(lowerBoundary, upperBoundary);
        double maxX = Math.max(lowerBoundary, upperBoundary);
        double xStep = (maxX - minX) / stepCount;

        String funcStr = arrangeFunction(leftSideStr, rightSideStr);

        try {
            int answer;
            List<Double> solutions = findSolutions(funcStr, minX, maxX, xStep);
            list.clear();

            if (solutions.isEmpty()) {
                answer = R.string.solve_output_no_solution;
            }
            else if (hasInfiniteManySolutions(solutions)) {
                answer = R.string.solve_output_inf_solutions;
            }
            else {
                answer = R.string.solve_output;
                addSolutions(solutions);
            }

            adapter.notifyDataSetChanged();
            outputTextView.setText(answer);
        }
        catch (ExpressionException e) {
            outputTextView.setText(errorMessages.get(e.CODE));
        }
    }

    private void addSolutions(List<Double> solutions) {
        for (Double solution : solutions) {
            if (solution != null) {
                list.add(solution);
            }
        }
    }

    private boolean hasInfiniteManySolutions(List<Double> solutions) {
        for (Double solution : solutions) {
            if (solution != null) {
                return false;
            }
        }

        return true;
    }

    private String arrangeFunction(String leftSideStr, String rightSideStr) {
        StringBuilder sb = new StringBuilder();

        sb.append(OtherTokenType.LEFT_PARENTHESIS.KEYWORD)
                .append(leftSideStr).append(OtherTokenType.RIGHT_PARENTHESIS.KEYWORD)
                .append(OperatorType.SUB.KEYWORD).append(OtherTokenType.LEFT_PARENTHESIS.KEYWORD)
                .append(rightSideStr).append(OtherTokenType.RIGHT_PARENTHESIS.KEYWORD);

        return sb.toString();
    }

    private List<Double> findSolutions(String funcStr,
                                       double minX, double maxX, double xStep) throws ExpressionException {
        expression.parse(funcStr);
        List<Double> solutions = new ArrayList<>();
        double epsilon = xStep / ACCURACY_COEFFICIENT;

        for (double x = minX; x < maxX; x += xStep) {
            findSolutionInInterval(solutions, x, x + xStep, epsilon);
        }

        return solutions;
    }

    private void findSolutionInInterval(List<Double> solutions, double a, double b,
            double epsilon) throws ExpressionException {
        double fA = expression.evaluate(a);
        double fB = expression.evaluate(b);

        // po obou stranách intervalu je funkční hodnota přibližně 0 - pravděpodobně nekonečný počet řešení
        if (Math.abs(fA) < epsilon && Math.abs(fB) < epsilon) {
            solutions.add(null);

            return;
        }

        // součin funkčních hodnot po stranách intervalu je kladný - interval neobsahuje žádné řešení
        if (fA * fB >= 0) {
            return;
        }

        // interval obsahuje jedno řešení - hledání pomocí metody bisekce
        while (Math.abs(a - b) > epsilon) {
            double c = (a + b) / 2;
            double fC = expression.evaluate(c);
            
            if (fC * fA == 0 || fC * fB == 0) {
                solutions.add(c);

                return;
            }
            else if (fC * fA > 0) {
                a = c;
            }
            else {
                b = c;
            }
            
            fA = expression.evaluate(a);
            fB = expression.evaluate(b);
        }

        solutions.add((a + b) / 2);
    }

}
