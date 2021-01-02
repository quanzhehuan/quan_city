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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.ScrollPaneConstants;
	
	public class Analyse_comparison_2 {

		public JFrame frame;
		Client client;
		private int cityID;
		private String date1;
		private String date2;
		private JTextArea jta;
		private JTextArea jta1;
		private JTextField txtEvolutionBetweenTwo;
		private JTextField txtInfomationOfEveryday;
		
		/**
		 * Create the application.
		 */
		
		public Analyse_comparison_2(Client client, int cID, String date1, String date2) {
			this.client = client;
			this.cityID = cID;
			this.date1 = date1;
			this.date2 = date2;
			initialize();
			
			getAnalyseInfo();
			getDailyInfo();
			
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
			
			JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setSize(0, 439);
			panel.add(scrollPane);
			
			jta = new JTextArea();
			scrollPane.setViewportView(jta);
			jta.setFont(new Font("Serif", Font.PLAIN, 20));
			jta.setBackground(new Color(224, 255, 255));
			jta.setEditable(false);
			
			txtEvolutionBetweenTwo = new JTextField();
			txtEvolutionBetweenTwo.setEditable(false);
			txtEvolutionBetweenTwo.setText("Analyse Information");
			txtEvolutionBetweenTwo.setHorizontalAlignment(SwingConstants.CENTER);
			txtEvolutionBetweenTwo.setFont(new Font("Tahoma", Font.PLAIN, 18));
			scrollPane.setColumnHeaderView(txtEvolutionBetweenTwo);
			txtEvolutionBetweenTwo.setColumns(10);
			
			JButton btnNewButton_3 = new JButton("Back");
			btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
			scrollPane.setRowHeaderView(btnNewButton_3);
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			
			JScrollPane scrollPane_1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			panel.add(scrollPane_1);
			
			jta1 = new JTextArea();
			jta1.setFont(new Font("Serif", Font.PLAIN, 20));
			jta1.setEditable(false);
			jta1.setBackground(new Color(224, 255, 255));
			scrollPane_1.setViewportView(jta1);
			
			txtInfomationOfEveryday = new JTextField();
			txtInfomationOfEveryday.setEditable(false);
			txtInfomationOfEveryday.setText("Infomation of each date");
			txtInfomationOfEveryday.setHorizontalAlignment(SwingConstants.CENTER);
			txtInfomationOfEveryday.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtInfomationOfEveryday.setColumns(10);
			scrollPane_1.setColumnHeaderView(txtInfomationOfEveryday);
		}
		
		
		public JFrame getJFrame() {
			return frame;
		}
		
		private void setDataToField(JSONObject res) {
			try {
				jta.setText("" + res.getInt("sensorNb"));
				/*
				lblSensors.setText("" + res.getInt("sensorNb"));
				lblStations.setText("" + res.getInt("stationNb"));
				lblBollards.setText("" + res.getInt("bollardNb"));
				lblVehicles.setText("" + res.getInt("vehicleNb"));
				lblPollutionRate.setText(res.getInt("pollutionRate") + "%");
				lblExceeding.setText(res.getInt("exceedingRate") + "%");
				*/
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void setDataToField1(JSONObject res) {
			try {
				jta1.setText(date1);
				jta1.setText(jta1.getText() + "\n" + "Air Sensors installed in city : " + res.getInt("sensorNb"));
				jta1.setText(jta1.getText() + "\n" + "Tramway Stations : " + res.getInt("stationNb"));
				jta1.setText(jta1.getText() + "\n" + "Retractable Bollards : " + res.getInt("bollardNb"));
				jta1.setText(jta1.getText() + "\n" + "Vehicles in city : " + res.getInt("vehicleNb"));
				jta1.setText(jta1.getText() + "\n" + "Pollution rate : " + res.getInt("pollutionRate") + "%");
				jta1.setText(jta1.getText() + "\n" + "Pollution exceeding rate : " + res.getInt("exceedingRate") + "%");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}	
		public void getAnalyseInfo() {
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "" + cityID);
				bodyItem.put("date1", date1);
				bodyItem.put("date2", date2);

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
		
		public void getDailyInfo() {
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
						setDataToField1((res.getJSONArray("data")).getJSONObject(0));
					}
				}
				// CLOSE
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
}
