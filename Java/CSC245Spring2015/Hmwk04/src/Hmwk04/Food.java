package Hmwk04;

public class Food {
	private String food;
	private double calories;
	private double fat;
	private double carbs;
	private double protein;
	
	//Constructors
	public Food(String foodName, double calorieCount, double gramFat, double gramCarbs, double gramProtein){
		setName(foodName);
		setCalories(calorieCount);
		setFat(gramFat);
		setCarbs(gramCarbs);
		setProtein(gramProtein);
	}
	public Food(){
	}
	
	//setters
	public void setName(String foodName){
		food = foodName.replace('_', ' ');
	}
	public void setCalories(double calorieCount){
		calories =  Math.abs(calorieCount);
	}
	public void setFat(double gramFat){
		fat = Math.abs(gramFat);
	}
	public void setCarbs(double gramCarbs){
		carbs = Math.abs(gramCarbs);
	}
	public void setProtein(double gramProtein){
		protein = Math.abs(gramProtein);
	}
	public void setAll(String foodName, double calorieCount, double gramFat, double gramCarbs, double gramProtein){
		setName(foodName);
		setCalories(calorieCount);
		setFat(gramFat);
		setCarbs(gramCarbs);
		setProtein(gramProtein);
	}
	//getters
	public String getName(){
		return food.replace('_', ' ');
	}
	public double getCalories(){
		return calories;
	}
	public double getFat(){
		return fat;
	}
	public double getCarbs(){
		return carbs;
	}
	public double getProtein(){
		return protein;
	}
	@Override
	public String toString(){
		String result = String.format("---------------\nFood Name: %s\nCalories: %s\nGrams of Fat: %s\nGrams of Carbohydrates: %s\nGrams of Protein: %s\n---------------", getName(), getFat(), getCalories(), getCarbs(), getProtein());
		return result;
	}
}
