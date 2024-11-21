package Hmwk06;

public class Stock implements Comparable<Stock> {
	//Vars
	private String symbol;
	private String name;
	private double openingPrice;
	private double lastPrice;
	private String dateTime;
	
	//Constructors
	public Stock(String symbol, String name, double openingPrice, double lastPrice, String dateTime){
			setSymbol(symbol);
			setName(name);
			setOpeningPrice(openingPrice);
			setDateTime(dateTime);
	}
	public Stock(){
		this("", "", 0.0, 0.0, "");
	}
	
	
	//Setters
	public void setSymbol(String symbol){
		this.symbol = symbol.trim();
	}
	public void setName(String name){
		this.name = name.trim().replace("	", "");
		//Yes that is a tab character
	}
	public void setOpeningPrice(double openingPrice){
		this.openingPrice = openingPrice;
	}
	public void setLastPrice(double lastPrice){
		this.lastPrice = lastPrice;
	}
	public void setDateTime(String dateTime){
		this.dateTime = dateTime.trim();
	}
	//Getters
	public String getSymbol(){
		return symbol;
	}
	public String getName(){
		return name;
	}
	public double getOpeningPrice(){
		return openingPrice;
	}
	public double getLastPrice(){
		return lastPrice;
	}
	public String getDateTime(){
		return dateTime;
	}
	//methods
	//Apparently writing override is giving me errors with Eclipse so I won't, but maybe has to do with the compiler and environment I'm in
	public int compareTo(Stock otherStock){
		int result = 0;
		result = this.symbol.compareTo(otherStock.symbol);
		return result;
	}
	@Override
	public String toString(){
		String result = String.format("%4s  %32s  %3.3d  %3.3d  %s", getSymbol(), getName(), getOpeningPrice(), getLastPrice(), getDateTime());
		return result;
	}
	
	public String toString(String symbol){
		String result = "";
		return result;
	}
}
