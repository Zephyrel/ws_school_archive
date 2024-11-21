package csc289Hmwk1;
import static csc289Hmwk1.CMinusDFAState.*;
import static csc289Hmwk1.CMinusTokens.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

//Note, we don't feed this thing a line at a time, we feed the whole text, but at a char at a time, okay?
//All states are marked ST, everything else not marked with ST and in allcaps is a token
public class CMinusDFA {
	public CMinusTokens currentToken;
	public CMinusDFAState currentState;
	public char[] buffer=new char[256];
	public char[] tokenBuffer=new char[256];
	public String tokenStr="";
	public int IDnum;
	int lineNo = 1;
	int linePos = 0;
	//identifiers holds chars or string tokens which aren't keywords, placed in there to identify an integer variable
	//syntax HashMap<Key, Value>
	private HashMap<String, Integer> ID = new HashMap<String, Integer>();
	private HashMap<CMinusTokens, String> keyMap;
	private HashMap<CMinusTokens, Character> alphabetMap;

	//DONE:	Constructors - (Default), (BufferedReader br)
	CMinusDFA(){
		initKeywords();
		initAlphabet();
	}
	CMinusDFA(BufferedReader br) throws IOException{
		try{buffer=br.readLine().toCharArray();
			}catch(IOException e){System.err.println("DFA Failed to get line! Exiting!");}
		initKeywords();
		initAlphabet();
		this.currentToken=keySearch(new String(buffer, 0, linePos));
		this.currentToken=alphabetSearch();
		this.currentToken=getToken();
		if(currentToken=ID){
			
		}
	}
	//DONE: Methods = initKeywords, initAlphabet,getLineNo,getLinePos,getNextChar,getPrevChar,getToken
	public void initKeywords(){
		keyMap=new HashMap<CMinusTokens, String>();
		keyMap.put(IF	,	"if");
		keyMap.put(ELSE,	"else");
		keyMap.put(INT	,	"int");
		keyMap.put(RETURN,	"return");
		keyMap.put(VOID,	"void");
		keyMap.put(WHILE,	"while");
	}
	//init chars - Use this HashMap to get through basic one-char functions
	public void initAlphabet(){
		alphabetMap.put(PLUS,'+');
		alphabetMap.put(MINUS,'-');
		alphabetMap.put(MULT,'*');
		alphabetMap.put(LP,'(');
		alphabetMap.put(RP,')');
		alphabetMap.put(LB,'[');
		alphabetMap.put(RB,']');
		alphabetMap.put(LCB,'{');
		alphabetMap.put(RCB,'}');
		alphabetMap.put(COMMA,',');
		alphabetMap.put(SEMI,';');

	}
	//Call on ID checks to see if it's a keyword
	public CMinusTokens keySearch(String tokenStr){
		if(tokenStr.equalsIgnoreCase(keyMap.get(IF)))	{return IF;}
		if(tokenStr.equalsIgnoreCase(keyMap.get(ELSE)))	{return ELSE;}
		if(tokenStr.equalsIgnoreCase(keyMap.get(INT))) 	{return INT;}
		if(tokenStr.equalsIgnoreCase(keyMap.get(RETURN))){return RETURN;}
		if(tokenStr.equalsIgnoreCase(keyMap.get(VOID))) {return VOID;}
		if(tokenStr.equalsIgnoreCase(keyMap.get(WHILE))){return WHILE;}
		return ID;
	}
	public CMinusTokens alphabetSearch(char tokenChar){
		if(tokenChar==(alphabetMap.get(PLUS))) 	{return PLUS;}
		if(tokenChar==(alphabetMap.get(MINUS))) {return MINUS;}
		if(tokenChar==(alphabetMap.get(MULT)))	{return MULT;}
		if(tokenChar==(alphabetMap.get(LP))) 	{return LP;}
		if(tokenChar==(alphabetMap.get(RP))) 	{return RP;}
		if(tokenChar==(alphabetMap.get(LB))) 	{return LB;}
		if(tokenChar==(alphabetMap.get(RB))) 	{return RB;}
		if(tokenChar==(alphabetMap.get(LCB))) 	{return LCB;}
		if(tokenChar==(alphabetMap.get(RCB))) 	{return RCB;}
		if(tokenChar==(alphabetMap.get(COMMA))) {return COMMA;}
		if(tokenChar==(alphabetMap.get(SEMI))) 	{return SEMI;}
		return ERROR;
		//Chars not included /<>!=
	}
	public void getNextChar(){
		++linePos;
	}
	public void getPrevChar(){
		--linePos;
	}
	public CMinusTokens getToken() throws IOException {
		
		int index = 0;
		CMinusTokens currentToken = ERROR;
		CMinusDFAState state = ST_START;
		
		while(state != ST_DONE){
			this.getNextChar();
			char c = buffer[linePos];
			//char c is the current char at index linePos
			boolean save = true;
			//decide whether or not to save the token
			//Now begins one giant case switch statement for all states, trying to return a token
			switch(state){
				case ST_START:{
					index = 0;
					//start. gets digits/chars and special chars that are the first char of more than one operator
					if (Character.isDigit(c)){state = ST_INNUM;	break;}
					if (Character.isLetter(c)){state = ST_INID;	break;}
					if (c == '='){	state = ST_ASSIGN_OR_EQ;	break;}
					if (c == '!'){	state = ST_NEQ;				break;}
					if (c == '<'){	state = ST_LT_OR_LTOE;		break;}
					if (c == '>'){	state = ST_GT_OR_GTOE;		break;}
					if (c == '/'){	state = ST_DIV_OR_COMMENT;	break;}
					//whitespace, tabspace, and newline readers
					if (c==' ' || c=='\t' || c=='\n' || c=='\r'){save = false;if(c!='\n'){break;}}
					state = ST_DONE;
					currentToken = ERROR;break;
			    }
				//EVERYTHING'S GOOD ABOVE, OR AT LEAST SHOULD BE. Give a read now and then for sanity check
				//After this Case up above is done, we have a new state and it goes to any of the below states on the next While loop
			    //Here be Comments handling
			    case ST_DIV_OR_COMMENT:{	//Divide or Comment. Decide your fate heeere
					if(c=='*'){	save = false;	state = ST_ENTER_COMMENT;	break;}
					this.getPrevChar();	currentToken = DIV;	state = ST_DONE;break;
			    }
			    case ST_ENTER_COMMENT:{	//You've entererd the comment. Should keep going til it hits endcomment "*/"
					save = false;
					if(c=='/'){	save = false;	state = ST_NESTED_COMMENT;	break;}
					if(c=='*'){	save = false;	state = ST_ENDCOMMENT;break;}
					if(c=='\n'){++this.lineNo;	break;}
					save = false;	break;
			    }
			    case ST_NESTED_COMMENT:{
					save = false;
					if(c=='*'){	save = false;	currentToken = ERROR;
						System.err.println("ERROR: HEY. Nested comments aren't allowed. Quitting.\n");	System.exit(0);break;}
					if(c=='/'){save = false;break;}
					if(c == '\n') {++this.lineNo;break;}
					state = ST_ENTER_COMMENT;
					break;
			    }
			    case ST_ENDCOMMENT: {
					save = false;
					if(c=='/'){state = ST_START;break;}
					if(c=='*'){save = false;break;}
					save = false;
					if(c=='\n'){++this.lineNo;}
					state = ST_ENTER_COMMENT;
					break;
			    }
			    //Comparison operators
			    case ST_ASSIGN_OR_EQ: {
					state = ST_DONE;
					if (c == '=') {	currentToken = EQ;	state = ST_DONE;	break;}
					this.getPrevChar();
					currentToken = ASSIGN;
					state = ST_DONE;
					break;
			    }
			    case ST_NEQ: {
					state = ST_DONE;
					if (c == '=') {	currentToken = NEQ;state = ST_DONE;	break;}
					save = false;
					this.getPrevChar();
					currentToken = ERROR;
					break;
			    }
			    case ST_LT_OR_LTOE: {
					state = ST_DONE;
					if (c == '=') {currentToken = LTOE;	state = ST_DONE;	break;}
					this.getPrevChar();
					currentToken = LT;
					break;
			    }
			    
			    case ST_GT_OR_GTOE: {
					state = ST_DONE;
					if (c == '=') {currentToken = GTOE;state = ST_DONE;break;}
					this.getPrevChar();
					currentToken = GT;
					break;
				}
			    //Is number or an identifier
			    case ST_INNUM:{
					if(Character.isDigit(c))break;
					this.getPrevChar();
					save = false;
					state = ST_DONE;
					currentToken = NUM;
					break;
			    }
			    case ST_INID:{
					if(Character.isLetter(c))break;
					this.getPrevChar();
					save = false;
					state = ST_DONE;
					currentToken = ID;
					break;
			    }
			    default:{
					state = ST_DONE;
					currentToken = ERROR;
			    }
			}//END OF THE BIG SWITCH
			if(save && index <= 256) {	this.tokenBuffer[index++] = c;}
			if(state != ST_DONE || currentToken != ID) continue; //Force it to keep going in case of error or unexpected
			//
			currentToken = this.keySearch(new String(this.tokenBuffer, 0, index));
		}//END OF BIG WHILE
		
	    this.tokenStr = new String(this.tokenBuffer, 0, index);
	    return currentToken;
	}
}
