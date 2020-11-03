package quancity.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import puzzle_city_client.Client;
import puzzle_city_client_model.ApiEnum;
import puzzle_city_client_model.SendPackage;

public class CityAddNew {


	public JFrame frame;
	private JTextField txtCityName;
	private JTextField txtLat;
	private JTextField txtLong;
	private JTextField txtHeight;
	private JTextField txtWidth;
	private JTextField txtMapZoom;
	private JLabel lbtMess;

	public 	Client client ;//= new Client("127.0.0.1", 4000);
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public CityAddNew(Client socket) {
		client = socket;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(50, 50, 900, 700);
		frame.getContentPane().add(panel);

		JPanel panel_cityinfo = new JPanel();
		panel_cityinfo.setBounds(44, 64, 826, 574);
		panel.add(panel_cityinfo);
		panel_cityinfo.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("City Name:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 11, 179, 14);
		panel_cityinfo.add(lblNewLabel_1);

		txtCityName = new JTextField();
		txtCityName.setBounds(212, 11, 315, 20);
		panel_cityinfo.add(txtCityName);
		txtCityName.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Latitude");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setBounds(10, 39, 179, 14);
		panel_cityinfo.add(lblNewLabel_1_1);

		txtLat = new JTextField();
		txtLat.setColumns(10);
		txtLat.setBounds(212, 39, 315, 20);
		panel_cityinfo.add(txtLat);

		JLabel lblNewLabel_1_1_1 = new JLabel("Longitude");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1.setBounds(10, 67, 179, 14);
		panel_cityinfo.add(lblNewLabel_1_1_1);

		txtLong = new JTextField();
		txtLong.setColumns(10);
		txtLong.setBounds(212, 67, 315, 20);
		panel_cityinfo.add(txtLong);

		txtHeight = new JTextField();
		txtHeight.setColumns(10);
		txtHeight.setBounds(212, 123, 315, 20);
		panel_cityinfo.add(txtHeight);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Height");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1_1.setBounds(10, 123, 179, 14);
		panel_cityinfo.add(lblNewLabel_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Width");
		lblNewLabel_1_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1_1_1.setBounds(10, 92, 179, 14);
		panel_cityinfo.add(lblNewLabel_1_1_1_1_1);

		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		txtWidth.setBounds(212, 92, 315, 20);
		panel_cityinfo.add(txtWidth);

		txtMapZoom = new JTextField();
		txtMapZoom.setColumns(10);
		txtMapZoom.setBounds(212, 154, 315, 20);
		panel_cityinfo.add(txtMapZoom);

		JLabel lbtMapZoom = new JLabel("Map Zoom");
		lbtMapZoom.setHorizontalAlignment(SwingConstants.RIGHT);
		lbtMapZoom.setBounds(10, 154, 179, 14);
		panel_cityinfo.add(lbtMapZoom);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createCity();
			}
		});
		btnCreate.setBounds(212, 185, 89, 23);
		panel_cityinfo.add(btnCreate);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setVisible(true);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CityList cityList = new CityList(client);
				cityList.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(314, 185, 89, 23);
		panel_cityinfo.add(btnCancel);

		lbtMess = new JLabel("");
		lbtMess.setBounds(212, 219, 315, 47);
		panel_cityinfo.add(lbtMess);

