package io.corn.uni.unfriend;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Friend {

    public static final int MAX_TWEET_PER_DAY = 50;
    public static final double MIN_TWEETS_PER_DAY = 0.02;
    public static final int MAX_DAYS_INACTIVE = 30;
    long id;
    String name;
    Date created;
    Date lastStatus;
    int followerCount;
    int friendCount;
    int tweetCount;

    public Friend(long id, String name, Date created,
                  Date lastStatus, int followerCount, int tweetCount, int friendCount) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.lastStatus = lastStatus;
        this.followerCount = followerCount;
        this.tweetCount = tweetCount;
        this.friendCount = friendCount;
    }

    public TestResult testFriend(){
        boolean fake = isFake();
        boolean inactive = isInactive();
        boolean overActive = isOverActive();
        int score = scoreFriend();
        return new TestResult(0, inactive, overActive, fake);
    }

    /* Returns a score between 0 and 100 for how good the friend seems. */
    private int scoreFriend() {
        return 0;

    }

    private boolean isOverActive() {
        if(tweetCount == 0){
            return false;
        }

        Date now = new Date();
        long diff = now.getTime() - created.getTime();
        long daysSinceCreation = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        float statusRatio = tweetCount / daysSinceCreation;
        if (statusRatio > MAX_TWEET_PER_DAY){
            return true;
        }
        return false;
    }

    private boolean isInactive() {
        if(tweetCount == 0){
            return true;
        }

        Date now = new Date();
        long diff = now.getTime() - created.getTime();
        long daysSinceCreation = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        float statusRatio = tweetCount / daysSinceCreation;
        if (statusRatio < MIN_TWEETS_PER_DAY) {
            return true;
        }
        long postDiff = now.getTime() - lastStatus.getTime();
        long daysSinceLastPost = TimeUnit.DAYS.convert(postDiff, TimeUnit.MILLISECONDS);
        if (daysSinceLastPost > MAX_DAYS_INACTIVE){
            return true;
        }

        return false;
    }

    private boolean isFake() {
        Date now = new Date();
        long diff = now.getTime() - created.getTime();
        long daysSinceCreation = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        float statusRatio = tweetCount / daysSinceCreation;
        if (statusRatio < 0.06 && followerCount > 100) {
            return true;
        }

        if (friendCount > 2000){
            return true;
        }

        return false;
    }

}
