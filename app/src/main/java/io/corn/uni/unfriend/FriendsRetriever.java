package io.corn.uni.unfriend;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.models.UserEntities;
import com.twitter.sdk.android.core.services.AccountService;
import com.twitter.sdk.android.core.services.SearchService;

import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FriendsRetriever {
    MyTwitterApiClient api;
    public FriendsRetriever() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        api = new MyTwitterApiClient(session);
    }

    public List<String> getIds(long uid){
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
        friends.getFriends(uid, null, true, new Callback<UserEntities>() {
            @Override
            public void success(Result<UserEntities> userResult) {
                String data = userResult.data.toString();
                System.out.println(data);
            }

            @Override
            public void failure(TwitterException e) {

            }
        });
        return null;
    }
}
