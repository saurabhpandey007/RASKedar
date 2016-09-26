package com.roomautomation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.roomautomation.pojo.User;
import com.roomautomation.provider.DotNetClient;
import com.roomautomation.utilities.TimeProvider;

@RestController
@CrossOrigin
@RequestMapping("/charts")
public class SensorController {

	Cluster cluster;
	Session session;
	@RequestMapping(value = "/sensordetail", method = RequestMethod.GET /*produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json"*/)
	public List<List<Object>>sensorDetailList () throws Exception {
				
		List<List<Object>> lst=new ArrayList<List<Object>>();
		List<Object> lst1=new ArrayList<Object>();
				
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		
		
		lst1.add("Time");
		lst1.add("Lux");
		lst1.add("humidity");
		lst1.add("pressure");
		lst1.add("temprature");
		lst.add(lst1);
		ResultSet results = session.execute("SELECT * FROM frequencydetail LIMIT 6");
		
		
		for (Row row : results) {
			lst1=new ArrayList<Object>();
			double tmpf = row.getDouble("temp_freq");
			double prsf = row.getDouble("pressure_freq");
			double luxf = row.getDouble("lux_freq");
			double humif = row.getDouble("humidity_freq");
			Date tm = row.getTimestamp("time");
			
			lst1.add(tm.getTime());
			lst1.add(luxf);
			lst1.add(humif);
			lst1.add(prsf);
			lst1.add(tmpf);
			lst.add(lst1);
		}								
		
		
		
		return lst;
	}
	
	@RequestMapping(value = "/apidetail", method = RequestMethod.GET /*, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json"*/)
	public List<List<Object>> apiDetailList() throws ParseException {
		
	
	List<List<Object>> lst=new ArrayList<List<Object>>();
	List<Object> lst1=new ArrayList<Object>();
			
	cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	session = cluster.connect("roomautomation");
	
	
	lst1.add("Response Code");
	lst1.add("Number Of Responses");
	lst.add(lst1);
	ResultSet results = session.execute("SELECT * FROM apidetails");
	
	Map<String, Integer> freq = new HashMap<String, Integer>();
	for (Row row : results) {
		
		
		Integer rescode = row.getInt("response_code");
		Integer count = freq.get(rescode.toString());
		if (count == null) {
		    freq.put(rescode.toString(), 1);
		   
		}
		else {
		    freq.put(rescode.toString(), count + 1);
		    
		}
		
	}
		
	 List<String> keys = new ArrayList<String>(freq.keySet());
	 List<Integer> values = new ArrayList<Integer>(freq.values());
	
	 for (int i = 0; i < keys.size(); i++) {
		 lst1=new ArrayList<Object>();
		 lst1.add(keys.get(i));
		 lst1.add(values.get(i));
		 lst.add(lst1);
	}
	
		
	return lst;		
	}								
	
	



	@RequestMapping(value = "/responsecode", method = RequestMethod.GET  /*, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json"*/)
	public List<Object> showAllApis() throws ParseException {
		

		
		return null;
	}
	
	
	

	@RequestMapping(value = "/roomlist", method = RequestMethod.GET  /*, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json"*/)
	public List<Object> roomList() throws ParseException {
		List<Object> lst1=new ArrayList<>();
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		ResultSet results = session.execute("SELECT roomid FROM room");
				
		for (Row row : results) {
			lst1.add(row.getInt(0));
					
		}
		return lst1;
	}
	
	
	
	
	
	@RequestMapping(value = "/tempdetail/{id}", method = RequestMethod.POST  , produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public List<List<Object>> tempDetail(@PathVariable int id) throws ParseException, java.text.ParseException {
		List<List<Object>> lst=new ArrayList<List<Object>>();
		List<Object> lst1=new ArrayList<Object>();
		lst1.add("Time");
		lst1.add("Temperature");
		lst.add(lst1);
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		PreparedStatement statement = session
				.prepare("select * from temperature where roomid =? ALLOW FILTERING");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		
		ResultSet results = session.execute(boundStatement.bind(id));
				
		for (Row row : results) {
			lst1=new ArrayList<Object>();
			
			double freq = row.getDouble("freq_val");
			Date tm = row.getTimestamp("time");
			String time=TimeProvider.getTime(tm.toString());
			lst1.add(time);
			lst1.add(freq);
			lst.add(lst1);
		}
		return lst;
	}
	
	
	@RequestMapping(value = "/pressdetail/{id}", method = RequestMethod.POST  , produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public List<List<Object>> pressDetail(@PathVariable int id) throws ParseException, java.text.ParseException {
		List<List<Object>> lst=new ArrayList<List<Object>>();
		List<Object> lst1=new ArrayList<Object>();
		lst1.add("Time");
		lst1.add("Pressure");
		lst.add(lst1);
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		PreparedStatement statement = session
				.prepare("select * from pressure where roomid =? ALLOW FILTERING");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		
		ResultSet results = session.execute(boundStatement.bind(id));
				
		for (Row row : results) {
			lst1=new ArrayList<Object>();
			
			double freq = row.getDouble("freq_val");
			Date tm = row.getTimestamp("time");
			String time=TimeProvider.getTime(tm.toString());
			lst1.add(time);
			lst1.add(freq);
			lst.add(lst1);
		}
		return lst;
	}
	@RequestMapping(value = "/luxdetail/{id}", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public List<List<Object>> luxDetail(@PathVariable int id) throws ParseException, java.text.ParseException {
		List<List<Object>> lst=new ArrayList<List<Object>>();
		List<Object> lst1=new ArrayList<Object>();
		lst1.add("Time");
		lst1.add("Lux");
		lst.add(lst1);
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");

		PreparedStatement statement = session
				.prepare("select * from lux where roomid =? ALLOW FILTERING");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		
		ResultSet results = session.execute(boundStatement.bind(id));
				
		for (Row row : results) {
			lst1=new ArrayList<Object>();
			
			double freq = row.getDouble("freq_val");
			Date tm = row.getTimestamp("time");
			String time=TimeProvider.getTime(tm.toString());
			lst1.add(time);
			lst1.add(freq);
			lst.add(lst1);
		}
		return lst;
	}
	@RequestMapping(value = "/humidetail/{id}", method = RequestMethod.POST  , produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public List<List<Object>> humiDetail(@PathVariable int id) throws ParseException, java.text.ParseException {
		List<List<Object>> lst=new ArrayList<List<Object>>();
		List<Object> lst1=new ArrayList<Object>();
		lst1.add("Time");
		lst1.add("Humidity");
		lst.add(lst1);
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		

		PreparedStatement statement = session
				.prepare("select * from humidity where roomid =? ALLOW FILTERING");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		
		ResultSet results = session.execute(boundStatement.bind(id));
		
				
		for (Row row : results) {
			lst1=new ArrayList<Object>();
			
			double freq = row.getDouble("freq_val");
			Date tm = row.getTimestamp("time");
			String time=TimeProvider.getTime(tm.toString());
			lst1.add(time);
			lst1.add(freq);
			lst.add(lst1);
		}
		return lst;
	}
	
	
	
	
}
