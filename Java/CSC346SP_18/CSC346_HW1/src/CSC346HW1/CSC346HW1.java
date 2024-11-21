package CSC346HW1;
import java.util.Scanner;

/*/
 *Author:	Russell Ferguson
 *Date:		January 25, 2018
 *Class:	CS346 - Data Exchange Technologies
 *Professor:Evan Noynaert - Missouri Western State University
 */
class CSC346HW1{

	public static void main(String[] args){
		String allowedSpecials = "!@#$%^&*()_+-=,./[]{}|;:\\\"\'<>?";
		Scanner scanner = new Scanner(System.in);
		String s = "";
		Long entropy = Long.valueOf(0);
		System.out.println("Enter your password:\n");
		s += scanner.nextLine();
		
		
		System.out.printf("String:\t\t\t\t%s\n", truncate(s, 20));
		entropy = calculateEntropy(s, allowedSpecials);
		
		System.out.print("Contains Digit:\t\t\t");
		if(hasDigit(s)) System.out.print("True\n"); else System.out.print("False\n");
		
		System.out.print("Contains Uppercase:\t\t");
		if(hasUpperCase(s)) System.out.print("True\n"); else System.out.print("False\n");
		
		System.out.print("Contains Lowercase:\t\t");
		if(hasLowerCase(s)) System.out.print("True\n"); else System.out.print("False\n");
		
		System.out.printf("Count of special characters:\t%d\n", countSpecial(s, allowedSpecials));

		System.out.printf("Password Length:\t\t%d\n", trimmedLength(s));
		
		System.out.printf("Password Entropy:\t\t%d\n", entropy);
		System.out.printf("String Entropy Rating:\t\t%s", evaluateEntropy(entropy));
			
		scanner.close();
	}
	static int countSpecial (String s, String allowedCharacters) {
		int k = 0;
		for(int i = 0; i<s.length(); i++) {
			for(int j = 0; j<allowedCharacters.length(); j++) {
				if(s.charAt(i)==allowedCharacters.charAt(j)) k++;
			}
		}
		return k;
	}
	static boolean hasDigit(String s) {
		for(int i = 0; i<s.length(); i++)
			if(s.charAt(i)>='0' && s.charAt(i)<='9') return true;
		return false;
	}
	static boolean hasUpperCase(String s) {
		for(int i = 0; i<s.length(); i++)
			if(s.charAt(i)>='A' && s.charAt(i)<='Z') return true;
		return false;
	}
	static boolean hasLowerCase(String s) {
		for(int i = 0; i<s.length(); i++)
			if(s.charAt(i)>='a' && s.charAt(i)<='z') return true;
		return false;
	}
	static int trimmedLength(String s) {
		int i = 0;
		int j = 0;
		//skip leading whitespace in beginning of string
		for(i=0; i<s.length(); i++) 
			if(s.charAt(i)!='\0' && s.charAt(i)!=' ' && s.charAt(i)!='\n' && s.charAt(i)!='\r' && s.charAt(i)!='\t') break;
		//skip trailing
		for(j=0; j<=s.length(); j++) 
			if(s.charAt(s.length()-j-1)!='\0' && s.charAt(s.length()-j-1)!=' ' && s.charAt(s.length()-j-1)!='\n'
				&& s.charAt(s.length()-j-1)!='\r' && s.charAt(s.length()-j-1)!='\t') break;
		return s.length()-j-i;
	}
		
	static String truncate(String s, int n) {
		if(s.length()<n)return s;
		else return s.substring(0, n)+"...";
	}
	static double log2(double x) {
		return (Math.log(x)/Math.log(2));
	}
	static Long calculateEntropy(String s, String allowedCharacters) {
		int L = s.length();
		int r = 0;
		if(hasLowerCase(s) || hasUpperCase(s)) {
			r=26;
		}
		if(hasLowerCase(s) && hasUpperCase(s) && !hasDigit(s)) {
			r=52;
		}
		if(hasLowerCase(s) && hasUpperCase(s) && hasDigit(s)) {
			r=62;
		}
		if(hasLowerCase(s) && hasUpperCase(s) && hasDigit(s) && countSpecial(s, allowedCharacters)<10 && countSpecial(s, allowedCharacters)>4) {
			r=67;
		}
		if(hasLowerCase(s) && hasUpperCase(s) && hasDigit(s) && countSpecial(s, allowedCharacters)>9) {
			r=72;
		}
		return Math.round(log2(Math.pow(r, L-1)));
	}
	static String evaluateEntropy(Long entropy) {
		String s="";
		if(entropy<65) {
			s="Very Weak";
		}else if(entropy<81) {
			s="Weak";
		}else if(entropy<113) {
			s="Moderate";
		}else if(entropy<129) {
			s="Strong";
		}else if(entropy>128) {
			s="Very Strong";
		}
		return s;
	}
}