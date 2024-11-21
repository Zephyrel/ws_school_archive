
/*    A grant for small businesses is available for companies in northwest Missouri.
	The eligibility requirements are as follows: The company must be located in
	Andrew, Buchanan, or Nodaway county.  The user enters the federal FIPS code for
	Missouri counties.
	
	Eligible FIPS codes would be 003, 021, and 145.
	
	The company must have had at least two but no more than 15 employees as of July 1, 2011.
	
	The state legislature intended for the grants to be for companies with net incomes of between 50,000 and 500,000. 
	Unfortunately the grant program was passed as a rider on another bill, and the actual grant requirements are a bit different.
	As it works out legally, business with net incomes of 150,000 through 250,000 (inclusive) are not eligible.  After all the 
	legalities are worked out, eligible companies must have net incomes of at least 50,000 up to 150,000 (but not including 150,000)
	and over 250,000 up through 500,000.  
*/

/*
*	Russell Ferguson
*	2/27/2014
*	CSC 184 MWF
*	Ricky Brown
*/


import java.util.Scanner;

public class Hmwk07 {
    public static void main(String[] args) {
        int countyCode;   //Federal FIPS code for Missouri Counties
        int employees;     //Number of employees
        int netIncome;     //Last year's net Income as shown on Federal 1040.
        String countyName = "";
        Scanner keyboard = new Scanner(System.in);

        //Input data
        //First county number, than employees, than income
        //003, 021, 145 are eligible
        System.out.println("What is the county code?\n"
                + "\tAndrew County         003\n"
                + "\tAtchison County       005\n"
                + "\tBuchanan County       021\n"
                + "\tClinton County        019\n"
                + "\tDeKalb County         063\n"
                + "\tNodaway County        145\n"
                + "\tPlatte County         165\n"
                + "\t Other                000");
        countyCode = keyboard.nextInt();
        switch (countyCode) {
        	case 3:		countyName = "Andrew County"; break;
        	case 5:		countyName = "Atchison County"; break;
        	case 19:	countyName = "Clinton County"; break;
        	case 21:	countyName = "Buchanan County"; break;
        	case 63:	countyName = "DeKalb County"; break;
        	case 145:	countyName = "Nodaway County"; break;
        	case 165:	countyName = "Platte County"; break;
        	case 000:	countyName = "Nodaway County"; break;
            default:    countyName = "VOID"; break;
        }
        
        System.out.print("How many employees did you have as of July 1, 2011? \n");
        employees = keyboard.nextInt();
        
        System.out.print("What was your Net Income as shown on ");
        System.out.print("your 2011 Federal Income Tax (line 12)? \n");
        netIncome = keyboard.nextInt();
        
        //Echo Print the input data
        System.out.println("County & County Code: " + countyCode + " - " + countyName );
        System.out.println("# of Employees = " + employees);
        System.out.println("Your Net Income: " + netIncome);
        
        //Determine eligibility set strings for later printing
        String yesEligible = "Your business is qualified to recieve the grant.";
        String noEligible = "Your business is not qualified to recieve the grant.";
        
        //Set a true or false boolean statement for the eligibility under the net income rules
        boolean incomeQualification=false;
        if(netIncome >= 50000 && netIncome < 150000) {
		    incomeQualification = true;
        } else if(netIncome > 250000 && netIncome <= 500000) {
        	incomeQualification = true;
        } else incomeQualification = false;
        
        //Qualification under Employee rule
        boolean employeeQualification=false;
        if (employees >= 2 && employees <= 15) {
            employeeQualification = true;
        } else employeeQualification = false;
        
        //Qualification under County rule
        boolean countyQualification=false;
        switch (countyCode) {
		    case 3: countyQualification = true; break;
		    case 21: countyQualification = true; break;
		    case 145: countyQualification = true; break;
		    default: countyQualification = false; break;
        }
		
        //003, 021, 145 are eligible, all others are ineligble regardless.
        //Was gonna do a series of fancy nested switch-case statements, but that didn't work out.
        //So I'm vying for this.
        if(incomeQualification=true) {
            if(employeeQualification=true) {
                if(countyQualification=true) {
                    System.out.println(yesEligible);
                } else System.out.println(noEligible);
            } else System.out.println(noEligible);
        } else System.out.println(noEligible);
    }
}