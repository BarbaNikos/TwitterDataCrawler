import twitter4j.*;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;
import java.io.IOException;

/**
 * Created by Nick R. Katsipoulakis on 8/23/15.
 */
public class TweetCrawler {

    public static void main(String[] args) throws IOException {
        String directoryName = "";
        long fileSize = 0L;
        if (args.length < 2) {
            System.err.println("parameters: <output-directory> <max-file-size-in-bytes>");
            System.exit(1);
        } else {
            directoryName = args[0];
            fileSize = Long.parseLong(args[1]);
        }
        TwitterStreamFactory factory = new TwitterStreamFactory();
        TwitterStream twitterStream = factory.getInstance();
        FileListener listener = new FileListener(directoryName, fileSize);
        FilterQuery fq = new FilterQuery();
        SampleListener sampleListener = new SampleListener();
        /**
         * Popular keywords taken from:
         * http://techland.time.com/2009/06/08/the-500-most-frequently-used-words-on-twitter/
         */
        String popularKeywords = "the,i,to,a,and,is,in,it,you,of,for,on,my,'s,that,at,with,me,do,have,just,this,be,n't,so,are,'m,not,was,but,out,up,what,now,new,from,your" +
                "like,good,no,get,all,about,we,if,time,as,day,will,one,twitter,how,can,some,an,am,by,going,they,go,or,has";
        String keywords[] = { popularKeywords };
        fq.track(keywords);
        fq.language(new String[]{"en"});
//        twitterStream.addListener(listener);
        twitterStream.addListener(sampleListener);
        twitterStream.filter(fq);
    }
}
