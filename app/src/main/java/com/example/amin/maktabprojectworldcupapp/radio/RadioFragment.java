package com.example.amin.maktabprojectworldcupapp.radio;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.radio.uploadAudio.UploadAudioActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment implements IRadioView{

    private RecyclerView recyclerView;

    private RadioPresenter presenter;

    public static RadioFragment newInstance() {

        Bundle args = new Bundle ();

        RadioFragment fragment = new RadioFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public RadioFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById ( R.id.radio_recyclerView );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setHasOptionsMenu ( true );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_radio, container, false );

        presenter = new RadioPresenter ( getContext (), this );

        initView ( view );

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu ( menu, inflater );
        inflater.inflate ( R.menu.radio_menu, menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.radio_menu_item:
                startActivity ( UploadAudioActivity.newIntent ( getActivity () ) );
                return true;
            default:
                return super.onOptionsItemSelected ( item );
        }
    }
}
