import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		int cntResult = 0;

		while (number != 42) {
			if (number > 1 && isPrime(sumDigits(number))) {
				++cntResult;
			}
			number = scan.nextInt();
		}
		scan.close();

		System.out.println("Count of coffee-request - " + cntResult);
	}

	private static boolean isPrime(int number) {
		if (number <= 1) {
			return false;
		} else if (number == 2) {
			return true;
		} else {
			for (int i = 2; i * i <= number; ++i) {
				if (number % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

	private static int sumDigits(int number) {
		int sum = 0;

		if (number < 0) {
			number *= -1;
		}
		while (number > 0) {
			sum += number % 10;
			number /= 10;
		}

		return sum;
	}
}