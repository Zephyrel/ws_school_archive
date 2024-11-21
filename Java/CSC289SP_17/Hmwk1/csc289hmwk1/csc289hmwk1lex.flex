package csc289hmwk1;
import java.io.*;
import java.util.Scanner;

%%

%public
%class csc289hmwk1

%line
%column
%unicode
%standalone

%state IP
%state EMAIL

LineTerminator = (\r|\n|\r\n)
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator}|[ \t\f]

d	= [0-9]
char	= [a-zA-Z_]
Octet	= {d}|[1-9]{d}|1{d}{d}|2[0-4]{d}|25[0-5]
IP		= {Octet}"."{Octet}"."{Octet}"."{Octet}{WhiteSpace}+
email	= ({char}|{d})+"@"({char}|{d})+"."({char}|{d})+{WhiteSpace}+

%%

<YYINITIAL> {
	{IP}	{System.out.println("IP Address found.");}
	{email}	{System.out.println("Email Address found.");}
	{WhiteSpace} {	}
	{char}	{System.out.println("Char Found");}
	{d}		{System.out.println("Digit Found");}
}
