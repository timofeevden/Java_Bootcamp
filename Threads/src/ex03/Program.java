package ex03;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Program {
	private static Thread[] threads;
	private static final Object lock = new Object();
	private static int numberOfFile = 0;

	public static void main(String[] args) {
		int threadsCnt = checkArgument(args);
		try {
			List<String> urlsStr = new ArrayList<>();
			urlsStr = parseUrls();
			threads = new Thread[threadsCnt];
			final List<String> urls = urlsStr;
			int amountFiles = urlsStr.size();

			for (int i = 0; i < threadsCnt; ++i) {
				threads[i] = new Thread(() -> {
					for (String url : urls) {
						synchronized (lock) {
							download(url, numberOfFile);
							if (numberOfFile == amountFiles) {
								break;
							}
							++numberOfFile;
						}
					}
				});
				threads[i].start();
			}

			for (int j = 0; j < threads.length; ++j) {
				threads[j].join();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int checkArgument(String[] args) {
		if (args.length != 1) {
			System.out.println("You need to write only one flag: --threadsCount=NUMBER !");
			System.exit(-1);
		}
		String[] line = args[0].split("=");

		if (line[0].equals("--threadsCount") == false) {
			System.out.println("You need to write format: --threadsCount=NUMBER !");
			System.exit(-1);
		}
		int threadsCnt = 0;
		try {
			threadsCnt = Integer.parseInt(line[1]);
			if (threadsCnt <= 0) {
				System.out.println("threadsCount must be > 0 !");
				System.exit(-1);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}

		return threadsCnt;
	}

	public static List<String>parseUrls() {
		List<String> urls = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader (new FileReader("ex03/files_urls.txt"));
			while(reader.ready()) {
				String line = reader.readLine().trim();
				urls.add(line);
			}
			reader.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return urls;
	}

	private static void download(String urlStr, int numberOfUrl) {
		System.out.println(Thread.currentThread().getName() + " start download file number " + numberOfUrl);
		try {
			URL url = new URL(urlStr);
			InputStream inputStream = url.openStream();
			int indx = urlStr.lastIndexOf("/");
			if (indx == 0 || indx == urlStr.length()) {
				System.out.println(Thread.currentThread().getName() + " error download file number " + numberOfUrl);
				return;
			}
			++indx;
			String filename = urlStr.substring(indx);
			Files.copy(inputStream, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
			System.out.println(Thread.currentThread().getName() + " finish download file number " + numberOfUrl);
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " error download file number " + numberOfUrl + " : " + e.getMessage());
		}
	}

}
