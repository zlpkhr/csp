package fr.emse.ai.csp.nqueen;

import fr.emse.ai.csp.core.Assignment;
import fr.emse.ai.csp.core.Constraint;
import fr.emse.ai.csp.core.Variable;
import fr.emse.ai.csp.core.ChessBoardCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the constraint that no two queens can attack each other.
 * Queens attack if they are in the same row, column, or diagonal.
 */
public class QueensConstraint implements Constraint {
    private Variable var1;
    private Variable var2;
    private List<Variable> scope;

    public QueensConstraint(Variable var1, Variable var2) {
        this.var1 = var1;
        this.var2 = var2;
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

        ChessBoardCell pos1 = (ChessBoardCell) value1;
        ChessBoardCell pos2 = (ChessBoardCell) value2;

        // Check if queens are in same row, column or diagonal
        return !(pos1.sameLine(pos2.getL()) || 
                pos1.sameColumn(pos2.getC()) || 
                pos1.samediagonal(pos2.getL(), pos2.getC()));
    }
} 