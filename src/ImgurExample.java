import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.security.utils.Base64;


public class ImgurExample {
	public static void upload(BufferedImage image) throws Exception {
	    String IMGUR_POST_URI = "http://api.imgur.com/3/upload.xml";
	    String IMGUR_API_KEY = "59313374550a8cb";

	    String file = "default.jpg";
	    try {
	    	/*
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] stream = baos.toByteArray();
	        */
	    	BufferedImage originalImage = ImageIO.read(new File("default.jpg"));
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	ImageIO.write( originalImage, "jpg", baos );
	    	baos.flush();
	    	byte[] imageInByte = baos.toByteArray();
	    	baos.close();
	        System.out.println("Writing image...");
	        ImageIO.write(image, "png", baos);
	        System.out.println("Encoding...");

	        //tring data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imageInByte.toString(), "UTF-8");
	        //data += "&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(IMGUR_API_KEY, "UTF-8");
	        System.out.println("Connecting...");
	        URL url = new URL("http://api.imgur.com/3/image");

	      //encodes picture with Base64 and inserts api key
	      String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(imageInByte, Base64.BASE64DEFAULTLENGTH).toString(), "UTF-8");
	      data += "&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(IMGUR_API_KEY, "UTF-8");

	      // opens connection and sends data
	      URLConnection conn = url.openConnection();
	      conn.setDoOutput(true);
	      OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	      wr.write(data);
	      wr.flush();
	        System.out.println("Finished.");
	        BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) 
	        	System.out.println(inputLine);
	        		in.close();
	    } catch(Exception e){
	        e.printStackTrace();
	    }
	}
}
