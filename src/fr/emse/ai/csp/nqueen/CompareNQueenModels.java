package fr.emse.ai.csp.nqueen;

import fr.emse.ai.csp.core.Assignment;
import fr.emse.ai.csp.core.BacktrackingStrategy;
import fr.emse.ai.csp.core.MinConflictsStrategy;
import fr.emse.ai.csp.core.Variable;
import fr.emse.ai.csp.core.ChessBoardCell;

/**
 * Compares the performance of the natural and smart N-Queens models
 * using both backtracking and min-conflicts strategies.
 */
public class CompareNQueenModels {
    
    private static void printNaturalSolution(Assignment solution, int n) {
        if (solution == null) {
            System.out.println("No solution found!");
            return;
        }

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

    private static void printSmartSolution(Assignment solution, int n) {
        if (solution == null) {
            System.out.println("No solution found!");
            return;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int row = (Integer) solution.getAssignment(new Variable("Q" + (j + 1)));
                System.out.print(row == i ? " Q " : " . ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Test for increasing board sizes
        for (int n = 4; n <= 12; n++) {  // Testing up to 12 to show the difference more clearly
            System.out.println("\nSolving " + n + "-Queens Problem");
            System.out.println("============================");
            
            // Create both types of CSPs
            NaturalNQueenCSP naturalCSP = new NaturalNQueenCSP(n);
            SmartNQueenCSP smartCSP = new SmartNQueenCSP(n);
            
            // Test with Backtracking
            System.out.println("\nUsing Backtracking Strategy:");
            System.out.println("-------------------------");
            
            // Natural model
            BacktrackingStrategy bts = new BacktrackingStrategy();
            long startTime = System.currentTimeMillis();
            Assignment solution = bts.solve(naturalCSP);
            long endTime = System.currentTimeMillis();
            System.out.println("Natural Model Time: " + (endTime - startTime) + " ms");
            
            // Smart model
            bts = new BacktrackingStrategy();
            startTime = System.currentTimeMillis();
            solution = bts.solve(smartCSP);
            endTime = System.currentTimeMillis();
            System.out.println("Smart Model Time: " + (endTime - startTime) + " ms");
            
            // Test with Min-Conflicts
            System.out.println("\nUsing Min-Conflicts Strategy:");
            System.out.println("---------------------------");
            
            // Natural model
            MinConflictsStrategy mcs = new MinConflictsStrategy(n * 100);
            startTime = System.currentTimeMillis();
            solution = mcs.solve(naturalCSP);
            endTime = System.currentTimeMillis();
            System.out.println("Natural Model Time: " + (endTime - startTime) + " ms");
            
            // Smart model
            mcs = new MinConflictsStrategy(n * 100);
            startTime = System.currentTimeMillis();
            solution = mcs.solve(smartCSP);
            endTime = System.currentTimeMillis();
            System.out.println("Smart Model Time: " + (endTime - startTime) + " ms");
        }
    }
} 