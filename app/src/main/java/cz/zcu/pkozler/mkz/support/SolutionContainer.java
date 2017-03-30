package cz.zcu.pkozler.mkz.support;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Petr Kozler
 */
public class SolutionContainer {

    private List<Double> solutions = new ArrayList<>();
    private boolean infiniteManySolutions = false;

    public void addSolution(Double value) {
        if (value == null) {
            return;
        }

        solutions.add(value);
    }

    public void setInfiniteManySolutions() {
        infiniteManySolutions = true;
    }

    public boolean hasNoSolution() {
        return !infiniteManySolutions && solutions.isEmpty();
    }

    public boolean hasInfiniteManySolutions() {
        return solutions.isEmpty() && infiniteManySolutions;
    }

    public void getSolutions(List<Double> list) {
        if (list == null) {
            return;
        }

        list.clear();
        list.addAll(solutions);
    }

}
