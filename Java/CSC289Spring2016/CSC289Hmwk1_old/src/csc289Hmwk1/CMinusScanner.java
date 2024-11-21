package csc289Hmwk1;

import static csc289Hmwk1.CMinusTokens.*;
import static csc289Hmwk1.CMinusDFAState.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
/*4. Create a java file called CMinusScanner.java. The scanner contains an instance of the CMinusDFA
and use it to identify the tokens from the characters read from a file or the command line.*/
/*
 * 
 * 
 * 
 */
public class CMinusScanner{
	//Declare vars for later
	BufferedReader br;
	char[] tokenBuffer;
	int tokenIndex;
	CMinusDFA dfa;
	CMinusTokens currentToken;
	String currentTokenStrl;
	int linePos = 0;
	int lineNo = 1;
	char lastChar = ' ';
	HashMap<String, CMinusTokens> reservedWordsMap;
	/*instantiate BufferedReader
	try{
		br = new BufferedReader(new FileReader("testinput1.txt"));
	}catch(IOException e){
		System.err.println("Error getting file.");
	}*/
	//Constructor
	public CMinusScanner(BufferedReader br){
		this.br=br;
		tokenBuffer=new char[256];
		tokenIndex = 0;
		dfa = new CMinusDFA();
		initReservedWords();
	}
	//TODO:METHODS. Disregard what's written below. I Need:
	
	public CMinusTokens reservedLookup(String idTokenStr){
		return reservedWordsMap.getOrDefault(idTokenStr.toLowerCase(), ID);
	}
	public CMinusTokens getToken() throws IOException{
		CMinusDFAState currentState = ST_START;
		CMinusDFAState newState;
		char c = '\u0000';
		
		CMinusTokens currentTokenType = ERROR;
		tokenIndex = 0;
		boolean save = false;
		while(currentState != ST_DONE && currentState != ST_ERROR){
			br.mark(1);
			int t = br.read(1);
			
		}
	}
	public void initReservedWords(){
		reservedWordsMap = new HashMap<>();
		reservedWordsMap.put("if", IF);
		reservedWordsMap.put("else", ELSE);
		reservedWordsMap.put("int", INT);
		reservedWordsMap.put("return", RETURN);
		reservedWordsMap.put("void", VOID);
		reservedWordsMap.put("while", WHILE);
	}
	
