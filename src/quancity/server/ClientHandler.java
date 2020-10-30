package quancity.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import org.json.*;

public class ClientHandler implements Runnable {

	private final Socket socket;
	private DataInputStream in = null;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// takes input from the client socket
		String line = "";
		

		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			System.out.println("  New client connected  in host   :    " + socket.getInetAddress().getHostAddress()
					+ "     Id Client   :   " + in.readUTF());

			while (!line.equals("0")) {
				String response = "";
				try {
					line = in.readUTF();
					if (!line.equals("0")) {
						JSONObject input;
						System.out.println(line);
						input = new JSONObject(line);
						System.out.println("line:" + line);
						if (input.get("api").toString().toUpperCase() == "CLOSE_CONNECTION") {
							line = "0";
							System.out.println("Client close connection");
						} else {
							// reponse du serveur pour le client format json
							response = Router.router(input);
							System.out.println(response.toString());
						}
					}
					DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
					// write object to Socket
					oos.writeUTF(response);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
					break;
				}
			}
			System.out.println("Closing connection");
			socket.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
