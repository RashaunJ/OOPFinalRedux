import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;


public class TwitBot {
	static OAuthSignpostClient client = new OAuthSignpostClient("LklP26VHolgFFDo4sfENWA", 
			"k0Hk1IForprskG6orwQJYSiHltI8ahyA1tmYYNdc7SQ", "oob");
	public static void main(String[] args){
		
	}

public void deploy(){
	Twitter twitter = new Twitter("OOPFinal", client);
	twitter.setStatus("Deployed from PhotoSnap!");

}
}
