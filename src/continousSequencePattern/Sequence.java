package continousSequencePattern;

import java.util.ArrayList;

public class Sequence {
	
	ArrayList<Integer>sequence;
	int support;
	ArrayList<Integer> sequenceIDs; 
	
	public Sequence(){
		sequence = new ArrayList<Integer>();
		sequenceIDs = new ArrayList<Integer>();
	}
	
	public void addItem(int item){
		
		sequence.add(item);
	}
	
	public void increaseSupport(){
		support++;
	}
	
	public ArrayList<Integer> getSequence(){
		return sequence;
	}
	
	public int getSupport(){
		return support;
	}
	
	public void addSequenceID(int sequenceID){
		sequenceIDs.add(sequenceID);
		
	}
	
	public ArrayList<Integer> getSequenceIDs(){
		return sequenceIDs;
	}
	

}
