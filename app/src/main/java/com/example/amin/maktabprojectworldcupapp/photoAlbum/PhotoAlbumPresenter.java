package com.example.amin.maktabprojectworldcupapp.photoAlbum;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Amin on 8/16/2018.
 */

public class PhotoAlbumPresenter implements IPhotoAlbumPresenter {

    private Context context;
    private IPhotoAlbumView photoAlbumView;
    private List<Photo> photos;

    public PhotoAlbumPresenter(Context context, IPhotoAlbumView photoAlbumView) {
        this.context = context;
        this.photoAlbumView = photoAlbumView;
        photos = new ArrayList<> ();
    }

    @Override
    public void loadPhotos() {
        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest ( Request.Method.GET, UrlEndPoints.LOAD_PHOTOS,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject jsonObject = new JSONObject ( response );
                            JSONArray array = jsonObject.getJSONArray ( "images" );

                            //traversing through all the object
                            for (int i = 0; i < array.length (); i++) {

                                //getting photo object from json array
                                JSONObject photoJson = array.getJSONObject ( i );

                                //adding the photo to photo list
                                photos.add ( new Photo ( UUID.fromString ( photoJson.getString ( "uuid" ) ),
                                        photoJson.getString ( "path" )
                                ) );
                            }
                            photoAlbumView.setRecyclerAdapter ( photos );
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "jsonException" + e.getMessage () );
                        }
                    }
                },
                new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i ( Constant.SERVER_LOG, "onErrorResponse" + error.getMessage () );
                    }
                } );

        //adding our stringRequest to queue
        Volley.newRequestQueue ( context ).add ( stringRequest );
    }
}
