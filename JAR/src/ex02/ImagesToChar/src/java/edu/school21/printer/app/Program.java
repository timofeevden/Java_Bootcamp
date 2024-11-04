package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.ArgsChecker;
import edu.school21.printer.logic.ColorConverter;
import java.io.File;
import java.io.IOException;

public class Program {
	public static void main(String[] args) {
		try {
			ArgsChecker arguments = new ArgsChecker();
			JCommander.newBuilder().addObject(arguments).build().parse(args);
			File fileImage = new File("target/resources/it.bmp");
			ColorConverter colorConverter = new ColorConverter(arguments.getWhite(), arguments.getBlack(), fileImage);
			colorConverter.print();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}