package com.example.amin.maktabprojectworldcupapp.photoAlbum;

import com.example.amin.maktabprojectworldcupapp.model.Photo;

import java.util.List;

/**
 * Created by Amin on 8/16/2018.
 */

public interface IPhotoAlbumView {
    void setRecyclerAdapter(List<Photo> photos);
}
