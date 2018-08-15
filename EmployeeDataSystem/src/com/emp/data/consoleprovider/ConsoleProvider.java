package com.emp.data.consoleprovider;

import java.io.Console;

public class ConsoleProvider {
	private static Console console = null;
	public static Console getConsole(){
		return console = System.console();
	}

}
