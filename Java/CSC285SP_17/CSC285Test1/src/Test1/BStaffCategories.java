package Test1;

class Badmin extends Boston{
	Badmin(String name, String ssn, String race, int spec){
		this.setName(name);
		this.setSSN(ssn);
		this.setRace(race);
		this.setSpecial(spec);
		calcSalary();
		calcStTax();
		calcFedTax();
	}
	@Override
	void print(){
		System.out.println("Admin "+this.getName());
		System.out.println("Resident Boston Hospital");
		if(this.getRace().equals("AA")){		System.out.println("Race African American");}
		else if(this.getRace().equals("CA")){	System.out.println("Race Caucasian");}
		else if(this.getRace().equals("AS")){	System.out.println("Race Asian");}
		else if(this.getRace().equals("HS")){	System.out.println("Race Hispanic");}
		else if(this.getRace().equals("OT")){	System.out.println("Race Other");}
		else{	System.out.println("ERROR");}
		System.out.println("SSN "+this.getSSN());
		switch(this.getSpecial()){
			case 1: System.out.println("Senior Executive");break;
			case 2:	System.out.println("Junior Executive");break;
			case 3:	System.out.println("Support");break;
			default:System.out.println("ERROR");break;
		}
		System.out.printf("Salary Base\t$%,7.2f%n", this.getSalary());
		System.out.printf("COLA (15%%)\t$%,7.2f%n", (this.getSalary()*0.15));
		switch(this.getSpecial()){
			case 1: System.out.printf("Bonus\t\t$%,7.2f%n", this.getSalary()*(0.20));break;
			case 2:	System.out.printf("Bonus\t\t$%,7.2f%n", this.getSalary()*(0.10));break;
			case 3:	System.out.printf("Bonus\t\t$%,7.2f%n", this.getSalary()*(0.00));break;
			default:System.out.printf("ERROR\t\t$%,7.2f%n", 0.00);break;
		}
		switch(this.getSpecial()){
			case 1: System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.20));break;
			case 2:	System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.10));break;
			case 3:	System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15));break;
			default:System.out.printf("ERROR\t\t$%,7.2f%n", 0.00);break;
		}
		System.out.println("Estimated Taxes");
		System.out.printf("Federal\t\t$%,7.2f%n", this.getFedTax());
		System.out.printf("Mass State\t$%,7.2f%n%n", this.getStTax());
	}

	@Override
	void calcSalary(){
		if(this.getSpecial()==1){
			this.salary=400000.00;
		}else if(this.getSpecial()==2){
			this.salary=175000.00;
		}else if(this.getSpecial()==3){
			this.salary=40000.00;
		}else{
			this.salary=0.00;
		}
	}

	@Override
	void calcStTax(){
		switch(this.getSpecial()){
			case 1: this.stTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.20))*(0.25);break;
			case 2:	this.stTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.10))*(0.25);break;
			case 3:	this.stTax=(this.getSalary()+(this.getSalary()*0.15))*(0.25);break;
			default:this.stTax=0.00;break;
		}
	}

	@Override
	void calcFedTax(){
		switch(this.getSpecial()){
			case 1: this.fedTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.20))*(0.25);break;
			case 2:	this.fedTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.10))*(0.25);break;
			case 3:	this.fedTax=(this.getSalary()+(this.getSalary()*0.15))*(0.25);break;
			default:this.fedTax=0.00;break;
		}
	}

	@Override
	void updateSpecialInfo(int i){
		this.setSpecial(i);
	}
}

class Bdoctor extends Boston{
	Bdoctor(String name, String ssn, String race, int spec){
		this.setName(name);
		this.setSSN(ssn);
		this.setRace(race);
		this.setSpecial(spec);
		calcSalary();
		calcStTax();
		calcFedTax();
	}
	@Override
	void print() {
		System.out.println("Dr. "+this.getName());
		System.out.println("Resident Boston Hospital");
		if(this.getRace().equals("AA")){		System.out.println("Race African American");}
		else if(this.getRace().equals("CA")){	System.out.println("Race Caucasian");}
		else if(this.getRace().equals("AS")){	System.out.println("Race Asian");}
		else if(this.getRace().equals("HS")){	System.out.println("Race Hispanic");}
		else if(this.getRace().equals("OT")){	System.out.println("Race Other");}
		else{	System.out.println("ERROR");}
		System.out.println("SSN "+this.getSSN());
		System.out.println("Patient Count "+this.getSpecial());
		System.out.printf("Salary Base\t$%,7.2f%n", this.getSalary());
		System.out.printf("COLA (15%%)\t$%,7.2f%n", (this.getSalary()*0.15));
		System.out.printf("Patient Adj\t$%,7.2f%n", this.getSalary()*(0.0025*this.getSpecial()));
		System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(this.getSpecial()*0.0025));
		System.out.println("Estimated Taxes");
		System.out.printf("Federal\t\t$%,7.2f%n", this.getFedTax());
		System.out.printf("Mass State\t$%,7.2f%n%n", this.getStTax());
	}

	@Override
	void calcSalary() {
		this.salary=155000.00;
	}

	@Override
	void calcStTax() {
		this.stTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(this.getSpecial()*0.0025))*0.05;
	}

	@Override
	void calcFedTax() {
		this.fedTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(this.getSpecial()*0.0025))*0.25;
	}

	@Override
	void updateSpecialInfo(int i) {
		this.setSpecial(i);
	}
}

