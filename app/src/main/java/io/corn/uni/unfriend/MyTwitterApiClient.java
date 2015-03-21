package io.corn.uni.unfriend;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.models.UserEntities;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by lp1813 on 21/03/15.
 */
public class MyTwitterApiClient extends TwitterApiClient{
    public MyTwitterApiClient(Session session) {
        super(session);
    }

    public UserService getUserService() {
        return getService(UserService.class);
    }

    public FriendsService getFriendsService () {
        return getService(FriendsService.class);
    }
}

// example users/show service endpoint
interface UserService {
    @GET("/1.1/users/show.json")
    void show(@Query("user_id") long id, Callback<User> cb);
}

interface FriendsService {
    @GET("/1.1/friends/ids.json")
    void getFriends(@Query("user_id") long id, @Query("screen_name") String screen_name,@Query("stringify_ids") boolean stringify_ids,  Callback<UserEntities> cb);
}
