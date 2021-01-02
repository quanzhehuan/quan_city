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
			String sql =  "SELECT (SELECT date FROM AnalyseInfo AS date"
					+ ",(SELECT COUNT(*) FROM airSensor) AS sensorNb"
					+ ",(SELECT COUNT(*) FROM station) AS stationNb"
					+ ",(SELECT COUNT(*) FROM bollardEquipment) AS bollardNb"
					+ ",(SELECT nbVehicleInCity FROM vehicleHistory ORDER BY historyId DESC LIMIT 1) AS vehicleNb"
					+ ",(SELECT SUM(no2) / COUNT(no2) FROM airSensorHistory) AS pollutionRate1"
					+ ",(SELECT SUM(pm10) / COUNT(pm10) FROM airSensorHistory) AS pollutionRate2"
					+ ",(SELECT SUM(o3) / COUNT(o3) FROM airSensorHistory) AS pollutionRate3"
					;
			st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sql); 

			JSONArray cityAll = new JSONArray();
			if(rs.next() == false) {
				return new ApiResponse(false, cityAll, "AnalyseProvider : City Not Found");
			}else {
				do {
					double pollutionRateAvg = (rs.getDouble("pollutionRate1") + rs.getDouble("pollutionRate2") + rs.getDouble("pollutionRate3")) / 3;
					double exceedingRate;
					if(pollutionRateAvg > 100)
						exceedingRate = pollutionRateAvg - 100;
					else
						exceedingRate = 0;
					JSONObject resItem = new JSONObject();                	

					resItem.put("sensorNb", "" + rs.getInt("sensorNb"));
					resItem.put("stationNb", "" + rs.getInt("stationNb"));
					resItem.put("bollardNb", "" + rs.getInt("bollardNb"));
					resItem.put("vehicleNb", "" + rs.getInt("vehicleNb"));
					resItem.put("pollutionRate", "" + Math.round(pollutionRateAvg));
					resItem.put("exceedingRate", "" + Math.round(exceedingRate));
					
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
					+ ",(SELECT COUNT(*) FROM airSensor WHERE installDate <= '" + date + "') AS sensorNb"
					+ ",(SELECT COUNT(*) FROM station WHERE openDate <= '" + date + "') AS stationNb"
					+ ",(SELECT COUNT(*) FROM bollardEquipment) AS bollardNb"
					+ ",(SELECT nbVehicleInCity FROM vehicleHistory WHERE date <= '" + date + "' ORDER BY historyId DESC LIMIT 1) AS vehicleNb"
					+ ",(SELECT SUM(no2) / COUNT(no2) FROM airSensorHistory WHERE date = '" + date + "') AS pollutionRate1"
					+ ",(SELECT SUM(pm10) / COUNT(pm10) FROM airSensorHistory WHERE date = '" + date + "') AS pollutionRate2"
					+ ",(SELECT SUM(o3) / COUNT(o3) FROM airSensorHistory WHERE date = '" + date + "') AS pollutionRate3"
					;
			st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sql);        	

			JSONArray cityAll = new JSONArray();
			if(rs.next() == false) {
				return new ApiResponse(false, cityAll, "Not Found");
			}else {
				do {
					double pollutionRateAvg = (rs.getDouble("pollutionRate1") + rs.getDouble("pollutionRate2") + rs.getDouble("pollutionRate3")) / 3;
					double exceedingRate;
					if(pollutionRateAvg > 100)
						exceedingRate = pollutionRateAvg - 100;
					else
						exceedingRate = 0;
					JSONObject resItem = new JSONObject();                
					
					resItem.put("sensorNb", "" + rs.getInt("sensorNb"));
					resItem.put("stationNb", "" + rs.getInt("stationNb"));
					resItem.put("bollardNb", "" + rs.getInt("bollardNb"));
					resItem.put("vehicleNb", "" + rs.getInt("vehicleNb"));
					resItem.put("pollutionRate", "" + Math.round(pollutionRateAvg));
					resItem.put("exceedingRate", "" + Math.round(exceedingRate));
					
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
	
	public static ApiResponse getAnalyseInfoByPeriod(int cID, String date, String date1) {
		try {
			String sql =  "SELECT (SELECT date FROM AnalyseInfo WHERE date = '" + date + "') AS date"
					+ ",(SELECT COUNT(*) FROM airSensor WHERE installDate <= '" + date + "') AS sensorNb"
					+ ",(SELECT COUNT(*) FROM station WHERE openDate <= '" + date + "') AS stationNb"
					+ ",(SELECT COUNT(*) FROM bollardEquipment) AS bollardNb"
					+ ",(SELECT nbVehicleInCity FROM vehicleHistory WHERE date <= '" + date + "' ORDER BY historyId DESC LIMIT 1) AS vehicleNb"
					+ ",(SELECT SUM(no2) / COUNT(no2) FROM airSensorHistory WHERE date = '" + date + "') AS pollutionRate1"
					+ ",(SELECT SUM(pm10) / COUNT(pm10) FROM airSensorHistory WHERE date = '" + date + "') AS pollutionRate2"
					+ ",(SELECT SUM(o3) / COUNT(o3) FROM airSensorHistory WHERE date = '" + date + "') AS pollutionRate3"
					;
			st =  conn.createStatement();
			ResultSet rs = st.executeQuery(sql);        	

			JSONArray cityAll = new JSONArray();
			if(rs.next() == false) {
				return new ApiResponse(false, cityAll, "Not Found");
			}else {
				do {
					double pollutionRateAvg = (rs.getDouble("pollutionRate1") + rs.getDouble("pollutionRate2") + rs.getDouble("pollutionRate3")) / 3;
					double exceedingRate;
					if(pollutionRateAvg > 100)
						exceedingRate = pollutionRateAvg - 100;
					else
						exceedingRate = 0;
					JSONObject resItem = new JSONObject();                
					
					resItem.put("sensorNb", "" + rs.getInt("sensorNb"));
					resItem.put("stationNb", "" + rs.getInt("stationNb"));
					resItem.put("bollardNb", "" + rs.getInt("bollardNb"));
					resItem.put("vehicleNb", "" + rs.getInt("vehicleNb"));
					resItem.put("pollutionRate", "" + Math.round(pollutionRateAvg));
					resItem.put("exceedingRate", "" + Math.round(exceedingRate));
					
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
