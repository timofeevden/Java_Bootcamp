package ex01;

public class Program {
	public static void main(String[] args) {
		if (args.length == 1) {
			String[] line = args[0].split("=");
			if (line[0].equals("--count")) {
				int cnt = 0;
				try {
					cnt = Integer.parseInt(line[1]);
					if (cnt <= 0) {
						System.out.println("Count must be > 0 !");
						System.exit(-1);
					}
					ProducerConsumer producerConsumer = new ProducerConsumer();
					Thread eggThread = new Egg(cnt, producerConsumer);
					Thread henThread = new Hen(cnt, producerConsumer);
					eggThread.start();
					henThread.start();
				} catch (Exception e) {
					System.out.println(e);
				}
				
			} else {
				System.out.println("You need to write format: --count=NUMBER !");
			}
		} else {
			System.out.println("You need to write only one flag: --count=NUMBER !");
		}
	}

}
