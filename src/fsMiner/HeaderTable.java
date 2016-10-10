package fsMiner;

import java.util.ArrayList;

public class HeaderTable {
	
	ArrayList<TableLink> frequentLinks = new ArrayList<TableLink>();
	
	public HeaderTable(){
		
	}
	
	public void addLink(TableLink link){
		frequentLinks.add(link);
	}
	
	public TableLink getLink(int start, int end){
		for(TableLink frequentLink:frequentLinks){
			if(frequentLink.isSameLink( start, end)){
				return frequentLink;
			}
		}
		return null;
	}
	
	

}
