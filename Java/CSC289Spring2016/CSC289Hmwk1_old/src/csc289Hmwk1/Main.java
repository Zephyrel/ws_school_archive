package csc289Hmwk1;
import java.io.*;

import static csc289Hmwk1.CMinusTokens.*;
import static csc289Hmwk1.CMinusDFAState.*;
import csc289Hmwk1.CMinusDFA;
import csc289Hmwk1.CMinusScanner;

public class Main {
	public static void main (String[] args) throws IOException{
		boolean cmdIn = true;
		boolean cmdOut = true;
		String pgm = null;
		boolean traceScan = true;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
		PrintWriter pw = new PrintWriter(System.out, true);
		
		for(int i=0;i<args.length; i++){
			if(args[i].equalsIgnoreCase("-i")){
				br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(pgm = args[i+1]))));cmdIn = false;
				System.out.println();
			}
		
		}
		
		CMinusScanner cms = new CMinusScanner(br);
		
		CMinusTokenType currentToken;
		while((currentToken=cms.getToken()) != ENDFILE){
			if(currentToken != COMMENT){
				if(traceScan){
					pw.printf("%s", cms.getToken());
				}
				if(currentToken == ERROR){
					System.exit(1);
				}
			}
		}
		
	}
}
