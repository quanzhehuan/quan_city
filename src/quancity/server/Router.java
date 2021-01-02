package quancity.server;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import quancity.dto.ActualDataProvider;
import quancity.dto.CarbonDataProvider;
import quancity.dto.CityProvider;
import quancity.dto.SensorQualityAirProvider;
import quancity.dto.ThresholdProvider;
import quancity.dto.TramwayProvider;
import quancity.dto.VehiculeSensorProvider;
import quancity.server.common.AnalyseProvider;
import quancity.server.common.ApiResponse;

public class Router {
	
	// ActualData

	public static String findAllActualData() {

		JSONObject mapper = new JSONObject();
		try {
			ActualDataProvider AD = new ActualDataProvider();
			return AD.getAll().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// CarbonData

	public static String findAllCarbonData() {

		JSONObject mapper = new JSONObject();
		try {
			CarbonDataProvider AD = new CarbonDataProvider();
			return AD.getAll().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static CityProvider cityP = new CityProvider();
	static TramwayProvider tramway = new TramwayProvider();

	public static String createCity(JSONObject city) {
		return cityP.create(city).toString();
	}

	public static String updateCity(JSONObject city) {
		return cityP.update(city).toString();
	}

	public static String deleteCity() {
		return cityP.delete().toString();
	}

	public static String findAllCity() {

		try {
			return cityP.getAll().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String findOneCityByID(int cityID) {

		try {
			return cityP.getByID(cityID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Tramway

	public static String findOneTramwayByCityID(int cityID) {

		try {
			return tramway.getTramWayByCityID(cityID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String createUpdateTramway(JSONObject inp) {
		return tramway.createAndUpdate(inp).toString();
	}

	public static String randomStation(JSONObject inp) {
		int cityID;
		try {
			cityID = (int) inp.getInt("ID");
			return tramway.randomStation(cityID).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				return new ApiResponse(false, null, e.getMessage()).toString();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		}
	}

	// air quality sensor
	public static String createSensorAir(JSONObject sensorAir) {
		return SensorQualityAirProvider.create(sensorAir).toString();
	}

	public static String updateSensorAir(JSONObject sensorAir) {
		return SensorQualityAirProvider.update(sensorAir).toString();
	}

	public static String updateAlerte(JSONObject alerte) throws JSONException, SQLException {
		System.out.println("alerte" + alerte.toString());
		return SensorQualityAirProvider.updateAlerte(alerte).toString();
	}

	public static String findAllSensorAir() {

		JSONObject mapper = new JSONObject();
		try {
			SensorQualityAirProvider sensorAir = new SensorQualityAirProvider();
			return sensorAir.getAll().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String findActiveSensors() {
		return SensorQualityAirProvider.getActiveSensors().toString();
	}

	public static String findOneSensorAirById(int sensorAirID) {

		try {
			return SensorQualityAirProvider.getByID(sensorAirID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteSensorQualityAirById(int id, int alert_id) {
		SensorQualityAirProvider.deleteSensorQualityAirById(id, alert_id);
	}

	public static String getAllAlertHistory(int sensorId) {
		return SensorQualityAirProvider.getAllAlertHistory(sensorId).toString();
	}

	// Vehicule sensor
	public static String createVehiculeSensor(JSONObject VehiculeSensor) {
		return VehiculeSensorProvider.create(VehiculeSensor).toString();
	}

	public static String updateVehiculeSensor(JSONObject VehiculeSensor) {
		return VehiculeSensorProvider.update(VehiculeSensor).toString();
	}

	public static String findAllVehiculeSensor() {

		JSONObject mapper = new JSONObject();
		try {
			VehiculeSensorProvider VehiculeSensor = new VehiculeSensorProvider();
			return VehiculeSensor.getAll().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String findOneVehiculeSensorById(int VehiculeSensorID) {

		try {
			return VehiculeSensorProvider.getByID(VehiculeSensorID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteVehiculeSensorById(int ID, int alert_ID) {
		VehiculeSensorProvider.deleteVehiculeSensorById(ID, alert_ID);
	}

	// Threshold
	public static String createThreshold(JSONObject Threshold) {
		return ThresholdProvider.create(Threshold).toString();
	}

	public static String updateThreshold(JSONObject Threshold) {
		return ThresholdProvider.update(Threshold).toString();
	}

	public static String findAllThreshold() {
		JSONObject mapper = new JSONObject();
		try {
			ThresholdProvider Threshold = new ThresholdProvider();
			return Threshold.getAll().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String findOneThresholdById(int ThresholdID) {
		try {
			return ThresholdProvider.getByID(ThresholdID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * public static void deleteThresholdById(int ID,int alert_ID) {
	 * ThresholdProvider.deleteThresholdrById(ID,alert_ID); }
	 */

	// analyse

	static AnalyseProvider analyseProvider = new AnalyseProvider();

	public static String AnalyseToday(int cID) {
		try {
			return analyseProvider.getTodayAnalyseInfo(cID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String AnalyseDate(int cID, String date) {
		try {
			return analyseProvider.getAnalyseInfoByDate(cID, date).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String AnalysePeriod(int cID, String date, String date1) {
		try {
			return analyseProvider.getAnalyseInfoByPeriod(cID, date, date1).toString();
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
			// CITY
			case "CITY_CREATE":
				System.out.println(input);
				return createCity((JSONObject) input.get("body"));

			case "CITY_UPDATE":
				return updateCity((JSONObject) input.get("body"));

			case "CITY_GET_ONE":
				body = input.getJSONObject("body");
				System.out.println(body.getString("ID"));
				return findOneCityByID((int) body.getInt("ID"));

			case "CITY_FIND_ALL":
				return findAllCity();

			case "CITY_DELETE":
				return deleteCity();
				
				
				
			case "ANALYSE_TODAY":
				body = input.getJSONObject("body");
				return AnalyseToday((int) body.getInt("ID"));
			
			case "ANALYSE_DATE":
				body = input.getJSONObject("body");
				return AnalyseDate((int)body.getInt("ID"), body.getString("date"));
			
			/*
			// tramway
			case "TRAMWAY_GET_ONE":
				body = input.getJSONObject("body");
				return findOneTramwayByCityID((int) body.getInt("ID"));

			case "TRAMWAY_UPDATE":
				return createUpdateTramway((JSONObject) input.get("body"));
			// random
			case "TRAMWAY_MAP_RANDOM":
				return randomStation((JSONObject) input.get("body"));

			case "ActualData_FIND_ALL":
				return findAllActualData();

			case "CarbonData_FIND_ALL":
				return findAllCarbonData();

			// air sensor
			case "SENSORAIR_CREATE":
				return createSensorAir((JSONObject) input.get("body"));

			case "SENSORAIR_UPDATE":
				return updateSensorAir((JSONObject) input.get("body"));
			case "SENSORAIR_FIND_ONE":
				body = input.getJSONObject("body");

				return findOneSensorAirById((int) body.getInt("ID"));

			case "SENSORAIR_FIND_ALL":
				return findAllSensorAir();
			case "SENSORAIR_FIND_ACTIVE":
				return findActiveSensors();

			case "SENSORAIR_DELETE":
				body = input.getJSONObject("body");
				deleteSensorQualityAirById((int) body.getInt("id"), (int) body.getInt("alert_id"));

			case "ALERT_HISTORY_FIND_ALL":
				body = input.getJSONObject("body");
				return getAllAlertHistory((int) body.getInt("id"));

			case "ALERT_UPDATE":
				System.out.println("ALERT_UPDATE");
				updateAlerte((JSONObject) input.get("body"));

				// vehicule sensor
			case "VEHICULESENSOR_CREATE":
				return createVehiculeSensor((JSONObject) input.get("body"));

			case "VEHICULESENSOR_UPDATE":
				return updateVehiculeSensor((JSONObject) input.get("body"));
			case "VEHICULESENSOR_FIND_ONE":
				body = input.getJSONObject("body");

				return findOneVehiculeSensorById((int) body.getInt("ID"));

			case "VEHICULESENSOR_FIND_ALL":
				return findAllVehiculeSensor();

			case "VEHICULESENSOR_DELETE":
				body = input.getJSONObject("body");
				deleteVehiculeSensorById((int) body.getInt("id"), (int) body.getInt("alert_id"));
				return "DELETED";
			
			// threshold
			case "THRESHOLD_CREATE":
				return createThreshold((JSONObject) input.get("body"));
			case "THRESHOLD_UPDATE":
				return updateThreshold((JSONObject) input.get("body"));
			case "THRESHOLD_FIND_ONE":
				body = input.getJSONObject("body");
				return findOneThresholdById((int) body.getInt("ID"));
			case "THRESHOLD_FIND_ALL":
				return findAllThreshold();
				
			case "THRESHOLD_DELETE":
				body = input.getJSONObject("body");
				// deleteThresholdById((int) body.getInt("id"),(int) body.getInt("alert_id"));
				return "DELETED";
			*/

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