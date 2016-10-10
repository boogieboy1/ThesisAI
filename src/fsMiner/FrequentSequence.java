package fsMiner;

import java.util.ArrayList;
import java.util.Collections;

public class FrequentSequence {
	
	private ArrayList<Integer>sequence;
	int support;
	
	public FrequentSequence(ArrayList<Integer> sequence, int count){
		this.sequence = sequence;
		Collections.reverse(this.sequence);
		this.support=count;
	}
	
	

}
