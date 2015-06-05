package inventory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;

import database.SQLQueries;

/**
 * This class is used to query order details. It is an improvement to
 * V1_Order.java
 * 
 * @author Qi Zhang
 */

@Path("/v2/orders")
public class V2_Order {

	/**
	 * Get all order details for a product Ex:
	 * http://localhost:8080/SimpleRestAPI/api/v2/orders?product=10
	 * 
	 * @param productID
	 * @return - json array results list from the database
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnOrderByProductID(@QueryParam("product") int productID)
			throws Exception {

		String returnString = null;
		JSONArray json = new JSONArray();

		try {
			if (productID == 0) {
				return Response
						.status(400)
						// http status code 400 bad request
						.entity("Error: please specify productID for this search")
						.build();
			}

			SQLQueries db = new SQLQueries();
			json = db.queryOrderDetailsByProduct(productID);
			returnString = json.toString();
		} catch (Exception e) { // http status code 500 Internal Server Error
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

	/**
	 * Get all order details for a product.
	 * This method uses the @PathParam to bring in the data. 
	 * Ex: http://localhost:8080/com.youtube.rest/api/v2/orders/10
	 * 
	 * @param productID
	 * @return - json array results list from the database
	 * @throws Exception
	 */
	@Path("/{product}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnOrder(@PathParam("product") int productID)
			throws Exception {

		String returnString = null;
		JSONArray json = new JSONArray();

		try {
			SQLQueries db = new SQLQueries();
			json = db.queryOrderDetailsByProduct(productID);
			returnString = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

}
