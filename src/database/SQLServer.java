package database;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;
/*
* This class returns the SQLServer database connect object instance
*/
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
	
	/**
	 * only java class in the database package can use this method.
	 * @return Connection to SQL Server database.
	 */
	protected static Connection SQLServerConnection() {
		Connection conn = null;
		try {
			conn = SQLServerConn().getConnection();
			return conn;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
