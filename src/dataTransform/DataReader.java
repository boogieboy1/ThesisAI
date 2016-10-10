package dataTransform;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.drill.exec.ExecConstants;
import org.apache.drill.jdbc.Driver;

public class DataReader {
	
	/*
	 * Reads the tags used per user independent on the event on the full file given in the arguments.
	 */
	public static ArrayList<ArrayList<String>> readJSONTagsPerUser(String file) throws SQLException{
		System.out.println("Start reading Tags");
		
		 String sql = "select username from dfs.`"+ file + "`";
		 Properties properties = new Properties();
		 properties.setProperty(ExecConstants.HTTP_ENABLE, "false");
	     Connection con = null;
	     String jdbcUrl = "jdbc:drill:zk=local";
	     ArrayList<ArrayList<String>> tagsPerUser = new ArrayList<ArrayList<String>>();
	     con = new Driver().connect(jdbcUrl, properties);
	     try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<String> users = new ArrayList<String>();
			while (rs.next()) {
				if(rs.getString(1)!=null && !users.contains(rs.getString(1))){
					users.add(rs.getString(1));
				}
			}
			
			for(String user: users){
				ArrayList<String> tags = new ArrayList<String>();
				tags.add(user);
				sql = "select tagNames from dfs.`"+ file + "` where username='"+ user + "'";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					if(rs.getString(1).length()>2){
						tags.add(rs.getString(1));
					}
				}
				tagsPerUser.add(tags);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	finally {
        con.close();
    	}
	     System.out.println("Finish reading Tags");
	     return tagsPerUser;
       
        
		
	}
	
	
	/*
	 * Reads the events per user per session. One session is one day.
	 */
	public static ArrayList<ArrayList<String>> readJSONEventsPerUserPerSession(String file) throws SQLException{
		System.out.println("Start reading Events");
		
		 
		 Properties properties = new Properties();
		 properties.setProperty(ExecConstants.HTTP_ENABLE, "false");
	     Connection con = null;
	     String jdbcUrl = "jdbc:drill:zk=local";
	     ArrayList<ArrayList<String>> eventsPerUserPerSession = new ArrayList<ArrayList<String>>();
	     con = new Driver().connect(jdbcUrl, properties);
		
		 try {
		
			
	    
			//Get the list of days
			String sql = "select username from dfs.`"+ file + "`";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<String> users = new ArrayList<String>();
			while (rs.next()) {
				if(rs.getString(1)!=null && !users.contains(rs.getString(1))){
					users.add(rs.getString(1));
				}
			}
			
			//Get the list of sessions (days)
			/**
			sql= "select timestamp1 from dfs.`"+ file + "`";
			rs = stmt.executeQuery(sql);
			ArrayList<String> days = new ArrayList<String>();
			String day;
			while (rs.next()) {
				if(rs.getString(1)!=null){
				day = rs.getString(1).substring(0, 10);
					if(!days.contains(day)){
						days.add(day);
					}
				}
			}
			**/
			
			
			//Get the list of events per user per day
			for(String user: users){
				ArrayList<ArrayList<String>> featuresPerUserPerSession = new ArrayList<ArrayList<String>>();
				ArrayList<String> featuresPerSession = new ArrayList<String>();
				featuresPerSession.add(user);
				//for(String date: days){
					
					
					//events.add(date);
					//sql = "select feature from dfs.`"+ file + "` where username='"+ user + "' and substr(timestamp1, 1, 10)= '" +date+"'";
					sql = "select feature,substr(timestamp1, 1, 10) from dfs.`"+ file + "` where username='"+ user + "'";
					rs = stmt.executeQuery(sql);
					String featureList = null;
					while(rs.next()){
					
						featureList = rs.getString(2) + "," +rs.getString(1);
						
					}
					featuresPerSession.add(featureList);
					
				//}
				eventsPerUserPerSession.add(featuresPerSession);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	finally {
        con.close();
    	}
	     System.out.println("Finish reading Events");
	     return eventsPerUserPerSession;
       
        
		
	}
	
	

}
