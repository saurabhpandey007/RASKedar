package com.roomautomation.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


public class DotNetClient {
	
	static String baseUrl="http://localhost:62383/RoomAutomationSystem.svc/";
	static List<String> lstn;
	public static Map<String,Integer> map=null;
	
	static{
		
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		Session session = cluster.connect("roomautomation");
		ResultSet results = session.execute("select * from sensor");
		map=new HashMap<>();
		for (Row row : results) {
			
		map.put(row.getString("sensortype"), row.getInt("frequency"));
			
			
		}
	}
	
	
	public static void getTemperatureFeq(int tempid,int roomid) throws Exception
	{
		System.out.println("in .net clnt temperature");
		lstn=new ArrayList<String>();
		  try {

			
			  URL url = new URL(baseUrl+"Temperature");
			  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			  System.out.println("before method"+new Date().getTime());
			
			  conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
			//System.out.println(conn.getResponseCode());
			String output;
			//System.out.println("after method"+ new Date().getTime());
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			List<String> oplist=DataProvider.getParseData("Frequency", "Date", "Temperature",output);
		//	System.out.println("inop"+oplist.get(0));	
			Double freqvalue = Double.parseDouble(oplist.get(0));
		//	System.out.println(value.getClass().getName());
			//System.out.println(value);
			//System.out.println("inop"+oplist.get(1));	
			
			String ss=oplist.get(1);
			
		
			
			//System.out.println("iiiii"+ss);
			
			String[] parts = ss.split("\\+");	
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = format.parse(parts[0]);
			//System.out.println(date);
			Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
			Session session = cluster.connect("roomautomation");
						
			PreparedStatement statement = session.prepare("INSERT INTO temperature(tempid,roomid,freq_val,time) VALUES (?,?,?,?)");
			BoundStatement boundStatement = new BoundStatement(statement);

			ResultSet results = session.execute(boundStatement.bind(tempid,roomid, freqvalue, date));
			
			
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		
	}
	
	
	
	public static void getPressureFeq(int pressid,int roomid) throws Exception
	{
		//System.out.println("in .net clnt preesure");
		lstn=new ArrayList<String>();
		  try {

			
			  URL url = new URL(baseUrl+"Pressure");
			  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//  System.out.println("before method"+new Date().getTime());
			
			  conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
			//System.out.println(conn.getResponseCode());
			String output;
			//System.out.println("after method"+ new Date().getTime());
			while ((output = br.readLine()) != null) {
				//System.out.println(output);
				String[] outputs = output.split("<P");	
				//System.out.println(outputs[0]);
				output="<P"+outputs[1];
				//System.out.println(output);
			List<String> oplist=DataProvider.getParseData("Frequency", "Date", "Pressure",output);
		//	System.out.println("inop"+oplist.get(0));	
			Double freqvalue = Double.parseDouble(oplist.get(0));
		//	System.out.println(value.getClass().getName());
			//System.out.println(value);
			//System.out.println("inop"+oplist.get(1));	
			
			String ss=oplist.get(1);
			
		
			
			//System.out.println("iiiii"+ss);
			
			String[] parts = ss.split("\\+");	
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = format.parse(parts[0]);
			//System.out.println(date);
			Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
			Session session = cluster.connect("roomautomation");
						
			PreparedStatement statement = session.prepare("INSERT INTO pressure(pressid,roomid,freq_val,time) VALUES (?,?,?,?)");
			BoundStatement boundStatement = new BoundStatement(statement);

			ResultSet results = session.execute(boundStatement.bind(pressid,roomid, freqvalue, date));
			
			
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	}
		  public static void getHumidityFeq(int humiid,int roomid) throws Exception
			{
				//System.out.println("in .net clnt humidity");
				lstn=new ArrayList<String>();
				  try {

					
					  URL url = new URL(baseUrl+"Humidity");
					  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					//  System.out.println("before method"+new Date().getTime());
					
					  conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");

					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ conn.getResponseCode());
					}
					
					BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
					//System.out.println(conn.getResponseCode());
					String output;
					//System.out.println("after method"+ new Date().getTime());
					while ((output = br.readLine()) != null) {
					System.out.println(output);
					List<String> oplist=DataProvider.getParseData("Frequency", "Date", "Humidity",output);
				//	System.out.println("inop"+oplist.get(0));	
					Double freqvalue = Double.parseDouble(oplist.get(0));
				//	System.out.println(value.getClass().getName());
					//System.out.println(value);
					//System.out.println("inop"+oplist.get(1));	
					
					String ss=oplist.get(1);
					
				
					
					//System.out.println("iiiii"+ss);
					
					String[] parts = ss.split("\\+");	
					
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Date date = format.parse(parts[0]);
					//System.out.println(date);
					Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
					Session session = cluster.connect("roomautomation");
								
					PreparedStatement statement = session.prepare("INSERT INTO humidity(humiid,roomid,freq_val,time) VALUES (?,?,?,?)");
					BoundStatement boundStatement = new BoundStatement(statement);

					ResultSet results = session.execute(boundStatement.bind(humiid,roomid, freqvalue, date));
					
					
					}

					conn.disconnect();

				  } catch (MalformedURLException e) {

					e.printStackTrace();

				  } catch (IOException e) {

					e.printStackTrace();

				  }
			}
				  public static void getLuxFeq(int luxid,int roomid) throws Exception
					{
						//System.out.println("in .net clnt");
						lstn=new ArrayList<String>();
						  try {

							
							  URL url = new URL(baseUrl+"Lux");
							  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							//  System.out.println("before method"+new Date().getTime());
							
							  conn.setRequestMethod("GET");
							conn.setRequestProperty("Accept", "application/json");

							if (conn.getResponseCode() != 200) {
								throw new RuntimeException("Failed : HTTP error code : "
										+ conn.getResponseCode());
							}
							
							BufferedReader br = new BufferedReader(new InputStreamReader(
								(conn.getInputStream())));
							//System.out.println(conn.getResponseCode());
							String output;
							//System.out.println("after method"+ new Date().getTime());
							while ((output = br.readLine()) != null) {
								System.out.println(output);
							List<String> oplist=DataProvider.getParseData("Frequency", "Date", "Lux",output);
						//	System.out.println("inop"+oplist.get(0));	
							Double freqvalue = Double.parseDouble(oplist.get(0));
						//	System.out.println(value.getClass().getName());
							//System.out.println(value);
							//System.out.println("inop"+oplist.get(1));	
							
							String ss=oplist.get(1);
							
						
							
							//System.out.println("iiiii"+ss);
							
							String[] parts = ss.split("\\+");	
							
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
							Date date = format.parse(parts[0]);
							//System.out.println(date);
							Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
							Session session = cluster.connect("roomautomation");
										
							PreparedStatement statement = session.prepare("INSERT INTO lux(luxid,roomid,freq_val,time) VALUES (?,?,?,?)");
							BoundStatement boundStatement = new BoundStatement(statement);

							ResultSet results = session.execute(boundStatement.bind(luxid,roomid, freqvalue, date));
							
							
							}

							conn.disconnect();

						  } catch (MalformedURLException e) {

							e.printStackTrace();

						  } catch (IOException e) {

							e.printStackTrace();

						  }

		
	}
	
	
	
}
