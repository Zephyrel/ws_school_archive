package hmwk05;

public class Pizza implements Comparable<Pizza>{
	private int inches;
	private boolean thinCrust;
	private String toppings;
	
	//Constructors
	public Pizza(int inches, String thinCrust, String toppings){
		setInches(inches);
		setCrust(thinCrust);
		setToppings(toppings);
	}
	public Pizza(){
		this(9, "Thin", "Cheese");
	}
	//Setters
	public void setInches(int inches){
		this.inches = inches;
	}
	public void setCrust(String thinCrust){
		this.thinCrust = ("thin".equalsIgnoreCase(thinCrust));
	}
	public void setToppings(String toppings){
		this.toppings = toppings.trim();
	}
	//public void setAll(int inches, String thinCrust, String toppings){
		//setInches(inches);
		//setCrust(thinCrust);
		//setToppings(toppings);
	//}
	//Getters
	public int getInches(){
		return inches;
	}
	public boolean getCrust(){
		return thinCrust;
	}
	public String getCrustString(){
		return (getCrust()) ? "Thin" : "Original";
	}
	public String getToppings(){
		return toppings;
	}
	
	//Core Methods
	public int compareTo(Pizza otherPizza){
		int comparison = 0;
		//Order of priority comparison: Size, Crust, Toppings
		//Continue going to next over priority if comparison = 0 upon comparison for current priority
		//returns positive if "this" is greater than "other"
		//negative if "other" is greater than "this"
		comparison = this.inches - otherPizza.inches;
		if(comparison == 0){
			if(this.thinCrust && otherPizza.thinCrust){
				comparison = 0;
			}else if(this.thinCrust == true && otherPizza.thinCrust == false){
				comparison = -1;
			}else if(this.thinCrust == false && otherPizza.thinCrust == true)
				//other Pizza has thin crust while this one does not, therefore it goes first
				comparison = 1;
		}
		if(comparison == 0){
			comparison = this.toppings.compareTo(otherPizza.toppings);
		}
		return comparison;
	}
	
	@Override
	public String toString(){
		String string = String.format("----------------\nInches: %s\n Thin Crust?: %s\n Toppings: %s", getInches(), getCrustString(), getToppings());
		return string;
	}
	
}
