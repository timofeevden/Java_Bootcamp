package ex02;

public class SumThread extends Thread {
	private int localSum = 0;
	private final int[] arr;
	private final int from;
	private final int to;

	public SumThread(int[] arr, int from, int to) {
		this.arr = arr;
		this.from = from;
		this.to = to;
	}

	@Override
	public synchronized void run() {
		for(int i = from; i < to; ++i) {
			localSum += arr[i];
		}
		System.out.println(Thread.currentThread().getName().replace("-", " ") + ": from " + from + " to " + to + " sum is " + localSum);
	}

	public int getSum() {
		return localSum;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}
}
