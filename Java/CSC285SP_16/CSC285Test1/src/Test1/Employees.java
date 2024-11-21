package Test1;

abstract class Employees implements Comparable<Employees>{
	//vars
	protected String name;
	protected String ssn;
	protected String race;
	protected int SpecialEmployee;
	protected double salary;
	protected double stTax;
	protected double fedTax; 
	//constructors
	Employees(String name, String ssn, String race, int spec){
		setName(name);
		setSSN(ssn);
		setRace(race);
		setSpecial(spec);
	}
	Employees(){
		setName("John Doe");
		setSSN("000000000");
		setRace("NO");
		setSpecial(0);
	}
	//setters
	public void setName(String name){
		this.name=name;
	}
	public void setSSN(String ssn){
		this.ssn=ssn;
	}
	public void setRace(String race){
		this.race=race;
	}
	public void setSpecial(int Special){
		this.SpecialEmployee=Special;
	}
	//getters
	public String getName(){
		return this.name;
	}
	public String getSSN(){
		return this.ssn;
	}
	public String getRace(){
		return this.race;
	}
	public int getSpecial(){
		return this.SpecialEmployee;
	}
	public double getSalary(){
		return this.salary;
	}
	public double getStTax(){
		return this.stTax;
	}
	public double getFedTax(){
		return this.fedTax;
	}
	//methods
	abstract void print();
	abstract void calcSalary();
	abstract void calcStTax();
	abstract void calcFedTax();
	abstract void updateSpecialInfo(int i);
	@Override
	public int compareTo(Employees emp){
		return this.race.compareTo(emp.getRace());
	}
}
