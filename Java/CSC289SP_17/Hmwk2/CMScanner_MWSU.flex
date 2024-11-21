/*
Author: Baoqiang Yan
Date:   Feb. 05, 2012
Description: This is the input file for jFlex.
*/

package handcodedscanner;
import java.io.*;

/**
 * This class is a scanner in Java for c-minus.
 */

%%

%class CMScanner
%unicode
/* %cup */

/*
  Turn line counting and column counting on. 
  Use the member variables yyline, yycolumn respectively
  to access this information.
*/
%line
%column

/* 	The basic method that produces a token 
	it will be called by whatever process drives the lexer! */
%function getToken

/* 
	This is the type that the basic method returns */
%type Tokens.TokenType

/* 
	What to do when the EOF is reached.
	In this case I have a special EOF token */
%eofval{
  return Tokens.TokenType.ENDFILE;
%eofval}

/*	After EOF is reached close the input stream */
%eofclose


/*
  Declarations
  
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied verbatim into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.   
*/

%{
    private boolean TraceScan = true;
    private BufferedReader br = null;
    private PrintWriter pw = null;
    
    public int getLineno(){
           return yyline + 1;       //yyline is 0 based 
    }    
    
    public boolean traceScan(){
           return TraceScan;       
    }
    
    public PrintWriter getPW(){
           return pw;       
    }

    public void setPW(PrintWriter pwr){
           pw = pwr;       
    }
    
    public String getCurrentTokenString(){
           return yytext();       
    }
%}

/*
	A list of expression macros that will help make the
	the definitions of tokens more readable. 
*/
NUM   	=	[0-9]+
NEWLINE =   \n
ID    	=	[a-zA-Z]+

/*
	if you have more  states you need to define them here
*/
%state JUST_ENTERED_COMMENT ENTERING_NESTED_COMMENT CLOSING_COMMENT

%%
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. 
   
   YYINITIAL is the state at which the lexer begins scanning.  So if there
   is no other state specified as the start state,  regular expressions will 
   only be matched if the scanner is in the start state YYINITIAL. 
*/

<YYINITIAL>[ \t\r\n]+  			         { /* ignore */ }
<YYINITIAL>"/*"                          {yybegin(JUST_ENTERED_COMMENT);}
<JUST_ENTERED_COMMENT>"/"                {yybegin(ENTERING_NESTED_COMMENT);}
<JUST_ENTERED_COMMENT>"*"                {yybegin(CLOSING_COMMENT);} 
<JUST_ENTERED_COMMENT><<EOF>>            {return Tokens.TokenType.ENDFILE;} 
<JUST_ENTERED_COMMENT>.|[ \t\r\n]        { /* ignore */ } 
<ENTERING_NESTED_COMMENT>"*"             {
                                                System.out.printf("%d: Error: Nested comment not allowed.\n", 
                                                                       yyline); 
                                                System.exit(1);
                                         }
<ENTERING_NESTED_COMMENT>{NEWLINE}       {yybegin(JUST_ENTERED_COMMENT);}	/*implement*/
<ENTERING_NESTED_COMMENT><<EOF>>         {return Tokens.TokenType.ENDFILE;}
<ENTERING_NESTED_COMMENT>.               {yybegin(JUST_ENTERED_COMMENT);}	/* implement */
<CLOSING_COMMENT>{NEWLINE}               {yybegin(JUST_ENTERED_COMMENT);}	/* implement */
<CLOSING_COMMENT>"*"                     { /* ignore */ }
<CLOSING_COMMENT>"/"                     {yybegin(YYINITIAL);} /*implement*/
<CLOSING_COMMENT><<EOF>>                 {return Tokens.TokenType.ENDFILE;}
<CLOSING_COMMENT>.                       {yybegin(JUST_ENTERED_COMMENT);} /*implement*/

<YYINITIAL>{
    {NUM}			                   {return Tokens.TokenType.NUM;}
    "if"                               {return Tokens.TokenType.IF;}
    "else"                             {return Tokens.TokenType.ELSE;}
    "int"                              {return Tokens.TokenType.INT;}
    "return"                           {return Tokens.TokenType.RETURN;}
    "void"                             {return Tokens.TokenType.VOID;}
    "while"                            {return Tokens.TokenType.WHILE;}
    {ID}			                   {return Tokens.TokenType.ID;}
    "+"		   	                       {return Tokens.TokenType.PLUS;}
    "-"		   	                       {return Tokens.TokenType.MINUS;}
    "*"		   	                       {return Tokens.TokenType.TIMES;}
    "/"		   	                       {return Tokens.TokenType.OVER;}
    "<"		   	                       {return Tokens.TokenType.LT;}
    "<="		   	                   {return Tokens.TokenType.LE;}
    ">"		   	                       {return Tokens.TokenType.GT;}
    ">="	   	                       {return Tokens.TokenType.GE;}
    "=="	   	                       {return Tokens.TokenType.EQ;}
    "!="		   	                   {return Tokens.TokenType.NE;}
    "="		   	                       {return Tokens.TokenType.ASSIGN;}
    ";"		   	                       {return Tokens.TokenType.SEMI;}
    ","		   	                       {return Tokens.TokenType.COMMA;}
    "("		   	                       {return Tokens.TokenType.LPAREN;}
    ")"		   	                       {return Tokens.TokenType.RPAREN;}
    "["		   	                       {return Tokens.TokenType.LSB;}
    "]"		   	                       {return Tokens.TokenType.RSB;}
    "{"		   	                       {return Tokens.TokenType.LBRACE;}
    "}"		   	                       {return Tokens.TokenType.RBRACE;}
    <<EOF>>                            {return Tokens.TokenType.ENDFILE;}
    /* No token was found for the input so through an error.  Print out an
       Illegal character message with the illegal character that was found. 
    */
    [^]                                { 
                                         System.out.println("Illegal character <"+yytext()+"> on line:" + 
                                                                     yyline + " at column: " + yycolumn); 
                                         System.exit(1);
                                       }
}

