package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ArgsChecker {
	@Parameter(
		names = "--white",
		description = "New color instead of white color",
		required = false
	)
	private String white;

	@Parameter(
		names = "--black",
		description = "New color instead of black color",
		required = false
	)
	private String black;

	public String getWhite() {
		return white;
	}

	public String getBlack() {
		return black;
	}

	public void print() {
		System.out.println("Args = " + white + ", " + black);
	}

}