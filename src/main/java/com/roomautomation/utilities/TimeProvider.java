package com.roomautomation.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeProvider {
	
	public  static String getTime(String str) throws ParseException
	{
		
		  DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
          Date date = (Date)formatter.parse(str);      
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
          Integer hrs=(cal.get(Calendar.HOUR));
          Integer min=cal.get(Calendar.MINUTE);
          return formatedDate+":"+hrs.toString()+":"+min.toString();
	}

}
