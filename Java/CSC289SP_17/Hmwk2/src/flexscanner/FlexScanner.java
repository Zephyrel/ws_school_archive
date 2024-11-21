/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flexscanner;
import java.io.*;

/**
 *
 * @author byan
 */
public class FlexScanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        boolean cmdIn   = true;
        boolean cmdOut  = true;
        String pgm = null;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out, true);

        for(int i=0; i<args.length; i++){
            if(args[i].equalsIgnoreCase("-i")){
                br = new BufferedReader(new FileReader(pgm = args[i+1]));
                cmdIn = false;
                System.out.println("Not Command Line: " + args[i+1]);
            }
            if(args[i].equalsIgnoreCase("-o")){
                pw = new PrintWriter(new File(args[i+1]));
            }
        }

        if(cmdIn)
        {
            pw.printf("\nC Minus Lexical Analyzer: Command Line\n\n");
        }
        else
        {
            pw.printf("\nC Minus Lexical Analyzer: %s\n\n", pgm);
        }
        
        CMScanner cms = new CMScanner(br);
        //cms.setBR(br);
        cms.setPW(pw);
        Tokens.TokenType currentToken;
        while((currentToken=cms.getToken())!=Tokens.TokenType.ENDFILE){
            if(cms.traceScan() && cms.getPW()!=null){
                pw.printf("\t%d: ", cms.getLineno());
                Tokens.printToken(pw, currentToken, cms.getCurrentTokenString());
            }
        }
        if(cms.traceScan() && cms.getPW()!=null){
            pw.printf("\t%d: ", cms.getLineno());
            Tokens.printToken(pw, currentToken, cms.getCurrentTokenString());
        }
        System.out.println("\nFinished scanning.");
        System.exit(0); 
    }
}
