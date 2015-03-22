package io.corn.uni.unfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(f.name);

        return rowView;
    }
}
