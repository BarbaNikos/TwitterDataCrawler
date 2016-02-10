import twitter4j.Status;
import twitter4j.User;

import java.io.*;
import java.util.Arrays;

/**
 * Created by nick on 12/6/15.
 */
public class FileDriver {

    private File directory;

    private Long maxFileBytes;

    private File currentFile;

    private Long fileIndex;

    private Long currentOffset;

    private PrintWriter writer;

    public FileDriver() {

    }

    public FileDriver(String directory, Long maxFileBytes) {
        this.directory = new File(directory);
        this.maxFileBytes = maxFileBytes;
        if (this.directory.exists() && !this.directory.isDirectory()) {
            System.err.println("path provided is not a directory.");
            System.exit(1);
        } else {
            this.directory.mkdir();
        }
        fileIndex = 0L;
        currentOffset = 0L;
        writer = null;
    }

    public void init() throws IOException {
        currentFile = new File(directory.getAbsolutePath() + File.separator + "tweet_file_" + fileIndex + ".txt");
        if (!currentFile.exists())
            currentFile.createNewFile();
        writer = new PrintWriter(currentFile, "UTF-8");
    }

    /**
     *
     * @param status
     * @throws IOException
     */
    public void writeTweet(Status status) throws IOException {
        if (writer == null)
            init();
        if (currentOffset < maxFileBytes) {
            User user = status.getUser();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(status.getCreatedAt() + "|");
            String tweet = status.getText();
            String cleanTweet = tweet.replaceAll("(\\r|\\n)", ";")
                    .replaceAll("\\r\\n", ";").replaceAll("\r", ";").replaceAll("\n", ";")
                    .replaceAll("\r\n", ";");
            tweet = cleanTweet.replaceAll("^[^a-zA-Z0-9\\\\s]+|[^a-zA-Z0-9\\\\s]+$", "");
            stringBuilder.append(tweet);
//            stringBuilder.append(Arrays.toString(status.getContributors()) + "|" + status.getCreatedAt() + "|" + status.getCurrentUserRetweetId() +
//            status.getFavoriteCount() + "|");
//            if (status.getGeoLocation() != null) {
//                stringBuilder.append(status.getGeoLocation().getLatitude() + "," + status.getGeoLocation().getLongitude() + "|");
//            } else {
//                stringBuilder.append(",|");
//            }
//            stringBuilder.append(status.getId() + "|" + status.getInReplyToScreenName() + "|" + status.getInReplyToStatusId() + "|" +
//                    status.getInReplyToUserId() + "|" + status.getLang() + "|");
//            if (status.getPlace() != null) {
//                stringBuilder.append(status.getPlace().getFullName() + "|");
//            } else {
//                stringBuilder.append("|");
//            }
//            stringBuilder.append(status.getQuotedStatusId() + "|" + status.getRetweetCount() + "|" + status.getSource() + "|" +
//            status.getText() + "|" + user.getName() + "|" + user.getId() + "|" + user.getFavouritesCount() + "|" +
//            user.getFollowersCount() + "|" + user.getFriendsCount() + "|" + status.isFavorited() + status.isPossiblySensitive() + "|" +
//            status.isRetweet() + "|" + status.isRetweeted() + "|" + status.isTruncated());
            writer.println(stringBuilder.toString());
            currentOffset += (stringBuilder.toString().length());
        }
        if (currentOffset >= maxFileBytes) {
            writer.flush();
            writer.close();
            fileIndex++;
            currentFile = new File(directory.getAbsolutePath() + File.separator + "tweet_file_" + fileIndex + ".txt");
            if (!currentFile.exists())
                currentFile.createNewFile();
            writer = new PrintWriter(currentFile, "UTF-8");
            currentOffset = 0L;
        }
    }

    public void destroy() {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }
}
