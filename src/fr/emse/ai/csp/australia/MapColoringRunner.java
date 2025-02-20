package fr.emse.ai.csp.australia;

import fr.emse.ai.csp.core.Assignment;
import fr.emse.ai.csp.core.BacktrackingStrategy;
import fr.emse.ai.csp.core.CSP;
import fr.emse.ai.csp.core.CSPStateListener;

public class MapColoringRunner {
    public static void main(String[] args) {
        // Create the CSP
        MapCSP map = new MapCSP();
        
        // Create the solving strategy
        BacktrackingStrategy bts = new BacktrackingStrategy();
        
        // Add a listener to track progress
        bts.addCSPStateListener(new CSPStateListener() {
            @Override
            public void stateChanged(Assignment assignment, CSP csp) {
                System.out.println("Assignment evolved : " + assignment);
            }
            
            @Override
            public void stateChanged(CSP csp) {
                System.out.println("CSP evolved : " + csp);
            }
        });
        
        // Solve and measure time
        System.out.println("Solving Australia Map Coloring Problem...\n");
        double start = System.currentTimeMillis();
        Assignment solution = bts.solve(map);
        double end = System.currentTimeMillis();
        
        // Print results
        System.out.println("\nSolution found:");
        System.out.println(solution);
        System.out.println("\nTime to solve = " + (end - start) + " ms");
    }
} 