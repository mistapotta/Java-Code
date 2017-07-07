package edu.gatech.coffeecart;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * A class to help connectivity with the mysql database through JSON interactions
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class DatabaseHelperJSON 
{
	//database specifications
	private static final String db_ip = "127.0.0.1";
	private static final String db_name = "myDatabase";
	private static final String db_user = "username";
	private static final String db_pass = "password";
	
	private static String jsonResult = "";
	private static String mainURLBase = "http://coffeecart.orware.com/api/";
	
	//database connection object
	private static URL url = null;
	private static URLConnection conn = null;
	private static InputStream instream = null;
	private static OutputStream outstream = null;
	private static String resultString = "";
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8192);
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append((line + "\n"));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	/**
	 * Creates new customer in the database.
	 * 
	 * @param fname The first name of the customer.
	 * @param lname The last name of the customer.
	 * @param phone The phone number of the customer.
	 * @param isGold The gold level state of the customer.
	 * @param birthday The birthdate of the customer.
	 *  
	 */
	public static void createCustomer(String fname, String lname, long phone, boolean isGold, Date birthdate)
	{
		try
		{
	         
	         HttpParams httpParams = new BasicHttpParams();
	         HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
	         HttpConnectionParams.setSoTimeout(httpParams, 1000);
	         HttpClient client = new DefaultHttpClient(httpParams);
	         HttpGet request = new HttpGet(mainURLBase+"customer");

	         request.setHeader( "Content-Type", "application/json" );     
	         
	         JSONObject obj = new JSONObject();
				obj.put("vip_card_number", ""+Customer.getNewCustomerVIPNumber());
				obj.put("first_name", fname);
				obj.put("last_name",  lname);
				obj.put("date_of_birth",  birthdate);
				obj.put("phone_number", ""+phone);
				//other fields not yet implemented, pending approval of code
				obj.put("address1", "a1");
				obj.put("address2",  "a2");
				obj.put("city", "ci");
				obj.put("state",  "st");
				obj.put("zip", "zi");
				obj.put("created_date",  "cd");
				//back to our regularly scheduled program

	         

	         StringEntity se = new StringEntity(obj.toString());

	         se.setContentEncoding("UTF-8");
	         se.setContentType("application/json");

	       //  request.setEntity(se);      

	         HttpResponse response = client.execute(request); 

	         HttpEntity entity = response.getEntity();
	         InputStream is = entity.getContent();
	         String _response = convertStreamToString(is);
	         System.out.println("res--  " + _response);

	         // Check if server response is valid code          
	         int res_code = response.getStatusLine().getStatusCode();
	         System.out.println("code-- " +res_code);

		} 
		catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Gets customer with specified VIP id number from the database.
	 * 
	 * @param vipNumber The specified VIP number
	 * @return Customer The customer object to be manipulated.
	 * 
	 */
	public static Customer getCustomer(long vipNumber)
	{
		try
		{
	         HttpParams httpParams = new BasicHttpParams();
	         HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
	         HttpConnectionParams.setSoTimeout(httpParams, 1000);
	         HttpClient client = new DefaultHttpClient(httpParams);
	         HttpPost request = new HttpPost(mainURLBase+"customer/:"+vipNumber); 
	         HttpResponse response = client.execute(request); 
	         HttpEntity entity = response.getEntity();
	         InputStream is = entity.getContent();
	         String JSONresult = convertStreamToString(is);
	         
	         // Check if server response is valid code          
	         int res_code = response.getStatusLine().getStatusCode();
	         if (res_code == 200)
	         {
		         JSONObject obj = new JSONObject(JSONresult);
		         
		         String fname = obj.optString("first_name");
		         String lname = obj.optString("last_name");
		         String date = obj.optString("date_of_birth");
		         Date birthdate = DateFormat.getInstance().parse(date);
		         String phone = obj.optString("phone_number");
		         long phonenumber = Long.parseLong(phone);
		         
		         return new Customer(vipNumber, fname, lname, phonenumber, false, birthdate);
	         }
	         else if (res_code == 404) //no customer with that number
	        	 return null;

		} 
		catch (Exception e) {e.printStackTrace();return null;}
		return null;
	}
	
	
		
	/**
	 * Updates an existing customer in the database.
	 * 
	 * @param vipNumber The customer's VIP number.
	 * @param fname The first name of the customer.
	 * @param lname The last name of the customer.
	 * @param phone The phone number of the customer.
	 * @param isGold The gold level state of the customer.
	 * @param birthday The birthdate of the customer.
	 *  
	 */
	public static void updateCustomer(long vipNumber, String fname, String lname, long phone, boolean isGold, Date birthdate)
	{
		try 
		{
		//	  conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + ":3306/" + db_name, db_user, db_pass); 
	    } 
		  catch (Exception e) 
		{
	            //do something with the error
		}
		  
		PreparedStatement insertStatement = null;
		if (getCustomer(vipNumber) == null)
		{
			String insertString = "INSERT INTO vip (vipnumber, fname, lname, phone, isgold, birthdate) VALUES (?,?,?,?,?,?)";
			if(conn != null)
			{
				try
				{
	//				insertStatement = conn.prepareStatement(insertString); //creates the statement

					insertStatement.setString(1, ""+vipNumber);
					insertStatement.setString(2, fname);
					insertStatement.setString(3, lname);
					insertStatement.setString(4, ""+phone);
					insertStatement.setString(5, ""+isGold);
					insertStatement.setString(6, birthdate.toString());
					insertStatement.execute();
					insertStatement.close();

					
					String update = "UPDATE vip SET fname, lname, phone, isgold, birthdate WHERE vipnumber = '"+vipNumber+"'"; 
		//			Statement updateStatement = conn.createStatement();
		//			updateStatement.execute(update);
			


		//				conn.close();
				}
				catch (Exception e){}
			}
		}
		
	}
	
	/**
	 * Deletes customer with specified VIP id number from the database by blanking all fields.
	 * 
	 * @param vipNumber The specified VIP number
	 *  
	 */
	public static void deleteCustomer(long vipNumber)
	{
		try 
		{
			//  conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + ":3306/" + db_name, db_user, db_pass); 
	    } 
		catch (Exception e) 
		{
	            //do something with the error
		}
		  
		PreparedStatement insertStatement = null;
		if (getCustomer(vipNumber) == null)
		{
			String insertString = "INSERT INTO vip (vipnumber, fname, lname, phone, isgold, birthdate) VALUES (?,?,?,?,?,?)";
			if(conn != null)
			{
				try
				{
				//	insertStatement = conn.prepareStatement(insertString); //creates the statement

					insertStatement.setString(1, ""+vipNumber);
					insertStatement.setString(2, "");
					insertStatement.setString(3, "");
					insertStatement.setString(4, "0");
					insertStatement.setString(5, "false");
					insertStatement.setString(6, "null");
					insertStatement.execute();
					insertStatement.close();
					
					String update = "UPDATE vip SET fname, lname, phone, isgold, birthdate WHERE vipnumber = '"+vipNumber+"'"; 
				//	Statement updateStatement = conn.createStatement();
				//	updateStatement.execute(update);
			
				//	conn.close();
				}
				catch (Exception e){}
			}
		}
	}
	
	/**
	 * adds a purchase to the database
	 * 
	 * @param vipNumber The VIP Account number of the customer
	 * @param purchase The purchase object to be added to db.
	 */
	public static void createPurchase(long vipNumber, Purchase purchase)
	{
		try 
		{
			//  conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + ":3306/" + db_name, db_user, db_pass); 
	    } 
		  catch (Exception e) 
		{
	            //do something with the error
		}
		  
		PreparedStatement insertStatement = null;
		if (getCustomer(vipNumber) != null)
		{
			String insertString = "INSERT INTO purchases (vipnumber, date, amount, purchase_as_string VALUES (?,?,?,?)";
			if(conn != null)
			{
				try
				{
			//		insertStatement = conn.prepareStatement(insertString); //creates the statement

					insertStatement.setString(1, ""+vipNumber);
					insertStatement.setString(2, purchase.getDate().toString());
					insertStatement.setString(3, purchase.getCost().toString());
					
					String purchaseAsString = "" + purchase.getDate().toString() + "#" + vipNumber + "#" + purchase.getCost().toString()+"#";
					List<Item> items = purchase.getItems();
					for (Item item: items)
					{
						if (item instanceof Coffee)
							purchaseAsString += "Coffee#" + item.getName() + "#" + item.getCost() + "#false#";
						else 
							purchaseAsString += "Dessert#" + item.getName() + "#" + item.getCost() + "#" + item.isBestSeller() + "#";
					}
					insertStatement.setString(4, purchaseAsString);

					insertStatement.execute();
					insertStatement.close();

					String update = "UPDATE purchases ADD vipnumber, date, amount, purchase_as_string"; 
				//	Statement updateStatement = conn.createStatement();
		//			updateStatement.execute(update);
			
				//	conn.close();
				}
				catch (Exception e){}
			}
		}
	}
	
	/**
	 * gets all purchases on a particular date, to generate a report later.
	 * 
	 * @param date The date of the purchase for report
	 * @return List of purchases that took place on given date.
	 */
	public static List<Purchase> getPurchases(Date date)
	{
	    try 
	    {
	     //      conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + ":3306/" + db_name, db_user, db_pass); 
	                                                 
	    } 
		catch (Exception e) 
		{
	           return null;
	    }

	    if(conn !=  null)
	    {
	    	try
	    	{	
	    	
	    	String dateToGet = date.toString();

	    	String userQuery = "SELECT vipnumber, date, amount, purchase_as_string FROM purchases WHERE date = " + dateToGet;

	  //  	Statement purchaseStatement = conn.createStatement();
	    	
	   // 	ResultSet purchaseResult = purchaseStatement.executeQuery(userQuery);

	 //   	if(purchaseResult != null)
	    	{
	    		List<Purchase> purchases = new LinkedList<Purchase>(); 
	   // 		while(purchaseResult.next())
	        	{
	   // 			String purchaseAsString = purchaseResult.getString("purchase_as_string");
	   //     		Purchase purchase = Purchase.stringToPurchase(purchaseAsString);
	   //     		purchases.add(purchase);
	        	}
	    		return purchases;
	    	}
	    	}
	    	catch(Exception e){}
	    	
	    }
	    return new LinkedList<Purchase>();
	}
	
	/**
	 * gets all purchases from a unique vip acocunt, to determine if a customer is gold.
	 * 
	 * @param vipnumber The vipnumber of the purchase for report
	 * @return List of purchases that took place from given customer.
	 */
	public static List<Purchase> getPurchases(long vipnumber)
	{
	    try 
	    {
	     //      conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + ":3306/" + db_name, db_user, db_pass); 
	                                                 
	    } 
		catch (Exception e) 
		{
	           return null;
	    }

	    if(conn !=  null)
	    {
	    	try
	    	{
	    	String userQuery = "SELECT vipnumber, date, amount, purchase_as_string FROM purchases WHERE vipnumber = " + vipnumber;

	    //	Statement purchaseStatement = conn.createStatement();
	    //	ResultSet purchaseResult = purchaseStatement.executeQuery(userQuery);
    		List<Purchase> purchases = new LinkedList<Purchase>(); 

	    //	if(purchaseResult != null)
	    	{
	   // 		while(purchaseResult.next())
	        	{
	    ///			String purchaseAsString = purchaseResult.getString("purchase_as_string");
	     //   		Purchase purchase = Purchase.stringToPurchase(purchaseAsString);
	     //   		purchases.add(purchase);
	        	}

	    	}
	    	return purchases;
	    	}
	    	catch (Exception e){}
	    }
	    return new LinkedList<Purchase>();
	}
	

	
/*
 * 
 * From http://php.about.com/od/phpwithmysql/fl/MySQL-and-Java.htm
 * 
 * 
 * 
 * 
 * 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class DBTest {

// Some variables:

private static final String db_ip = "127.0.0.1";
private static final String db_name = "myDatabase";
private static final String db_user = "username";
private static final String db_pass = "password";

private Connection conn = null;

public void dbTest(){

// Now, to connect:

    try {
    
           conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + ":3306/" + db_name, db_user, db_pass); 
                                                 
    } catch (SQLException e) {
            //do something with the error
    }

    //Now we should have a connection, but it's foot to check that it isn't null, then select some data

    if(conn !=  null){

    //Let's get a list of users who live in the zip code 11111.

    String zipToGet = "11111";

    String userQuery = "SELECT age, user_name, created_on FROM users WHERE zip = " + zipToGet;

    Statement userStatement = conn.createStatement();

    ResultSet userResult = userStatement.exequteQuery(userQuery);

    //Not to scary so far, right? in the userQuery we're saying we want the age, user_name, and created_on fields from the 
    //users table whenever the zip is the one we specified. This is just a MySQL statement.


    //userResult will be null if something went wrong, or if there's just no result.

    if(userResult != null){
         List<User> users = new ArrayList<>(); //hypothetical user class
        while(userResult.hasNext(){
        int age = result.getInt("age");
        String username = result.getString("user_name");
        Date createdOn = result.getTimestamp("created_on"); //This returns a Timestamp, but it can be cast directly into a date

       //Do stuff with this data:
       //Using the hypothetical user class with what data we have

        User user = new User(username); 
        user.age = age;
        user.createDate = createdOn;
        users.add(user);
    }

    for(user : users){
        //do something with the users
    }

    }else{
        //here is where you put code to deal with no result from user query
    }
    }

//Now we'll work on inserting new data into the database. Keep in mind that manipulating that data has no affect on the //database, for that we need to to create another statement:

PreparedStatement insertStatement = null;

String insertString = "INSERT INTO users (user_name, created_on, age) VALUES (?,?,?)";
if(conn != null){

insert = conn.prepareStatement(insertString); //creates the statement

insert.setString(1, "newUserName); // 1 represents the position of the question mark we left in the insertString

Date now = new Date();

insert.setTimestamp(2, new Timestamp(now.getTime()); 

insert.setInt(3, 28);

insert.execute();

insert.close();
}
 
//Now we've selected and inserted, we can also update:

if(conn != null){

String update = "UPDATE users SET age WHERE user_name = 'someUser'"; 
//this assumes usernames are unique, usually would use an id number

Statement updateStatement = conn.createStatement();

updateStatement.execute(update);


}

//And of course close the connection when you're done:

conn.close();

}
*/
}