	public BufferedReader getBr(){	return br;
	}
	public void setBr(){}
	public int getLineno(){	return lineNo;}
	public void setLineNo(int lineNo){	this.lineNo = lineNo;}

public CMinusTokens getToken() throws IOException {
		
		int tokenStringIndex = 0;
		CMinusTokens currentToken = CMinusTokens.ERROR;
		CMinusDFAState state = CMinusDFAState.ST_START;
		
		while(state != CMinusDFAState.ST_DONE){
			char c = this.getNextChar();
			boolean save = true;
			//Now begins one giant case switch statement
			block0 : switch (state) {
				//First block 
				case ST_START:{
					tokenStr
					ingIndex = 0;
					//start. gets digits/chars and special chars that are the first char of more than one operator
					if (Character.isDigit(c)){	state = CMinusDFAState.ST_INNUM;break;}
					if (Character.isLetter(c)){	state = CMinusDFAState.ST_INID;	break;}
					if (c == '='){	state = CMinusDFAState.ST_ASSIGN_OR_EQ;		break;}
					if (c == '!'){	state = CMinusDFAState.ST_NEQ;				break;}
					if (c == '<'){	state = CMinusDFAState.ST_LT_OR_LTOE;		break;}
					if (c == '>'){	state = CMinusDFAState.ST_GT_OR_GTOE;		break;}
					if (c == '/'){	state = CMinusDFAState.ST_DIV_OR_COMMENT;	break;}
					//whitespace, tabspace, and newline readers
					if(c == ' ' || c == '\t' || c == '\n' || c == '\r'){save = false;if(c!='\n'){break;++this.lineno;break;}}
					
					state = CMinusDFAState.ST_DONE;
					//Math Symbols and Syntax
					switch (c) {
					    case ' ': {	save = false;	currentToken = CMinusTokens.ENDFILE;	break block0;}
					    case '+': {	currentToken = CMinusTokens.PLUS;	break block0;}
					    case '-': {	currentToken = CMinusTokens.MINUS;	break block0;}
					    case '*': {	currentToken = CMinusTokens.MULT;	break block0;}
					    case '(': {	currentToken = CMinusTokens.LP;		break block0;}
					    case ')': {	currentToken = CMinusTokens.RP;		break block0;}
					    case ';': {	currentToken = CMinusTokens.SEMI;	break block0;}
					    case ',': { currentToken = CMinusTokens.COMMA;	break block0;}
					    case '[': {	currentToken = CMinusTokens.LB;		break block0;}
					    case ']': {	currentToken = CMinusTokens.RB;		break block0;}
					    case '{': {	currentToken = CMinusTokens.LCB;	break block0;}
					    case '}': {	currentToken = CMinusTokens.RCB;	break block0;}
					}
					currentToken = CMinusTokens.ERROR;break;
			    }
				//After this Case up above is done, we have a new state and it goes to any of the below states on the next While loop
			    //Here be Comments handling
			    case ST_DIV_OR_COMMENT:{	//Set by '/'
					if(c=='*'){	save = false;	state = CMinusDFAState.ST_LCOMMENT;break;}
					this.ungetNextChar();	currentToken = CMinusTokens.DIV;	state = CMinusDFAState.ST_DONE;	break;
			    }
			    case ST_COMMENT: {
					save = false;
					if(c=='/'){	save = false;	state = CMinusDFAState.ST_E;	break;}
					if(c=='*'){	save = false;	state = CMinusDFAState.ST_RCOMMENT;	break;}
					if(c=='\n'){++this.lineno;	break;}
					save = false;	break;
			    }
			    case ST_LCOMMENT:{
					save = false;
					if(c=='*'){	save = false;	currentToken = CMinusTokens.ERROR;
						System.out.printf("%d: Error: Nested comment not allowed.\n", this.lineno);	System.exit(1);break;}
					if(c=='/'){save = false;break;}
					if (c == '\n') {++this.lineno;break;}
					state = CMinusDFAState.ST_COMMENT;
					break;
			    }
			    case ST_RCOMMENT: {
					save = false;
					if(c=='/'){state = CMinusDFAState.START;break;}
					if(c=='*'){save = false;break;}
					save = false;
					if(c=='\n'){++this.lineno;}
					state = CMinusDFAState.ST_COMMENT;
					break;
			    }
			    //Comparison operators
			    case ST_ASSIGN_OR_EQ: {
					state = CMinusDFAState.ST_DONE;
					if (c == '=') {	currentToken = CMinusTokens.EQ;	state = CMinusDFAState.ST_DONE;	break;}
					this.ungetNextChar();
					currentToken = CMinusTokens.ASSIGN;
					state = CMinusDFAState.ST_DONE;
					break;
			    }
			    case ST_NEQ: {
					state = CMinusDFAState.ST_DONE;
					if (c == '=') {	currentToken = CMinusTokens.NEQ;state = CMinusDFAState.ST_DONE;	break;}
					save = false;
					this.ungetNextChar();
					currentToken = CMinusTokens.ERROR;
					break;
			    }
			    case ST_LT_OR_LTOE: {
					state = CMinusDFAState.ST_DONE;
					if (c == '=') {currentToken = CMinusTokens.LTOE;	state = CMinusDFAState.ST_DONE;	break;}
					this.ungetNextChar();
					currentToken = CMinusTokens.LT;
					break;
			    }
			    
			    case ST_GT_OR_GTOE: {
					state = CMinusDFAState.ST_DONE;
					if (c == '=') {currentToken = CMinusTokens.GTOE;state = CMinusDFAState.ST_DONE;break;}
							this.ungetNextChar();
							currentToken = CMinusTokens.GT;
							break;
				}
			    case ST_INNUM:{
					if(Character.isDigit(c))break;
					this.ungetNextChar();
					save = false;
					state = CMinusDFAState.ST_DONE;
					currentToken = CMinusTokens.NUM;
					break;
			    }
			    case ST_INID:{
					if (Character.isLetter(c)) break;
					this.ungetNextChar();
					save = false;
					state = CMinusDFAState.ST_DONE;
					currentToken = CMinusTokens.ID;
					break;
			    }
			    default: {
					state = CMinusDFAState.ST_DONE;
					currentToken = CMinusTokens.ERROR;
			    }
					}
					if (save && tokenStringIndex <= 256) {	this.tokenString[tokenStringIndex++] = c;}
					if (state != CMinusDFAState.ST_DONE || currentToken != CMinusTokens.ID) continue;
					currentToken = this.keywordLookup(new String(this.tokenString, 0, tokenStringIndex));
			    }
			    this.currentToken = new String(this.tokenString, 0, tokenStringIndex);
			    return currentToken;
			}

		public CMinusTokens keywordLookup(String idTokenStr) {
			if (idTokenStr.equalsIgnoreCase("if"))	{return CMinusTokens.IF;}
			if (idTokenStr.equalsIgnoreCase("else")){return CMinusTokens.ELSE;}
			if (idTokenStr.equalsIgnoreCase("int")) {return CMinusTokens.INT;}
			if (idTokenStr.equalsIgnoreCase("return")){return CMinusTokens.RETURN;}
			if (idTokenStr.equalsIgnoreCase("void")) {return CMinusTokens.VOID;}
			if (idTokenStr.equalsIgnoreCase("while")) {return CMinusTokens.WHILE;}
			return CMinusTokens.ID;
		}
}
