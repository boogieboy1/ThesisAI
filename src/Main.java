import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;
 




import maxSequentialPatterns.AlgoBIDEPlus;

import org.apache.drill.exec.ExecConstants;
import org.apache.drill.jdbc.Driver;

import continousSequencePattern.ContinuousSequenceAlgo;
import dataTransform.DataReader;
import dataTransform.DataUtil;
import fpgrowth.AlgoFPGrowth;
import sequentialPatterns.PrefixSpanAlgorithm;
import frequentItemsets.Apriori;
import frequentItemsets.AprioriAlgorithm;
import fsMiner.FSMiner;
 
public class Main {
 
    public static void main(String[] args) {
        try {
            method();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
    public static void method() throws SQLException {
    	//ArrayList<ArrayList<String>> tagsPerUser = DataReader.readJSONTagsPerUser("D:\\AI\\Thesis\\CSV\\analytics-compiled.json");
    	//ArrayList<ArrayList<String>> tagsPerUser = DataReader.readJSONTagsPerUser("/home/nico/AI/Thesis/Development/Input/analytics-compiled.json");
    	
    	ArrayList<ArrayList<String>> eventsPerUSerPerSession = DataReader.readJSONEventsPerUserPerSession("/home/nico/AI/Thesis/Development/Input/analytics-compiled1.json");
    	//Test data
        //String sql = "select * from dfs.`D:\\AI\\Thesis\\CSV\\tags\\tags-A.csv`";
        //Connection con = null;
        //String jdbcUrl = "jdbc:drill:zk=local";
        
         
        try {
        	System.out.println("START !!");
            //con = new Driver().connect(jdbcUrl, null);
            //Statement stmt = con.createStatement();
            
            //ResultSet rs = stmt.executeQuery(sql);
            
            //ArrayList<String> result = DataUtil.resultSetToArrayList(rs);
            
            //FREQUENTPATTERNS APPRIORI
           //DataMiningUtil.getfrequentPatternsForTagsAllUSers(tagsPerUser);
           //DataMiningUtil.getfrequentPatternsForTagsGeneral(tagsPerUser);
          
            
            
          //FREQUENTPATTERNS FPGrowth
            /*
            String frequentPAtternOutputFileFPGrowth = "D://AI//Thesis//frequentPatternTagsFPGrowth.txt";
            AlgoFPGrowth algoFPGrowth = new AlgoFPGrowth();
            algoFPGrowth.runAlgorithm(rs, frequentPAtternOutputFileFPGrowth,0.02);
            */
            
            
            //FrequentSequences
            //PREFIXSPAN
            //For frequent non-contiguous sequences
        	
        	DataMiningUtil.getFrequentSequencesForEventsPerUser(eventsPerUSerPerSession);
        	
            //String nonContiguousFrequentOutputFile = "D://AI//Thesis//noncontiguousSequences.txt";
            //PrefixSpanAlgorithm prefixSpanAlgo = new PrefixSpanAlgorithm();            
            //prefixSpanAlgo.runAlgorithm(rs, 1, nonContiguousFrequentOutputFile);
            
            
            //SequenceDatabase seqDB = DataUtil.stringToIntDB(rs);
            
            //AlgoBIDEPlus bideAlgo = new AlgoBIDEPlus();
            //bideAlgo.runAlgorithm(rs, "D://AI//Thesis//test3.txt", 1);
            
            /*
            Apriori apriori = new Apriori();
            apriori.preprocessCSV(rs);
            */
            
            //ContinuousSequenceAlgo seqAlgo = new ContinuousSequenceAlgo();
            //seqAlgo.runAlgo(rs, 1);
            
            
            //FSMINER
            //Frequent contiguous sequence
            /*
            FSMiner fs = new FSMiner();
            fs.createFSTree(null, 0.2);
            fs.mineTree();*/
            
            
            
            System.out.println("DONE !!");
        } catch (Exception ex) {
            ex.printStackTrace();
            
        } 
    }
 
    public static Properties getDefaultProperties() {
        final Properties properties = new Properties();
        properties.setProperty("drillJDBCUnitTests", "true");
        properties.setProperty(ExecConstants.HTTP_ENABLE, "false");
        return properties;
    }
}