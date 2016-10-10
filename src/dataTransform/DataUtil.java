package dataTransform;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import org.apache.drill.jdbc.Driver;

import ca.pfv.spmf.input.sequence_database_list_integers.Sequence;
import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;
import librec.data.SparseMatrix;

public class DataUtil {

	
	public static SequenceDatabase stringToIntDB(ResultSet input){
		SequenceDatabase database = new SequenceDatabase();
		Calendar date = null;
		int sequenceID = 0;
		Sequence seq = null;
		try {
			while (input.next()) { 
				// if the line is  a comment, is  empty or is a
				// kind of metadata
				String row = input.getString(1);
				String cleanRow = row.substring(2, row.length()-2);
				// split the line according to spaces�( 
				String[] entry = cleanRow.split(",");
				if(!entry[0].equals("Time")){
				
					Calendar entryDate = getDate(entry[0]);
					
					
					
					if(date == null||!date.equals(entryDate)){
						if(seq!=null){
							database.addSequence(seq);
						}
						
						date = entryDate;
						sequenceID++;
						seq = new Sequence(sequenceID);
					}
					// create an array of int to store the items in this transaction
					//String transaction[] = new int[lineSplited.length];
					
					List <Integer> itemList = new ArrayList<Integer>();;
					int itemInt = getHashString(entry[1]);
					itemList.add(itemInt);
					
					seq.addItemset(itemList);
				}
				
				
				
				
			}
			database.addSequence(seq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return database;
		
	}
	
	private static Calendar getDate(String entryDateTIme) {
		String [] dateAndTime = entryDateTIme.split(" ");
		String [] dateSplit = dateAndTime[0].split("-");
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]));
		calendar.set(Calendar.YEAR, Integer.parseInt(dateSplit[0]));
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[2]));
		
			
		
		
		
		// TODO Auto-generated method stub
		return calendar;
	}

	public static int getHashString(String item) {
		Hashtable <Integer, String> stringHash= new Hashtable<Integer, String>();
		if(stringHash.containsValue(item)){
			for(int i = 0; i<stringHash.size();i++){
				if(stringHash.get(i).equals(item)){
					return i;
				}
			}
			
			
		}
		else{
			stringHash.put(stringHash.size(), item);
			
		}
		return stringHash.size()-1;
		
	}
	
	public static String intToString(int intItem,Hashtable <Integer, String> stringHash ) {
		
		// TODO Auto-generated method stub
		return stringHash.get(intItem);
		
	}
	
	
	

	
	public static void createSparseTagMatrix(){
		SparseMatrix sparseTagMatrix = new SparseMatrix(0, 0, null);
		
	}
	
	public static ArrayList<String> resultSetToArrayList(ResultSet rs) throws SQLException{
		ArrayList<String> result = new ArrayList<String>();
		while(rs.next()){
			result.add(rs.getString(1));
		}
		return result;
	}
	
	public static ArrayList<ArrayList<String>> getFeaturesPerUser(ArrayList<ArrayList<String>> eventsPerUserPerSession){
		ArrayList<ArrayList<String>> featuresPerUser= new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> eventPerUserPerSession : eventsPerUserPerSession){
			
			
		}
		
		
		return featuresPerUser;
	}
	
}
