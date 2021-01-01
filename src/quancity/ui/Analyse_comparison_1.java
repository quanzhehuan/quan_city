package quancity.ui;


	import java.awt.Color;
	import java.awt.EventQueue;
	import java.awt.GridLayout;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JButton;
	import javax.swing.JTextField;

	import org.json.JSONException;
	import org.json.JSONObject;

	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.ObjectMapper;

	import quancity.client.Client;
	import quancity.client.common.ApiEnum;
	import quancity.client.common.SendPackage;

	import javax.swing.JLabel;
	import java.awt.event.ActionListener;
	import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
	import java.util.Iterator;
	import java.awt.event.ActionEvent;
	import javax.swing.SwingConstants;
	import java.awt.Font;
	
	public class Analyse_comparison_1 {

		public JFrame frame;
		Client client;
		private int cityID;
		private JLabel lblSensors;
		private JLabel lblStations ;
		private JLabel lblBollards;
		private JLabel lblVehicles;
		private JLabel lblExceeding;
		private JLabel lblPollutionRate;
		private String date1;
		private String date2;
		JLabel label_3;
		JLabel label_5;
		JLabel label_7;
		JLabel label_9;
		JLabel label_11;
		JLabel label_13;
		
		/**
		 * Create the application.
		 */
		
		public Analyse_comparison_1(Client client, int cID, String date1, String date2) {
			this.client = client;
			this.cityID = cID;
			this.date1 = date1;
			this.date2 = date2;
			initialize();
			
			getSensorInfo();
			getSensorInfo1();
			
			/*
			getSensorInfo(date1);
			getCityInfo(date1);
			getSensorInfo(date2);
			getCityInfo(date2);
			*/
		}
		
		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame();
			frame.setBounds(100, 100, 700, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);

			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(Color.WHITE);
			panel.setBounds(10, 11, 664, 439);
			frame.getContentPane().add(panel);
			panel.setLayout(new GridLayout(0, 2, 0, 8));
			
			JLabel label = new JLabel("Statistics of ");
			label.setFont(new Font("Tahoma", Font.PLAIN, 16));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(label);
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setText(date1);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Air Sensors installled in city : ");
			lblNewLabel_1.setForeground(Color.BLACK);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblNewLabel_1);
			
			lblSensors = new JLabel();
			lblSensors.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblSensors);
			
			JLabel lblTheNumberOf_1 = new JLabel("Tramway Stations : ");
			lblTheNumberOf_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblTheNumberOf_1);
			
			lblStations = new JLabel("");
			lblStations.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblStations);
			
			JLabel lblNewLabel_7 = new JLabel("Retractable Bollards : ");
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblNewLabel_7);
			
			lblBollards = new JLabel("");
			lblBollards.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblBollards);
			
			JButton btnNewButton_3 = new JButton("Back");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			
			JLabel lblNewLabel_8 = new JLabel("Vehicles in city : ");
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblNewLabel_8);
			
			lblVehicles = new JLabel("");
			lblVehicles.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblVehicles);
			
			JLabel lblExceedingRateOf = new JLabel("Pollution rate : ");
			lblExceedingRateOf.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblExceedingRateOf);
			
			lblPollutionRate = new JLabel("");
			lblPollutionRate.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblPollutionRate);
			
			JLabel lblExceedingRateOf_1 = new JLabel("Pollution exceeding rate : ");
			lblExceedingRateOf_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblExceedingRateOf_1);
			
			
			lblExceeding = new JLabel("");
			lblExceeding.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblExceeding);
			
			JLabel lblNewLabel_2 = new JLabel("");
			panel.add(lblNewLabel_2);
			
			JLabel label_14 = new JLabel("");
			panel.add(label_14);
			
			JLabel label_1 = new JLabel("Statistics of ");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			panel.add(label_1);
			
			JLabel lblNewLabel_6 = new JLabel(date2);
			lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblNewLabel_6);
			
			JLabel lblAirSensorsInstallled = new JLabel("Air Sensors installled in city : ");
			lblAirSensorsInstallled.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAirSensorsInstallled.setForeground(Color.BLACK);
			panel.add(lblAirSensorsInstallled);
			
			label_3 = new JLabel("");
			label_3.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_3);
			
			JLabel lblTramwayStations = new JLabel("Tramway Stations : ");
			lblTramwayStations.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblTramwayStations);
			
			label_5 = new JLabel("");
			label_5.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_5);
			
			JLabel lblRetractableBollards = new JLabel("Retractable Bollards : ");
			lblRetractableBollards.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblRetractableBollards);
			
			label_7 = new JLabel("");
			label_7.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_7);
			
			JLabel lblVehiclesInCity = new JLabel("Vehicles in city : ");
			lblVehiclesInCity.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblVehiclesInCity);
			
			label_9 = new JLabel("");
			label_9.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_9);
			
			JLabel lblPollutionRate = new JLabel("Pollution rate : ");
			lblPollutionRate.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblPollutionRate);
			
			label_11 = new JLabel("");
			label_11.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_11);
			
			JLabel lblPollutionExceedingRate = new JLabel("Pollution exceeding rate : ");
			lblPollutionExceedingRate.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblPollutionExceedingRate);
			
			label_13 = new JLabel("");
			label_13.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_13);
			panel.add(btnNewButton_3);
			
		}
		
		
		public JFrame getJFrame() {
			return frame;
		}
		
		public void getCityInfo() {
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "" + cityID);
				bodyItem.put("date", date1);

				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.ANALYSE_TODAY);
				sendPa.setBody(bodyItem);
				client.setSendP(sendPa);

				JSONObject res = null;
				while (res == null) {
					res = client.getResponseData();

					System.out.println("wait res:" + res);
					if (res != null) {
						// if success true - get data bind to table
						setDataToField((res.getJSONArray("data")).getJSONObject(0));
					}
				}
				// CLOSE

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void setDataToField(JSONObject res) {
			try {
				lblSensors.setText("" + res.getInt("sensorNb"));
				lblStations.setText("" + res.getInt("stationNb"));
				lblBollards.setText("" + res.getInt("bollardNb"));
				lblVehicles.setText("" + res.getInt("vehicleNb"));
				lblPollutionRate.setText(res.getInt("pollutionRate") + "%");
				lblExceeding.setText(res.getInt("exceedingRate") + "%");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void setDataToField1(JSONObject res) {
			try {
				label_3.setText("" + res.getInt("sensorNb"));
				label_5.setText("" + res.getInt("stationNb"));
				label_7.setText("" + res.getInt("bollardNb"));
				label_9.setText("" + res.getInt("vehicleNb"));
				label_11.setText(res.getInt("pollutionRate") + "%");
				label_13.setText(res.getInt("exceedingRate") + "%");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}	
		public void getSensorInfo() {
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "" + cityID);
				bodyItem.put("date", date1);

				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.ANALYSE_DATE);
				sendPa.setBody(bodyItem);
				client.setSendP(sendPa);

				JSONObject res = null;
				while (res == null) {
					res = client.getResponseData();

					System.out.println("wait res:" + res);
					if (res != null) {
						// if success true - get data bind to table
						setDataToField((res.getJSONArray("data")).getJSONObject(0));
					}
				}
				// CLOSE
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		public void getSensorInfo1() {
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "" + cityID);
				bodyItem.put("date", date2);

				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.ANALYSE_DATE);
				sendPa.setBody(bodyItem);
				client.setSendP(sendPa);

				JSONObject res = null;
				while (res == null) {
					res = client.getResponseData();

					System.out.println("wait res:" + res);
					if (res != null) {
						// if success true - get data bind to table
						setDataToField1((res.getJSONArray("data")).getJSONObject(0));
					}
				}
				// CLOSE
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
}
