package com.example.amin.maktabprojectworldcupapp.photoAlbum;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Photo;
import com.example.amin.maktabprojectworldcupapp.photoAlbum.uploadPhoto.UploadPhotoActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoAlbumFragment extends Fragment implements IPhotoAlbumView {

    private RecyclerView recyclerView;

    private PhotoAlbumPresenter presenter;

    public static PhotoAlbumFragment newInstance() {

        Bundle args = new Bundle ();

        PhotoAlbumFragment fragment = new PhotoAlbumFragment ();
        fragment.setArguments ( args );
        return fragment;
    }

    public PhotoAlbumFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById ( R.id.photo_album_recyclerView );
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
        View view = inflater.inflate ( R.layout.fragment_photo_album, container, false );

        presenter = new PhotoAlbumPresenter ( getActivity (), this );

        initView ( view );

        recyclerView.setLayoutManager ( new GridLayoutManager ( getActivity (), 3) );
        presenter.loadPhotos ();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu ( menu, inflater );

        inflater.inflate ( R.menu.photo_album_menu, menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.upload_photo_menu_item:
                startActivity ( UploadPhotoActivity.newIntent ( getActivity () ) );
                return true;
            default:
                return super.onOptionsItemSelected ( item );
        }
    }

    @Override
    public void setRecyclerAdapter(List<Photo> photos) {
        //creating adapter object and setting it to recyclerView
        recyclerView.setAdapter ( new PhotoAlbumAdapter ( getActivity (), photos ) );
    }
}
