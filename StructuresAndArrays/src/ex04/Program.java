import java.util.Scanner;

public class Program {

	private static final int MAX_CODE_VALUE = 65535;

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		char[] str = scan.nextLine().toCharArray();
		scan.close();

		if (str.length == 0) {
			System.err.println("Empty string!");
			System.exit(-1);
		} else {
			int[] cntSymbols = new int[MAX_CODE_VALUE];
			checkAllSymbols(str, cntSymbols);
			int[] topSymbols = takeTopSymbols(cntSymbols);
			printResult(cntSymbols, topSymbols);
		}
	}


	private static void checkAllSymbols(char[] str, int[] cntSymbols) {
		for (int i = 0; i < str.length; ++i) {
			++cntSymbols[(int)str[i]];
		}
	}


    private static int[] takeTopSymbols(int[] cntSymbols) { 
	    int[] topSymbols = new int[10]; 

	    for (int i = 0; i < cntSymbols.length; ++i) {
	    	int indexToReplace = -1;
	        int value = cntSymbols[i];

	        for (int j = 0; j < topSymbols.length; ++j) { 
	            if (value != 0 && (cntSymbols[topSymbols[j]] == 0 || value > cntSymbols[topSymbols[j]])) { 
	                indexToReplace = j; 
	                break; 
	            } 
	        }

	        if (indexToReplace != -1) { 
	            for (int j = topSymbols.length - 1; j > indexToReplace; --j) { 
	                topSymbols[j] = topSymbols[j - 1]; 
	            } 
	            topSymbols[indexToReplace] = i; 
	        } 
	    } 

	    return topSymbols; 
	} 


	private static void printResult(int[] cntSymbols, int[] topSymbols) {
		int maxValue = cntSymbols[topSymbols[0]];

		if (maxValue > 999) {
			System.err.println("Word more than 999!");
			System.exit(-1);
		}

		int[] histogramValues = new int[10];

		for (int i = 0; i < topSymbols.length; ++i) {
			histogramValues[i] = 10 * cntSymbols[topSymbols[i]] / maxValue;
		}

		for (int i = 11; i  > 0; --i) {

			for (int k = 0; k < topSymbols.length; ++k) {
				System.out.printf(" ");
				if (i == histogramValues[k] + 1 && cntSymbols[topSymbols[k]] > 0) { 
					printNumber(cntSymbols[topSymbols[k]]);
				} else if (i <= histogramValues[k]) {
					System.out.printf("  #");
				}
			}
			System.out.printf("\n"); 
		}

		for (int j = 0; j < 10; ++j) {
			System.out.printf("   " + (char)(topSymbols[j]));
		}
		System.out.printf("\n"); 
	}


	private static void printNumber(int number) {
		if (number / 100 == 0) System.out.printf(" ");
		if (number / 10 == 0) System.out.printf(" ");
		System.out.printf("%d", number);
	}


}

