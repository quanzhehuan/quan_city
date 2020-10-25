package quancity.server;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import quancity.server.common.AnalyseProvider;
import quancity.server.common.ApiResponse;

public class Router {

	static AnalyseProvider Analyse = new AnalyseProvider();

	public static String AnalyseAirSensor(int cID) {
		try {
			return Analyse.getInfoAnalyse(cID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String AnalyseCity(int cID) {
		try {
			return Analyse.getInfoAnalyse(cID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String AnalyseBollard(int cID) {
		try {
			return Analyse.getInfoAnalyse(cID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String AnalyseDistance(int cID) {
		try {
			return Analyse.getInfoAnalyse(cID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// input {api:"CITYSave",body:{}}
	public static String router(JSONObject input) throws SQLException {
		String api;
		JSONObject body;
		try {
			api = input.get("api").toString();
			// Converting the Object to JSONString

			switch (api) {
			
			case "ANALYSE_AIR_SENSOR_NUMBER":
				body = input.getJSONObject("body");
				return AnalyseCity((int) body.getInt("ID"));

			case "ANALYSE_ONE_CITY":
				body = input.getJSONObject("body");
				return AnalyseCity((int) body.getInt("ID"));
				
			case "ANALYSE_BOLLARD_NUMBER":
				body = input.getJSONObject("body");
				return AnalyseCity((int) body.getInt("ID"));
				
			case "ANALYSE_DISTANCE":
				body = input.getJSONObject("body");
				return AnalyseCity((int) body.getInt("ID"));
				
			case "ANALYSE_RATE_POLLUTION":
				body = input.getJSONObject("body");
				return AnalyseCity((int) body.getInt("ID"));
				
			case "ANALYSE_EXCEEDING":
				body = input.getJSONObject("body");
				return AnalyseCity((int) body.getInt("ID"));

			default:
				return new ApiResponse(false, null, "Not found API").toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				return new ApiResponse(false, null, e.getMessage()).toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}
}
