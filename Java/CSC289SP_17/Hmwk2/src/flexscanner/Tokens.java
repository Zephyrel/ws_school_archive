/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flexscanner;
import java.io.PrintWriter;

/**
 *
 * @author byan
 */
public class Tokens {

    public enum TokenType{
        /*book-keeping tokens*/  
       ENDFILE, ERROR, 
       /*reserved words*/
       IF, ELSE, INT, RETURN, VOID, WHILE, 
       /*Identifiers and Numbers*/
       /*MULTI-CHAR --> NEED CORRESPONDING IN-STATE*/
       ID, NUM,
       /*Special Symbols*/
       ASSIGN, EQ,/*INEQ*/ NE,/*INNE*/ LT, LE,/*INLE*/ GT, GE,/*INGE*/ PLUS, 
       MINUS, TIMES, OVER, LPAREN, RPAREN, 
       SEMI, COMMA, LSB, RSB, LBRACE, RBRACE
    }
    
    public static void printToken(PrintWriter pw, TokenType token, String tokenString)
    {
        switch(token)
        {
            /*RESERVED WORDS*/         
            case IF:
            case ELSE:
            case INT:
            case RETURN:
            case VOID:
            case WHILE:
                 pw.printf("reserved word: \t%s\n", tokenString);
                 break;
            /*SPECIAL CHARACTERS*/
            case ASSIGN:      pw.printf("special char: \t=\n"); break;
            case EQ:          pw.printf("special char: \t==\n"); break;
            case NE:          pw.printf("special char: \t!=\n"); break;
            case LT:          pw.printf("special char: \t<\n"); break;
            case LE:          pw.printf("special char: \t<=\n"); break;
            case GT:          pw.printf("special char: \t>\n"); break;
            case GE:          pw.printf("special char: \t>=\n"); break;
            case PLUS:        pw.printf("special char: \t+\n"); break;
            case MINUS:       pw.printf("special char: \t-\n"); break;
            case TIMES:       pw.printf("special char: \t*\n"); break;
            case OVER:        pw.printf("special char: \t/\n"); break;
            case LPAREN:      pw.printf("special char: \t(\n"); break;
            case RPAREN:      pw.printf("special char: \t)\n"); break;
            case SEMI:        pw.printf("special char: \t;\n"); break;
            case COMMA:       pw.printf("special char: \t,\n"); break;
            case LSB:         pw.printf("special char: \t[\n"); break;
            case RSB:         pw.printf("special char: \t]\n"); break;
            case LBRACE:      pw.printf("special char: \t{\n"); break;
            case RBRACE:      pw.printf("special char: \t}\n"); break;

            case NUM:
                 pw.printf("NUM, val= \t\t%s\n", tokenString);
                 break;
            case ID:
                 pw.printf("ID, name= \t\t%s\n", tokenString);
                 break;
            case ENDFILE:
                 pw.printf("ENDFILE");
                 break;
            case ERROR:
                 pw.printf("Unknown token: \t%s\n", tokenString);
                 System.exit(1);
                 break;
        } 
    }
}
