package ex01;

import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Program {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Need 2 files to compare!");
			System.exit(-1);
		}

		try {
			FilesComparator fileComparator = new FilesComparator(args[0], args[1]);
			fileComparator.fillDictionary();
			fileComparator.writeDictionary();
			fileComparator.fillWordCounts();
			double similarity = fileComparator.CalculateSimilarity();
			similarity = Math.floor(similarity * 100.0) / 100.0;
			System.out.println("Similarity = " + similarity);
		} catch(Exception e) {
			System.out.println(e);
		}

	}

}


