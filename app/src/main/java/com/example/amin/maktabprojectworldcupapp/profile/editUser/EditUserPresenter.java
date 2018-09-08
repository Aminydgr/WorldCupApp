package com.example.amin.maktabprojectworldcupapp.profile.editUser;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.R;
import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.User;
import com.example.amin.maktabprojectworldcupapp.server.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amin on 9/1/2018.
 */

public class EditUserPresenter implements IEditUserPresenter {

    private Context context;
    private IEditUserView editUserView;
    private User user;
    private Bitmap bitmap;

    public EditUserPresenter(Context context, IEditUserView editUserView) {
        this.context = context;
        this.editUserView = editUserView;
        user = SharedPrefManager.getInstance ( context ).getUser ();
    }

    public boolean checkPermission() {
        //check permission at runtime for Marshmallow & greater than Marshmallow version
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission ( context, Manifest.permission.READ_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale (
                        (Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE )) {
                    editUserView.showPermissionDialog ();
                } else {
                    editUserView.requestPermissions ();
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void userInfo() {
        editUserView.showUserInfo ( user );
    }

    @Override
    public void selectChooser() {
        editUserView.showChooserDialog ();
    }

    @Override
    public CharSequence selectImage(CharSequence item, DialogInterface dialog) {
        String userChosenTask = null;
        if (item.equals ( context.getString ( R.string.camera ) )) {
            userChosenTask = context.getString ( R.string.camera );
            if (checkPermission ()) {
                editUserView.cameraIntent ();
            }
        } else if (item.equals ( context.getString ( R.string.gallery ) )) {
            userChosenTask = context.getString ( R.string.gallery );
            if (checkPermission ()) {
                editUserView.galleryIntent ();
            }
        } else if (item.equals ( context.getString ( R.string.back ) )) {
            editUserView.hideChooserDialog ( dialog );
        }
        return userChosenTask;
    }

    @Override
    public void requestPermissionResult(CharSequence userChosenTask) {
        if (userChosenTask.equals ( context.getString ( R.string.camera ) ))
            editUserView.cameraIntent ();
        else if (userChosenTask.equals ( context.getString ( R.string.gallery ) ))
            editUserView.galleryIntent ();
    }

    @Override
    public void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras ().get ( "data" );
        ByteArrayOutputStream bytes = new ByteArrayOutputStream ();
        bitmap.compress ( Bitmap.CompressFormat.JPEG, 90, bytes );
        File destination = new File ( Environment.getExternalStorageDirectory (),
                System.currentTimeMillis () + ".jpg" );
        FileOutputStream fo;
        try {
            destination.createNewFile ();
            fo = new FileOutputStream ( destination );
            fo.write ( bytes.toByteArray () );
            fo.close ();
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        editUserView.setImageView ( bitmap );
    }

    @Override
    public void onSelectedFromGalleryResult(Intent data) {
        if (data != null && data.getData () != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap ( context.getContentResolver (), data.getData () );
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        editUserView.setImageView ( bitmap );
    }

    @Override
    public void validInput(String name, String pass, String repeatPass) {
        if (name.length () >= 5 && pass.length () >= 8 && repeatPass.equals ( pass )) {
            user.setName ( name );
            user.setPassword ( pass );
            editUserDetail ();
        } else {
            if (name.length () < 5)
                editUserView.showNameError ( "نام و نام خانوادگی نباید کمتر از 5 حرف داشته باشد!" );
            if (pass.length () < 8)
                editUserView.showPasswordError ( "رمز عبور نباید کمتر از 8 حرف باشد!" );
            if (!repeatPass.equals ( pass ) || repeatPass.length () == 0)
                editUserView.showRepeatPassError ( "تکرار رمز عبور به درستی وارد نشده است!" );
        }
    }

    private void editUserDetail() {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest ( Request.Method.POST, UrlEndPoints.UPDATE_USER,
                new Response.Listener<NetworkResponse> () {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject ( new String ( response.data ) );
                            if (obj.getString ( "message" ).equals ( "تغییرات با موفقیت ثبت شد!" )) {
                                SharedPrefManager.getInstance ( context ).userLogin ( user );
                                editUserView.editOk ( obj.getString ( "message" ) );
                            } else
                                editUserView.editFailed ( obj.getString ( "message" ) );
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "error in onResponse -> " + e.getMessage () );
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i ( Constant.SERVER_LOG, "error in onErrorResponse -> " + error.getMessage () );
            }
        } ) {
            //Here we add user detail
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put ( "name", user.getName () );
                params.put ( "uuid", user.getUuid ().toString () );
                params.put ( "password", user.getPassword () );
                Log.i ( Constant.SERVER_LOG, "getParams()" );
                return params;
            }

            //Here we are passing passing photo by renaming it with a unique name
            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<> ();
                params.put ( "pic", new DataPart ( System.currentTimeMillis () + ".jpg", getFileDataFromDrawable ( bitmap ) ) );
                Log.i ( Constant.SERVER_LOG, "getByteData()" );
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue ( context ).add ( volleyMultipartRequest );
    }
    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * */
    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
        bitmap.compress ( Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream );
        return byteArrayOutputStream.toByteArray ();
    }
}
