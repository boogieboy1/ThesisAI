package fsMiner;

import java.util.ArrayList;



public class TableLink {
	
	int [] link = new int[2];
	
	int count;
	
	ArrayList<Edge> ListH = new ArrayList<Edge>();
	
	ArrayList<Integer> SID = new ArrayList<Integer>();
	
	String table;
	
	public TableLink(int start, int end){
		link[0] = start;
		link[1] = end;
		count = 1;
		
	}
	
	public void addCount(){
		count++;
	}

	public boolean isSameLink(int start, int end) {
		if(link[0] == start && link[1]==end){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public int getCount(){
		return count;
	}
	
	
	public void addSID(int SID){
		this.SID.add(SID);
	}
	
	public void addListH(Edge edge){
		this.ListH.add(edge);
		
	}
	
	public ArrayList<Edge> getListHPointers(){
		return ListH;
	}
	


}
