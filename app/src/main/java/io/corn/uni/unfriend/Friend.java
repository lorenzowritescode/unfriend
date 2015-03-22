package io.corn.uni.unfriend;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Friend {

    public static final int MAX_TWEET_PER_DAY = 50;
    public static final double MIN_TWEETS_RATIO = 0.02;
    public static final int MAX_DAYS_INACTIVE = 30;

    FriendType type;
    long id;
    String name;
    String screenName;
    Date created;
    Date lastStatus;
    int followerCount;
    int friendCount;
    int tweetCount;
    String pictureUrl;

    @Override
    public String toString() {
        return name;
    }

    public Friend(long id, FriendType type) {
        this.id = id;
        this.type = type;
    }

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
        Date now = new Date();
        float statusRatio;
        long diff = now.getTime() - created.getTime();
        long daysSinceCreation = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(daysSinceCreation != 0) {
            statusRatio = tweetCount / daysSinceCreation;
        } else {
            statusRatio = 0;
        }

        long postDiff = now.getTime() - lastStatus.getTime();
        long daysSinceLastPost = TimeUnit.DAYS.convert(postDiff, TimeUnit.MILLISECONDS);

        boolean newProfile = (daysSinceCreation < 3);

        boolean fake = isFake(statusRatio);
        boolean inactive = isInactive(statusRatio, daysSinceLastPost);
        boolean overActive = isOverActive(statusRatio);

        int activityScore = actScore(statusRatio, daysSinceLastPost);
        int realnessScore = rScore();
        return new TestResult(activityScore, realnessScore, inactive, overActive, fake, newProfile);
    }

    private boolean isFake(float statusRatio){
        return (friendCount > 2000 || (statusRatio < 0.05 && followerCount > 1000));
        
    }

    private boolean isInactive(float statusRatio, long daysSinceLastPost){
    	return tweetCount == 0
                || (statusRatio < MIN_TWEETS_RATIO || daysSinceLastPost > MAX_DAYS_INACTIVE);
    }

    private boolean isOverActive(float statusRatio){
        return statusRatio > MAX_TWEET_PER_DAY;
    }

    /* Returns a score between 0 and 100 depending on the activity of the friend
     * (where 0 is the best) */
    private int actScore(float statusRatio, float days) {
        int ratioScore = 0;
        if(statusRatio > 10){
            ratioScore = Math.round((statusRatio - 10)/30 * 25);
        }
        else if(statusRatio < 0.33)
        {
            ratioScore = (int) Math.round((statusRatio - 0.02)/0.31 * 25);
        }

        if(ratioScore < 0){
            ratioScore = 0;
        }

        int activityScore = 0;
        if(days > 30){
            activityScore = 25;
        } else if(days > 7){
            activityScore = Math.round((days - 7)/23 * 25);
        }

        assert (activityScore <= 50 && activityScore >= 0);
        assert (ratioScore <= 50 && ratioScore >= 0);

        return activityScore + ratioScore;
    }

    /* Returns a score between 0 and 100 depending on how real the friend appears
    (0 is the best)
     */
    private int rScore (){
        int friendLowerBound = 200;
        int friendUpperBound = 2000;
        if(friendCount > friendUpperBound){
            return 100;
        }
        if(followerCount < friendCount && friendCount > friendLowerBound){
            /* Using a bit of linear algebra here. you'll just have to trust the math */
            double d = Math.abs(followerCount - friendCount + friendLowerBound) / Math.sqrt(2);
            double maxD = Math.abs(0 - friendUpperBound + friendLowerBound) / Math.sqrt(2);
            return (int) Math.round(d/maxD * 50);
        }
        return 0;
    }

    public void setType(FriendType type) {

        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLastStatus(Date lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setScreenName(String screen_name) {
        this.screenName = screen_name;
    }
}

