import twitter4j.*;

import java.io.IOException;
import java.util.Date;

/**
 * Created by nick on 12/6/15.
 */
public class FileListener implements StatusListener {

    private FileDriver driver;

    public FileListener(String directory, Long maxFileSize) throws IOException {
        driver = new FileDriver(directory, maxFileSize);
        driver.init();
    }

    public void onStatus(Status status) {
        try {
            driver.writeTweet(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
