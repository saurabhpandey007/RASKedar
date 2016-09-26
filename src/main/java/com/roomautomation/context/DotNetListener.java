package com.roomautomation.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.roomautomation.provider.DotNetClient;

public class DotNetListener implements ServletContextListener {
static int count=0;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		count=0;
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		count++;
		
		if(count==1)
		{
			TemperatureServiceThread tst=new TemperatureServiceThread();
			PressureServiceThread pst=new PressureServiceThread();
			HumidityServiceThread hst=new HumidityServiceThread();
			LuxServiceThread lst=new LuxServiceThread();
			
			tst.start();
			pst.start();
			hst.start();
			lst.start();
			
		}
		
	}

}
