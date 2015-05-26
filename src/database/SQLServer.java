package database;

import javax.naming.*;
import javax.sql.*;

public class SQLServer {
	private static DataSource SQLServer = null; // hold the database object
	private static Context context = null; // used to lookup the database connection

	public static DataSource SQLServerConn() throws Exception {

		if (SQLServer != null) {
			return SQLServer;
		}
		try {
			if (context == null) {
				context = new InitialContext();
			}
			SQLServer = (DataSource)context.lookup("java:comp/env/jdbc/sqlserver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SQLServer;
	}
}
