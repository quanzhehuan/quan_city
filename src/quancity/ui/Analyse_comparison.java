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

import com.eltima.components.ui.DatePicker;
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
import java.util.HashMap;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.SystemColor;

public class Analyse_comparison{

	public JFrame frmCompareBetween;
	Client client;
	private int cityID;
	private String date1;
	private String date2;
	private String date3;
	private String date4;
	private JTextField txtDate1;
	private JTextField txtDate2;
	private JTextField txtDate3;
	private JTextField txtDate4;
	
	/**
	 * Create the application.
	 */
	
	public Analyse_comparison(Client client, int cityID) {
		this.client = client;
		this.cityID = cityID;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCompareBetween = new JFrame();
		frmCompareBetween.setTitle("Compare between 2 dates");
		frmCompareBetween.setBounds(100, 100, 700, 500);
		frmCompareBetween.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompareBetween.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 664, 439);
		frmCompareBetween.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 8));
		
		JButton btnNewButton_3 = new JButton("Continue");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date1 = txtDate1.getText();
				date2 = txtDate2.getText();
				if (isValidDate(date1) && isValidDate(date2)) {
					Analyse_comparison_1 c1 = new Analyse_comparison_1(client, cityID, date1, date2);
					c1.getJFrame().setVisible(true);
				} else {
					while(!isValidDate(date1)) {
						txtDate1.setText(date1);
						date1 = JOptionPane.showInputDialog("Your first date is not correct. Please input FIRST DATE (yyyy-mm-dd): ");
					}
					while(!isValidDate(date2)) {
						txtDate2.setText(date2);
						date2 = JOptionPane.showInputDialog("Your second date is not correct. Please input SECOND DATE (yyyy-mm-dd): ");
					}
					Analyse_comparison_1 c1 = new Analyse_comparison_1(client, cityID, date1, date2);
					c1.getJFrame().setVisible(true);
				}
			}
		});
		
		JLabel lblPleaseType = new JLabel("Please type 2 dates (yyyy-mm-dd) :");
		lblPleaseType.setHorizontalAlignment(SwingConstants.LEFT);
		lblPleaseType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblPleaseType);
		
		JLabel label_3 = new JLabel("");
		panel.add(label_3);
		
		txtDate1 = new JTextField();
		txtDate1.setFont(new Font("Tahoma", Font.ITALIC, 16));
		txtDate1.setHorizontalAlignment(SwingConstants.CENTER);
		txtDate1.setText(Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-"  + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		panel.add(txtDate1);
		txtDate1.setColumns(10);
		
		txtDate2 = new JTextField();
		txtDate2.setFont(new Font("Tahoma", Font.ITALIC, 16));
		txtDate2.setHorizontalAlignment(SwingConstants.CENTER);
		txtDate2.setText("Second Date");
		panel.add(txtDate2);
		txtDate2.setColumns(10);
		JLabel lblIfYouWant = new JLabel("To check a period, please type 2 dates here : ");
		lblIfYouWant.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIfYouWant.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblIfYouWant);
		
		JLabel lblNewLabel_1 = new JLabel("");
		panel.add(lblNewLabel_1);
		
		txtDate3 = new JTextField();
		panel.add(txtDate3);
		txtDate3.setText("First Date");
		txtDate3.setHorizontalAlignment(SwingConstants.CENTER);
		txtDate3.setFont(new Font("Tahoma", Font.ITALIC, 16));
		txtDate3.setColumns(10);
		
		txtDate4 = new JTextField();
		txtDate4.setText("Second Date");
		txtDate4.setHorizontalAlignment(SwingConstants.CENTER);
		txtDate4.setFont(new Font("Tahoma", Font.ITALIC, 16));
		txtDate4.setColumns(10);
		panel.add(txtDate4);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnBack);
		panel.add(btnNewButton_3);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnalyseUI anUI = new AnalyseUI(client, cityID);
				anUI.frame.setVisible(true);
				frmCompareBetween.dispose();
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
		return frmCompareBetween;
	}
}
