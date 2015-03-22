package io.corn.uni.unfriend;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Friend {

    int id;
    String name;
    Date created;
    Date friendsSince;
    Date lastStatus;
    int followerCount;
    int friendCount;
    int tweetCount;

    public Friend(int id, String name, Date created, Date friendsSince,
                  Date lastStatus, int followerCount, int tweetCount, int friendCount) {

        this.id = id;
        this.name = name;
        this.created = created;
        this.friendsSince = friendsSince;
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
        return (statusRatio < 0.02 || daysSinceLastPost > 30);
    }

    private boolean isOverActive(float statusRatio){
        return statusRatio > 50;
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
            ratioScore = (int) Math.round((statusRatio - 0.02)/0.31 * 50);
        }

        if(ratioScore < 0){
            ratioScore = 0;
        }

        int activityScore = 0;
        if(days > 30){
            activityScore = 25;
        } else if(days > 7){
            activityScore = Math.round((days - 7)/23 * 50);
        }

        assert (activityScore <= 25 && activityScore >= 0);
        assert (ratioScore <= 25 && ratioScore >= 0);

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
            double d = Math.abs(followerCount - friendCount + 200) / Math.sqrt(2);
            double maxD = Math.abs(0 - friendUpperBound + 200) / Math.sqrt(2);
            return (int) Math.round(d/maxD * 100);
        }
        return 0;
    }



}