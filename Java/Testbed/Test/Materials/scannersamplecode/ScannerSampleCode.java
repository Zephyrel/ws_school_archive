/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannersamplecode;
import static scannersamplecode.States.*;
import java.util.HashMap;
/**
 *
 * @author byan
 */
public class ScannerSampleCode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('A', 0);
        States[][] transitions = new States[States.values().length][alphabet.size()];
        alphabet.
        //initialize the table here
        transitions[START.ordinal()][alphabet.get('A')] = INID;
        
        States currentState = START;
        char nextChar = getNextChar();
        switch(currentState){
            case START:
                switch(nextChar){
                    case 'A':  
                        System.out.println(transitions[START.ordinal()][alphabet.get('A')]);
                        break;
                }
                break;
            default:
                break;
        }
    }
    
    public static char getNextChar(){
        return 'A';
    }
}
