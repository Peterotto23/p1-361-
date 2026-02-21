package fa.dfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

/**
 * Deterministic Finite Automaton (DFA) implementation.
 * 
 * @author Michael Murphy, Peter Krahn
 *         Stores states, alphabet (Sigma), transitions (via DFAState), start
 *         state,
 *         and final states. Uses insertion-ordered sets so output (toString)
 *         preserves
 *         the creation order of states and symbols.
 */
public class DFA implements DFAInterface {
    private LinkedHashSet<DFAState> states;
    private LinkedHashSet<Character> sigmas;
    private DFAState startState;
    private LinkedHashSet<DFAState> finalStates;

    /**
     * Construct an empty DFA.
     */
    public DFA() {
        this.states = new LinkedHashSet<>();
        this.sigmas = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
    }

    /**
     * Add a state with the given name.
     *
     * @param name the state label
     * @return true if the state was added, false if a state with that name exists
     */
    @Override
    public boolean addState(String name) {
        if (getState(name) == null) {
            this.states.add(new DFAState(name));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mark an existing state as final (accepting).
     *
     * @param name the state label
     * @return true if the state exists and was marked final, false otherwise
     */
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

    /**
     * Set the start state of the DFA.
     *
     * @param name the state label to use as start
     * @return true if the state exists and was set as start, false otherwise
     */
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

    /**
     * Adds a symbol to the alphabet (Sigma).
     *
     * @param symbol the character to add to Sigma
     */
    @Override
    public void addSigma(char symbol) {
        this.sigmas.add(symbol);
    }

    /**
     * Simulate the DFA on the given input.
     *
     * The test-suite uses the string "e" to denote the empty string (epsilon).
     * For other inputs, each character must be in Sigma and a transition must
     * exist at each step; otherwise the input is rejected.
     *
     * @param input the input string (or "e" for empty)
     * @return true if the DFA accepts the input, false otherwise
     */
    @Override
    public boolean accepts(String input) {
        if (input == null)
            return false;
        if (input.equals("e")) {
            return (this.startState != null && this.finalStates.contains(this.startState));
        }

        if (this.startState == null)
            return false;

        DFAState currentState = this.startState;
        for (int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            if (!this.sigmas.contains(symbol))
                return false;

            String nextStateName = currentState.getTransition(symbol);
            if (nextStateName == null)
                return false;

            DFAState nextState = getState(nextStateName);
            if (nextState == null)
                return false;
            currentState = nextState;
        }

        return this.finalStates.contains(currentState);
    }

    /**
     * Return the alphabet Sigma.
     *
     * @return the set of alphabet symbols
     */
    @Override
    public Set<Character> getSigma() {
        return sigmas;
    }

    /**
     * Look up a state by name.
     *
     * @param name the state label
     * @return the DFAState or null if not found
     */

    @Override
    public DFAState getState(String name) {
        for (DFAState state : this.states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    /**
     * Check whether a named state is final.
     *
     * @param name the state label
     * @return true if the state exists and is final
     */
    @Override
    public boolean isFinal(String name) {
        return this.finalStates.contains(getState(name));
    }

    /**
     * Check whether a named state is the start state.
     *
     * @param name the state label
     * @return true if the state exists and is the start
     */
    @Override
    public boolean isStart(String name) {
        if (this.startState == null)
            return false;
        return this.startState.getName().equals(name);
    }

    /**
     * Add a transition: fromState --onSymb--> toState.
     *
     * The method validates that both states exist and that the symbol is in Sigma.
     *
     * @param fromState label of the source state
     * @param toState   label of the target state
     * @param onSymb    the symbol triggering the transition
     * @return true if the transition was added, false otherwise
     */
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

    /**
     * Produce a readable textual representation of the DFA.
     *
     * The output includes Q, Sigma, the transition table (delta), q0, and F.
     *
     * @return the textual DFA representation
     */
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

        for (int i = 0; i < sigmasArray.length; i++) {
            sb.append(sigmasArray[i]);
            if (i < sigmasArray.length - 1)
                sb.append("\t");
        }
        sb.append("\n");

        for (DFAState state : this.states) {
            sb.append(state.getName()).append("\t");
            for (int i = 0; i < sigmasArray.length; i++) {
                Character sym = sigmasArray[i];
                String nextState = state.getTransition(sym);
                sb.append(nextState == null ? " " : nextState);
                if (i < sigmasArray.length - 1)
                    sb.append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Create a deep copy of this DFA with two alphabet symbols swapped.
     *
     * The returned DFA contains new state objects but the same state names,
     * start state, and final-state membership; every transition labelled
     * symb1 is relabelled symb2 and vice versa.
     *
     * @param symb1 first symbol to swap
     * @param symb2 second symbol to swap
     * @return a new DFA with the two symbols swapped
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        DFA copy = new DFA();

        for (Character sigma : this.sigmas) {
            copy.addSigma(sigma);
        }

        for (DFAState orginalState : this.states) {
            copy.addState(orginalState.getName());
        }

        if (this.startState != null) {
            copy.setStart(this.startState.getName());
        }

        for (DFAState finalState : this.finalStates) {
            copy.setFinal(finalState.getName());
        }

        Character[] sigmasArray = this.sigmas.toArray(new Character[0]);
        for (DFAState orginalState : this.states) {
            String fromName = orginalState.getName();
            for (Character sigma : sigmasArray) {
                String toName = orginalState.getTransition(sigma);
                if (toName == null)
                    continue;
                char used = sigma;
                if (sigma == symb1)
                    used = symb2;
                else if (sigma == symb2)
                    used = symb1;
                copy.addTransition(fromName, toName, used);
            }
        }

        return copy;
    }
}
