package fsMiner;

import java.util.ArrayList;

public class Edge {
	
	
	private int start;
	private int end;
	
	private int count;
	
	private Edge previous;
	private ArrayList<Edge> next;
	
	
	public Edge(int start, int end, Edge previous){
		this.start = start;
		
		this.end = end;
		
		this.previous = previous;
		
		count = 1;
		next = new ArrayList<Edge>();
	
		
	}
	
	public int getStart(){
		return start;
	}
	
	public int getEnd(){
		return end;
	}
	
	public void addNext(Edge edge){
		next.add(edge);
	}
	
	public void setPrevious(Edge edge){
		this.previous = edge;
	}
	
	public ArrayList<Edge> getNextEdge(){
		return next;
	}
	
	public void addCount(){
		count =count+1;
	}
	
	public void setCount(int count){
		this.count= count;
	}
	
	public int getCount(){
		return count;
	}
	
	public Edge getPreviousEdge(){
		return previous;
	}
	
	public void addCount(int countPlus){
		count+=countPlus;
	}
}
