package fa.dfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

/**
 * @author Michael Murphy
 * DFA represents a deterministic finite automaton. 
 * It uses a LinkedHashSet to store states and sigmas, 
 * and a simple reference to store the start state.
 */
public class DFA implements DFAInterface {
    private LinkedHashSet<DFAState> states;
    private LinkedHashSet<Character> sigmas;
    private DFAState startState;
    private LinkedHashSet<DFAState> finalStates;

    public DFA() {
        this.states = new LinkedHashSet<>();
        this.sigmas = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (getState(name) == null) {
            this.states.add(new DFAState(name));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setFinal(String name) {
        DFAState state = getState(name);

        if (state == null) {
            return false;
        } else {
            this.finalStates.add(state);
            return true;
        }
    }

    @Override
    public boolean setStart(String name) {
        DFAState state = getState(name);

        if (state != null) {
            this.startState = state;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addSigma(char symbol) {
        this.sigmas.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        char[] chars = s.toCharArray();
        DFAState currState = this.startState;

        for (int i = 0; i < chars.length; i++) {
            currState = getState(currState.getTransition(chars[i]));
            if (currState == null) {
                return false;
            }
        }
        if (!this.finalStates.contains(currState)) {
            return false;
        }
        return true;
    }

    @Override
    public Set<Character> getSigma() {
        return sigmas;
    }

    @Override
    public DFAState getState(String name) {
        for (DFAState state : this.states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return this.finalStates.contains(getState(name));
    }

    @Override
    public boolean isStart(String name) {
        return this.startState.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        DFAState fState = getState(fromState);
        DFAState tState = getState(toState);

        if (fState == null || tState == null) {
            return false;
        }
        if (!this.sigmas.contains(onSymb)) {
            return false;
        }
        return fState.addTransition(toState, onSymb);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Q = {").append(formattedSet(this.states.toString())).append("}\n")
                .append("Sigma = {").append(formattedSet(this.sigmas.toString())).append("}\n")
                .append(generateDelta())
                .append("q0 = ").append(this.startState.getName()).append("\n")
                .append("F = {").append(formattedSet(this.finalStates.toString())).append("}\n");
        return sb.toString();
    }

    private String formattedSet(String str) {
        return str.replace("[", "")
                .replace(",", "")
                .replace("]", "");
    }

    private String generateDelta() {
        StringBuilder sb = new StringBuilder();
        sb.append("delta =\n\t");
        Character[] sigmasArray = this.sigmas.toArray(new Character[0]);

        // Write out all sigmas
        for (int i = 0; i < this.sigmas.size(); i++) {
            sb.append(sigmasArray[i]);
            if (i - 1 == this.sigmas.size()) {
                sb.append("\n");
            } else {
                sb.append("\t");
            }
        }
        sb.append("\n");

        // For each state, print the name, and then all transitions the state has
        for (DFAState state : this.states) {
            sb.append(state.getName()).append("\t");

            // Print all of this state's transitions
            for (int i = 0; i < this.sigmas.size(); i++) {
                String nextState = state.getTransition(sigmasArray[i]);
                sb.append(nextState == null ? " " : nextState);
                if(i-1 == this.sigmas.size()) {
                    sb.append("\n");
                } else {
                    sb.append("\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        return null;
    }
}
