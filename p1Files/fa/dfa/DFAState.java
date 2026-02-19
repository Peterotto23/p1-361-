package fa.dfa;

import java.util.HashMap;

public class DFAState extends fa.State {
    private HashMap<Character, String> transitions;
    
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
        if(this.transitions.containsKey(onSymb)) {
            if(!this.transitions.get(onSymb).equals(toState)){
                return false;
            }
        } 
        this.transitions.put(onSymb, toState);
        return true;
    }
    
    /* Implementing this method is what finally
    made me realize that I've stored toState and onSymb
    backwards in the HashMap and everything wouldve
    been so much easier if i didnt 😅 */
    public String getTransition(Character c){
        return this.transitions.get(c);
    }
}
