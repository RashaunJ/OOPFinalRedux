import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class Filters  {


public static void applyMonoChromeFilter(BufferedImage img){
	ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
    op.filter(img,img);	
}
public static void applyInvert(BufferedImage img){
	/*
	 float[] blur = {
		        0.111f, 0.111f, 0.111f, 
		        0.111f, 0.111f, 0.111f, 
		        0.111f, 0.111f, 0.111f, 
		    };
	BufferedImage output = null;
    Kernel kernel = new Kernel(3,3,blur);			     
    BufferedImageOp op = new ConvolveOp(kernel);

    img=op.filter(img,output);
    img=output;
    */
    for (int x = 0; x < img.getWidth(); x++) {
        for (int y = 0; y < img.getHeight(); y++) {
            int rgba = img.getRGB(x, y);
            Color col = new Color(rgba, true);
            col = new Color(255 - col.getRed(),
                            255 - col.getGreen(),
                            255 - col.getBlue());
            img.setRGB(x, y, col.getRGB());
        }
    }
}
public static void applyBorder(BufferedImage img){
	
    for (int x = 0; x < img.getWidth(); x++) {
        for (int y = 0; y < img.getHeight()/20; y++) {
            int rgba = img.getRGB(x, y);
            Color col = new Color(rgba, true);
            col = new Color(0,0,0);
            img.setRGB(x, y, col.getRGB());
        }
    }
    for (int x = 0; x < img.getWidth()/25; x++) {
        for (int y = 0; y < img.getHeight(); y++) {
            int rgba = img.getRGB(x, y);
            Color col = new Color(rgba, true);
            col = new Color(0,0,0);
            img.setRGB(x, y, col.getRGB());
        }
    }
    for (int x = img.getWidth()-1; x > img.getWidth()-(img.getWidth()/20); x--) {
        for (int y = img.getHeight()-1; y >0; y--) {
            int rgba = img.getRGB(x, y);
            Color col = new Color(rgba, true);
            col = new Color(0,0,0);
            img.setRGB(x, y, col.getRGB());
        }
    }
    for (int x = img.getWidth()-1; x > 0; x--) {
        for (int y = img.getHeight()-1; y > img.getHeight()-(img.getHeight()/25); y--) {
            int rgba = img.getRGB(x, y);
            Color col = new Color(rgba, true);
            col = new Color(0,0,0);
            img.setRGB(x, y, col.getRGB());
        }
    }
}
public static void applySepiaFilter(BufferedImage img, int
sepiaIntensity) throws Exception
{

  int sepiaDepth = 20;

  int w = img.getWidth();
  int h = img.getHeight();

  WritableRaster raster = img.getRaster();

  int[] pixels = new int[w*h*3];
  raster.getPixels(0, 0, w, h, pixels);

  for (int i=0;i<pixels.length; i+=3)
  {
    int r = pixels[i];
    int g = pixels[i+1];
    int b = pixels[i+2];

    int gry = (r + g + b) / 3;
    r = g = b = gry;
    r = r + (sepiaDepth * 2);
    g = g + sepiaDepth;

    if (r>255) r=255;
    if (g>255) g=255;
    if (b>255) b=255;

    // Darken blue color to increase sepia effect
    b-= sepiaIntensity;

    // normalize if out of bounds
    if (b<0) b=0;
    if (b>255) b=255;

    pixels[i] = r;
    pixels[i+1]= g;
    pixels[i+2] = b;
  }
  raster.setPixels(0, 0, w, h, pixels);
}

}
