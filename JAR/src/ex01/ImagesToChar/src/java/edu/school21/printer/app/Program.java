package edu.school21.printer.app;

import edu.school21.printer.logic.Converter;
import java.io.File;
import java.io.IOException;

public class Program {
	public static void main(String[] args) {
		checkAgrs(args);
		try {
			File fileImage = new File("target/resources/it.bmp");
			char whiteSymbol = args[0].charAt(0);
			char blackSymbol = args[1].charAt(0);
			Converter converter = new Converter(whiteSymbol, blackSymbol, fileImage);
			converter.print();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void checkAgrs(String[] args) {
		if (args.length != 2 && args[0].length() != 1 && args[1].length() != 1) {
			System.out.println("Uncorrect arguments! Need format : \"WhiteSymbol BlackSymbol PathToBMPFile\"  -  Example: \". 0 \"");
			System.exit(-1);
		}
	}
}