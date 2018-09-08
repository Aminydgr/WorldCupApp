package com.example.amin.maktabprojectworldcupapp.profile;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;
import com.example.amin.maktabprojectworldcupapp.model.User;
import com.example.amin.maktabprojectworldcupapp.profile.editUser.EditUserActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements IProfileView, View.OnClickListener {

    private TextView userNameTxtView;
    private TextView userInfoTxtView;
    private TextView inviteFriendTxtView;
    private Button logOutBtn;

    private ProfilePresenter presenter;
    private User user;

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle ();

        ProfileFragment fragment = new ProfileFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        userNameTxtView = (TextView) view.findViewById ( R.id.user_name_txtView );
        userInfoTxtView = (TextView) view.findViewById ( R.id.user_info_txtView );
        inviteFriendTxtView = (TextView) view.findViewById ( R.id.invite_friend_txtView );
        logOutBtn = (Button) view.findViewById ( R.id.log_out_btn );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_profile, container, false );

        presenter = new ProfilePresenter ( getContext (), this );
        user = SharedPrefManager.getInstance ( getContext () ).getUser ();

        initView ( view );

        userNameTxtView.setText ( user.getName () );
        userInfoTxtView.setOnClickListener ( this );
        inviteFriendTxtView.setOnClickListener ( this );
        logOutBtn.setOnClickListener ( this );

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.user_info_txtView:
                startActivity ( EditUserActivity.newIntent ( getActivity () ) );
                break;
            case R.id.invite_friend_txtView:
                Intent pickContact = new Intent ( Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI );
                startActivityForResult ( pickContact, Constant.REQUEST_CONTACT );
                break;
            case R.id.log_out_btn:
                presenter.logout ();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (resultCode != Activity.RESULT_OK)
            return;
        
        if (requestCode == Constant.REQUEST_CONTACT && data != null) {
            Intent intent = new Intent ( Intent.ACTION_SENDTO, Uri.parse ( "smsto:09191230912" ) );
            intent.putExtra ( "sms_body", "My App Link" );
            startActivity ( intent );
        }
    }
}