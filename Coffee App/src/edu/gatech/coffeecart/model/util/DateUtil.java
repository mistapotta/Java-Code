package edu.gatech.coffeecart.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date getDate(int daysFromNow) {
		return new Date((new Date()).getTime()+((long)daysFromNow*24l*3600l*1000l));
	}
	
	public static Date getDateWithoutTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			return format.parse(format.format(date));
		} catch(ParseException e) {
			e.printStackTrace();
			return date;
		}
	}
	
}
