package cz.zcu.pkozler.mkz.handlers;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.pkozler.mkz.core.Expression;
import cz.zcu.pkozler.mkz.core.ExpressionException;
import cz.zcu.pkozler.mkz.core.tokens.OperatorType;
import cz.zcu.pkozler.mkz.core.tokens.OtherTokenType;

/**
 *
 * @author Petr Kozler
 */
public class EquationSolver {

    private Expression expression;
    private ArrayAdapter<Double> adapter;

    public EquationSolver(Expression expression, ArrayAdapter<Double> adapter) {
        this.expression = expression;
        this.adapter = adapter;
    }

    public List<Double> solve(String leftSideStr, String rightSideStr,
            double lowerBoundary, double upperBoundary, int stepCount)
            throws ExpressionException {
        double minX = Math.min(lowerBoundary, upperBoundary);
        double maxX = Math.max(lowerBoundary, upperBoundary);
        double xStep = (maxX - minX) / stepCount;

        String funcStr = arrangeFunction(leftSideStr, rightSideStr);
        List<Double> solutions = findSolutions(funcStr, minX, maxX, xStep);

        return solutions;
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
        List<Double> solutions = new ArrayList<>();
        double epsilon = xStep / 1000;

        for (double x = minX; x < maxX; x += xStep) {
            Double solution = findSolutionInInterval(funcStr, x, x + xStep, epsilon);

            if (solution != null) {
                solutions.add(solution);
            }
        }

        return solutions;
    }

    private Double findSolutionInInterval(String funcStr, double a, double b,
            double epsilon) throws ExpressionException {
        double fA = expression.eval(funcStr, a);
        double fB = expression.eval(funcStr, b);
        
        if (fA * fB >= 0) {
            return null;
        }
        
        while (Math.abs(a - b) > epsilon) {
            double c = (a + b) / 2;
            double fC = expression.eval(funcStr, c);
            
            if (fC * fA == 0 || fC * fB == 0) {
                return c;
            }
            else if (fC * fA > 0) {
                a = c;
            }
            else {
                b = c;
            }
            
            fA = expression.eval(funcStr, a);
            fB = expression.eval(funcStr, b);
        }
        
        return (a + b) / 2;
    }

}
