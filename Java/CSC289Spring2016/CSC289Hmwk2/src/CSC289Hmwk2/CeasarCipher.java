package CSC289Hmwk2;
import java.util.HashMap;

public class CeasarCipher {
    private int n;	//character shift
    private int alphaMod;
    private int numMod;
    private int invAlphaMod; //alphabet's additive inverse modulo: x=(26-n), (x+=26 until x>0)mod26 ex. n is 3, this is 23
    private int invNumMod;	//number's additive inverse modulo: x=(10-n), (x+=10 until x>0)mod10 ex. n is 3, this is 7
    public CeasarCipher(){}
    public int getN(){
        return n;
    }
    public int getAlphMod(){
        return alphaMod;
    }
    public int getNumMod(){
        return numMod;
    }
    public int getInvAlphMod(){
        return invAlphaMod;
    }
    public int getInvNumMod(){
        return invNumMod;
    }
    public void setN(int n){
        this.n = n;
    }
    public void calcModulo(){
    	alphaMod=getN();
    	numMod=getN();
    	//32 should become 6
        if(alphaMod>25){
        	while(alphaMod>25){
        		alphaMod-=26;
        	}
        }
        //24 should become 4
        if(numMod>10){
        	while(numMod>10){
        		numMod-=10;
        	}
        }
        invAlphaMod=26-getN();
        invNumMod=10-getN();
        if(invAlphaMod<0){
        	while(invAlphaMod<0){
        		invAlphaMod+=26;
        	}
        }
        if(invNumMod<0){
        	while(invNumMod<0){
        		invNumMod+=10;
        	}
        }
    }
    //Java follows Unicode for strings
    //digits = (0x30 -> 0x39)
    //upperCase = (0x41 -> 0x5A)
    //lowerCase = (0x61 -> 0x7A)
    //[digits] [SYMBOLS] [upperCase] [SYMBOLS] [lowerCase] [Symbols]
    public String encrypt(String plaintext){
    	String s="";
    	char a=' ';
    	boolean flag=true;
    	calcModulo();
    	for(int i=0;i<plaintext.length();i++){
    		a=(char)(plaintext.charAt(i));
    		if(!(Character.isDigit(a)||Character.isAlphabetic(a))){
    			flag=false;	//this causes an add the non-digit, non-alphabetical char 
    		}
    		
    		if(flag){
				if(a<='9'){	//handle Digits. =DONE=
					if(a >= '0'+getInvNumMod()){
						s += (char)(a-getInvNumMod());
					}else{
						s += (char)(a+getNumMod());
					}
				}else if(a>='A'&&a<='Z'){	//handle UpperCase =DONE=
		    		if(a >= 'A'+getInvAlphMod()){
		    			s += (char)(a - getInvAlphMod());
		    		}else{
		    			s += (char)(a + getAlphMod());
		    		}
				}else{	//handle LowerCase	=DONE=
					if(a >= 'a'+getInvAlphMod()){
		    			s += (char)(a - getInvAlphMod());
		    		}else{
		    			s += (char)(a + getAlphMod());
		    		}
				}
    		}else{
    			s+=a;
    		}
    		flag=true;	//Back to regular handling after handling non-digit, non-alphabetic characterr
		}
    	return s;
    }
    
    public String decrypt(String ciphertext){
    	String s="";
    	char c=' ';
    	boolean flag=true;
    	for(int i=0;i<ciphertext.length();i++){
    		c=(char)(ciphertext.charAt(i));
    		if(!(Character.isDigit(c)||Character.isAlphabetic(c))){
    			flag=false;
    		}
    		if(flag){
	    		if(c<='9'){	//Digits: =DONE=
	    			if(c<'0'+getNumMod()){
	    				s+=(char)(c + getInvNumMod());
	    			}else{
	    				s += (char)(c - getNumMod());
	    			}
	    		}else if(c<='Z'){	//Uppercase: DONE
		    		if(c < 'A'+getAlphMod()){
		    			s += (char)(c + getInvAlphMod());
		    		}else{
		    			s += (char)(c - getAlphMod());
		    		}
	    		}else{	//Lowercase: DONE
		    		if(c < 'a'+getAlphMod()){
		    			s += (char)(c + (getInvAlphMod()));
		    		}else{
		    			s += (char)(c - getAlphMod());
		    		}
	    		}
    		}else{
    			s+=c;
    		}
    		flag=true;
		}
    	return s;
    }
}

