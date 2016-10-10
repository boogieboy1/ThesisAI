package fsMiner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class FSMiner {
	
	//test
	double minSup;
	int absoluteMinSup;
	
	HeaderTable HT;
	NonFrequentLinksTable NFLT;
	private Hashtable<Integer, String> stringHash;
	FSTree fsTree;
	
	ArrayList<FrequentSequence> condFSTreeSequences = new ArrayList<FrequentSequence>();
	
	
	public FSMiner(){
		HT = new HeaderTable();
		NFLT = new NonFrequentLinksTable();
		stringHash = new Hashtable<Integer, String>();
		fsTree = new FSTree();
	}
	
	//////////////////////////////////////////////////////////////
	///FS MINE
	/////////////////////////////////////////////////////////////
	
	public ArrayList<FrequentSequence> mineTree(){
		ArrayList<ArrayList<Integer>> freqSequences = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0;i<HT.frequentLinks.size();i++){
			
			TableLink link = HT.frequentLinks.get(i);
			ArrayList<Edge> edges = link.getListHPointers();
			
			Edge rootCondFSTree = getEdgeFrequentSequence(edges);
			
			getFreqSeqiencesFromCondTree(rootCondFSTree);
			
			
			
			
		}
		
		return condFSTreeSequences;
		
	}
	
	private void getFreqSeqiencesFromCondTree(Edge rootCondFSTree) {
		
		ArrayList<Integer> freqSeq = new ArrayList<Integer>();
		freqSeq.add(rootCondFSTree.getStart());
		freqSeq.add(rootCondFSTree.getEnd());
		boolean next = false;
		if(rootCondFSTree.getNextEdge().size()!=0){
			ArrayList<Edge> nextEdges = rootCondFSTree.getNextEdge();
			for(int i=0; i<nextEdges.size();i++){
					if(nextEdges.get(i).getCount()>=absoluteMinSup){
						next=true;
						getFreqSeqForEdge(freqSeq, nextEdges.get(i));
					}
					
				
				
			}
			
			
			
		}
		if(next==false){
			TableLink link = HT.getLink(rootCondFSTree.getEnd(), rootCondFSTree.getStart());
			FrequentSequence seq = new FrequentSequence(freqSeq, link.getCount());
			condFSTreeSequences.add(seq);
		}
		
	}

	private void  getFreqSeqForEdge(ArrayList<Integer> freqSeq, Edge edge) {
		freqSeq.add(edge.getEnd());
		boolean next = false;
		if(edge.getNextEdge().size()!=0){
			ArrayList<Edge> nextEdges = edge.getNextEdge();
			for(int i=0; i<nextEdges.size();i++){
					if(nextEdges.get(i).getCount()>=absoluteMinSup){
						//freqSeq.add(nextEdges.get(i).getEnd());
						next = true;
						getFreqSeqForEdge(new ArrayList(freqSeq), nextEdges.get(i));
					}
					
					
				
				
			}
			
			
		}
		if(next == false){
			FrequentSequence seq = new FrequentSequence(freqSeq, edge.getCount());
			condFSTreeSequences.add(seq);
		}
		
	}

	private Edge getEdgeFrequentSequence(ArrayList<Edge> edgesLinkTable){
		Edge root= new Edge(edgesLinkTable.get(0).getEnd(), edgesLinkTable.get(0).getStart(), null);
		root.setCount(0);
		
		for(Edge edgeLink: edgesLinkTable){
			int count = edgeLink.getCount();
			Edge currentEdge = root;
			while(edgeLink.getPreviousEdge()!= null){
				Edge nextEdge = new Edge(edgeLink.getPreviousEdge().getEnd(), edgeLink.getPreviousEdge().getStart(), null);
				nextEdge.setCount(count);
				ArrayList<Edge> nextTreeEdges = currentEdge.getNextEdge();
				if(nextTreeEdges.size()!=0){
					Edge nextFoundEdge = containsEdge(nextTreeEdges, nextEdge);
					if(nextFoundEdge==null){
						currentEdge.addNext(nextEdge);
						while(nextEdge.getPreviousEdge()!=null){
							Edge nextNewEdge = new Edge(edgeLink.getPreviousEdge().getEnd(), edgeLink.getPreviousEdge().getStart(), currentEdge);
							nextNewEdge.setCount(count);
							currentEdge.addNext(nextNewEdge);
							currentEdge=nextNewEdge;
							edgeLink = edgeLink.getPreviousEdge();
						}
					}
					else{
						nextFoundEdge.addCount(nextEdge.getCount());
						nextEdge.setPrevious(currentEdge);
						currentEdge=nextFoundEdge;
					}
					edgeLink = edgeLink.getPreviousEdge();
				}
				else{
					while(edgeLink.getPreviousEdge()!= null){
						Edge nextNewEdge = new Edge(edgeLink.getPreviousEdge().getEnd(), edgeLink.getPreviousEdge().getStart(), currentEdge);
						nextNewEdge.setCount(count);
						currentEdge.addNext(nextNewEdge);
						currentEdge=nextNewEdge;
						edgeLink = edgeLink.getPreviousEdge();
					}
				}
			}
		}
		return root;
		
	}
	
	private Edge containsEdge(ArrayList<Edge> nextEdges, Edge nextEdge) {
		for(Edge edge:nextEdges){
			if(edge.getStart()==nextEdge.getStart()&&edge.getEnd()==nextEdge.getEnd()){
				return edge;
			}
		}
		return null;
		
	}
	
	///search Cond FS tree
	/*public void getFreqSeqsFromCondFsTree(Edge root, ArrayList<Integer> freqSet){
		if(root.getNextEdge()==null){
			freqSet.add(root.getEnd());
			freqSet.add(root.getCount());
			condFSTreeSequences.add(freqSet);
		}
		else{
			ArrayList<Edge> nextEdges = root.getNextEdge();
			for(Edge nextEdge:nextEdges){
				if(nextEdge.getCount()>=minSup){
					freqSet.add(root.getStart());
					getFreqSeqsFromCondFsTree(nextEdge, freqSet);
				}
				else{
					
				}
			}
		}
	}*/

	//////////////////////////////////////////////////////////////
	//CREATE FS TREE
	///////////////////////////////////////////////////////////////
	
	
	//Sets the data in the correct format
	public void createFSTree(ResultSet rs, double minSup){
		
		this.minSup = minSup;
		//ArrayList <ArrayList<Integer>> sequenceList = stringToIntDB(rs);
		
		//////////////
		//TEST DATA
		//////////////
		ArrayList <ArrayList<Integer>> sequenceListTEST = new ArrayList <ArrayList<Integer>>();
		ArrayList<Integer> one = new ArrayList<Integer>();
		one.add(4);
		one.add(7);
		one.add(9);
		sequenceListTEST.add(one);
		
		ArrayList<Integer> two = new ArrayList<Integer>();
		two.add(4);
		two.add(7);
		sequenceListTEST.add(two);
		
		ArrayList<Integer> three = new ArrayList<Integer>();
		three.add(3);
		three.add(4);
		three.add(5);
		three.add(8);
		three.add(9);
		sequenceListTEST.add(three);
		
		ArrayList<Integer> four = new ArrayList<Integer>();
		four.add(3);
		four.add(4);
		four.add(5);
		sequenceListTEST.add(four);
		
		ArrayList<Integer> five = new ArrayList<Integer>();
		five.add(3);
		five.add(2);
		five.add(3);
		five.add(4);
		five.add(7);
		sequenceListTEST.add(five);
		
		ArrayList<Integer> six = new ArrayList<Integer>();
		six.add(3);
		six.add(2);
		sequenceListTEST.add(six);
		
		ArrayList<Integer> seven = new ArrayList<Integer>();
		seven.add(1);
		seven.add(2);
		seven.add(3);
		seven.add(4);
		seven.add(7);
		seven.add(9);
		sequenceListTEST.add(seven);
		
		ArrayList<Integer> eight = new ArrayList<Integer>();
		eight.add(1);
		eight.add(2);
		eight.add(3);
		eight.add(4);
		sequenceListTEST.add(eight);
		
		ArrayList<Integer> nine = new ArrayList<Integer>();
		nine.add(2);
		nine.add(4);
		nine.add(5);
		nine.add(8);
		nine.add(9);
		sequenceListTEST.add(nine);
		
		ArrayList<Integer> ten = new ArrayList<Integer>();
		ten.add(2);
		ten.add(4);
		ten.add(5);
		ten.add(8);
		sequenceListTEST.add(ten);
		
		ArrayList<Integer> eleven = new ArrayList<Integer>();
		eleven.add(3);
		eleven.add(4);
		eleven.add(5);
		eleven.add(2);
		eleven.add(6);
		eleven.add(1);
		eleven.add(2);
		eleven.add(3);
		sequenceListTEST.add(eleven);
		
		ArrayList<Integer> twelve = new ArrayList<Integer>();
		twelve.add(3);
		twelve.add(4);
		twelve.add(5);
		twelve.add(6);
		twelve.add(1);
		twelve.add(2);
		twelve.add(3);
		sequenceListTEST.add(twelve);
		
		ArrayList<Integer> thirteen = new ArrayList<Integer>();
		thirteen.add(1);
		thirteen.add(9);
		thirteen.add(3);
		sequenceListTEST.add(thirteen);
		
		ArrayList<Integer> fourteen = new ArrayList<Integer>();
		fourteen.add(4);
		fourteen.add(9);
		fourteen.add(5);
		sequenceListTEST.add(fourteen);
		
		ArrayList<Integer> fifteen = new ArrayList<Integer>();
		fifteen.add(9);
		fifteen.add(7);
		fifteen.add(4);
		fifteen.add(2);
		fifteen.add(1);
		sequenceListTEST.add(fifteen);
		
		///////////////
		///END TEST DATA
		///////////////
		absoluteMinSup = (int) Math.round(sequenceListTEST.size()*minSup);
		
		classifyLinks(sequenceListTEST);
		
		for(ArrayList<Integer> sequence:sequenceListTEST){
			insertTree(sequence);
		}
		
		
	}
	
	
	// Insertes the given sequence into the FSTree
	private void insertTree(ArrayList<Integer> sequence) {
		ArrayList<ArrayList<Integer>> sequenceSplit = splitSequence(sequence);
		for(ArrayList<Integer> splittedSequence:sequenceSplit){
			addSequenceToTree(splittedSequence);
		}
		
	}
	
	//INserts the sequence with all edges in the HT table into the FSTree
	private void addSequenceToTree(ArrayList<Integer> splittedSequence) {
		Edge edge = fsTree.getRoot(splittedSequence.get(0), splittedSequence.get(1));
		if(edge==null){
			createNewBranchFromRoot(splittedSequence);
		}
		else{
			addSplittedSequenceToExistingBranche(edge, splittedSequence);
		}
		
	}
	
	//A sequence with all edges in the HT table is added to the tree where at least the initial edge already exists in the tree
	private void addSplittedSequenceToExistingBranche(Edge edge, ArrayList<Integer> splittedSequence) {
		edge.addCount();
		TableLink link = HT.getLink(edge.getStart(), edge.getEnd());
		//link.addListH(edge);
		ArrayList<Edge> nextEdges = edge.getNextEdge();
		boolean found = false;
		if(nextEdges.size()!=0){
			for(Edge nextEdge:nextEdges){
				if(splittedSequence.size()>2 && nextEdge.getStart()==splittedSequence.get(1)&&nextEdge.getEnd()==splittedSequence.get(2)){
					
					addSplittedSequenceToExistingBranche(nextEdge, new ArrayList<Integer>(splittedSequence.subList(1, splittedSequence.size())));
					found = true;
					break;
				}
			}
		}
		if(found==false){
			addToBranch(edge, splittedSequence);
		}
		
		
		
	}

	private void addToBranch(Edge edge, ArrayList<Integer> splittedSequence) {
		while(splittedSequence.size()>2){
			Edge nextEdge = new Edge(splittedSequence.get(1), splittedSequence.get(2),edge);
			edge.addNext(nextEdge);
			TableLink link = HT.getLink(nextEdge.getStart(), nextEdge.getEnd());
			link.addListH(nextEdge);
		}
		
	}

	private void createNewBranchFromRoot(ArrayList<Integer> splittedSequence) {
		Edge edge= null;
		for(int i=0;i<splittedSequence.size()-1;i++){
			int startItem = splittedSequence.get(i);
			int endItem = splittedSequence.get(i+1);
			if(i==0){
				edge = new Edge(startItem, endItem, null);
				fsTree.addRoot(edge);
				TableLink link = HT.getLink(startItem, endItem);
				link.addListH(edge);
			}
			else{
				Edge previous = edge;
				edge = new Edge(startItem, endItem, previous);
				previous.addNext(edge);
				TableLink link = HT.getLink(startItem, endItem);
				link.addListH(edge);
				
			}
			
			
			
		}
		
	}

	private ArrayList<ArrayList<Integer>> splitSequence(ArrayList<Integer> sequence) {
		 ArrayList<ArrayList<Integer>> frequentSequences = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<sequence.size()-1;i++){
			if(HT.getLink(sequence.get(i), sequence.get(i+1))==null){
				if(i==0 && sequence.size()>2){
					sequence.remove(0);
					i=-1;
				}
				else if(sequence.size()==2){
					sequence.remove(i);
					sequence.remove(i);
				}
				else{
					ArrayList<Integer> subSequence = new ArrayList<Integer>(sequence.subList(0, i+1));
					frequentSequences.add(subSequence);
					sequence = new ArrayList<Integer>(sequence.subList(i+1, sequence.size()));
					i=-1;
				}
			}
		}
		if(sequence.size()>1){
			frequentSequences.add(sequence);
		}
		
		return frequentSequences;
	}

	private void classifyLinks(ArrayList<ArrayList<Integer>> sequenceList) {
		ArrayList <TableLink> links = getLinks( sequenceList);
		classify(links);
		
	}
	
	private void classify(ArrayList<TableLink> links) {
		for(TableLink link:links){
			if(link.getCount()>= absoluteMinSup){ 
				HT.addLink(link);
			}
			else{
				NFLT.addLink(link);
			}
		}
		
	}

	public ArrayList <TableLink> getLinks(ArrayList<ArrayList<Integer>> sequenceList){
		ArrayList <TableLink> links = new ArrayList<TableLink>();
		for(ArrayList<Integer> sequence:sequenceList){
			for(int i=0;i<sequence.size()-1;i++){
				int startItem = sequence.get(i);
				int endItem = sequence.get(i+1);
				TableLink searchedLink = getLink(links, startItem, endItem);
				if(searchedLink == null){
					TableLink link = new TableLink(startItem, endItem);
					links.add(link);
					link.addSID(sequenceList.indexOf(sequence));
					
				}
				else{
					searchedLink.addCount();
				}
				
			}
		}
		return links;
		
		
	}

	private TableLink getLink(ArrayList<TableLink> links, int startItem,int endItem) {
		for(TableLink link:links){
			if(link.isSameLink(startItem, endItem)){
				return link;
			}
		}
		return null;
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
