package com.roomautomation.context;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.roomautomation.provider.DotNetClient;
import com.roomautomation.utilities.IdProvider;

public class TemperatureServiceThread extends Thread{
	List<Integer> al=new ArrayList<>();
	
	@Override
	public void run() {
		
		
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		Session session = cluster.connect("roomautomation");
		ResultSet results = session.execute("select roomid from room");
		 if (!results.isExhausted()){
		for(Row row :results)
			{
				al.add(row.getInt("roomid"));
				
             
			}	
		 }
		
		
			try { 
				while(true){
				for (Integer i : al) { 
					
					DotNetClient.getTemperatureFeq(IdProvider.getTempId(),i);
				    }
				//Thread.sleep(3000);
				Thread.sleep(DotNetClient.map.get("Temperature")*60000);
				
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}