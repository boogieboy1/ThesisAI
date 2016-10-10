import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import fpgrowth.AlgoFPGrowth;
import frequentItemsets.AprioriAlgorithm;
import sequentialPatterns.PrefixSpanAlgorithm;

public class DataMiningUtil {
	
	public static void getfrequentPatternsForTagsAllUSers(ArrayList<ArrayList<String>> tagsAllUser) throws IOException, NumberFormatException, SQLException{
		for(ArrayList<String> tagsPerUser: tagsAllUser){
		  String frequentPAtternOutputFile = "/home/nico/AI/Thesis/Development/Output/Patterns/frequentPatternTags"+ tagsPerUser.get(0) +".txt";
          AprioriAlgorithm aprioriAlg = new AprioriAlgorithm();
          //AlgoFPGrowth fpGrowthAlgo = new AlgoFPGrowth();
          ArrayList <String> tags =   new ArrayList<String>(tagsPerUser.subList(1, tagsPerUser.size()));
          aprioriAlg.runAlgorithm(0.2, tags, frequentPAtternOutputFile);
          //fpGrowthAlgo.runAlgorithm(tags,frequentPAtternOutputFile, 0.2 );
		}
          
		
	}
	
	public static void getfrequentPatternsForTagsGeneral(ArrayList<ArrayList<String>> tagsAllUser) throws IOException, NumberFormatException, SQLException{
		
		ArrayList <String> tags = new ArrayList<String>();
		
		for(ArrayList<String> tagsPerUser: tagsAllUser){
		  
          tags.addAll(new ArrayList<String>(tagsPerUser.subList(1, tagsPerUser.size())));
         
          //fpGrowthAlgo.runAlgorithm(tags,frequentPAtternOutputFile, 0.2 );
		}
		
		String frequentPAtternOutputFile = "/home/nico/AI/Thesis/Development/Output/Patterns/frequentPatternTagsGeneral.txt";
        AprioriAlgorithm aprioriAlg = new AprioriAlgorithm();
        //AlgoFPGrowth fpGrowthAlgo = new AlgoFPGrowth();
        aprioriAlg.runAlgorithm(0.2, tags, frequentPAtternOutputFile);
		
	}
	
	public static void getFrequentSequencesForEventsPerUser(ArrayList<ArrayList<String>> featuresPerUserPerSession) throws IOException{
		
		for(ArrayList<String> featuresPerUser: featuresPerUserPerSession){
			  String frequentPAtternOutputFile = "/home/nico/AI/Thesis/Development/Output/Sequences/frequentPatternTags"+ featuresPerUser.get(0) +".txt";
			  PrefixSpanAlgorithm prefixSpanAlgo = new PrefixSpanAlgorithm();  
	          //AlgoFPGrowth fpGrowthAlgo = new AlgoFPGrowth();
	          ArrayList <String> events =   new ArrayList<String>(featuresPerUser.subList(1, featuresPerUser.size()));
	          prefixSpanAlgo.runAlgorithm(events, 0.2, frequentPAtternOutputFile);
	          //fpGrowthAlgo.runAlgorithm(tags,frequentPAtternOutputFile, 0.2 );
			}
	}

}
