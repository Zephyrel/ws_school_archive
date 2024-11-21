package edu.missouriwestern.csmp.csc289;

/**
 * Created by byan on 2/23/2016.
 */
public class CaesarCipher {
    private int a;
    private int n;
    private char[] letters;

    public CaesarCipher() {
        this.letters = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    }

    public int getA() {
        return a;
    }

    public int getN() {
        return n;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int encrypt(int x){
    }

    public int inverseOf(int e){
    }

    public int decrypt(int y){
    }

    public String encrypt(String plaintext){
    }

    public String decrypt(String ciphertext){
    }
}
