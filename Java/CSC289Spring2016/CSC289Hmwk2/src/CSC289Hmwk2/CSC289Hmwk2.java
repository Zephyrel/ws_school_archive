package CSC289Hmwk2;
import java.io.*;
import java.util.*;

import CSC289Hmwk2.CeasarCipher.*;
/*************************************
 *Author:	Russell Ferguson
 *Date:		February 29, 2016
 *Class:	CSC285 - Data Structures
 *Professor:Baoqiang Yan - Missouri Western State University
 ************************************/
public class CSC289Hmwk2 {
	
	public static void main(String[] args){
		//Test String:
		//ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789
		Scanner input = new Scanner(System.in);
		int n=0;
		String plaintext="";	//regular text
		String ciphertext="";	//ceasar formula (n+a)mod26 with n being the original letter
		CeasarCipher cipher = new CeasarCipher();
		
		System.out.println("Type in the number n that you want to be used by the Ceaser Cipher:");
		try{
			n=input.nextInt();
		}catch(InputMismatchException e){
			System.err.println("That's not a number! Exiting!");
			System.exit(0);
		}
		cipher.setN(n);
		System.out.println("Type in the plaintext that you want:");
		System.out.println("(A note: Digits 0-9 follow their own ceasar cipher based on n mod 10 while characters still follow ceasar cipher based on n mod 26!)");
		System.out.println("Special Characters such as { or $ or # are ignored in the cipher.");
		plaintext=input.nextLine();
		plaintext=input.nextLine();
		
		System.out.println("The Original Text is:");
		System.out.println(plaintext);
		System.out.println("The Encrypted Text is:");
		ciphertext=cipher.encrypt(plaintext);
		System.out.println(ciphertext);
		System.out.println("The Decrypted Text is:");
		System.out.println(cipher.decrypt(ciphertext));
	}
}
