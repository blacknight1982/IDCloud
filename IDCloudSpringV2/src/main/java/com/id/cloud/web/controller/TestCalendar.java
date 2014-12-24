package com.id.cloud.web.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class TestCalendar {

	public static void main(String[] args) {
		/*Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		System.out.println(new java.sql.Date(today.getTimeInMillis()));
		UUID idOne = UUID.randomUUID();
	    UUID idTwo = UUID.randomUUID();
	    System.out.println("UUID One: " + idOne);
	    System.out.println("UUID Two: " + idTwo);*/
	   // System.out.println("UUID One: " + id3);
	   // System.out.println("UUID Two: " + id4);
		try {
            
            Robot robot = new Robot();
            // Creates the delay of 5 sec so that you can open notepad before
            // Robot start writting
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_H);
            robot.keyPress(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyPress(KeyEvent.VK_B);
            robot.keyPress(KeyEvent.VK_U);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyPress(KeyEvent.VK_Y);
            robot.mouseMove(40, 130);
        } catch (AWTException e) {
            e.printStackTrace();
        }
	    
	}

}