class Bnurse extends Boston{
	Bnurse(String name, String ssn, String race, int spec){
		this.setName(name);
		this.setSSN(ssn);
		this.setRace(race);
		this.setSpecial(spec);
		calcSalary();
		calcStTax();
		calcFedTax();
}
	@Override
	void print() {
		System.out.println("Nurse "+this.getName());
		System.out.println("Resident Boston Hospital");
		if(this.getRace().equals("AA")){		System.out.println("Race African American");}
		else if(this.getRace().equals("CA")){	System.out.println("Race Caucasian");}
		else if(this.getRace().equals("AS")){	System.out.println("Race Asian");}
		else if(this.getRace().equals("HS")){	System.out.println("Race Hispanic");}
		else if(this.getRace().equals("OT")){	System.out.println("Race Other");}
		else{	System.out.println("ERROR");}
		System.out.println("SSN "+this.getSSN());
		switch(this.getSpecial()){
			case 1: System.out.println("Clinic Nurse");break;
			case 2:	System.out.println("Hospital Floor Nurse");break;
			case 3:	System.out.println("Hospital Administrative Nurse");break;
			default:System.out.println("ERROR");break;
		}
		System.out.printf("Salary Base\t$%,7.2f%n", this.getSalary());
		System.out.printf("COLA (15%%)\t$%,7.2f%n", (this.getSalary()*0.15));
		switch(this.getSpecial()){
			case 1: System.out.printf("Bonus\t\t$%,7.2f%n", this.getSalary()*(0.10));break;
			case 2:	System.out.printf("Bonus\t\t$%,7.2f%n", this.getSalary()*(0.15));break;
			case 3:	System.out.printf("Bonus\t\t$%,7.2f%n", this.getSalary()*(0.20));break;
			default:System.out.printf("ERROR\t\t$%,7.2f%n", 0.00);break;
		}
		switch(this.getSpecial()){
			case 1: System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.10));break;
			case 2:	System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.15));break;
			case 3:	System.out.printf("Total\t\t$%,7.2f%n", this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.20));break;
			default:System.out.printf("ERROR\t\t$%,7.2f%n", 0.00);break;
		}
		System.out.println("Estimated Taxes");
		System.out.printf("Federal\t\t$%,7.2f%n", this.getFedTax());
		System.out.printf("Mass State\t$%,7.2f%n%n", this.getStTax());
	}

	@Override
	void calcSalary() {
		this.salary=65000.00;
	}

	@Override
	void calcStTax() {
		switch(this.getSpecial()){
			case 1: this.stTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.10))*(0.05);break;
			case 2:	this.stTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.15))*(0.05);break;
			case 3:	this.stTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.20))*(0.05);break;
			default:this.stTax=0.00;break;
		}
	}

	@Override
	void calcFedTax() {
		switch(this.getSpecial()){
			case 1: this.fedTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.10))*(0.25);break;
			case 2:	this.fedTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.15))*(0.25);break;
			case 3:	this.fedTax=(this.getSalary()+(this.getSalary()*0.15)+this.getSalary()*(0.20))*(0.25);break;
			default:this.fedTax=0.00;break;
		}
	}

	@Override
	void updateSpecialInfo(int i) {
		this.setSpecial(i);
	}
}

class Bsupport extends Boston{
	Bsupport(String name, String ssn, String race, int spec){
		this.setName(name);
		this.setSSN(ssn);
		this.setRace(race);
		this.setSpecial(spec);
		calcSalary();
		calcStTax();
		calcFedTax();
}
	@Override
	void print() {
		System.out.println("Support "+this.getName());
		System.out.println("Resident Boston Hospital");
		if(this.getRace().equals("AA")){		System.out.println("Race African American");}
		else if(this.getRace().equals("CA")){	System.out.println("Race Caucasian");}
		else if(this.getRace().equals("AS")){	System.out.println("Race Asian");}
		else if(this.getRace().equals("HS")){	System.out.println("Race Hispanic");}
		else if(this.getRace().equals("OT")){	System.out.println("Race Other");}
		System.out.println("SSN "+this.getSSN());
		System.out.printf("Salary Base\t$%,7.2f", this.getSalary());
		System.out.printf("COLA (15%%)\t$%,7.2f", (this.getSalary()*0.15));
		System.out.printf("Total\t$%,7.2f", this.getSalary()+(this.getSalary()*0.15));
		System.out.println("Estimated Taxes");
		System.out.printf("Federal\t\t$%,7.2f", this.getFedTax());
		System.out.printf("Mass State\t$%,7.2f%n%n", this.getStTax());
	}

	@Override
	void calcSalary() {
		switch(this.getSpecial()){
			case 1: this.salary=45000.00;
			case 2:	this.salary=35000.00;
			default:this.salary=0.00;
		}
	}

	@Override
	void calcStTax() {
		this.stTax=(this.getSalary()+(this.getSalary()*0.15))*0.05;
	}

	@Override
	void calcFedTax() {
		this.fedTax=(this.getSalary()+(this.getSalary()*0.15))*0.25;
	}

	@Override
	void updateSpecialInfo(int i) {
		this.setSpecial(i);
	}
}