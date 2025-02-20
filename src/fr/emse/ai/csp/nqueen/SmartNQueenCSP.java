package fr.emse.ai.csp.nqueen;

import fr.emse.ai.csp.core.CSP;
import fr.emse.ai.csp.core.Domain;
import fr.emse.ai.csp.core.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * A smarter implementation of the N-Queens problem where:
 * - Each queen is pre-assigned to a column
 * - We only need to determine the row position
 * - Domain size is reduced from N^2 to N
 */
public class SmartNQueenCSP extends CSP {
    private int n;  // board size and number of queens

    public SmartNQueenCSP(int n) {
        this.n = n;
        constructQueens();
    }

    private void constructQueens() {
        // Create variables (one for each queen/column)
        List<Variable> queens = new ArrayList<Variable>();
        for (int i = 0; i < n; i++) {
            queens.add(new Variable("Q" + (i + 1)));
        }

        // Create domain (possible row positions 0 to N-1)
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            positions.add(i);
        }
        Domain domain = new Domain(positions);

        // Add variables and set their domains
        for (Variable queen : queens) {
            addVariable(queen);
            setDomain(queen, domain);
        }

        // Add constraints between all pairs of queens
        // Note: Column constraint is automatically satisfied by our model
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                addConstraint(new SmartQueensConstraint(queens.get(i), queens.get(j), i, j));
            }
        }
    }

    public int getSize() {
        return n;
    }
} 