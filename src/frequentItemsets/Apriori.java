package frequentItemsets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Apriori {
	
	Hashtable StringMapping;
	
	
	public Apriori(){
		
	}
	
	

	
	public ArrayList<String[]> preprocessCSV(ResultSet input){
		
		ArrayList<String[]> transactions =  new ArrayList<String[]> ();
		
		
		try {
			while(input.next()){
				String row = input.getString(1);
				String cleanRow = row.substring(2, row.length()-2);
				System.out.println(cleanRow);
				String [] transaction = cleanRow.split(",");
				System.out.println(transaction);
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			System.out.println("error preprocessing input");
			e.printStackTrace();
		}
		
		return null;
		
	}
	

}
