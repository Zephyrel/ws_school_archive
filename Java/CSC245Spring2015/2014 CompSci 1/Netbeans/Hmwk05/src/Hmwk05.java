/**
 * Russell Ferguson
 * 02/15/14
 * CSC 184 MWF
 */
public class Hmwk05 {

    public static void main(String[] args) {
        char ch0 = 'd';
        char ch1 = 'o';
        char ch2 = 'g';
        char ch3 = (char)115;
        char ch4 = '}';
        String word = "";
        char lower, upper;
        int asciiCode;
        
        lower = ch4;                    //Add '{'. It's two characters behind from
        asciiCode = (int) lower;        //The closing brace '}'
        asciiCode -= 2;
        upper = (char) asciiCode;
        word += upper;                  //Adds to the 'end' of the empty string
        
        lower = ch0;                    //Add 'D'
        asciiCode = (int) lower;
        asciiCode -= 32;                //Upper case letters are 32 characters
        upper = (char) asciiCode;       //under the lower case characters
        word += upper;                  //Adds to the end of the string
        
        lower = ch1;                    //Add 'O'
        asciiCode = (int) lower;
        asciiCode -= 32;
        upper = (char) asciiCode;
        word += upper;                  //adds
        
        lower = ch2;                    //Add 'G'
        asciiCode = (int) lower;
        asciiCode -= 32;
        upper = (char) asciiCode;
        word += upper;
        
        lower = ch3;                    //Add 'S'
        asciiCode = (int) lower;
        asciiCode -= 32;
        upper = (char) asciiCode;
        word += upper;
        
        word += ch4;                    //Add } to top it off.
        System.out.println("The word is \"" + word + '\"');
    }
    
}
