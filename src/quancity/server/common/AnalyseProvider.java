package quancity.server.common;

import java.io.*;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import quancity.server.connection.JDBCConnectionPool;

public class AnalyseProvider {

	JDBCConnectionPool dbconn;
	static Connection conn;
	static Statement st;

	public AnalyseProvider() {
		System.out.println("create a connection to db");
		dbconn = new JDBCConnectionPool();
		//-------------------JDBCConnection to JDBCConnectionPool
		//conn = dbconn.setConnection();
		System.out.println("create connection successfully");
	}
	
	//get byID 
	public ApiResponse getInfoAnalyse(int id) {
		try {
			String sql =  "select (SELECT COUNT(*) AS CountStation from tblstation WHERE sIdCity ="+ id +") AS CountStation"
					+ ",(SELECT COUNT(*) AS CountSensor from tblsensorair) AS CountSensor"
					+ ",(SELECT COUNT(*) AS CountBollard from tblvehiculesensor) AS CountBollard"
					+ ",(SELECT distance AS CountDistance from tbldistance WHERE distance > 0) AS CountDistance"
					+ ",(SELECT COUNT(*) AS CountRatePollution from tblalert where isAlert = 1)*100/6 AS CountRatePollution"
					+ ",(SELECT COUNT(*) AS CountExceeding from tblalert where isAlert = 1)*100/6 AS CountExceeding"
					;
			st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sql);        	

			JSONArray cityAll = new JSONArray();
			if(rs.next() == false) {
				return new ApiResponse(false, cityAll, "Not Found");
			}else {
				do {
					JSONObject resItem = new JSONObject();                	

					resItem.put("CountStation", rs.getInt("CountStation"));
					System.out.println("(Result of JSONObject) CountStation : " + rs.getInt("CountStation"));
					resItem.put("CountSensor",  rs.getInt("CountSensor"));
					resItem.put("CountBollard",  rs.getInt("CountBollard"));
					resItem.put("CountDistance",  rs.getInt("CountDistance"));
					resItem.put("CountRatePollution",  rs.getInt("CountRatePollution"));
					resItem.put("CountExceeding",  rs.getInt("CountExceeding"));
					                   
					cityAll.put(resItem);                    
				}	while(rs.next());
				return new ApiResponse(true, cityAll, "Success");
			}     	
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return new ApiResponse(false, null, e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
				return null;
			}

		}
	}
}