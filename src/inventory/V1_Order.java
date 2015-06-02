package inventory;

import java.sql.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;

import util.ToJSON;

import database.SQLServer;

@Path("/v1/orders")
public class V1_Order {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String result = null;
		Response rb = null;
		try {
			conn = SQLServer.SQLServerConn().getConnection();
			query = conn.prepareStatement("SELECT * FROM [ORDER DETAILS]");
			ResultSet rs = query.executeQuery();

			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close(); // close db connection
			
			result = json.toString();
			rb = Response.ok(result).build();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return rb;
	}
}
