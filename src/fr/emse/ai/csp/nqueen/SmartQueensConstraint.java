package fr.emse.ai.csp.nqueen;

import fr.emse.ai.csp.core.Assignment;
import fr.emse.ai.csp.core.Constraint;
import fr.emse.ai.csp.core.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * A smarter constraint for N-Queens that:
 * - Doesn't need to check columns (automatically satisfied by model)
 * - Only checks rows and diagonals
 * - Uses integer positions instead of ChessBoardCell objects
 */
public class SmartQueensConstraint implements Constraint {
    private Variable var1;
    private Variable var2;
    private int col1;  // Column of first queen
    private int col2;  // Column of second queen
    private List<Variable> scope;

    public SmartQueensConstraint(Variable var1, Variable var2, int col1, int col2) {
        this.var1 = var1;
        this.var2 = var2;
        this.col1 = col1;
        this.col2 = col2;
        scope = new ArrayList<Variable>(2);
        scope.add(var1);
        scope.add(var2);
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment assignment) {
        Object value1 = assignment.getAssignment(var1);
        Object value2 = assignment.getAssignment(var2);
        
        if (value1 == null || value2 == null) {
            return true; // not yet assigned
        }

        int row1 = (Integer) value1;
        int row2 = (Integer) value2;

        // Check same row
        if (row1 == row2) {
            return false;
        }

        // Check diagonal
        // Two queens are on the same diagonal if the difference in rows
        // equals the difference in columns
        return Math.abs(row1 - row2) != Math.abs(col1 - col2);
    }
} 