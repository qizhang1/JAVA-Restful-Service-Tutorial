package util;

import java.sql.*;
import org.codehaus.jettison.json.*;
import org.owasp.esapi.ESAPI;

/**
 * This utility will convert a database data into JSON format.
 * Note:  this java class requires the ESAPI 1.4.4 jar file
 * ESAPI is used to encode data
 * 
 */
public class ToJSON {

	/**
	 * This will convert database records into a JSON Array
	 * Simply pass in a ResultSet from a database connection and it
	 * loop return a JSON array.
	 * 
	 * It important to check to make sure that all DataType that are
	 * being used is properly encoding.
	 * 
	 * varchar is currently the only dataType that is being encode by ESAPI
	 * 
	 * @param rs - database ResultSet
	 * @return - JSON array
	 * @throws Exception
	 */
	public JSONArray toJSONArray(ResultSet rs) throws Exception {

        JSONArray json = new JSONArray(); 
        String temp = null;

        try {
             ResultSetMetaData rsmd = rs.getMetaData();

             while( rs.next() ) {
                 int numColumns = rsmd.getColumnCount();
                 //each row in the ResultSet will be converted to a JSON Object
                 JSONObject obj = new JSONObject();

                 //loop through all the columns and place them into the JSON Object
                 for (int i = 1; i < numColumns + 1; i++) {

                     String column_name = rsmd.getColumnName(i);

                     if(rsmd.getColumnType(i) == java.sql.Types.ARRAY){
                    	 obj.put(column_name, rs.getArray(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    	 obj.put(column_name, rs.getBoolean(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    	 obj.put(column_name, rs.getBlob(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    	 obj.put(column_name, rs.getDouble(column_name)); 
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    	 obj.put(column_name, rs.getFloat(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    	 obj.put(column_name, rs.getNString(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){                   	 
                    	 temp = rs.getString(column_name); //saving column data to temp variable
                    	 temp = ESAPI.encoder().canonicalize(temp); //decoding data to base state
                    	 temp = ESAPI.encoder().encodeForHTML(temp); //encoding to be browser safe
                    	 obj.put(column_name, temp); //putting data into JSON object
                    	 
                    	 //obj.put(column_name, rs.getString(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    	 obj.put(column_name, rs.getDate(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    	 obj.put(column_name, rs.getTimestamp(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NUMERIC){
                    	 obj.put(column_name, rs.getBigDecimal(column_name));
                      }
                     else {
                    	 obj.put(column_name, rs.getObject(column_name));
                    	 System.out.println("ToJson: Object "+column_name);
                     } 

                    }                
                 json.put(obj);
             }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
	}
}
