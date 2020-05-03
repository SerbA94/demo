package com.demo.db.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;


public class BookingTest {
	
	@InjectMocks
	private Booking booking = new Booking();
	private Room room = new Room();


	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}

	@Test
	void getExpiringDateTest() throws NoSuchFieldException, SecurityException {
		
		Timestamp currentTS = new Timestamp(System.currentTimeMillis());
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateOfBooking"), currentTS);
		assertEquals("Invalid expiring date.",
				Timestamp.valueOf(currentTS.toLocalDateTime().plusMinutes(5)),
				booking.getExpiringDate());
		
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateOfBooking"), null);
	    assertNull("Null expected.", booking.getExpiringDate());
	}
	
	@Test
	void getTotalBookingDaysTest() throws NoSuchFieldException, SecurityException {
		
		Timestamp currentTS = new Timestamp(System.currentTimeMillis());
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateIn"), currentTS);
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateOut"), 
	    		Timestamp.valueOf(currentTS.toLocalDateTime().plusDays(4)));
	    assertEquals("Invalid expiring date.", new Integer(4), booking.getTotalBookingDays());
	    
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateIn"), null);
	    assertNull("Null expected.", booking.getTotalBookingDays());
	    
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateOut"), null);
	    assertNull("Null expected.", booking.getTotalBookingDays());
	}
	
	@Test
	void getTotalPriceTest() throws NoSuchFieldException, SecurityException {
		
		Timestamp currentTS = new Timestamp(System.currentTimeMillis());
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateIn"), currentTS);
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateOut"), 
	    		Timestamp.valueOf(currentTS.toLocalDateTime().plusDays(4)));

	    FieldSetter.setField(room, room.getClass().getDeclaredField("price"), 1);
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("room"), room);
	    assertEquals("Invalid total price.", new Integer(4), booking.getTotalPrice());
	    
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateIn"), null);
	    assertNull("Null expected.", booking.getTotalPrice());
	    
	    FieldSetter.setField(booking, booking.getClass().getDeclaredField("dateOut"), null);
	    assertNull("Null expected.", booking.getTotalPrice());
	}

}
