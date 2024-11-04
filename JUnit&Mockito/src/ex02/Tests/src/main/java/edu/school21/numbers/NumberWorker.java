package edu.school21.numbers;

public class NumberWorker {
    public static boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Invalid number in method IsPrime");
        }
        for (int i = 2; i * i <= number; ++i) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int digitsSum(int number) {
        int sum = 0;
		if (number < 0) {
			number *= -1;
		}
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}