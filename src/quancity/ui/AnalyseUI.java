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
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class AnalyseUI {

	public JFrame frame;
	Client client;
	private int cID;
	private String date;
	private JLabel lblDate;
	private JLabel lblSensors;
	private JLabel lblStations;
	private JLabel lblBollards;
	private JLabel lblVehicles;
	private JLabel lblExceeding;
	private JLabel lblPollutionRate;
	private String typedDate;

	/**
	 * Create the application.
	 */

	public AnalyseUI(Client client, int cID) {
		this.client = client;
		this.cID = cID;
		Date datedate = new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
		this.date = dateFormat.format(datedate);
		initialize();

		//getSensorInfo();
		getCityInfo();
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

		//JLabel lblNewLabel = new JLabel(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-"
		//+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.YEAR));
		
		
		JLabel lblDate = new JLabel(date);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblDate);

		JLabel lblNewLabel_1 = new JLabel("Air Sensors installed in city : ");
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

		JButton btnNewButton_3 = new JButton("Comparison");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Analyse_comparison c = new Analyse_comparison(client, cID);
				// c.setVisible(true);
				c.getJFrame().setVisible(true);
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

		JLabel lblOtherDate = new JLabel("Other date : ");
		lblOtherDate.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblOtherDate);

		JButton btnSelect = new JButton("Select");
		panel.add(btnSelect);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				typedDate = JOptionPane.showInputDialog("Please input a date (yyyy-mm-dd): ");
				if (isValidDate(typedDate)) {
					lblDate.setText(typedDate);
					// lbtSensors.setText(res.String("CountSensor"));
					date = typedDate;
					getSensorInfo();
					//AnalyseUI.this.getCityInfo();
				} else {
					while(!isValidDate(typedDate))
						typedDate = JOptionPane.showInputDialog("Your input is not correct. Please input a date (yyyy-mm-dd): ");
					date = typedDate;
					getSensorInfo();
					//AnalyseUI.this.getCityInfo();
				}
			}
		});		

		JButton btnBack = new JButton("Back");
		panel.add(btnBack);
		panel.add(btnNewButton_3);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dashboard db = new Dashboard();
				db.frame.setVisible(true);
				frame.dispose();
			}
		});

	}
	
	private static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public JFrame getJFrame() {
		return frame;
	}

	public void getCityInfo() {
		try {
			client.setResponseData(null);
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("ID", "" + cID);
			bodyItem.put("date", date);

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

	private void setDataToField(JSONObject res) {
		try {
			lblSensors.setText("" + res.getInt("SensorNb"));
			lblStations.setText("" + res.getInt("stationNb"));
			lblBollards.setText("" + res.getInt("bollardNb"));
			lblVehicles.setText(res.getInt("distance") + " km");
			lblPollutionRate.setText(res.getDouble("pollutionRate") + "%");
			lblExceeding.setText(res.getDouble("exceedingRate") + "%");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void getSensorInfo() {
		try {
			client.setResponseData(null);
			JSONObject bodyItem = new JSONObject();
			bodyItem.put("ID", "" + cID);
			bodyItem.put("date", date);

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
