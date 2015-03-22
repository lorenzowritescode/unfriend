package io.corn.uni.unfriend;

import com.twitter.sdk.android.core.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FriendContainer {
    List<Friend> friends;

    final static String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    public FriendContainer() {
        friends = new ArrayList<>();
    }

    public void convertFromTwitter(List<User> users) {
        for (User u : users){
            convertFromTwitter(u);
        }
        System.out.println("Friends list now is " + friends.size());
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
        } catch(NullPointerException e){
            return;
        }

        Friend f = new Friend(
                u.id,
                u.name,
                createdAt,
                lastTweet,
                u.followersCount,
                u.statusesCount,
                u.friendsCount);

        f.setPictureUrl(u.profileImageUrl);
        f.setScreenName(u.screenName);

        this.friends.add(f);
    }

    private Date parseTwitterDate(String dateString) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        return sf.parse(dateString);
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public List<Friend> getSortedFriends() {
        Collections.sort(friends, Collections.reverseOrder());
        return friends;
    }
}
