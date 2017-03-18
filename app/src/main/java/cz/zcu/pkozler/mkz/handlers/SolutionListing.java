package cz.zcu.pkozler.mkz.handlers;
/*
import core.AExpressionException;
import core.Expression;
import core.tokens.OperatorType;
import core.tokens.OtherTokenType;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;*/

/**
 *
 * @author Petr Kozler
 */
public class SolutionListing {

    /*private Expression expression;
    private ListView<Double> listView;

    public SolutionListing(Expression expression, ListView<Double> listView) {
        this.expression = expression;
        this.listView = listView;
    }

    public void solve(String leftSideStr, String rightSideStr,
            double lowerBoundary, double upperBoundary, int stepCount)
            throws AExpressionException {
        double minX = Math.min(lowerBoundary, upperBoundary);
        double maxX = Math.max(lowerBoundary, upperBoundary);
        double xStep = (maxX - minX) / stepCount;

        String funcStr = arrangeFunction(leftSideStr, rightSideStr);
        List<Double> solutions = findSolutions(funcStr, minX, maxX, xStep);
        listView.setItems(FXCollections.observableArrayList(solutions));
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
            double minX, double maxX, double xStep) throws AExpressionException {
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
            double epsilon) throws AExpressionException {
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
    }*/

}
