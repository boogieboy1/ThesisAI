package ContiguousSequences;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class AprioriContiguousSequence {
	
	Hashtable <Integer, String> stringHash;
	
	double minSup;
	int absoluteMinSup;
	
	
	public AprioriContiguousSequence(){
		stringHash = new Hashtable<Integer, String>();
	}
	
	public void runAlgo(ResultSet rs, double minSup){
		
		this.minSup = minSup;
		ArrayList <ArrayList<Integer>> sequenceList = stringToIntDB(rs);
		absoluteMinSup = (int) Math.round(sequenceList.size()*minSup);
		
		getContinuousSequences(sequenceList);
		
	}
	
	private void getContinuousSequences(ArrayList<ArrayList<Integer>> sequenceList) {
		ArrayList<Integer> frequentItems = getItemSupport(sequenceList);
		
		ArrayList<Sequence> frequentPairSequences =  getFrequentContiguousSequencesForItems(frequentItems, sequenceList);
		
		if(frequentPairSequences.size()>2){
			getFrequentContiguousSequencesforSequences(frequentPairSequences,sequenceList);
		}
		
		
		
		
	}
	
	private void getFrequentContiguousSequencesforSequences(ArrayList<Sequence> frequentPairSequences,	ArrayList<ArrayList<Integer>> sequenceList) {
		int length = 3;
		boolean next = true;
		while(next){
			ArrayList<Sequence> candidates = getFrequentContiguousSequenceCandidatesForSequences(frequentPairSequences, length);
		}
		
	}

	private ArrayList<Sequence> getFrequentContiguousSequenceCandidatesForSequences(ArrayList<Sequence> frequentPairSequences, int length) {
		ArrayList<Sequence> frequentCandidates = new ArrayList<Sequence>();
		for(int i=0;i<frequentPairSequences.size();i++){
			for(int j=i+1;j<frequentPairSequences.size();j++){
				boolean possibleCandidiate = true;
				for(int x=0;x<length;x++){
					//if(frequentPairSequences.get(i).getItem(x+))
					
				}
			}
		}
			
		
		return null;
	}

	private void getContiguousSequences(ArrayList<Sequence> candidates, ArrayList<ArrayList<Integer>> sequenceList, int sequenceLength) {
		for(ArrayList<Integer> sequence:sequenceList ){
			for(int i=0;i<sequence.size()-1;i++){
				for(Sequence candidate:candidates){
					boolean equal = true;
					for(int j =0;j<sequenceLength;j++){
						
						if(sequence.get(i+j)!=candidate.getItem(i+j)){
							equal = false;
						}
					}
					if(equal==true){
						candidate.addSupport();
					}
				}
				
			}
		}
		
	}

	

	private ArrayList<Sequence> getFrequentContiguousSequencesForItems(ArrayList<Integer> frequentItems, ArrayList<ArrayList<Integer>> sequenceList) {
		
		
		ArrayList<Sequence> candidates = getFrequentContiguousSequenceCandidatesForItems(frequentItems);
		
		getContiguousSequences(candidates, sequenceList, 2);
		
		pruneCandidates(candidates);
		
		
		
		return candidates;
		


	}
	
	private void pruneCandidates(ArrayList<Sequence> candidates) {
		ArrayList<Sequence> frequentCandidates = new ArrayList<Sequence>();
		for(Sequence candidate:candidates){
			if(candidate.getSupport()>=absoluteMinSup){
				frequentCandidates.add(candidate);
				System.out.println(candidate);
			}
		}
		candidates=frequentCandidates;
	}

	private ArrayList<Sequence> getFrequentContiguousSequenceCandidatesForItems(ArrayList<Integer> frequentItems) {
		ArrayList<Sequence> candidates = new ArrayList<Sequence>();
		for(int i=0;i<frequentItems.size();i++){
			for(int j=i+1;j<frequentItems.size();j++){
				Sequence candidate = new Sequence();
				candidate.addItem(frequentItems.get(i));
				candidate.addItem(frequentItems.get(j));
				candidates.add(candidate);
			}
		}
		return candidates;
		
		
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
