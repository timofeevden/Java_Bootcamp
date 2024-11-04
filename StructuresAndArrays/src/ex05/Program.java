import java.util.Scanner;

public class Program {
	private static final int MAX_STUDENTS = 10;
	private static final int MAX_WEEK_LESSONS = 10;
	private static final int DAYS_IN_MONTH = 30;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String[] students = new String[MAX_STUDENTS];
		int cntStudents = takeStudentList(students, scan);
		int[] days = fillWeekDays();
		int[][] lessons = new int[MAX_WEEK_LESSONS][2];
		int cntLessons = takeLessons(lessons, scan);
		sortLessons(lessons, cntLessons);

		int[][] visits = takeVisits(lessons, cntLessons, students, cntStudents, scan);
		scan.close();

		printTable(days, students, cntStudents, lessons, cntLessons, visits);
	}


	private static int takeStudentList(String[] students, Scanner scan) {
		String inputName = "";
		int cnt = 0;

		for (; cnt < MAX_STUDENTS; ++cnt) {
			inputName = scan.next();

			if (inputName.equals(".")) {
				break;
			} else if (inputName.length() > 10) {
				System.err.println("Name of student more than maximum symbols allowed!");
				System.exit(-1);
			}
			students[cnt] = inputName;
		}

		if (cnt > MAX_STUDENTS) {
			System.err.println("Students more than maximum!");
			System.exit(-1);
		}

		return cnt;
	}


	private static int[] fillWeekDays() {
		int[] days = new int[DAYS_IN_MONTH];
		for (int i = 0, day = 2; i < 30; ++i, ++day) {
			if (day > 7) day = 1;
			days[i] = day;
		}

		return days;
	}


	private static int takeLessons(int[][] lessons, Scanner scan) {
		String input = "";
		int i = 0;

		for (; i < MAX_WEEK_LESSONS; ++i) {
			input = scan.next();
			if (input.equals(".")) break;
			lessons[i][0] = (input.toCharArray()[0] - (char)'0');

			if (lessons[i][0] < 1 || lessons[i][0] > 6) {
				System.err.println("Incorrect time for lesson!");
				System.exit(-1);
			}

			String dayOfWeek = scan.next();
			lessons[i][1] = getNumberOfWeekDay(dayOfWeek);
		}

		return i;
	}


	private static int getNumberOfWeekDay(String dayOfWeek) {
		int result = -1;
		if (dayOfWeek.equals("MO") || dayOfWeek.equals("Mo")) {
			result = 1;
		} else if (dayOfWeek.equals("TU") || dayOfWeek.equals("Tu")) {
			result = 2;
		} else if (dayOfWeek.equals("WE") || dayOfWeek.equals("We")) {
			result = 3;
		} else if (dayOfWeek.equals("TH") || dayOfWeek.equals("Th")) {
			result = 4;
		} else if (dayOfWeek.equals("FR") || dayOfWeek.equals("Fr")) {
			result = 5;
		} else if (dayOfWeek.equals("SA") || dayOfWeek.equals("Sa")) {
			result = 6;
		} else if (dayOfWeek.equals("SU") || dayOfWeek.equals("Su")) {
			result = 7;
		}

		return result;
	}


	private static void sortLessons(int[][] lessons, int cntLessons) {
	    for (int i = 0; i < cntLessons - 1; ++i) {
            for (int j = i + 1; j < cntLessons; ++j) {
                if (lessons[i][1] > lessons[j][1] || (lessons[i][1] == lessons[j][1] && lessons[i][0] > lessons[j][0])) {
                    int tmpHour = lessons[i][0];
                    int tmpDay = lessons[i][1];
                    lessons[i][0] = lessons[j][0];
                    lessons[i][1] = lessons[j][1];
                    lessons[j][0] = tmpHour;
                    lessons[j][1] = tmpDay;
                }
            }
        }
    }


	private static int[][] takeVisits(int[][] lessons, int cntLessons, String[] students, int cntStudents, Scanner scan) {
		int[][] visits = new int[cntStudents * MAX_WEEK_LESSONS * 5][4];

		for (int vi = 0; scan.hasNext(); ++vi) {
			String input = scan.next();
			if (input.equals(".")) {
				if (vi == 0) visits = null;
				break;
			}
			for (int j = 0; j < cntStudents; ++j) {
				if (students[j].equals(input)) {
					visits[vi][0] = j;
					input = scan.next();
					visits[vi][1] = (input.toCharArray()[0] - (char)'0');
					input = scan.next();
					for (int q = 0; q < input.length(); ++q) {
						visits[vi][2] *= 10;
						visits[vi][2] += (input.toCharArray()[q] - (char)'0');
					}
					input = scan.next();
					if (input.equals("HERE")) {
						visits[vi][3] = 1;
					} else {
						visits[vi][3] = -1;
					}
					break;
				}
			}

		}

		return visits;
	}


	private static void printTable(int[] days, String[] students, int cntStudents, int[][] lessons, int cntLessons, int[][] visits) {
		for (int k = -1; k < cntStudents; ++k, System.out.printf("\n")) {
			if (k == -1) {
				System.out.printf("          ");
			} else {
				System.out.printf("%s", students[k]);
				for (int q = 0; q < 10 - students[k].length(); ++q)
					System.out.printf(" ");
			}
			for (int day = 0; day < DAYS_IN_MONTH; ++day) {
				for (int i = 0; i < cntLessons; ++i) {
					if (days[day] == lessons[i][1]) {
						if (k == -1) {
							System.out.printf("%d:00 ", lessons[i][0]);
							printDayOfWeek(lessons[i][1]);
							if (day + 1 < 10) System.out.printf(" ");
							System.out.printf("%d|", day + 1);
						} else {
							if (visits != null) {
								System.out.printf("        ");
								int maxCntVisits = cntStudents * MAX_WEEK_LESSONS * 5;
								Boolean wasWrite = false;

								for (int t = 0; t < maxCntVisits ; ++t) {
									if (visits[t][0] == k && visits[t][2] == day + 1 && visits[t][1] == lessons[i][0]) {
										if (wasWrite == true) System.out.printf("        ");
										if (visits[t][3] != -1) System.out.printf(" ");
										System.out.printf("%d|", visits[t][3]);
										wasWrite = true;
									}
								}
								if (wasWrite == false) System.out.printf("  |");
							} else {
								System.out.printf("         |");
							}
						}
					}
				}
			}
		}
	}


	private static void printDayOfWeek(int day) {
		if (day == 1) {
			System.out.printf("MO ");
		} else if (day == 2) {
			System.out.printf("TU ");
		} else if (day == 3) {
			System.out.printf("WE ");
		} else if (day == 4) {
			System.out.printf("TH ");
		} else if (day == 5) {
			System.out.printf("FR ");
		} else if (day == 6) {
			System.out.printf("SA ");
		} else if (day == 7) {
			System.out.printf("SU ");
		}
	}
}