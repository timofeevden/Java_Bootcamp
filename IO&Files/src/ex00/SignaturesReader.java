package ex00;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class SignaturesReader {
	private final String signaturesPath = "ex00/signatures.txt";
	private final String resultSignaturesPath = "ex00/result.txt";
	private Map<String, String> signatures;
	private FileOutputStream outStream;

	public SignaturesReader() throws Exception {
		try {
			FileInputStream inputFile = new FileInputStream(signaturesPath);
			Scanner scanner = new Scanner(inputFile);
			signatures = new HashMap<>();

			while (scanner.hasNextLine()) {
				String fullLine = scanner.nextLine();
				String[] lines = fullLine.split(",");
				lines[1] = lines[1].replaceAll("\\s", "");

				if (lines.length != 2) {
					throw new Exception("Uncorrect signature file!");
				}

				signatures.put(lines[0], lines[1]);
			}
			inputFile.close();
			outStream = new FileOutputStream(resultSignaturesPath);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
	}

	public String checkSignature(String path) throws Exception {
		String result = "";
		InputStream input = null;
		try {
			input = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return result;
		}
			String extension = "";
			for (int i = 0; i < 8; ++i) {
				extension += String.format("%02X", input.read());
			}
			//	System.out.println("format = " + extension);

			input.close();

			for (Map.Entry<String, String> entry : signatures.entrySet()) {
				if (entry.getValue().equals(extension.substring(0, entry.getValue().length()))) {
					result = entry.getKey();
				//	System.out.println("resut = " + result);
					break;
				}
			}

		return result;
	}

	public void writeResultToFile(String result) throws IOException {
		try {
			outStream.write(result.getBytes());
			outStream.write('\n');
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public void closeOutput() throws IOException {
		try {
			outStream.close();
		} catch (Exception e) {
			throw new IOException(e);
		}
	} 

}


