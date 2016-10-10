package fsMiner;

import java.util.ArrayList;

public class FSTree {
	
	ArrayList<Edge> root;
	
	public FSTree(){
		root = new ArrayList<Edge>();
	}
	
	public void addRoot(Edge edge){
		root.add(edge);
		
	}
	
	public Edge getRoot(int start, int end){
		for(Edge edge:root){
			if(edge.getStart() == start && edge.getEnd()==end){
				return edge;
			}
		}
		return null;
		
	}
	
	

}
