package csc289Hmwk1;

import static csc289Hmwk1.CMinusDFA.*;
import java.io.BufferedReader;

public class CMinusScanner {
	BufferedReader br;
	CMinusDFA DFA;
	//Constructor
	CMinusScanner(BufferedReader br, CMinusDFA DFA){
		this.br = br;
		this.DFA = new CMinusDFA(br);
	}
	//Methods
	
}