		JLabel lblNewLabel = new JLabel("Add New City");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(214, 11, 197, 27);
		panel.add(lblNewLabel);
	}


	private void setField(JSONObject res) {
		// TODO Auto-generated method stub
		try {
			txtCityName.setText(res.getString("Name"));
			txtHeight.setText(String.valueOf( res.getDouble("Height")));
			txtWidth.setText(String.valueOf( res.getDouble("Width")));
			txtLat.setText(String.valueOf( res.getDouble("CenterLat")));
			txtLong.setText(String.valueOf( res.getDouble("CenterLong")));
			txtMapZoom.setText(String.valueOf( res.getInt("MapZoom")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private void createCity() {
		if(isValid()) {			
			try {			
				client.setResponseData(null);		
				JSONObject bodyItem = new JSONObject();
				bodyItem.put("ID", "0");
				bodyItem.put("name", "" +txtCityName.getText());
				bodyItem.put("height", txtHeight.getText());
				bodyItem.put("width", txtWidth.getText());
				bodyItem.put("centerLat", txtLat.getText());
				bodyItem.put("centerLong", "" +txtLong.getText());
				bodyItem.put("mapZoom", "" + txtMapZoom.getText());


				SendPackage sendPa = new SendPackage();
				sendPa.setApi(ApiEnum.CITY_CREATE);		
				sendPa.setBody(bodyItem);
				client.setSendP(sendPa);

				JSONObject res = null;
				while(res == null) {
					res = client.getResponseData();
					System.out.println("wait for return:"+res);
					if(res!= null) {
						// if success
						boolean sMess = res.getBoolean("success");
						if(sMess) {
							lbtMess.setText("Add Success");
							//move to dashboard
							
							Dashboard ctDetail =	new Dashboard(client, (res.getJSONArray("data").getJSONObject(0)).getInt("ID"));
							Dashboard.frame.setVisible(true);
							frame.dispose();

						}else {
							lbtMess.setText("Error :"+res.getString("msg") );						
						}
						System.out.println("Return:"+res.toString());
					}
				} 

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean isValid() {
		boolean valid = true;
		String textErr = "";
		// TODO Auto-generated method stub
		System.out.println("txtCityName:"+txtCityName.getText());
		//check valid Latitude
		try {	
			Integer.parseInt( txtMapZoom.getText());
		} catch (Exception e) {
			textErr = "MapZoom value is not valid.";
			valid = false;
		}	
		//check valid Latitude
		try {	
			Double.parseDouble( txtLong.getText());
		} catch (Exception e) {
			textErr = "Longitude value is not valid.";
			valid = false;
		}

		//check valid Latitude
		try {	
			Double.parseDouble( txtLat.getText());
		} catch (Exception e) {
			textErr = "Latitude value is not valid.";
			valid = false;
		}


		//check valid width
		try {	
			Double.parseDouble( txtWidth.getText());
		} catch (Exception e) {
			textErr = "Width value is not valid.";
			valid = false;
		}

		//check valid height
		try {	
			Double.parseDouble( txtHeight.getText());
		} catch (Exception e) {
			textErr = "Height value is not valid.";
			valid = false;
		}


		if(txtCityName.getText().isEmpty()) {
			textErr = "Name City is required.";
			valid = false;
		};	

		lbtMess.setText(textErr);
		return valid;
	}

	private void getCityData() {
		// TODO Auto-generated method stub		
		client.setResponseData(null);
		SendPackage sendP = new SendPackage();
		sendP.setApi(ApiEnum.CITY_FIND_ALL);		
		client.setSendP(sendP);
		JSONObject res = null;
		while(res == null) {
			res = client.getResponseData();
			System.out.println("waiting:"+res);
			if(res!= null) {
				// if success true - get data bind to table 
				System.out.println(res.toString());
				boolean sMess;
				try {
					sMess = res.getBoolean("success");				
					if(sMess) {
						JSONArray jArray = res.getJSONArray("data");
						if(jArray.length()>0) {
							System.out.println("Select last city");
							frame.dispose();
							int cID = jArray.getJSONObject(jArray.length()-1).getInt("ID");
							Dashboard ctDetail = new Dashboard(client, cID);
							ctDetail.frame.setVisible(true);
						}else{
							System.out.println("Add new");
							frame.dispose();
							CityAddNew ctAdd =	new CityAddNew(client);
							ctAdd.frame.setVisible(true);
						};
					}else {						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
		//

		client.setResponseData(null);
	}
}
