package ex00;


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

					Thread eggThread = new Egg(cnt);
					Thread henThread = new Hen(cnt);
					eggThread.start();
					henThread.start();
					HumanPrint(cnt);
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

	public static void HumanPrint(int cnt) {
		for(int i = 0; i < cnt; ++i) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("Human");
		}
	}

}
