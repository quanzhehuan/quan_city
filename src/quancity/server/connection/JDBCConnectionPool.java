package quancity.server.connection;

import java.sql.*;
import java.util.ArrayList;

public class JDBCConnectionPool {

	public static int countConnectionsUsing = 0;
	public ArrayList<Connection> ConnectionsReadyToUse = new ArrayList<Connection>();

	public JDBCConnectionPool() {
		//minimal size of connections = 2
		while (getSize() < 2) {
			addConnection();
			System.out.println(
					"Add Connection: ReadyToUse:" + ConnectionsReadyToUse.size() + " - InUse:" + countConnectionsUsing);
		}
	}

	public synchronized Connection getConnection() {
		System.out.println("Start get connection!");
		while (ConnectionsReadyToUse.isEmpty()) {
			//create new connection pool
			//maximal size of connections
			if (countConnectionsUsing < 20) {
				addConnection();
				System.out.println("Add Connection: ReadyToUse:" + ConnectionsReadyToUse.size() + " - InUse:"
						+ countConnectionsUsing);
			} else {
				//saturation
				System.out.println("Nombre excessif de connexions. Veuillez patientez.");
				break;
			}
		}
		Connection tempConnection = ConnectionsReadyToUse.get(0);
		ConnectionsReadyToUse.remove(0);
		countConnectionsUsing++;
		System.out.println(
				"Use Connection: ReadyToUse:" + ConnectionsReadyToUse.size() + " - InUse:" + countConnectionsUsing);
		return tempConnection;
	}

	public synchronized void returnConnection(Connection c) {
		ConnectionsReadyToUse.add(c);
		countConnectionsUsing = countConnectionsUsing > 0 ? countConnectionsUsing - 1 : 0;
		System.out.println(
				"Return Connection: ReadyToUse:" + ConnectionsReadyToUse.size() + " - InUse:" + countConnectionsUsing);
	}

	public Connection addConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://172.31.249.120:3306/puzzle_01?serverTimezone=UTC", "root", "toto");
			// Connection conn = DriverManager.getConnection(CONNECT_LINK, "root", "");
			if (conn != null) {
				System.out.println("Add new connection pool success!");
				ConnectionsReadyToUse.add(conn);
				return conn;
			} else {
				System.out.println("Failed to create new connection pool!");
				return null;
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void closeAllConnection() throws SQLException {
		for (Connection c : ConnectionsReadyToUse) {
			c.close();
		}
		ConnectionsReadyToUse.clear();
	}

	public int getSize() {
		return ConnectionsReadyToUse.size() + countConnectionsUsing;
	}
}