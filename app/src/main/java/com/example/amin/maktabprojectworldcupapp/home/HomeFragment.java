package com.example.amin.maktabprojectworldcupapp.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.chatRoom.ChatListActivity;
import com.example.amin.maktabprojectworldcupapp.photoAlbum.PhotoAlbumActivity;
import com.example.amin.maktabprojectworldcupapp.profile.ProfileActivity;
import com.example.amin.maktabprojectworldcupapp.radio.RadioActivity;
import com.example.amin.maktabprojectworldcupapp.survey.SurveyPagerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IHomeView, View.OnClickListener {

    private LinearLayout radioPageLinLayout;
    private LinearLayout photoAlbumPageLinLayout;
    private LinearLayout chatRoomPageLinLayout;
    private LinearLayout surveyPageLinLayout;
    private LinearLayout worldCupPageLinLayout;
    private LinearLayout profilePageLinLayout;

    private HomePresenter presenter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle ();

        HomeFragment fragment = new HomeFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        radioPageLinLayout = (LinearLayout) view.findViewById ( R.id.radio_page );
        photoAlbumPageLinLayout = (LinearLayout) view.findViewById ( R.id.photo_album_page );
        chatRoomPageLinLayout = (LinearLayout) view.findViewById ( R.id.chat_room_page );
        surveyPageLinLayout = (LinearLayout) view.findViewById ( R.id.survey_page );
        worldCupPageLinLayout = (LinearLayout) view.findViewById ( R.id.football_page );
        profilePageLinLayout = (LinearLayout) view.findViewById ( R.id.profile_page );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_home, container, false );

        presenter = new HomePresenter ( this );

        initView ( view );

        radioPageLinLayout.setOnClickListener ( this );
        photoAlbumPageLinLayout.setOnClickListener ( this );
        chatRoomPageLinLayout.setOnClickListener ( this );
        surveyPageLinLayout.setOnClickListener ( this );
        worldCupPageLinLayout.setOnClickListener ( this );
        profilePageLinLayout.setOnClickListener ( this );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.radio_page:
                presenter.setPage ( 1 );
                break;
            case R.id.photo_album_page:
                presenter.setPage ( 2 );
                break;
            case R.id.chat_room_page:
                presenter.setPage ( 3 );
                break;
            case R.id.survey_page:
                presenter.setPage ( 4 );
                break;
            case R.id.football_page:
                presenter.setPage ( 5 );
                break;
            case R.id.profile_page:
                presenter.setPage ( 6 );
                break;
        }
    }

    @Override
    public void radioPage() {
        startActivity ( RadioActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void photoAlbumPage() {
        startActivity ( PhotoAlbumActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void chatRoomPage() {
        startActivity ( ChatListActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void surveyPage() {
        startActivity ( SurveyPagerActivity.newIntent ( getActivity () ) );
    }

    @Override
    public void worldCupPage() {

    }

    @Override
    public void ProfilePage() {
        startActivity ( ProfileActivity.newIntent ( getActivity () ) );
    }
}
