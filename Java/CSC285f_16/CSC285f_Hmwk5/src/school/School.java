package school;

public class School implements Comparable<School>{
	private String school;
	private String city;
	private String state;
	//CONSTRUCTORS
	public School(){
		school="I";
		city="AM";
		state="ERROR";
	}
	public School(String sc, String c, String st){
		school=sc;
		city=c;
		state=st;
	}
	//SETTERS
	public void setSchool(String sc){
		this.school=sc;
	}
	public void setCity(String c){
		this.city=c;
	}
	public void setState(String st){
		this.state=st;
	}
	public void setAll(String sc, String c, String st){
		this.school=sc;
		this.city=c;
		this.state=st;
	}
	//GETTERS
	public String getSchool(){
		return this.school;
	}
	public String getCity(){
		return this.city;
	}
	public String getState(){
		return this.state;
	}
	
	public boolean equals(School s){
		return (s.getCity().equals(this.getCity())&&s.getSchool().equals(this.getSchool())&&s.getState().equals(this.getState()));
	}
	public String hash(){
		return this.hash();
	}
	
	@Override
	public String toString(){
		return String.format("%60s %18s,%14s\n", getSchool(), getCity(), getState());
	}
	public int compareTo(School s){
		int i = this.school.compareTo(s.getSchool());
		if(i==0){
			i=this.state.compareTo(s.getState());
		}else if(i==0){
			i=this.state.compareTo(s.getCity());
		}
		return i;
	}
}
