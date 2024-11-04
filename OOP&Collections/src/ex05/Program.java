package ex05;

public class Program {

	public static void main(String[] args) {
		boolean isDevMode = false;

		if (args.length > 0) {
			for (int i = 0; i < args.length; ++i) {
				if (args[i].equals("--profile=dev")) {
					isDevMode = true;
				} else {
					System.err.println("Unknown option: " + args[i]);
					System.exit(0);
				}
			}
		}
		Menu menu = new Menu(isDevMode);
		menu.run();
	}
}