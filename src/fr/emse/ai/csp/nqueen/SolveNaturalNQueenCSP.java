package fr.emse.ai.csp.nqueen;

import fr.emse.ai.csp.core.Assignment;
import fr.emse.ai.csp.core.BacktrackingStrategy;
import fr.emse.ai.csp.core.MinConflictsStrategy;
import fr.emse.ai.csp.core.ChessBoardCell;
import fr.emse.ai.csp.core.Variable;

/**
 * Solves the N-Queens problem using both backtracking and min-conflicts approaches
 * and compares their performance.
 */
public class SolveNaturalNQueenCSP {
    
    private static void printSolution(Assignment solution, int n) {
        if (solution == null) {
            System.out.println("No solution found!");
            return;
        }

        // Print the board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolean queenFound = false;
                for (int q = 0; q < n; q++) {
                    ChessBoardCell pos = (ChessBoardCell) solution.getAssignment(new Variable("Q" + (q + 1)));
                    if (pos.getL() == i && pos.getC() == j) {
                        System.out.print(" Q ");
                        queenFound = true;
                        break;
                    }
                }
                if (!queenFound) {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Test for increasing board sizes from 4 to 10
        for (int n = 4; n <= 10; n++) {
            System.out.println("\nSolving " + n + "-Queens Problem");
            System.out.println("======================");
            
            NaturalNQueenCSP csp = new NaturalNQueenCSP(n);
            
            // Solve using Backtracking
            System.out.println("\nUsing Backtracking Strategy:");
            BacktrackingStrategy bts = new BacktrackingStrategy();
            long startTime = System.currentTimeMillis();
            Assignment solution = bts.solve(csp);
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            printSolution(solution, n);
            
            // Solve using Min-Conflicts (with different max steps for larger boards)
            System.out.println("\nUsing Min-Conflicts Strategy:");
            MinConflictsStrategy mcs = new MinConflictsStrategy(n * 100);
            startTime = System.currentTimeMillis();
            solution = mcs.solve(csp);
            endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            printSolution(solution, n);
        }
    }
} 