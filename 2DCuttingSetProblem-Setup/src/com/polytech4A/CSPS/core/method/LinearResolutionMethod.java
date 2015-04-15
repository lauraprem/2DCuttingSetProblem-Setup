package com.polytech4A.CSPS.core.method;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Solution;
import com.polytech4A.CSPS.core.resolution.util.context.Context;
import com.polytech4A.CSPS.core.util.Log;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import java.util.ArrayList;
import java.util.Arrays;

public class LinearResolutionMethod {

    /**
     * Function to minimize.
     */
    private LinearObjectiveFunction function;

    /**
     * List containing the constraints that the function is subject to.
     */
    private ArrayList<LinearConstraint> constraints;

    /**
     * Current context.
     */
    private Context context;

    public ArrayList<Long> getCount(Solution solution) {
        return minimize(solution);
    }

    public Long getFitness(Solution solution, Long costOfPattern,
                           Long costOfPrinting) {
        ArrayList<Long> count = getCount(solution);
        Long prints = count
                .parallelStream()
                .mapToLong(i -> i.longValue()).sum();
        Long fitness = prints * costOfPrinting + count.size() * costOfPattern;
        Log.log.trace(String.format("fitness = %s", fitness));
        return fitness;
    }


    /**
     * Create a LinearResolution Method with initial resolution to initialize function and constraints.
     *
     * @param context Current context.
     */
    public LinearResolutionMethod(Context context) {
        this.context = context;
    }

    /**
     * Resolve linear programming problem when minimizing the equation with current constraints. Returns
     *
     * @param solution Solution to minimize the objective the function from.
     * @return Number of printings and cost value.
     */
    private ArrayList<Long> minimize(Solution solution) {
        updateFunction(solution);
        updateConstraints(solution);
        try {
            RealPointValuePair result = new SimplexSolver().optimize(function, constraints, GoalType.MINIMIZE, true);
            double[] points = result.getPoint();
            ArrayList<Long> count = new ArrayList<>();
            for (int i = 0; i < points.length; i++) {
                count.add(Math.round(Math.ceil(points[i])));
            }
            return count;
        } catch (OptimizationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update current context's objective function using the solution.
     *
     * @param solution Solution used to update the function.
     */
    public void updateFunction(Solution solution) {
        double[] coefficients = new double[solution.getPatterns().size()];
        Arrays.fill(coefficients, context.getSheetCost());
        function = new LinearObjectiveFunction(coefficients, solution.getPatterns().size() * context.getPatternCost());
    }

    /**
     * Update current context's constraints using the solution.
     *
     * @param solution Solution used to update the constraints.
     */
    public void updateConstraints(Solution solution) {
        ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        double[] coefficients;
        int i;
        for (Image ctxImage : context.getImages()) {
            coefficients = new double[solution.getPatterns().size()];
            i = 0;
            for (Pattern p : solution.getPatterns()) {
                for (Image box : p.getListImg()) {
                    if (box.equals(ctxImage)) {
                        coefficients[i] = box.getAmount();
                        ++i;
                    }
                }
            }
            constraints.add(new LinearConstraint(coefficients, Relationship.GEQ, ctxImage.getGoal()));
        }
        this.constraints = constraints;
    }

    public static Boolean check(ArrayList<Long> count, Context context, Solution solution) {
        ArrayList<Long> countImage = new ArrayList<>();
        Long sum;
        for (Long i = 0L; i < context.getImages().size(); i++) {
            countImage.add(0L);
        }
        int index;
        Pattern pattern;
        for (int i = 0; i < solution.getPatterns().size(); i++) {
            pattern = solution.getPatterns().get(i);
            for (Image image : pattern.getListImg()) {
                index = image.getId().intValue();
                countImage.set(index, countImage.get(index) + count.get(i) * image.getAmount());
            }
        }
        Boolean check = Boolean.TRUE;
        for (int i = 0; i < context.getImages().size(); i++) {
            if (countImage.get(i) < context.getImages().get(i).getGoal()) {
                check = Boolean.FALSE;
                Log.log.warn(context.getImages().get(i).getId()
                        + " : "
                        + context.getImages().get(i).getGoal()
                        + " -> "
                        + countImage.get(i));
            }
        }
        return check;
    }
}
