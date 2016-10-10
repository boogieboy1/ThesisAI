package fsMiner;

import java.util.ArrayList;

public class NonFrequentLinksTable {
	
ArrayList<TableLink> nonFrequentLinks = new ArrayList<TableLink>();
	
	public NonFrequentLinksTable(){
		
	}
	
	public void addLink(TableLink link){
		nonFrequentLinks.add(link);
	}
	
	public TableLink getLink(int start, int end){
		for(TableLink nonFrequentLink:nonFrequentLinks){
			if(nonFrequentLink.isSameLink( start, end)){
				return nonFrequentLink;
			}
		}
		return null;
	}
}
