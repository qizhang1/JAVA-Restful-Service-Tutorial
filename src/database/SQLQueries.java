package database;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import util.ToJSON;

/**
 * This class will hold all the SQL queries 
 * Having all SQL/database code in one package makes it easier to maintain and audit but increase complexity. 
 * Note: This class extends SQLServer class to inherit all the methods
 * 
 * @author Qi Zhang
 */

public class SQLQueries extends SQLServer {
	public JSONArray queryOrderDetailsByProduct(int productID) throws Exception {
		
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet result = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = SQLServerConnection();
			query = conn
					.prepareStatement("SELECT * FROM [ORDER DETAILS] WHERE PRODUCTID = ? ");

			query.setInt(1, productID);
			result = query.executeQuery();

			json = converter.toJSONArray(result);
			result.close();
			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result != null) result.close();
			if (query != null) query.close();
			if (conn != null) conn.close();
		}

		return json;
	}

	/**
	 * Search for the orders for a specific productID with no less than given quantity from 
	 * the Order Details table.
	 * 
	 * @param productID
	 * @param quantity
	 * @return - json array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryOrderDetailsByProduct_Quantity(int productID, int quantity) throws Exception{
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet result = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = SQLServerConnection();
			query = conn
					.prepareStatement("SELECT * FROM [ORDER DETAILS] WHERE PRODUCTID = ? AND QUANTITY >= ?");

			query.setInt(1, productID);
			query.setInt(2, quantity);
			result = query.executeQuery();

			json = converter.toJSONArray(result);
			result.close();
			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result != null) result.close();
			if (query != null) query.close();
			if (conn != null) conn.close();
		}

		return json;
	}
}
