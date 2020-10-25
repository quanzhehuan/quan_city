package quan_city_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import quan_city_client_common.ApiEnum;
import quan_city_client_common.SendPackage;


public class Client extends Thread {
	// Thread for socket
	private Thread t;
	private String threadName = "Connect Socket";
	// initialize socket and input output streams
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private PrintWriter outmsg;
	private BufferedReader inmsg;
	public SendPackage sendP = null;
	public JSONObject responseData = new JSONObject();
	private String UserName = "Admin";

	// constructor to put ip address and port
	public Client(String address, int port) {
		try {
			socket = new Socket(address, port);
			outmsg = new PrintWriter(socket.getOutputStream(), true);
			inmsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

	}

	private void showClientId() {
		try {
			System.out.println("Please enter id of the client ");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			Scanner scanner = new Scanner(System.in);
//			String line = scanner.nextLine();
			out.writeUTF("UserName");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void closeConnection() {
		// close the connection
		try {
			out.writeUTF("0");
			System.out.println("Close socket");
			input.close();
			inmsg.close();
			out.close();
			outmsg.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public SendPackage getSendP() {
		return sendP;
	}

	public void setSendP(SendPackage sendPackage) {
		sendP = sendPackage;
	}

	public JSONObject getResponseData() {
		return responseData;
	}

	public void setResponseData(JSONObject resData) {
		responseData = resData;
	}

	@Override
	public void run() {
		// establish a connection
		try {
			System.out.println("Connected");
			// takes input from terminal
			input = new DataInputStream(System.in);
			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());

//				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				out.writeUTF("UserName");
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

		showClientId();
		// sendP.setApi(ApiEnum.CITY_FIND_ALL);
		Boolean isSend = false;
		while (!isSend) {
			// if have new request from ui
			// System.out.println("SendPackage:"+ sendP);
			if (sendP != null) {
				System.out.println("SendPackage:" + sendP.toString());
				try {
					// get all city
					out.writeUTF(sendP.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// safina chof lmok
				try {
					// System.out.println("Waiting for the result");
					DataInputStream oos = new DataInputStream(socket.getInputStream());
					String msg = oos.readUTF();
					try {
						JSONObject resd = new JSONObject(msg);
						responseData = resd;
						// System.out.println(resd);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					sendP = null;
					// isSend = true;

				} catch (IOException i) {
					System.out.println(i);
				}
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMessage(String msg) throws IOException {
		outmsg.println(msg);
	}
	
	public String getMessage() throws IOException {
		String resp;
		resp = inmsg.readLine();
		return resp;
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
