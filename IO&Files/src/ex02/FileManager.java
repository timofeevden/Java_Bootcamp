package ex02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;

public class FileManager {
	private Path dir;

	public boolean setDirectory(String pathStr) {
		if (pathStr == null || pathStr == "") {
			return false;
		}
		dir = Paths.get(pathStr).toAbsolutePath().normalize();

		if (dir.isAbsolute() == false || Files.exists(dir) == false) {
			dir = null;
			return false;
		}

		return true;
	}

	public String getDirectory() {
		return dir.toString();
	}

	public boolean cd(String newPathStr) {
		Path newPath = Paths.get(newPathStr);
		boolean result = false;

		if (newPath.isAbsolute() && Files.isDirectory(newPath)) {
			dir = newPath.normalize();
			result = true;
		} else {
			Path tmp = dir.resolve(newPath).normalize();
			if (Files.exists(tmp) && Files.isDirectory(tmp)) {
				dir = tmp;
				result = true;
			} else {
				System.out.println("cd: No such directory!");
			}
		}

		return result;
	}

	public void ls() {
		try {
			DirectoryStream<Path> allFiles = Files.newDirectoryStream(dir);
			for (Path path : allFiles) {
				char firstSymbol = path.getFileName().toString().charAt(0);
				if (firstSymbol != '.') {
					long size = getSizeOfPath(path);
					double dSize = size;
					if (dSize > 100) {
						dSize = Math.round((double)size / 100.0) * 100.0;
					}
					System.out.println(path.getFileName() + " " + dSize / 1000.0 + " KB");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private long getSizeOfPath(Path path) {
		long size = 0;
		try {
			char first = path.getFileName().toString().charAt(0);
			if (first != '.') {
				if (Files.isDirectory(path)) {
					DirectoryStream<Path> allFiles = Files.newDirectoryStream(path);
					for (Path file : allFiles) {
						size += getSizeOfPath(file);
					}
				} else if (Files.exists(path)) {
					size = Files.size(path);
				}
			}
		} catch(Exception e) {
		//	System.out.println(e);
		}

		return size;
	}

	public void mv(String from, String to) {
		if (from == null || to == null || from == "" || to == "") {
			System.out.println("mv: Can't read path from/to !");
			return;
		}
		Path pathFrom = dir.resolve(Paths.get(from));
		Path pathTo = dir.resolve(Paths.get(to));

		if (Files.isDirectory(pathFrom)) {
			if (Files.isDirectory(pathTo) == false && Files.exists(pathTo)) {
					System.out.println("mv: " + to + " is not a directory!");
					return;
			}
		} else if (Files.isDirectory(pathTo)) {
			pathTo = pathTo.resolve(pathFrom.getFileName());
		}

		try {
			Files.move(pathFrom, pathTo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("mv: " + from + " No such file!");
		}
	}

}
