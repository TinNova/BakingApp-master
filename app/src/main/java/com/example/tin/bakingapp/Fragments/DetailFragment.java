package com.example.tin.bakingapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tin.bakingapp.Models.TheSteps;
import com.example.tin.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private static final String PLAYER_POSITION = "player_position";
    private static final String PLAYER_STATE = "player_state";

    private ArrayList<TheSteps> mSteps;

    private int mPosition;

    private TextView tvStepDescription;
    private ImageView ivNoVideo;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private long videoPosition = 0;
    private boolean videoState = false;

    // Mandatory Constructor for initiating the fragment
    public DetailFragment(){

    }

    // Inflates the fragment layout and sets any image resources
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Extracting the data from the Bundle
        Bundle getExtras = getArguments();
        mSteps = new ArrayList<>();
        mSteps = getExtras.getParcelableArrayList(StepsFragment.CURRENT_STEP);
        mPosition = getExtras.getInt(StepsFragment.CURRENT_POSITION);

        // Setting up the UI
        tvStepDescription = rootView.findViewById(R.id.step_description);
        tvStepDescription.setText(mSteps.get(mPosition).getDescription());
        mExoPlayerView = rootView.findViewById(R.id.exoPlayer);
        ivNoVideo = rootView.findViewById(R.id.no_video_img);

        // if there is a savedInstanceState, get the videoPosition which was captured during the
        // lifeCycle stage onPause, then start the video again
        // also get the videoState (pause/play) so we continue to have the video paused, or continue
        // to have the video play depending on what the state was.
        if (savedInstanceState != null){

            videoPosition = savedInstanceState.getLong(PLAYER_POSITION);
            videoState = savedInstanceState.getBoolean(PLAYER_STATE);

            initialiseMediaPlayer(Uri.parse(mSteps.get(mPosition).getVideoURL()));
            mExoPlayer.seekTo(videoPosition);
            mExoPlayer.setPlayWhenReady(videoState);

            /** Logic which handles missing VideoUrls and Thumbnail Images */
        } else {
            // If No Video & No Thumbnail Image, show the "No Image" ImageView
            if (mSteps.get(mPosition).getVideoURL().isEmpty() && mSteps.get(mPosition).getThumbnailURL().isEmpty()){
                mExoPlayerView.setVisibility(View.GONE);
                ivNoVideo.setVisibility(View.VISIBLE);

                // If No Video & There IS a Thumbnail, Load Thumbnail into the "No Image" ImageView
            } else if (mSteps.get(mPosition).getVideoURL().isEmpty() && !mSteps.get(mPosition).getThumbnailURL().isEmpty()){

                Picasso.with(getContext()).load(mSteps.get(mPosition).getThumbnailURL()).into(ivNoVideo);

                // Else there is a video, and irrespective if there is a thumbnail or not we will
                // show the video
            } else {
                initialiseMediaPlayer(Uri.parse(mSteps.get(mPosition).getVideoURL()));
            }
        }

        return rootView;
    }


    /**
     * Code which Initialises the MediaPlayer
     * @param mediaUri is the VideoUrl which will be played
     */
    private void initialiseMediaPlayer(Uri mediaUri){

        if (mExoPlayer == null){
            // Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector,loadControl);
            // Setting the mExoPlayer to the view mExoPlayerView
            mExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(),
                    userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }
    }
    // It's a good idea to call this in onStop to terminate the video when the activity
    // enters onStop
    private void releasePlayer() {
        // if mExoPlayer is not null, release it correctly, (if we don't do this, app will crash)
        if (mExoPlayer != null) {
            // First we stop the mediaPlayer
            mExoPlayer.stop();
            // Then we release the media from the player
            mExoPlayer.release();
            // Finally to ensure it's empty in order to allow another piece of media to attach to it
            // we ensure it's null
            mExoPlayer = null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // On Fragment lifeCycle onDestroyView release the Player
        releasePlayer();
    }

    // onPause which is the lifecycle stage that occurs on rotation we want to get the videoPosition
    // and the videoState so that we can return those values within onSaveInstance
    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null){
            videoPosition = mExoPlayer.getCurrentPosition();
            videoState = mExoPlayer.getPlayWhenReady();

            releasePlayer();
        } else {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, videoPosition);
        outState.putBoolean(PLAYER_STATE, videoState);
    }
}
