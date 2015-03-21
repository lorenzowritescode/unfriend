package io.corn.uni.unfriend;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lp1813 on 21/03/15.
 */
public class FriendList implements Serializable {
    @com.google.gson.annotations.SerializedName("previous_cursor")
    public final int previous_cursor;
    @com.google.gson.annotations.SerializedName("ids")
    public final List<Long> ids;
    @com.google.gson.annotations.SerializedName("previous_cursor_str")
    public final String previous_cursor_str;
    @com.google.gson.annotations.SerializedName("next_cursor")
    public final int next_cursor;
    @com.google.gson.annotations.SerializedName("next_cursor_str")
    public final String next_cursor_str;


    public FriendList(int previous_cursor, List<Long> ids, String previous_cursor_str, int next_cursor, String next_cursor_str) {
        this.previous_cursor = previous_cursor;
        this.ids = ids;
        this.previous_cursor_str = previous_cursor_str;
        this.next_cursor = next_cursor;
        this.next_cursor_str = next_cursor_str;
    }

}
