package io.corn.uni.unfriend;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link twitter_result.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link twitter_result#newInstance} factory method to
 * create an instance of this fragment.
 */
public class twitter_result extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "friend";

    // TODO: Rename and change types of parameters
    private Friend f;
    private View widget;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment twitter_result.
     */
    public static twitter_result newInstance(Friend f) {
        twitter_result fragment = new twitter_result();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, f.name);
        fragment.setArguments(args);
        return fragment;
    }

    public twitter_result() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getArguments().getString(ARG_PARAM1);
        }
    }

    public void setup() {
        ImageView view = (ImageView) widget.findViewById(R.id.profile_pic);
        new ImageDownloader(view).execute(f.pictureUrl);

        TextView name = (TextView) widget.findViewById(R.id.name);
        name.setText(f.name);

        TextView sn = (TextView) widget.findViewById(R.id.screen_name);
        sn.setText(f.screenName);

        int score = f.getResult().getFinalScore();

        ProgressBar pb = (ProgressBar) widget.findViewById(R.id.progressBar);
        pb.setMax(100);
        pb.setProgress(score);

        TextView percentage = (TextView) widget.findViewById(R.id.percentage);
        percentage.setText(score + "%");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        widget = inflater.inflate(R.layout.fragment_twitter_result, container, false);
        return widget;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
