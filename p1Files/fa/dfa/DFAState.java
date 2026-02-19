package fa.dfa;

import java.util.HashMap;

import fa.State;

public class DFAState extends fa.State {
    private HashMap<String, Character> transitions;
    
    public DFAState(String name) {
        super(name);
        this.transitions = new HashMap<>();
    }
    /**
     * 
     * @param toState state to transition to
     * @param onSymb symbol to map this transition to
     * @return true if no mapping previously existed
     */
    public boolean addTransition(String toState, char onSymb) {
        /** In order to maintain determinism, we need to check to see
         *  if there are any existing mappings from this state to any other
         *  state given the same Σ. 
         */
        Character c = this.transitions.get(toState);

        if(c == onSymb) {
            return false;
        }
        this.transitions.put(toState, onSymb);
        return true;
    }
    
}
