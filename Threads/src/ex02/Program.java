package ex02;

import java.util.Random;

public class Program {
	private static int[] arr;

	public static void main(String[] args) {
		if (args.length == 2) {
			String[] arg1 = args[0].split("=");
			String[] arg2 = args[1].split("=");

			if (arg1[0].equals("--arraySize") && arg2[0].equals("--threadsCount")) {
				int arraySize = 0;
				int threadsCnt = 0;
				try {
					arraySize = Integer.parseInt(arg1[1]);
					threadsCnt = Integer.parseInt(arg2[1]);
					
					if (arraySize <= 0 || arraySize > 2000000 || threadsCnt <= 0 || threadsCnt > arraySize) {
						System.out.println("arraySize must be > 0 and < 2000000, arraySize must be greatear than threadsCount!");
						System.exit(-1);
					}
					arr = createRandomArray(arraySize);
					int sum = 0;
					for(int i = 0; i < arr.length; ++i) {
						sum += arr[i];
					//	System.out.printf(arr[i] + " ");
					}
					
					System.out.println("Sum = " + sum);
					int resultSum = calcThreadSumArray(threadsCnt);
					System.out.println("Sum by threads: " + resultSum);
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				System.out.println("You need to write format: --arraySize=NUMBER --threadsCount=NUMBER !");
			}
		} else {
			System.out.println("You need to write only 2 flags: --arraySize=NUMBER --threadsCount=NUMBER !");
		}
	}

	public static int[] createRandomArray(int size) {
		Random rand = new Random();
		int[] arr = rand.ints(size, -1000, 1000).toArray();
		return arr;
	}

	public static int calcThreadSumArray( int threadsCnt) {
		int threadElements = arr.length / threadsCnt;
		SumThread[] threads = new SumThread[threadsCnt];
		int from = 0, to = 0;
		for(int i = 0; i < threadsCnt - 1; ++i) {
			from = i * threadElements;
			to = (i + 1) * threadElements;
			threads[i] = new SumThread(arr, from, to);
			threads[i].start();
		}
		from = (threadsCnt - 1) * threadElements;
		to = arr.length;
		threads[threadsCnt - 1] = new SumThread(arr, from, to);
		threads[threadsCnt - 1].start();

		int resultSum = 0;
		for(int i = 0; i < threadsCnt; ++i) {
			try {
				threads[i].join();
				resultSum += threads[i].getSum();
				//System.out.println("Thread " + i + ": from " + threads[i].getFrom() + " to " + threads[i].getTo() + " sum is " + threads[i].getSum());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return resultSum;
	}

}
