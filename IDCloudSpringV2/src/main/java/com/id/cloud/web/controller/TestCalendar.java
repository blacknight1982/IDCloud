package com.id.cloud.web.controller;

import java.util.Calendar;
import java.util.UUID;

public class TestCalendar {

	public static void main(String[] args) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		System.out.println(new java.sql.Date(today.getTimeInMillis()));
		UUID idOne = UUID.randomUUID();
	    UUID idTwo = UUID.randomUUID();
	    System.out.println("UUID One: " + idOne);
	    System.out.println("UUID Two: " + idTwo);
	  //  System.out.println("UUID One: " + id3);
	   // System.out.println("UUID Two: " + id4);
	}

}
