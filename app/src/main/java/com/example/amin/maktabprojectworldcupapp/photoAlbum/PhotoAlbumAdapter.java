package com.example.amin.maktabprojectworldcupapp.photoAlbum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.model.Photo;

import java.util.List;

/**
 * Created by Amin on 8/16/2018.
 */

public class PhotoAlbumAdapter extends RecyclerView.Adapter<PhotoAlbumAdapter.PhotoAlbumHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotoAlbumAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }


    @Override
    public PhotoAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.photo_album_recycler_item, null, true );
        return new PhotoAlbumHolder ( view );
    }

    @Override
    public void onBindViewHolder(PhotoAlbumHolder holder, int position) {
        holder.bindPhoto ( photos.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return photos.size ();
    }

    public class PhotoAlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoImgView;
        private Photo photo;

        public PhotoAlbumHolder(View itemView) {
            super ( itemView );
            photoImgView = (ImageView) itemView.findViewById ( R.id.photo_album_recyclerView_item_imgView );
            itemView.setOnClickListener ( this );
        }

        public void bindPhoto(Photo photo) {
            this.photo = photo;
            //loading the image
            Glide.with ( context )
                    .load ( photo.getPath () )
                    .into ( photoImgView );
        }

        @Override
        public void onClick(View v) {
            Intent shareIntent = new Intent (Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, photo.getPath ());
            context.startActivity(Intent.createChooser(shareIntent, "اشتراک گذاری عکس:"));
        }
    }
}
