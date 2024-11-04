package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
	private NumberWorker numberWorker;

    @BeforeEach
    void beforeEachMethod() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 17})
    public void isPrimeForPrimes(int number) {
        Assertions.assertTrue(numberWorker.isPrime(number), String.valueOf(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 20})
    public void isPrimeForNotPrimes(int number) {
        Assertions.assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -5, -10})
    public void isPrimeForIncorrectNumbers(int number) {
        Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void digitsSumTest(int number, int result) {
        Assertions.assertEquals(numberWorker.digitsSum(number), result);
    }
}