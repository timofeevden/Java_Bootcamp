package ex00;

public class Hen extends Thread {
	private final int cnt;

	public Hen(int count) {
		cnt = count;
	}

	@Override
	public void run() {
		for(int i = 0; i < cnt; ++i) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("Hen");
		}
	}
}
