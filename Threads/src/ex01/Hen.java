package ex01;

public class Hen extends Thread {
	private final int cnt;
	ProducerConsumer producerConsumer;

	public Hen(int count, ProducerConsumer producerConsumer) {
		cnt = count;
		this.producerConsumer = producerConsumer;
	}

	@Override
	public void run() {
		for(int i = 0; i < cnt; ++i) {
			try {
				producerConsumer.sayHen();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
