package fr.emse.ai.csp.australia;

import fr.emse.ai.csp.core.Assignment;
import fr.emse.ai.csp.core.BacktrackingStrategy;
import fr.emse.ai.csp.core.ForwardCheckingStrategy;
import fr.emse.ai.csp.core.CSP;
import fr.emse.ai.csp.core.CSPStateListener;

public class MapColoringRunner {
    public static void main(String[] args) {
        // Create the CSP
        MapCSP map = new MapCSP();
        
        System.out.println("Testing Backtracking Strategy:");
        System.out.println("============================");
        
        // Create and test backtracking strategy
        BacktrackingStrategy bts = new BacktrackingStrategy();
        bts.addCSPStateListener(new CSPStateListener() {
            @Override
            public void stateChanged(Assignment assignment, CSP csp) {
                System.out.println("Backtracking Assignment: " + assignment);
            }
            
            @Override
            public void stateChanged(CSP csp) {
                System.out.println("Backtracking CSP evolved: " + csp);
            }
        });
        
        // Solve with backtracking and measure time
        long startTime = System.nanoTime();
        Assignment solution = bts.solve(map);
        long endTime = System.nanoTime();
        
        System.out.println("\nBacktracking Solution:");
        System.out.println(solution);
        System.out.printf("Backtracking Time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);
        
        System.out.println("\nTesting Forward Checking Strategy:");
        System.out.println("================================");
        
        // Create and test forward checking strategy
        ForwardCheckingStrategy fcs = new ForwardCheckingStrategy();
        fcs.addCSPStateListener(new CSPStateListener() {
            @Override
            public void stateChanged(Assignment assignment, CSP csp) {
                System.out.println("Forward Checking Assignment: " + assignment);
            }
            
            @Override
            public void stateChanged(CSP csp) {
                System.out.println("Forward Checking CSP evolved: " + csp);
            }
        });
        
        // Solve with forward checking and measure time
        startTime = System.nanoTime();
        solution = fcs.solve(map);
        endTime = System.nanoTime();
        
        System.out.println("\nForward Checking Solution:");
        System.out.println(solution);
        System.out.printf("Forward Checking Time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);
    }
} 