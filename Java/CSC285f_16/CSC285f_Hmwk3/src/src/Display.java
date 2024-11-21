package src;

import src.Game;

//Display Logic
public class Display{
	public void showGeneration(Game game){
		for(int i=0;i < game.rows;i++){
			for(int j=0; j < game.columns;j++){
				if(game.world[i][j][game.current]==true){
					System.out.print("|█");
					if(j==game.columns-1){System.out.print("|");}
				}else{
					System.out.print("|▒");
					if(j==game.columns-1){System.out.print("|");}
				}	
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}