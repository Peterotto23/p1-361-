package fa.dfa;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

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

        // Checking that we dont map two different states to the same symbol
        for(Entry<String, Character> c : this.transitions.entrySet()){
            if(c.getValue() == onSymb){
                if(!c.getKey().equals(toState)){
                    return false;
                }
            }
        }

        
        this.transitions.put(toState, onSymb);
        return true;
    }
    
}
