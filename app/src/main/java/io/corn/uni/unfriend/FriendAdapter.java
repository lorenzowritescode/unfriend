package io.corn.uni.unfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.corn.uni.unfriend.dummy.DummyContent;

/**
 * Created by lp1813 on 22/03/15.
 */
public class FriendAdapter extends ArrayAdapter<Friend> {


    private final Friend[] values;
    private final Context context;

    public FriendAdapter(Context context, Friend[] objects) {
        super(context, R.layout.friend_list_item, objects);

        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Friend f = values[position];
        View rowView = inflater.inflate(R.layout.friend_list_item, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.full_name);
        name.setText(f.name);

        TextView screenName = (TextView) rowView.findViewById(R.id.screen_name);
        screenName.setText(f.screenName);

        TextView unfollowScore = (TextView) rowView.findViewById(R.id.unfollowScore);
        TestResult tr = f.testFriend();
        int score = tr.activityScore + tr.realnessScore;
        unfollowScore.setText(score + "%");

        ProgressBar bar = (ProgressBar) rowView.findViewById(R.id.progressBar);
        bar.setMax(100);
        bar.setProgress(score);

        ImageView profile_pic = (ImageView) rowView.findViewById(R.id.profile_pic);
        new ImageDownloader(profile_pic).execute(f.pictureUrl);

        return rowView;
    }



}
