package com.demo.web.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class TimestampUtilTest {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Test
	void parseTimestampTest() {

		Timestamp timestamp = null;
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT).parse("2020-04-01 12:00:00");
			timestamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals(TimestampUtil.parseTimestamp("2020-04-01"), timestamp);
		assertNull(TimestampUtil.parseTimestamp("2020-04-"));
		assertNull(TimestampUtil.parseTimestamp(null));

	}

	@Test
	void getNextDateInTest() {
		Timestamp result = TimestampUtil.getNextDateIn();
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		if (currentTimestamp.toLocalDateTime().getHour() < 12) {
			assertTrue(result.before(currentTimestamp));
			assertEquals(currentTimestamp.toLocalDateTime().getDayOfYear(),
					result.toLocalDateTime().getDayOfYear());
		} else {
			assertTrue(result.after(currentTimestamp));
			assertEquals(currentTimestamp.toLocalDateTime().plusDays(1).getDayOfYear(),
					result.toLocalDateTime().getDayOfYear());
		}
		assertEquals(result.toLocalDateTime().getHour(), 12);
		assertEquals(result.toLocalDateTime().getMinute(), 00);
		assertEquals(result.toLocalDateTime().getSecond(), 00);
		
	}

	@Test
	void getNextDateOutTest() {
		Timestamp result = TimestampUtil.getNextDateOut();
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		if (currentTimestamp.toLocalDateTime().getHour() < 12) {
			assertTrue(result.before(currentTimestamp));
			assertEquals(currentTimestamp.toLocalDateTime().plusDays(1).getDayOfYear(),
					result.toLocalDateTime().getDayOfYear());
		} else {
			assertTrue(result.after(currentTimestamp));
			assertEquals(currentTimestamp.toLocalDateTime().plusDays(2).getDayOfYear(),
					result.toLocalDateTime().getDayOfYear());
		}
		assertEquals(result.toLocalDateTime().getHour(), 12);
		assertEquals(result.toLocalDateTime().getMinute(), 00);
		assertEquals(result.toLocalDateTime().getSecond(), 00);
		
	}

	@Test
	void getCurrentShiftChangeTimestampTest() {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		Timestamp result = TimestampUtil.getCurrentShiftChangeTimestamp();
		
		assertEquals(result.toLocalDateTime().getDayOfYear(),
				currentTimestamp.toLocalDateTime().getDayOfYear());
		
		assertEquals(result.toLocalDateTime().getHour(), 12);
		assertEquals(result.toLocalDateTime().getMinute(), 00);
		assertEquals(result.toLocalDateTime().getSecond(), 00);
		
	}

}
