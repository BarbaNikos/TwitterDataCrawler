package katsip.crawler;

import twitter4j.*;

/**
 * Created by katsip on 12/10/2015.
 */
public class SampleListener implements StatusListener {

    public void onStatus(Status status) {
        User user = status.getUser();
        String tweet = status.getText();
        String cleanTweet = tweet.replaceAll("(\\r|\\n)", ";")
                .replaceAll("\\r\\n", ";").replaceAll("\r", ";").replaceAll("\n", ";")
                .replaceAll("\r\n", ";");
        tweet = cleanTweet.replaceAll("^[^a-zA-Z0-9\\\\s]+|[^a-zA-Z0-9\\\\s]+$", "");
        System.out.println("{" + tweet + "}");
    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    public void onTrackLimitationNotice(int i) {

    }

    public void onScrubGeo(long l, long l1) {

    }

    public void onStallWarning(StallWarning stallWarning) {

    }

    public void onException(Exception e) {

    }
}
