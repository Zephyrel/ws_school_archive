package csc289Hmwk1;
import static csc289Hmwk1.CMinusDFAState.*;
import static csc289Hmwk1.CMinusTokens.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
/*This class contains the allowed character set,
namely the alphabet, and the set of DFA states. It also should contain the transition table
and a method to lookup the table based upon the current state and input char.*/


//DFA - Deterministic Finite Automata
//This Class/Object manages states and transitions between states
public class CMinusDFA{
	CMinusTokens currentToken;
	CMinusDFAState dfa;
	String currentTokenStr;
	String prevToken;
	HashMap<String, CMinusTokens> reservedWordsMap;
	HashMap<Character, Integer> alphabet = new HashMap<>();
	
    /*
    CMScanner(Reader in) {
        if (in instanceof BufferedReader) {
            this.br = (BufferedReader)in;
        } else {
            System.out.println("Only buffered reader supported for now!");
            System.exit(0);
        }
    }

    CMScanner(InputStream in){	this(new BufferedReader(new InputStreamReader(in)));}*/
    //Text handling methods

	//Constructors
    public CMinusDFA(){
    	
    }
    //Methods
	//reserved keywords
	/*public CMinusTokens keywordLookup(String idTokenStr) {
		if (idTokenStr.equalsIgnoreCase("if"))	{return CMinusTokens.IF;}
		if (idTokenStr.equalsIgnoreCase("else")){return CMinusTokens.ELSE;}
		if (idTokenStr.equalsIgnoreCase("int")) {return CMinusTokens.INT;}
		if (idTokenStr.equalsIgnoreCase("return")){return CMinusTokens.RETURN;}
		if (idTokenStr.equalsIgnoreCase("void")) {return CMinusTokens.VOID;}
		if (idTokenStr.equalsIgnoreCase("while")) {return CMinusTokens.WHILE;}
		return CMinusTokens.ID;
    }*/
	
    
	//Giant thing that returns a Token based on the char
	//I need to use a switch case for every state while it isn't the DONE State to figure what to do
	
}
