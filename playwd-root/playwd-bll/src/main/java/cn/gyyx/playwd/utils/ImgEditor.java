 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年5月3日下午3:51:58
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 图片编辑类
 * @author lidudi
 *
 */
@Component
public class ImgEditor {
	
	private static final Logger logger = GYYXLoggerFactory.getLogger(ImgEditor.class);

	/** 
     * 图像切割(按指定起点坐标和宽高切割) 
     * @param srcImageFile 源图像地址 
     * @param result 切片后的图像地址 
     * @param x 目标切片起点坐标X 
     * @param y 目标切片起点坐标Y 
     * @param width 目标切片宽度 
     * @param height 目标切片高度 
     */  
    public File cut(File srcImageFile, String result,  int x, int y, int width, int height) {  
        try {  
            // 读取源图像  
            BufferedImage bi = ImageIO.read(srcImageFile);  
            int srcWidth = bi.getWidth(); // 源图宽度  
            int srcHeight = bi.getHeight(); // 源图高度  
            if (srcWidth > 0 && srcHeight > 0) {  
                Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_SMOOTH);  
                // 四个参数分别为图像起点坐标和宽高  
                // 即: CropImageFilter(int x,int y,int width,int height)  
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);  
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(),cropFilter));  
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图  
                g.dispose();  
                // 输出为文件  
                File file=new File(result);
                ImageIO.write(tag, "PNG", file);
                return file;
            }  
            return new File("");
        } catch (Exception e) { 
        	logger.error("图像切割异常", e);
        	return new File("");
        }
    }  
    
    /** 
     * 缩放图像（按高度和宽度缩放） 
     * @param srcImageFile 源图像文件地址 
     * @param result 缩放后的图像地址 
     * @param height 缩放后的高度 
     * @param width 缩放后的宽度 
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白; 
     */  
    public File scale(URL srcImageFile, String result, int height, int width) {  
        try {  
        	
            double ratio = 0.0; // 缩放比例  
            BufferedImage bi = ImageIO.read(srcImageFile); 
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);  
            // 计算比例  
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {  
                if (bi.getHeight() > bi.getWidth()) {  
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();  
                } else {  
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();  
                }  
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);  
                itemp = op.filter(bi, null);  
            }  
            
            File file=new File(result);
            ImageIO.write(convertToBufferedImage(itemp), "PNG", file); 
            return file;
        } catch (IOException e) {
        	logger.error("缩放图像异常", e); 
        	return new File("");
        }  
    } 
    
    /** 
     * 缩放图像（按高度和宽度缩放） 
     * @param srcImageFile 源图像文件地址 
     * @param result 缩放后的图像地址 
     * @param height 缩放后的高度 
     * @param width 缩放后的宽度 
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白; 
     */  
    public File scale(File srcImageFile, String result, int height, int width) {  
        try {  
            double ratio = 0.0; // 缩放比例  
            BufferedImage bi = ImageIO.read(srcImageFile);  
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);  
            // 计算比例  
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {  
                if (bi.getHeight() > bi.getWidth()) {  
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();  
                } else {  
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();  
                }  
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);  
                itemp = op.filter(bi, null);  
            }  
            
            File file=new File(result);
            ImageIO.write(convertToBufferedImage(itemp), "PNG", file); 
            return file;
        } catch (IOException e) {
        	logger.error("缩放图像异常", e); 
        	return new File("");
        }  
    } 
    
    /**
     * ImageConvertBufferedImage
     * @param image
     * @return
     */
    public BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
