package io.corn.uni.unfriend;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.models.User;

import java.util.HashMap;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedOutput;

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

    public ExpandIdService expandIds (){
        return getService(ExpandIdService.class);
    }
}

// example users/show service endpoint
interface UserService {
    @GET("/1.1/users/show.json")
    void show(@Query("user_id") long id, Callback<User> cb);
}

interface FriendsService {
    @GET("/1.1/friends/ids.json")
    void getFriends(@Query("user_id") long id,
                    @Query("screen_name") String screen_name,
                    @Query("stringify_ids") boolean stringify_ids,
                    Callback<FriendList> friendList);

    void getFriendsCursored(@Query("user_id") long id,
                            @Query("screen_name") String screen_name,
                            @Query("stringify_ids") boolean stringify_ids,
                            @Query("count") int count,
                            @Query("cursor") int cursor,
                            Callback<FriendList> friendList);
}

interface ExpandIdService {
    @GET("/1.1/users/lookup.json")
    void expandIds(@Query("user_id") String user_ids,
                   @Query("include_entities") boolean include_entities,
                   Callback<List<User>> callback);
}


