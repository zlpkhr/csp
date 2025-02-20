package fr.emse.ai.csp.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements Forward Checking strategy for CSP solving.
 * Forward checking keeps track of remaining legal values for unassigned variables
 * and terminates search when any variable has no legal values left.
 */
public class ForwardCheckingStrategy extends SolutionStrategy {
    
    @Override
    public Assignment solve(CSP csp) {
        return forwardCheckingSearch(csp, new Assignment());
    }
    
    /**
     * The main forward checking search algorithm.
     */
    private Assignment forwardCheckingSearch(CSP csp, Assignment assignment) {
        if (assignment.isComplete(csp.getVariables())) {
            return assignment;
        }
        
        Variable var = selectUnassignedVariable(assignment, csp);
        for (Object value : orderDomainValues(var, assignment, csp)) {
            assignment.setAssignment(var, value);
            fireStateChanged(assignment, csp);
            
            if (assignment.isConsistent(csp.getConstraints(var))) {
                // Forward check and get the domain restore info
                DomainRestoreInfo info = forwardCheck(var, assignment, csp);
                
                if (!info.isEmptyDomainFound()) {
                    if (!info.getSavedDomains().isEmpty()) {
                        fireStateChanged(csp);
                    }
                    Assignment result = forwardCheckingSearch(csp, assignment);
                    if (result != null) {
                        return result;
                    }
                }
                // Restore domains before trying next value
                info.restoreDomains(csp);
            }
            assignment.removeAssignment(var);
        }
        return null;
    }
    
    /**
     * Implements forward checking.
     * For each unassigned variable Y that is connected to variable var by a constraint,
     * delete from Y's domain any value that is inconsistent with the value chosen for var.
     */
    private DomainRestoreInfo forwardCheck(Variable var, Assignment assignment, CSP csp) {
        DomainRestoreInfo info = new DomainRestoreInfo();
        
        // Check constraints that include var
        for (Constraint constraint : csp.getConstraints(var)) {
            // Get the other variable in the constraint
            Variable neighbor = csp.getNeighbor(var, constraint);
            if (neighbor != null && !assignment.hasAssignmentFor(neighbor)) {
                // Forward check neighbor's domain
                Domain currentDomain = csp.getDomain(neighbor);
                List<Object> newValues = new ArrayList<>();
                
                // Test each value in the domain
                for (Object value : currentDomain) {
                    assignment.setAssignment(neighbor, value);
                    if (constraint.isSatisfiedWith(assignment)) {
                        newValues.add(value);
                    }
                    assignment.removeAssignment(neighbor);
                }
                
                // If domain changed, store the old domain and update with new one
                if (newValues.size() < currentDomain.size()) {
                    info.storeDomainFor(neighbor, currentDomain);
                    csp.setDomain(neighbor, new Domain(newValues));
                    
                    // If no values left, set empty domain flag
                    if (newValues.isEmpty()) {
                        info.setEmptyDomainFound(true);
                        return info;
                    }
                }
            }
        }
        return info;
    }
    
    /**
     * Select the next unassigned variable. 
     * This implementation uses the minimum remaining values (MRV) heuristic.
     */
    protected Variable selectUnassignedVariable(Assignment assignment, CSP csp) {
        Variable selected = null;
        int minSize = Integer.MAX_VALUE;
        
        for (Variable var : csp.getVariables()) {
            if (!assignment.hasAssignmentFor(var)) {
                Domain domain = csp.getDomain(var);
                if (domain.size() < minSize) {
                    selected = var;
                    minSize = domain.size();
                }
            }
        }
        return selected;
    }
    
    /**
     * Order domain values for the given variable.
     * This implementation returns values in their default order.
     */
    protected Iterable<?> orderDomainValues(Variable var, Assignment assignment, CSP csp) {
        return csp.getDomain(var);
    }
} 