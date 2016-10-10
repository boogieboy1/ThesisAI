package continousSequencePattern;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import ca.pfv.spmf.input.sequence_database_list_integers.Sequence;
import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;

public class ContinuousSequenceAlgo {
	
	Hashtable <Integer, String> stringHash;
	
	ArrayList<Sequence> frequentContinuousSequences = new ArrayList<Sequence>();
	ArrayList<ArrayList<Integer>> nonFrequentContinuousSequences = new ArrayList<ArrayList<Integer>>();
	
	double minSup;
	int absoluteMinSup;
	
	public ContinuousSequenceAlgo(){
		stringHash = new Hashtable<Integer, String>();
		
	}
	
	public void runAlgo(ResultSet rs, double minSup){
		
		this.minSup = minSup;
		ArrayList <ArrayList<Integer>> sequenceList = stringToIntDB(rs);
		absoluteMinSup = (int) Math.round(sequenceList.size()*minSup);
		
		getContinuousSequences(sequenceList, absoluteMinSup);
		
		
		
		
	}
	
	private void getContinuousSequences(ArrayList<ArrayList<Integer>> sequenceList, int minSequenceSupport) {
		//Dependent on the minSup only a limited nb of sequences need to be checked for possible continuous subsequences
		
		
		ArrayList<Integer> frequentItems = getItemSupport(sequenceList);
		
		//FREQUENTSEQUENCE
		
		
		
		for(Integer item:frequentItems){
			
			System.out.println(item);
			
			getFrequentSequencesForItem(item,sequenceList);
			
		}
		
	}

	private void getFrequentSequencesForItem(Integer item, ArrayList<ArrayList<Integer>> sequenceList) {
		
		ArrayList<ArrayList<Integer>> projectedSequences = getProjectedSequences(item, sequenceList);
		ArrayList<Integer> frequentItems = getItemSupport(projectedSequences);
		for(Integer frequentItem:frequentItems){
			ArrayList<Integer>frequentItemSet = new ArrayList<Integer>();
			frequentItemSet.add(item);
			frequentItemSet.add(frequentItem);
			
			//FREQUENTSEQUENCE
			System.out.println(frequentItemSet);
			
			
			
			getFrequentSequencesForSequence(frequentItemSet, projectedSequences);
		}
		
		
	}


	private void getFrequentSequencesForSequence(ArrayList<Integer> frequentItemSet, ArrayList<ArrayList<Integer>> sequenceList) {
		
		ArrayList<ArrayList<Integer>> projectedSequences = getProjectedSequences(frequentItemSet.get(frequentItemSet.size()-1), sequenceList);
		ArrayList<Integer> frequentItems = getItemSupport(projectedSequences);
		for(Integer frequentItem:frequentItems){
			
			
			
			@SuppressWarnings("unchecked")
			ArrayList<Integer> newFrequentItemSet = (ArrayList<Integer>) frequentItemSet.clone();
			newFrequentItemSet.add(frequentItem);
			//FREQUENTSEQUENCE
			System.out.println(newFrequentItemSet);
			
			
			
			getFrequentSequencesForSequence(newFrequentItemSet, projectedSequences);
		}
		
		
	}

	private ArrayList<ArrayList<Integer>> getProjectedSequences(Integer item, ArrayList<ArrayList<Integer>> sequenceList) {
		
		ArrayList<ArrayList<Integer>> projectedSequences = new ArrayList<ArrayList<Integer>>();
		
		for(ArrayList<Integer> sequence: sequenceList){
			int index = sequence.indexOf(item);
			if(index>=0){
				ArrayList<Integer> projectedSequence = new ArrayList<Integer>(sequence.subList(index+1, sequence.size()));
				projectedSequences.add(projectedSequence);
			}
		}
		
		return projectedSequences;
	}

	private ArrayList<Integer> getItemSupport(ArrayList<ArrayList<Integer>> sequenceList) {
		ArrayList<Integer>frequentItems = new ArrayList<Integer>();
		ArrayList<Integer>nonFrequentItems = new ArrayList<Integer>();
		for(int i=0; i<sequenceList.size();i++){
			ArrayList<Integer> sequence = sequenceList.get(i);
			for(int item: sequence){
				if(!frequentItems.contains(item)&&!nonFrequentItems.contains(item)){
				boolean frequent = isFrequentItem(item, sequenceList);
					if(frequent){
						frequentItems.add(item);
					}
					else{
						nonFrequentItems.add(item);
					}
				}
			}
		}
		
		
		
		return frequentItems;
	}

	private boolean isFrequentItem(int item,ArrayList<ArrayList<Integer>> sequenceList ) {
		int occurences = 0;
		
		for(ArrayList<Integer> sequence: sequenceList){
			if(sequence.contains(item)){
				occurences++;
			}
		}
		if(occurences>=absoluteMinSup){
			return true;
		}
		else{
			return false;
		}
	}

	public ArrayList<ArrayList<Integer>> stringToIntDB(ResultSet input){
		ArrayList<ArrayList<Integer>> database = new ArrayList<ArrayList<Integer>>();
		Calendar date = null;
		
		ArrayList <Integer> seq = null;
		try {
			while (input.next()) { 
				// if the line is  a comment, is  empty or is a
				// kind of metadata
				String row = input.getString(1);
				String cleanRow = row.substring(2, row.length()-2);
				// split the line according to spaces§( 
				String[] entry = cleanRow.split(",");
				if(!entry[0].equals("Time")){
				
					Calendar entryDate = getDate(entry[0]);
					
					
					
					if(date == null||!date.equals(entryDate)){
						if(seq!=null){
							database.add(seq);
						}
						
						date = entryDate;
						
						seq = new ArrayList<Integer>();
					}
					// create an array of int to store the items in this transaction
					//String transaction[] = new int[lineSplited.length];
					
					
					int itemInt = getHashString(entry[1]);
					seq.add(itemInt);
					
					//seq.add(itemList);
				}
				
				
				
				
			}
			database.add(seq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return database;
		
	}
	
	private Calendar getDate(String entryDateTIme) {
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

	private int getHashString(String item) {
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
	
	private String intToString(int intItem) {
		
		// TODO Auto-generated method stub
		return stringHash.get(intItem);
		
	}

}
