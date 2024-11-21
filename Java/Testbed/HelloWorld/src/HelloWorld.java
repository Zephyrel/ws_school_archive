import java.io.IOException;

public class HelloWorld {
	public static void main(String[] args){
		System.out.print("Hello World!");
		try{
			Thread.sleep(1000);
		} catch(InterruptedException e){
			
		}
		
		System.out.print("\r\r");
		System.out.print("BuBye");
	}
}
