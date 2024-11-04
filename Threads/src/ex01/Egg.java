package ex01;

public class Egg extends Thread {
	private final int cnt;
	ProducerConsumer producerConsumer;

	public Egg(int count, ProducerConsumer producerConsumer) {
		cnt = count;
		this.producerConsumer = producerConsumer;
	}

	@Override
	public void run() {
		for(int i = 0; i < cnt; ++i) {
			try {
				producerConsumer.sayEgg();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
