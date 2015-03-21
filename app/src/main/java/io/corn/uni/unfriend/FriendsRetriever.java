package io.corn.uni.unfriend;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FriendsRetriever {
    public static final int CURSOR_FIRST_PAGE = -1;
    MyTwitterApiClient api;

    public FriendsRetriever() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        api = new MyTwitterApiClient(session);
    }

    public void getIds(long uid){
//        TwitterApiClient client = Twitter.getApiClient();
//        SearchService search = client.getSearchService();
//        search.tweets("Kanye West", null, null, null, "recent", 20, null, null,
//                null, true, new Callback<Search>() {
//                    @Override
//                    public void success(Result<Search> searchResult) {
//                        List<Tweet> data = searchResult.data.tweets;
//                        for (Tweet t : data){
//                            System.out.println(t.text);
//                        }
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        System.err.println(e.getMessage());
//                    }
//                });
        FriendsService friends = api.getFriendsService();
        friends.getFriends(uid, null, false, new Callback<FriendList>() {
            @Override
            public void success(Result<FriendList> userResult) {
                System.out.println(userResult.toString());
                List<Long> blah = userResult.data.ids;
                for (Long l : blah){
                    System.out.println(l);
                }
                expandIDs(userResult.data.ids);
            }

            @Override
            public void failure(TwitterException e) {
                System.err.println("FAILED TO REQUEST IDS " + e.getMessage());
            }
        });
    }

    public void expandIDs(List<Long> _ids){
        List<Long> ids = _ids.subList(0, 30);

        String comma_separated = null;

        for (Long s : ids) {
            String _s = s.toString();
            if (comma_separated == null)
                comma_separated = _s;
            else
                comma_separated += "," + _s;
        }

        System.out.println(comma_separated);
        System.out.println("SIZE " + ids.size());

        ExpandIdService expander = api.expandIds();
        expander.expandIds(comma_separated, false, new Callback<FullUsers>() {
            @Override
            public void success(Result<FullUsers> userEntitiesResult) {
                for (User u : userEntitiesResult.data.users){
                    System.out.println(u.name);
                }
            }

            @Override
            public void failure(TwitterException e) {
                System.err.println("Failed to expand user_ids: " + e.getMessage());
            }
        });

    }
}
