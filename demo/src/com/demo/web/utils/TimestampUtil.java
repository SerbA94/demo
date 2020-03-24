package com.demo.web.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class TimestampUtil {

	private static final Logger log = Logger.getLogger(TimestampUtil.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String SHIFT_CHANGE_TIME = "12:00:00";

	/**
	 * 	Input string format      : "yyyy-MM-dd"
	 * 	Parsing timestamp format : "yyyy-MM-dd HH:mm:ss"
	 * 	Output timestamp format  : "yyyy-MM-dd 12:00:00"
	 * */
	public static Timestamp parseTimestamp(String timestampStr) {
		log.debug("Parsing starts.");
		Timestamp timestamp = null;
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT).parse(timestampStr + " " + SHIFT_CHANGE_TIME);
			timestamp = new Timestamp(date.getTime());
			log.trace("Timestamp timestamp --> " + timestamp);
		} catch (ParseException e) {
			log.error("Date parsing exception.");
			e.printStackTrace();
		}
		log.debug("Parsing finished.");
		return timestamp;
	}


}