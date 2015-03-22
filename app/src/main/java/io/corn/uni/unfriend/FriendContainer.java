package io.corn.uni.unfriend;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FriendContainer {
    List<Friend> friends;

    public FriendContainer() {
        friends = new ArrayList<>();
    }

    public void convertFromTwitter(List<User> users) {
        for (User u : users){
            convertFromTwitter(u);
        }
    }

    public void convertFromTwitter(User u) {
        Date createdAt = null;
        Date lastTweet = null;
        try {
            createdAt = parseTwitterDate(u.createdAt);
            lastTweet = parseTwitterDate(u.status.createdAt);
        } catch (ParseException e) {
            System.err.println("Error parsing the date " + e.getMessage());
            e.printStackTrace();
        }

        Friend f = new Friend(
                u.id,
                u.name,
                createdAt,
                lastTweet,
                u.followersCount,
                u.statusesCount,
                u.friendsCount);

        this.friends.add(f);
    }

    private Date parseTwitterDate(String dateString) throws ParseException {
        final String TWITTER="EEE, dd MMM yyyy HH:mm:ss ZZZZZ";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        return sf.parse(dateString);
    }
}
