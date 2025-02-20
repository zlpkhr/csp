package fr.emse.ai.csp.nqueen;

import fr.emse.ai.csp.core.CSP;
import fr.emse.ai.csp.core.Domain;
import fr.emse.ai.csp.core.Variable;
import fr.emse.ai.csp.core.ChessBoardCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the N-Queens problem using a natural approach where each queen
 * can be placed in any cell of the board.
 */
public class NaturalNQueenCSP extends CSP {
    private int n;  // board size and number of queens

    public NaturalNQueenCSP(int n) {
        this.n = n;
        constructQueens();
    }

    private void constructQueens() {
        // Create variables (one for each queen)
        List<Variable> queens = new ArrayList<Variable>();
        for (int i = 0; i < n; i++) {
            queens.add(new Variable("Q" + (i + 1)));
        }

        // Create domain (all possible board positions)
        List<ChessBoardCell> positions = new ArrayList<ChessBoardCell>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                positions.add(new ChessBoardCell(i, j));
            }
        }
        Domain domain = new Domain(positions);

        // Add variables and set their domains
        for (Variable queen : queens) {
            addVariable(queen);
            setDomain(queen, domain);
        }

        // Add constraints between all pairs of queens
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                addConstraint(new QueensConstraint(queens.get(i), queens.get(j)));
            }
        }
    }

    public int getSize() {
        return n;
    }
} 