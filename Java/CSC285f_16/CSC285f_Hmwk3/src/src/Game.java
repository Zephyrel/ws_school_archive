package src;

import com.noynaert.csc285.life.GetFile;
import com.noynaert.csc285.life.Point;

//Game Logic
public class Game{
	public boolean[][][] world;
	public int rows, columns;
	public String title;
	public String description;
	boolean torus = true;
	//torus true = wrap around
	//torse false = finite space
	int current = 0; //0 or 1
	
	public Game(GetFile game){
		title = game.getTitle();
		description = game.getDescription();
		rows = game.getRows();
		columns = game.getColumns();
		
		current = 1;
		
		world = new boolean[rows][columns][2];
		//sets those in the pointlist to true
		for(Point point:game.pointList){
			this.world[point.r][point.c][current] = true;
		}
	}

    //Any live cell with fewer than two live neighbors dies, as if caused by under-population.
    //Any live cell with two or three live neighbors lives on to the next generation.
    //Any live cell with more than three live neighbors dies, as if by over-population.
    //Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
	public void genNext(){
		//noynaert snippet
		int next = (current==0)?1:0;
		//clear next before genning
		for(int r=0;r<rows;r++){
			for(int c=0; c<columns;c++){
				world[r][c][next]=false;
			}
		}
		
		if(torus==true){
			for(int r=0;r<rows;r++){
				for(int c=0; c<columns;c++){
					boolean alive = world[r][c][current]; //is target alive
					int count = 0;
					
					if(r!=0){						if(world[r-1][c][current]){count++;}	//N OK!
					}else{							if(world[rows-1][c][current]){count++;}}
					
					if(r!=0 && c!=columns-1){		if(world[r-1][c+1][current]){count++;}	//NE OK!
					}else if(r==0 && c!=columns-1){	if(world[rows-1][c+1][current]){count++;}
					}else if(r!=0 && c==columns-1){ if(world[r-1][0][current]){count++;}
					}else{							if(world[rows-1][0][current]){count++;}}
					
					if(c!=columns-1){				if(world[r][c+1][current]){count++;}	//E OK!
					}else{							if(world[r][0][current]){count++;}}
					
					if(r!=rows-1 && c!=columns-1){	if(world[r+1][c+1][current]){count++;}	//SE OK!
					}else if(r==rows-1 && c!=columns-1){	if(world[0][c+1][current]){count++;}
					}else if(r!=rows-1 && c==columns-1){	if(world[r+1][0][current]){count++;}
					}else{							if(world[0][0][current]){count++;}}
					
					if(r!=rows-1){					if(world[r+1][c][current]){count++;}	//S OK!
					}else{							if(world[0][c][current]){count++;}}
					
					if(r!=rows-1 && c!=0){			if(world[r+1][c-1][current]){count++;}	//SW OK!
					}else if(r==rows-1 && c!=0){	if(world[0][c-1][current]){count++;}
					}else if(r!=rows-1 && c==0){	if(world[r+1][columns-1][current]){count++;}
					}else{							if(world[0][columns-1][current]){count++;}}
					
					if(c!=0){						if(world[r][c-1][current]){count++;}	//W OK!
					}else{							if(world[r][columns-1][current]){count++;}}
					
					if(r!=0 && c!=0){				if(world[r-1][c-1][current]){count++;}	//NW OK
					}else if(r==0 && c!=0){			if(world[rows-1][c-1][current]){count++;}
					}else if(r!=0 && c==0){			if(world[r-1][columns-1][current]){count++;}
					}else{							if(world[rows-1][columns-1][current]){count++;}}
					
					if(alive && count<2){
						world[r][c][next] = false;
					}else if(alive && count==2 || count==3){
						world[r][c][next] = true;
					}else if(alive && count>3){
						world[r][c][next] = false;
					}else if(!alive && count==3){
						world[r][c][next] = true;
					}
				}
			}
		}else{
			for(int r=0;r<rows;r++){
				for(int c=0; c<columns;c++){
					//Cycle Directions N, NE, E, SE, S, SW, W, NW around target for neighbors
					boolean alive = world[r][c][current]; //is target alive
					int count = 0;	//neighbor count
					//scan neighbors around target
					if(r!=0){						if(world[r-1][c][current]){count++;}}
					if(r!=0 && c!=columns-1){		if(world[r-1][c+1][current]){count++;}}
					if(c!=columns-1){				if(world[r][c+1][current]){count++;}}
					if(r!=rows-1 && c!=columns-1){	if(world[r+1][c+1][current]){count++;}}
					if(r!=rows-1){					if(world[r+1][c][current]){count++;}}
					if(r!=rows-1 && c!=0){			if(world[r+1][c-1][current]){count++;}}
					if(c!=0){						if(world[r][c-1][current]){count++;}}
					if(r!=0 && c!=0){				if(world[r-1][c-1][current]){count++;}}
					
					if(alive && count<2){
						world[r][c][next] = false;
					}else if(alive && count==2 || count==3){
						world[r][c][next] = true;
					}else if(alive && count>3){
						world[r][c][next] = false;
					}else if(!alive && count==3){
						world[r][c][next] = true;
					}
				}
			}
		}
		this.current=next;
	}
	
	public void printWorld(){
		for(int i=0;i<rows; i++){
			for(int j=0;j<columns;j++){
				System.out.print( (world[i][j][current]) ? "+" : "-" );
			}
			System.out.println();
		}
	}
}