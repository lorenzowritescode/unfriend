package io.corn.uni.unfriend;

import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FullUsers {

    @com.google.gson.annotations.SerializedName("users")
    public final List<User> users;

    public FullUsers(List<User> users) {
        this.users = users;
    }
}
