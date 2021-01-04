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
import java.text.DateFormat;
import java.text.ParseException;
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
		private Date bt;
		private Date et;
		private JTextArea jta;
		private JTextArea jta1;
		private JTextField txtEvolutionBetweenTwo;
		private JTextField txtInfomationOfEveryday;
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		/**
		 * Create the application.
		 * @throws ParseException 
		 */
		
		public Analyse_comparison_2(Client client, int cID, String date1, String date2) throws ParseException {
			this.client = client;
			this.cityID = cID;
			this.date1 = date1;
			this.date2 = date2;
			initialize();
			
			 
			bt=sdf.parse(date1); 
			et=sdf.parse(date2); 
			getAnalyseInfo(bt, et);

			while (bt.before(et) || bt.equals(et)) {
				getDailyInfo(bt);
				bt.setDate(bt.getDate() + 1);
			}
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
			
			JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setSize(0, 439);
			panel.add(scrollPane);
			
			jta = new JTextArea();
			jta.setLineWrap(true);
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
				jta.setText(jta.getText() + "The evolution between \n" + sdf.format(bt).toString() + " \nand\n" + sdf.format(et).toString() + " : \n");
				jta.setText(jta.getText() + "\n" + "The evolution of Air Sensor installed in city : " + res.getInt("sensorNb"));
				jta.setText(jta.getText() + "\n" + "The difference of Tramway Stations : " + res.getInt("stationNb"));
				jta.setText(jta.getText() + "\n" + "The difference of retractable bollards installed : " + res.getInt("bollardNb"));
				jta.setText(jta.getText() + "\n" + "The evolution of vehicles in city : " + res.getInt("vehicleNb"));
				jta.setText(jta.getText() + "\n" + "The difference of pollution rate : " + res.getInt("pollutionRate") + "%");
				jta.setText(jta.getText() + "\n" + "The difference of pollution exceeding rate : " + res.getInt("exceedingRate") + "%\n\n");
				jta.setText(jta.getText() + "\n" + "Pollution rate's history avg : " + res.getInt("pollutionRateHis") + "%");
				jta.setText(jta.getText() + "\n" + "Pollution exceeding rate's history avg : " + res.getInt("exceedingRateHis") + "%\n");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void setDataToField1(JSONObject res) {
			try {
				jta1.setText(jta1.getText() + sdf.format(bt));
				jta1.setText(jta1.getText() + "\n" + "Air Sensors installed in city : " + res.getInt("sensorNb"));
				jta1.setText(jta1.getText() + "\n" + "Tramway Stations : " + res.getInt("stationNb"));
				jta1.setText(jta1.getText() + "\n" + "Retractable Bollards : " + res.getInt("bollardNb"));
				jta1.setText(jta1.getText() + "\n" + "Vehicles in city : " + res.getInt("vehicleNb"));
				jta1.setText(jta1.getText() + "\n" + "Pollution rate : " + res.getInt("pollutionRate") + "%");
				jta1.setText(jta1.getText() + "\n" + "Pollution exceeding rate : " + res.getInt("exceedingRate") + "%\n\n");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}	
		public void getAnalyseInfo(Date date1, Date date2) {
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "" + cityID);
				bodyItem.put("date1", sdf.format(date1));
				bodyItem.put("date2", sdf.format(date2));

				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.ANALYSE_INFO);
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
		
		public void getDailyInfo(Date date) throws ParseException {
			
			try {
				client.setResponseData(null);
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "" + cityID);
				bodyItem.put("date", sdf.format(date));
				//bodyItem.put("date2", date2);

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
