package ex00;

import java.util.Scanner;
import java.util.Map;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Program {
	public static void main(String[] args) {
		try {
			SignaturesReader signaturesReader = new SignaturesReader();
			Scanner scanner = new Scanner(System.in);
			String input = "";

			while (!input.equals("42")) {
				System.out.print("-> ");

				if (scanner.hasNext() == false) {
					break;
				}
				input = scanner.nextLine();

				if (!input.equals("42")) {
					try {
						String result = signaturesReader.checkSignature(input);
						if (result == "") {
							System.out.println("UNDEFINED");
						} else {
							signaturesReader.writeResultToFile(result);
							System.out.println("PROCESSED");
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			signaturesReader.closeOutput();
			scanner.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}


