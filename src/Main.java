import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.drill.exec.ExecConstants;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import dataTransform.DataReader;
 
public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
 
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
        	logger.info("START2 !!");
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