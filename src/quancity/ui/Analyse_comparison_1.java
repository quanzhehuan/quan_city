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
		private JLabel lblDistance;
		private JLabel lblExceeding;
		private JLabel lblRatePollution;
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
			//getCityInfo();
			
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
			
			JLabel lblNewLabel_1 = new JLabel("The number of sensors installed in the city : ");
			lblNewLabel_1.setForeground(Color.BLACK);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblNewLabel_1);
			
			lblSensors = new JLabel();
			lblSensors.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblSensors);
			
			JLabel lblTheNumberOf_1 = new JLabel("The number of Stations : ");
			lblTheNumberOf_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblTheNumberOf_1);
			
			lblStations = new JLabel("");
			lblStations.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblStations);
			
			JLabel lblNewLabel_7 = new JLabel("The number of bollards : ");
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
			
			JLabel lblNewLabel_8 = new JLabel("Distance of public transit : ");
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblNewLabel_8);
			
			lblDistance = new JLabel("");
			lblDistance.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblDistance);
			
			JLabel lblExceedingRateOf = new JLabel("Rate of pollution : ");
			lblExceedingRateOf.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(lblExceedingRateOf);
			
			lblRatePollution = new JLabel("");
			lblRatePollution.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lblRatePollution);
			
			JLabel lblExceedingRateOf_1 = new JLabel("Exceeding rate of pollution : ");
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
			
			JLabel label_2 = new JLabel("The number of sensors installed in the city : ");
			label_2.setHorizontalAlignment(SwingConstants.RIGHT);
			label_2.setForeground(Color.BLACK);
			panel.add(label_2);
			
			label_3 = new JLabel("");
			label_3.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_3);
			
			JLabel label_4 = new JLabel("The number of Stations : ");
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(label_4);
			
			label_5 = new JLabel("");
			label_5.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_5);
			
			JLabel label_6 = new JLabel("The number of bollards : ");
			label_6.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(label_6);
			
			label_7 = new JLabel("");
			label_7.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_7);
			
			JLabel label_8 = new JLabel("Distance of public transit : ");
			label_8.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(label_8);
			
			label_9 = new JLabel("");
			label_9.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_9);
			
			JLabel label_10 = new JLabel("Rate of pollution : ");
			label_10.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(label_10);
			
			label_11 = new JLabel("");
			label_11.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label_11);
			
			JLabel label_12 = new JLabel("Exceeding rate of pollution : ");
			label_12.setHorizontalAlignment(SwingConstants.RIGHT);
			panel.add(label_12);
			
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
				System.out.println("++++++++++++++++++++++++" + res.getString("date"));
				if(res.getString("date") == date1) {
					lblSensors.setText("" + res.getInt("SensorNb"));
					lblStations.setText("" + res.getInt("stationNb"));
					lblBollards.setText("" + res.getInt("bollardNb"));
					lblDistance.setText(res.getInt("distance") + " km");
					lblRatePollution.setText(res.getDouble("pollutionRate") + "%");
					lblExceeding.setText(res.getDouble("exceedingRate") + "%");
				}
				else if(res.getString("date") == date2) {
					label_3.setText("" + res.getInt("SensorNb"));
					label_5.setText("" + res.getInt("stationNb"));
					label_7.setText("" + res.getInt("bollardNb"));
					label_9.setText(res.getInt("distance") + " km");
					label_11.setText(res.getDouble("pollutionRate") + "%");
					label_13.setText(res.getDouble("exceedingRate") + "%");
				}
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
}
