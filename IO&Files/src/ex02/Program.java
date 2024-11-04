package ex02;

import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		String pathStr = takeArgumentPath(args);
		FileManager manager = new FileManager();

		if (manager.setDirectory(pathStr) == false) {
			System.out.println(pathStr + " is not absolute path!");
			System.exit(-1);
		}

		System.out.println(manager.getDirectory());
		runInputCommands(manager);
	}

	private static String takeArgumentPath(String[] args) {
		String flag = "--current-folder=";
		if (args.length != 1 || args[0].startsWith(flag) == false) {
			System.out.println("Need argument format: --current-folder=CURRENT_FOLDER");
			System.exit(-1);
		}
		String pathName = args[0].substring(flag.length());

		if (pathName == null) {
			System.out.println("Need argument format: --current-folder=CURRENT_FOLDER, argument-folder is empty!");
			System.exit(-1);	
		}

		return pathName;
	}

	private static void runInputCommands(FileManager manager) {
		Scanner scanner = new Scanner(System.in);
		String input = "", exit = "exit", ls = "ls", mv = "mv", cd = "cd", pwd = "pwd";
		System.out.print("-> ");

		while (scanner.hasNext()) {
			input = scanner.nextLine().replaceAll("\\s+", " ").trim();
			if (input != "") {
				String[] line = input.split(" ");
				if (line[0].equals(exit)) {
					break;
				} else if (line[0].equals(ls)) {
					if (line.length > 1) {
						System.out.println("ls: too many arguments!");
						continue;
					}
					manager.ls();
				} else if (line[0].equals(cd)) {
					boolean wasCD = false;
					if (line.length > 2) {
						System.out.println("cd: too many arguments!");
						continue;
					} else if (line.length == 1) {
						wasCD = manager.cd(System.getProperty("user.home"));
					} else {
						wasCD = manager.cd(line[1]);
					}
					if (wasCD) {
						System.out.println(manager.getDirectory());
					}
				}  else if (line[0].equals(mv)) {
					if (line.length != 3) {
						System.out.println("mv: Uncorrect arguments! try - mv FILE1 FILE2");
						continue;
					}
					manager.mv(line[1], line[2]);
				} else if (line[0].equals(pwd)) {
					System.out.println(manager.getDirectory());
				} else {
					System.out.println(line[0] + " : Command not found!");
				}
			}
			System.out.print("-> ");
		}
		scanner.close();
	}

}
