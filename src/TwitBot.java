import java.awt.image.BufferedImage;



public class TwitBot {
public static void deploy(BufferedImage img){
	try {
		ImgurExample.upload(img);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
