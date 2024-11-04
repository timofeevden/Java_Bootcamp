import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputStr;
		int cntWeek = 1;
		long weeksMinGrades = 0;

		for (; cntWeek <= 18; ++cntWeek) {
			inputStr = scan.nextLine();

			if (inputStr.equals("42")) {
				break;
			} else if (inputStr.equals("Week " + cntWeek) == false) {
				System.out.println("Illegal Argument");
				System.exit(-1);
			}
			long minGrade = takeMinGrade(scan);
			weeksMinGrades += addGrade(minGrade, cntWeek);
		}

		scan.close();
		printProgress(weeksMinGrades, cntWeek);
	}


	private static long takeMinGrade(Scanner scan) {
		long minGrade = 9;

		for (int i = 0; i < 5; ++i) {
			long tmp = scan.nextLong();
			if (tmp < minGrade) {
				minGrade = tmp;
			}
		}
		scan.nextLine();

		return minGrade;
	}


	private static long addGrade(long grade, int cntWeek) {
		long ten = 1;

		for (int i = 1; i < cntWeek; ++i) {
			ten *= 10;
		}

		return grade * ten;
	}


	private static void printProgress(long weeksMinGrades, int cntWeek) {
		for (int i = 1; i < cntWeek; ++i) {
			System.out.printf("Week " + i + " ");
			long grade = weeksMinGrades % 10;
			weeksMinGrades /= 10;

			while (grade-- > 0) {
				System.out.printf("=");
			}
			System.out.println(">");
		}
	}
}