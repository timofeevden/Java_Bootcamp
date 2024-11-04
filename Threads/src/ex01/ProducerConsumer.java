package ex01;

public class ProducerConsumer {
	private boolean isEgg = false;

	public synchronized void sayEgg() throws InterruptedException {
		while(isEgg == true) {
			wait();
		}
		System.out.println("Egg");
		isEgg = true;
		notify();
	}

	public synchronized void sayHen() throws InterruptedException {
		while(isEgg == false) {
			wait();
		}
		System.out.println("Hen");
		isEgg = false;
		notify();
	}
}
