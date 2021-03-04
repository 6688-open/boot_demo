package com.dj.boot.common.util.security;

import com.dj.boot.common.util.awt.ColorUtils;
import com.dj.boot.common.util.awt.FontUtils;
import com.dj.boot.common.util.collection.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCodeUtils {
	public static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static final Random RANDOM = new Random();

	public static BufferedImage create(int width, int height, int digit, int fontSize) {
		String randomString = RandomUtils.getString(CHARS, digit);
		return create(width, height, randomString, fontSize);
	}

	public static BufferedImage create(int width, int height, String code, int fontSize) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bufferedImage.createGraphics();
		graphics2d.fillRect(0, 0, width, height);

		Font font = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
		graphics2d.setFont(font);

		Color color = ColorUtils.getRandomColor();
		graphics2d.setColor(color);

		Color reverseColor = ColorUtils.getReverseColor(color);
		graphics2d.setColor(reverseColor);

		float xOffset = FontUtils.getCentricXOffset(width, graphics2d, font, code);
		float yOffset = FontUtils.getCentricYOffset(height, graphics2d, font);
		graphics2d.drawString(code, xOffset, yOffset);

		int obstacleCount = RANDOM.nextInt(getMaxObstacleCount(width, height));
		setObstacle(graphics2d, obstacleCount, width, height);
		return bufferedImage;
	}

	public static int getMaxObstacleCount(int width, int height) {
		return Math.max(width, height);
	}

	public static void setObstacle(Graphics2D graphics2d, int count, int width, int height) {
		for (int i = 0; i < count; i++) {
			graphics2d.drawRect(RANDOM.nextInt(width), RANDOM.nextInt(height), 1, 1);
		}
	}
}