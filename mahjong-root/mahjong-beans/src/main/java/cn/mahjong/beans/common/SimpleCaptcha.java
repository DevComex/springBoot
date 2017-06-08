package cn.mahjong.beans.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成验证码
 */
public class SimpleCaptcha {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCaptcha.class);

    private static final int CAPTCHA_LENGTH = 4;
    private HttpServletResponse response;

    public SimpleCaptcha(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 生成验证码并发送到响应流里
     * 
     * @param response 响应流
     * @param width 宽
     * @param height 高
     * @throws IOException
     */
    public String generate() {
        String captcha = getRandomString(CAPTCHA_LENGTH);

        try {
            BufferedImage image = outputImage(captcha, 113, 45);
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("generate captcha file",e);
        }
        
        return captcha;
    }

    private BufferedImage outputImage(String code, int width, int height)
            throws IOException {

        int size = code.length();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, width, height);
        
        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, width, height - 4);
        
        Random random = ThreadLocalRandom.current();
        
        // 绘制干扰线
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        shear(g2, width, height, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = height - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < size; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(
                    Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                    (width / size) * i + fontSize / 2, height / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((width - 10) / size) * i + 5,
                    height / 2 + fontSize / 2 - 10);
        }
        
        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        g2.dispose();
        
        return image;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = ThreadLocalRandom.current();
        if (fc > 255){
            fc = 255;
        }  
        if (bc > 255){
            bc = 255;
        } 
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private int[] getRandomRgb() {
        Random random = ThreadLocalRandom.current();
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {

        Random random = ThreadLocalRandom.current();
        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        Random random = ThreadLocalRandom.current();
        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }

    private String getRandomString(int length) {
        char[] src = new char[length];
        for (int i = 0; i < src.length; i++) {

            src[i] = (char) getRandomNum(48, 57);
        }
        return new String(src);
    }

    private int getRandomNum(int smallistNum, int BiggestNum) {
        Random random = ThreadLocalRandom.current();
        return (Math.abs(random.nextInt()) % (BiggestNum - smallistNum + 1)) + smallistNum;
    }
}
