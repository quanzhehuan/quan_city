package quancity.server.common;

import java.io.*;
import java.awt.print.Printable;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import quancity.dto.JDBCConnection;
import quancity.model.AlertModel;
import quancity.model.ApiResponse;
import quancity.model.SensorQualityAirModel;

public class AnalyseProvider {

	JDBCConnection dbconn;
	static Connection conn;
	static Statement st;

	public AnalyseProvider() {
		System.out.println("create a connection to db");
		dbconn = new JDBCConnection();
		conn = dbconn.setConnection();
		System.out.println("successfully created a connection");
	}
	
	//get byID 
	public static ApiResponse getTodayAnalyseInfo(int id) {
		try {
			String sql =  "select (SELECT COUNT(*) AS CountStation from tblstation WHERE sIdCity ="+ id +") AS CountStation"
					+ ",(SELECT COUNT(*) AS CountSensor from tblsensorair) AS CountSensor"
					+ ",(SELECT COUNT(*) AS CountBollard from tblvehiculesensor) AS CountBollard"
					+ ",(SELECT distance AS CountDistance from tbldistance) AS CountDistance"
					+ ",(SELECT COUNT(*) AS CountRatePollution from tblalert where isAlert = 1) AS CountRatePollution"
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
	
	
	public static ApiResponse getAnalyseInfoByDate(int cID, String date) {
		try {
			String sql =  "SELECT (SELECT date FROM AnalyseInfo WHERE date = '" + date + "') AS date"
					+ ",(SELECT SensorNb FROM AnalyseInfo WHERE date = '" + date + "') AS SensorNb"
					+ ",(SELECT stationNb FROM AnalyseInfo WHERE date = '" + date + "') AS stationNb"
					+ ",(SELECT bollardNb FROM AnalyseInfo WHERE date = '" + date + "') AS bollardNb"
					+ ",(SELECT distance FROM AnalyseInfo WHERE date = '" + date + "') AS distance"
					+ ",(SELECT pollutionRate FROM AnalyseInfo WHERE date = '" + date + "') AS pollutionRate"
					+ ",(SELECT exceedingRate FROM AnalyseInfo WHERE date = '" + date + "') AS exceedingRate"
					;
			st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sql);        	

			JSONArray cityAll = new JSONArray();
			if(rs.next() == false) {
				return new ApiResponse(false, cityAll, "Not Found");
			}else {
				do {
					JSONObject resItem = new JSONObject();                	

					resItem.put("date", rs.getTimestamp("date"));
					resItem.put("SensorNb", "" + rs.getInt("SensorNb"));
					resItem.put("stationNb", "" + rs.getInt("stationNb"));
					resItem.put("bollardNb", "" + rs.getInt("bollardNb"));
					resItem.put("distance", "" + rs.getInt("distance"));
					resItem.put("pollutionRate", "" + rs.getDouble("pollutionRate"));
					resItem.put("exceedingRate", "" + rs.getDouble("exceedingRate"));
					
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
	
	/*
	public static ApiResponse getAnalyseInfoByDate(int id) throws SQLException {
		
		System.out.println("getAnalyseInfoByDate");
		st = conn.createStatement();
		PreparedStatement rechercheAnalyseInfo = conn.prepareStatement("SELECT * FROM AnalyseInfo WHERE date = ?");
		rechercheAnalyseInfo.setObject(1, id);
		ResultSet resultats = rechercheAnalyseInfo.executeQuery();
		boolean en = resultats.next();
		
		if (en) {
			System.out.println(resultats.getTimestamp("date"));
			System.out.println("getAnalyseInfoByDate");
			try {
				return new ApiResponse(true, id, "Success");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}
	*/
}
