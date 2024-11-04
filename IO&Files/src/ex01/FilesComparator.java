package ex01;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class FilesComparator {
	private Set<String> dictionarySet;
	private ArrayList<String> dictionaryList;
	private int[] cntFirstWords;
	private int[] cntSecondWords;
	private BufferedReader readerOne;
	private BufferedReader readerTwo;
	private BufferedWriter dictionaryWriter;
	private String fileOne;
	private String fileTwo;

	public FilesComparator(String file1, String file2) {
		fileOne = file1;
		fileTwo = file2;
	}

	public void fillDictionary() throws IOException {
		dictionarySet = new HashSet<>();
		readerOne = new BufferedReader(new FileReader(fileOne));
		readerTwo = new BufferedReader(new FileReader(fileTwo));
		fillDictionary(readerOne);
		fillDictionary(readerTwo);
		readerOne.close();
		readerTwo.close();
		dictionaryList = new ArrayList<String>(dictionarySet);
		Collections.sort(dictionaryList);
	}

	private void fillDictionary(BufferedReader reader) throws IOException {
		while (reader.ready()) {
			String[] line = reader.readLine().split("\\s+");	//	replaceAll("\\p{Punct}", "")
			for (String word : line) {
				if (!dictionarySet.contains(word) && !word.isEmpty()) {
					dictionarySet.add(word);
				}
			}
		}
	}


	public void writeDictionary() throws IOException {
		dictionaryWriter = new BufferedWriter(new FileWriter("ex01/dictionary.txt"));

		for(String word : dictionaryList) {
			dictionaryWriter.write(word);
			if (dictionaryList.indexOf(word) != dictionaryList.size() -1) {
				dictionaryWriter.write(", ");
			}
		}
		
		dictionaryWriter.close();
	}



	public void fillWordCounts() throws IOException {
		readerOne = new BufferedReader(new FileReader(fileOne));
		readerTwo = new BufferedReader(new FileReader(fileTwo));
		cntFirstWords = new int[dictionaryList.size()];
		cntSecondWords = new int[dictionaryList.size()];
		fillWordCounts(readerOne, cntFirstWords);
		fillWordCounts(readerTwo, cntSecondWords);
		readerOne.close();
		readerTwo.close();
	}



	private void fillWordCounts(BufferedReader reader, int[] countsWords) throws IOException {
		while (reader.ready()) {
			String[] line = reader.readLine().split("\\s+");	//	.replaceAll("\\p{Punct}", "")
			for (String word : line) {
				if (word != "" && dictionaryList.contains(word)) {
					countsWords[dictionaryList.indexOf(word)]++;
				}
			}	
		}
	}

	public double CalculateSimilarity() {
		double numerator = 0;
		double denominator = 0, denominator1 = 0, denominator2 = 0;
		
		for (int i = 0; i < cntFirstWords.length; ++i) {
			numerator += cntFirstWords[i] * cntSecondWords[i];
			denominator1 += cntFirstWords[i] * cntFirstWords[i];
			denominator2 += cntSecondWords[i] * cntSecondWords[i];
		}
		if (denominator1 == 0 || denominator2 == 0) {
			return 0;
		}
		denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
		double result = numerator / denominator;

		return result;
	}

}


