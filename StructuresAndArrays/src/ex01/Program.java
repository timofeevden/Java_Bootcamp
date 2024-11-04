import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		scan.close();

		if (number <= 1) {
			System.out.println("Illegal Argument");
			System.exit(-1);
		} else if (number == 2) {
			System.out.println("true 1");
		} else {
			int i = 2;
			for (; i * i <= number; ++i) {
				if (number % i == 0) {
					System.out.println("false " + (i - 1));
					System.exit(0);
				}
			}

			System.out.println("true " + (i - 1));
		}
	}
}