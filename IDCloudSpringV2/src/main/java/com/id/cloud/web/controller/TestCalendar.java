package com.id.cloud.web.controller;

import java.util.Calendar;
import java.util.UUID;

public class TestCalendar {

	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().getTime());
		UUID idOne = UUID.randomUUID();
	    UUID idTwo = UUID.randomUUID();
	    System.out.println("UUID One: " + idOne);
	    System.out.println("UUID Two: " + idTwo);
	  //  System.out.println("UUID One: " + id3);
	   // System.out.println("UUID Two: " + id4);
	}

}
