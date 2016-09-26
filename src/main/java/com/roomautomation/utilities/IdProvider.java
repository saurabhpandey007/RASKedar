package com.roomautomation.utilities;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class IdProvider {
	
	
	static	Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	static	Session session = cluster.connect("roomautomation");
	
	public static int  getTempId()
	{
		 int  tempid=1;
		
		ResultSet rs=session.execute("select MAX(tempid) from temperature");
				
		if (!rs.isExhausted()) {
		
			for (Row row : rs) {
				tempid = row.getInt(0);
				tempid = tempid + 1;
				}
             }
    	 
		
		 return tempid;
      }
		

	

	public static int  getPressId()
	{
		int pressid=1;
		ResultSet rs=session.execute("select MAX(pressid) from pressure");
		if (!rs.isExhausted()) {
			
			for (Row row : rs) {
            	pressid=row.getInt(0);
            	pressid=pressid+1;
                 
            }
   	 	} 
	
		
		 return pressid;
		
	}

	public static int  getHumidityId()
	{
		int humiid=0;
	
		ResultSet rs=session.execute("select MAX(humiid) from humidity");
		
		if (!rs.isExhausted()) {
			
			for (Row row : rs) {
            	humiid=row.getInt(0);
            	humiid=humiid+1;
                 
            }
		} 
	
		 return humiid;
	}

	public static int  getLuxId()
	{
		int luxid=0; 
	
		ResultSet rs=session.execute("select MAX(luxid) from lux");

		if (!rs.isExhausted()) {
			
			for (Row row : rs) {
            	luxid=row.getInt(0);
            	luxid=luxid+1;
                 
            }
   	 } 
		 
		 return luxid;

}
}
