package com.ljmu.andre.SimulationHelpers.Packets;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

public class Main {
	
	private static final Logger logger = new Logger(Main.class);

    public static Scenarioo scenarioo;
	
	  public static void main(String[] args) {

		  try {

	            scenarioo = new Scenarioo();

	        } catch (Throwable e) {

	            e.printStackTrace();
	        }
	    }
}
