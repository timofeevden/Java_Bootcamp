package edu.school21.printer.logic;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;

public class Converter {
	private char whiteSymbol;
	private char blackSymbol;
	private BufferedImage image;

	public Converter(char whiteSymbol, char blackSymbol, File file) throws IOException {
		this.whiteSymbol = whiteSymbol;
		this.blackSymbol = blackSymbol;
		this.image = ImageIO.read(file);
	}

	public void print() {
		int width = image.getWidth();
		int height = image.getHeight();

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				int color = image.getRGB(j, i);

				if (color == Color.WHITE.getRGB()) {
					System.out.print(whiteSymbol);
				} else if (color == Color.BLACK.getRGB()) {
					System.out.print(blackSymbol);
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
	}
}