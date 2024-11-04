package edu.school21.printer.logic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class ColorConverter {
	private Color whiteColor;
	private Color blackColor;
	private BufferedImage image;

	public ColorConverter(String white, String black, File file) throws IOException {
		try {
			if (white != null) {
				Field field = Class.forName("java.awt.Color").getField(white);
				whiteColor = (Color)field.get(null);
			} else {
				this.whiteColor = Color.WHITE;
			}

			if (black != null) {
				Field field = Class.forName("java.awt.Color").getField(black );
				blackColor = (Color)field.get(null);
			} else {
				this.blackColor = Color.BLACK;
			}
		} catch (Exception e) {
			throw new IOException("Can't find cpecifed color! Check arguments!");
		}

		this.image = ImageIO.read(file);
	}

	public void print() {
		int width = image.getWidth();
		int height = image.getHeight();

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				Color color = new Color(image.getRGB(j, i));

				if (color.equals(Color.WHITE)) {
					color = whiteColor;
				} else if (color.equals(Color.BLACK)) {
					color = blackColor;
				}
				Attribute backColor = BACK_COLOR(color.getRed(), color.getGreen(), color.getBlue());
				System.out.print(colorize("  ", backColor));
			}
			System.out.print("\n");
		}
	}
}