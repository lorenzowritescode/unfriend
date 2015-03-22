package io.corn.uni.unfriend;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FriendsRetriever {
    public static final int MAX_GET_CHUNK = 100;
    private final FriendContainer fc;
    private final Notifiable[] observed;
    MyTwitterApiClient api;

    public FriendsRetriever(FriendContainer fc, Notifiable... observed) {
        this.fc = fc;
        this.observed = observed;

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
                expandIDs(userResult.data.ids);
            }

            @Override
            public void failure(TwitterException e) {
                System.err.println("FAILED TO REQUEST IDS " + e.getMessage());
            }
        });
    }

    public void expandIDs(List<Long> _ids){
        for(int i = 0; i * MAX_GET_CHUNK < _ids.size(); i++){
            int last_index = Math.min((i + 1) * MAX_GET_CHUNK, _ids.size());
            List<Long> ids = _ids.subList(i * MAX_GET_CHUNK, last_index);

            String comma_separated = null;

            for (Long s : ids) {
                String _s = s.toString();
                if (comma_separated == null)
                    comma_separated = _s;
                else
                    comma_separated += "," + _s;
            }

            expandIDsHelper(comma_separated);
        }

    }

    private void expandIDsHelper(String comma_separated){
        ExpandIdService expander = api.expandIds();

        expander.expandIds(comma_separated, false, new Callback<List<User>>() {
            @Override
            public void success(Result<List<User>> listResult) {
                fc.convertFromTwitter(listResult.data);
                callAll();
            }

            @Override
            public void failure(TwitterException e) {
                System.err.println("Exception with twitter " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void callAll() {
        for (Notifiable n : observed){
            n.allDone();
        }
    }
}
