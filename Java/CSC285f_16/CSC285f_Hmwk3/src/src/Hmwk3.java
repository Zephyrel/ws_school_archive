package src;

import java.io.FileNotFoundException;
import com.noynaert.csc285.life.*;
import src.Display;
import src.Game;

public class Hmwk3 {
	//Main
	public static void main(String[] args){
		//args: file.txt, iterations
		String dataFile = (args.length >= 1) ? args[0] : "lineblinker.txt";
		int iterations = (args.length >= 2) ? (Integer.parseInt(args[1])) : 1000;
		boolean torus = true;
		if(args.length >= 3 && (args[2].compareToIgnoreCase("finite")==0)){	torus=false;	}
		System.out.printf("Using file \"%s\" with %d iterations.\n", dataFile, iterations);
		try{
			GetFile data = new GetFile(dataFile);
			System.out.println(data);
			Game newGame = new Game(data);
			if(!torus){
				newGame.torus=false;
			}
			Display display = new Display();
			if(newGame.torus){
				System.out.println("Torus of Finite Game Mode: Torus\n");
			}else{
				System.out.println("Torus of Finite Game Mode: Finite\n");
			}
			for(int k=0;k<iterations;k++){
				System.out.print("Iteration "+(k+1)+"\n");
				display.showGeneration(newGame);
				try{
					Thread.sleep(500);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
				newGame.genNext();
				//I can't get carriage returns working for this properly
				//but I can overwrite a Hello World program with a Bubye World with \r's in a Hello World program
				//HMMM
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("\nGame Over!");
	}
}