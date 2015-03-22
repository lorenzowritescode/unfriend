package io.corn.uni.unfriend;

public class TestResult {

    int activityScore;
    int realnessScore;
    boolean inactive;
    boolean overActive;
    boolean fake;
    boolean newProfile;

    public TestResult(int ascore, int rscore, boolean inactive, boolean overActive,
                      boolean fake, boolean newProfile) {
        this.activityScore = ascore;
        this.realnessScore = rscore;
        this.inactive = inactive;
        this.overActive = overActive;
        this.fake = fake;
        this.newProfile = newProfile;
    }

    public int getFinalScore() {
        return activityScore + realnessScore;
    }
}
