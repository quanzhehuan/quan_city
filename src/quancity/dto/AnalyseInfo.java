package quancity.dto;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import quancity.client.Client;
import quancity.client.common.ApiEnum;
import quancity.client.common.SendPackage;

public class AnalyseInfo {
	
	private Client client;
	
	private Timestamp date;
	private int sensorNb;
	private int stationNb;
	private int bollardNb;
	private int distance;
	private double pollutionRate;
	private double exceedingRate;
	
	private void createAnalyseInfo() {
		try {
			if (!isNotEmpty(sensorNb) && !isNotEmpty(stationNb) && !isNotEmpty(bollardNb) && !isNotEmpty(distance) && !isNotEmpty(pollutionRate) && !isNotEmpty(exceedingRate)) {
				JOptionPane.showMessageDialog(null, "Empty data");
				return;
			}
			if (isInvalidData(sensorNb) && isInvalidData(stationNb) && isInvalidData(bollardNb) && isInvalidData(distance) && isInvalidData(pollutionRate) && isInvalidData(exceedingRate)) {
				JOptionPane.showMessageDialog(null, "Invalid data");
				return;
			}
			if (checkLength(sensorNb) && checkLength(stationNb) && checkLength(bollardNb) && checkLength(distance) && checkLength(pollutionRate) && checkLength(exceedingRate)) {
				JOptionPane.showMessageDialog(null, "There's no information");
				return;
				
			}
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("date", date.toString());
			bodyItem.put("sensorNb", "" + sensorNb);
			bodyItem.put("stationNb", "" + stationNb);
			bodyItem.put("bollardNb", "" + bollardNb);
			bodyItem.put("distance", "" + distance);
			bodyItem.put("pollutionRate", "" + pollutionRate);
			bodyItem.put("exceedingRate", "" + exceedingRate);

			SendPackage sendPa = new SendPackage();
			sendPa.setApi(ApiEnum.SENSORAIR_CREATE);
			sendPa.setBody(bodyItem);
			client.setSendP(sendPa);

			JSONObject res = null;
			while (res == null) {
				res = client.getResponseData();
				System.out.println("AnalyseInfo : " + res);
				if (res != null) {
					// if success
					boolean sMess = res.getBoolean("Success");
					if (sMess) {
						System.out.println("Add Success");
					} else {
						System.out.println("Error : " + res.getString("msg"));
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private boolean isNotEmpty(String date) {
		return date != null & !date.isEmpty();
	}
	
	private boolean isNotEmpty(int i) {
		return i >= 0;
	}

	private boolean isNotEmpty(double d) {
		return d >= 0;
	}

	private boolean isInvalidData(Object o) {
		Pattern p = Pattern.compile("[^a-z0-9- ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(o.toString());
		return m.find();

	}
	private boolean checkLength(Object o) {
		return o.toString().length() >= 0;
	}
}
