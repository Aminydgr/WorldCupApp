package com.example.amin.maktabprojectworldcupapp.register;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amin on 8/10/2018.
 */

public class RegisterPresenter implements IRegisterPresenter {

    private Context context;
    private User user;
    private IRegisterView registerView;
    private Bitmap bitmap;

    public RegisterPresenter(IRegisterView registerView, Context context) {
        this.context = context;
        this.registerView = registerView;
    }

    @Override
    public void back() {
        registerView.backToLoginPage ();
    }

    @Override
    public void selectChooser() {
        registerView.showChooserDialog ();
    }


    @Override
    public boolean checkPermission() {
        //check permission at runtime for Marshmallow & greater than Marshmallow version
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission ( context, Manifest.permission.READ_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale (
                        (Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE )) {
                    registerView.showPermissionDialog ();
                } else {
                    registerView.requestPermissions ();
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
    public String selectImage(CharSequence item, DialogInterface dialog) {
        String userChosenTask = null;
        if (item.equals ( context.getString ( R.string.camera ) )) {
            userChosenTask = context.getString ( R.string.camera );
            if (checkPermission ()) {
                registerView.cameraIntent ();
            }
        } else if (item.equals ( context.getString ( R.string.gallery ) )) {
            userChosenTask = context.getString ( R.string.gallery );
            if (checkPermission ()) {
                registerView.galleryIntent ();
            }
        } else if (item.equals ( context.getString ( R.string.back ) )) {
            registerView.hideChooserDialog ( dialog );
        }
        return userChosenTask;
    }

    @Override
    public void requestPermissionResult(CharSequence userChosenTask) {
        if (userChosenTask.equals ( context.getString ( R.string.camera ) ))
            registerView.cameraIntent ();
        else if (userChosenTask.equals ( context.getString ( R.string.gallery ) ))
            registerView.galleryIntent ();
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
        registerView.setImageView ( bitmap );
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
        registerView.setImageView ( bitmap );
    }

    @Override
    public void validInput(String name, String phoneNumber, String password, String repeatPassword, boolean admin) {
        if (name.length () >= 5 && validPhoneNumber ( phoneNumber ) && password.length () >= 8 && repeatPassword.equals ( password )) {
            user = new User ( name, phoneNumber, password, admin );
            uploadUserDetail ();
        } else {
            if (name.length () < 5)
                registerView.showNameError ( "نام و نام خانوادگی نباید کمتر از 5 حرف داشته باشد!" );
            if (!validPhoneNumber ( phoneNumber ))
                registerView.showPhoneNumberError ( "شماره وارد شده معتبر نیست!" );
            if (password.length () < 8)
                registerView.showPasswordError ( "رمز عبور نباید کمتر از 8 حرف باشد!" );
            if (!repeatPassword.equals ( password ) || repeatPassword.length () == 0)
                registerView.showRepeatPassError ( "تکرار رمز عبور به درستی وارد نشده است!" );
        }
    }

    private void uploadUserDetail() {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest ( Request.Method.POST, UrlEndPoints.UPLOAD_USER,
                new Response.Listener<NetworkResponse> () {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject ( new String ( response.data ) );
                            if (obj.getString ( "message" ).equals ( "ثبت نام با موفقیت انجام شد!" ))
                                registerView.registerOk ( obj.getString ( "message" ) );
                            else
                                registerView.registerFailed ( obj.getString ( "message" ) );
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
                params.put ( "phoneNumber", user.getPhoneNumber () );
                if (user.getAdmin ())
                    params.put ( "admin", "yes" );
                else
                    params.put ( "admin", "no" );

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

    private boolean validPhoneNumber(String phoneNumber) {
        String mobileReg = "(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}";
        Pattern pattern = Pattern.compile ( mobileReg );
        Matcher matcher = pattern.matcher ( phoneNumber );
        return matcher.matches ();
    }

}
