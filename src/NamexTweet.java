import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class NamexTweet {
    private final static String CONSUMER_KEY = "LklP26VHolgFFDo4sfENWA";
    private final static String CONSUMER_KEY_SECRET = "k0Hk1IForprskG6orwQJYSiHltI8ahyA1tmYYNdc7SQ";

 public void start() throws TwitterException, IOException {

	    Twitter twitter = TwitterFactory.getSingleton();
	    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
	    RequestToken requestToken = twitter.getOAuthRequestToken();
	    AccessToken accessToken = null;
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    while (null == accessToken) {
	      System.out.println("Open the following URL and grant access to your account:");
	      System.out.println(requestToken.getAuthorizationURL());
	      System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
	      String pin = br.readLine();
	      try{
	         if(pin.length() > 0){
	           accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	         }else{
	           accessToken = twitter.getOAuthAccessToken();
	         }
	      } catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	          System.out.println("Unable to get the access token.");
	        }else{
	          te.printStackTrace();
	        }
	      }
	    }
	    //persist to the accessToken for future reference.
	    Status status = twitter.updateStatus("Testing");
	    System.out.println("Successfully updated the status to [" + status.getText() + "].");
	    System.exit(0);
	  }    

    public static void main(String[] args) throws Exception {
 new NamexTweet().start();// run the Twitter client
    }
}