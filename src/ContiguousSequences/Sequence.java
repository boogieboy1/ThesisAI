package ContiguousSequences;

import java.util.ArrayList;

public class Sequence {
	
	ArrayList<Integer>sequence;
	int support;
	ArrayList<Integer> sequenceIDs; 
	
	public Sequence(){
		sequence = new ArrayList<Integer>();
		sequenceIDs = new ArrayList<Integer>();
		support = 0;
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
	
	
	public Integer getItem(int index){
		return sequence.get(index);
	}
	
	public void addSupport(){
		support++;
	}

}
