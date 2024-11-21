package Hmwk06;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.URL;

public class Hmwk06 {
	
	public static void main(String[] args){
		String urlString = "http://REDACTED/stocks.txt";

		final int MAX_LIMIT = 100;
		Stock[] stockList = new Stock[MAX_LIMIT];
		int foundLimit = 0;
		
		//read url and store Stocks
		try {
			URL url = new URL(urlString);
			Scanner input = new Scanner(url.openStream());
			//Holy looping hell
				for(foundLimit = 0; (foundLimit < MAX_LIMIT) && input.hasNext(); foundLimit++){
					String symbol = "";
					String name = "";
					double openingPrice = 0.0;
					double closingPrice = 0.0;
					String dateTime = "";
					
					//Every line has a symbol, but not every line has a name, price, and date
					//So we must be careful about how we make and instantiate our object in the array of objects
					//Don't want nulls lying around do we?
					symbol = input.next();
					
					boolean endOfName = false;
					try{
						while(endOfName == false){
							//try seeing if the next input is price, otherwise the next bit is part of the name
							//If the name comes up nonexistent then we instantiate the object and move on
							try{
								openingPrice = input.nextDouble();
								endOfName = true;
							}catch(java.util.InputMismatchException e){
								name += " " + input.next();
								endOfName = false;
							}catch(java.util.NoSuchElementException e){
								stockList[foundLimit].setSymbol(symbol);
							}
							
							if(endOfName == true){
								closingPrice = input.nextDouble();
								dateTime = input.next()+"_"+input.next();
								stockList[foundLimit] = new Stock(symbol, name, openingPrice, closingPrice, dateTime);
							}
						}
					}catch(java.util.InputMismatchException e){
						endOfName = true;
					}catch(java.util.NoSuchElementException e){
						endOfName = true;
					}
				input.close();
			}
		}catch(java.net.MalformedURLException e){
			System.out.println("Malformed URL Exception")
			e.printStackTrace();
		}finally{
			System.out.println("Done with the try");
		}
		//sort stocks
		int i = 0;
		while(i < foundLimit){
			int j = (i+1);
			Stock tempStock = new Stock();
			tempStock = stockList[i];
			Stock currentLeast = stockList[i];
			int currentLeastIndex = -1;
			
			//scan for least valued stock ahead of i, relative to i
			while(j < foundLimit){
				if(currentLeast.compareTo(stockList[j])>0){
					//Other Stock is smaller, therefore its the current least
					currentLeast = stockList[j];
					currentLeastIndex = j;
					j++;
				}else if(currentLeast.compareTo(stockList[j])<0){
					//skip to next one down
					j++;
				}else if(currentLeast.compareTo(stockList[j])==0){
					//skip to next one down
					currentLeast = stockList[j];
					currentLeastIndex = j;
					j++;
				}
			}
			

			if(currentLeastIndex!=-1){
				//aka we actually found a Stock less than Stock at index i
				//swap!
				stockList[i] = currentLeast;
				stockList[currentLeastIndex] = tempStock;
			}
			
			i++;
		}
		//print stocks
		i=0;
		while(i < foundLimit){
			System.out.println(stockList[i].toString());
			i++;
		}
		//Search Stocks
	}
}
		
/*The URL http://REDACTED/stocks.txt contains stock data in a "tab delmited" text file.  The file will update periodically.  Your program may fail if the file is temporarily unreadable while the file is being updated.  Your program should take that into account in a catch{  }  clause.

There are two types of rows in the file.  If the row only contains one field, then the stock was not found in the Yahoo Finance database.  If it has 6 fields then it is assumed to be a valid entry.  Any other number of fields is an invalid entry.

If a line of data contains 6 fields, they are the following information:

    The stock symbol
    The name of the company (which may be shortened)
    The opening price of the stock
    The last sale price for the stock
    The date of the last sale of the stock
    The time of the last sale.

For this assignment you should have a Stock class that has the following fields:

    symbol
    name
    openingPrice
    lastPrice
    dateTime of the last sale.  You may create this by concatinating the strings.  Overachievers may create an actual date object.

The class should implement the Comparable<Stock> interface have the following methods

    Constructor that takes six fields corresponding tot the fields in the file.  The constructor should call the setters in the class to create each field.
    Constructor that takes just the symbol.  It fills in numeric fields with 0.00 and strings other than the symbol field with empty strings.  You must call the 6-field constructor from this constructor.
    A full set of setters (date/time would have a single setter, but would probably have 2 arguments)
    A full set of getters
    A toString() that returns one of two nicely formatted strings.  It should either return all 5 fields if the data is available, or just return the symbol if it is missing the other fields.
    A compareTo that works only on the symbol.

Your main() file should do the following tasks:

    Open the URL and read the data.
    Store each record in an array of type Stock.  There will be roughly 100 records, but there may be more or fewer.  Your program should print do 100 records if there are 100 or more records, or process fewer if there are less than 100.
    Sort the list.   Use the selection sort in the textbook.  The sort in the book (depending on your version) may use array.size, but this will not work for our program.  You will need to pass in "n" representing the number of items actually in the array.  Also, you will be comparing strings.
    Print the list of stock reports.  They should be in ascending order by symbol.
    Prompt the user and read stock symbols from the keyboard.  Use a manually written linear search to search the array for the selected symbol.  Print one of the following things:
        If the stock symbol is found, but the name of the stock is empty, print the symbol and "No data available"
        If the stock is found and has a complete record, print the difference between the opening and latest price using the "getter" methods.
        If the stock is not in the list print the symbol with a message that the stock was not found.
    If the user types "QUIT" (ignoring case) quit asking for symbols and exit the program.*/

