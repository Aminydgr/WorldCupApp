package com.example.amin.maktabprojectworldcupapp.survey;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.SharedPrefManager;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.Option;
import com.example.amin.maktabprojectworldcupapp.model.Question;
import com.example.amin.maktabprojectworldcupapp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Amin on 8/17/2018.
 */

public class SurveyPresenter implements ISurveyPresenter {

    private ISurveyView surveyView;
    private Context context;
    private List<Question> questionList;
    private List<Option> optionList;

    public SurveyPresenter(ISurveyView surveyView, Context context) {
        this.surveyView = surveyView;
        this.context = context;
        this.questionList = new ArrayList<> ();
        this.optionList = new ArrayList<> ();
    }

    @Override
    public void loadQuestions() {
        StringRequest stringRequest = new StringRequest ( Request.Method.GET, UrlEndPoints.LOAD_QUESTIONS,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject ( response );
                            JSONArray jsonArray = jsonObject.getJSONArray ( "questions" );
                            for (int i = 0; i < jsonArray.length (); i++) {
                                JSONObject obj = jsonArray.getJSONObject ( i );
                                questionList.add ( new Question (
                                        UUID.fromString ( obj.getString ( "uuid" ) ),
                                        obj.getString ( "text" )
                                ) );
                            }
                            surveyView.setUpPager ( questionList );
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "onResponseError" + e.getMessage () );
                        }
                    }
                },
                new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i ( Constant.SERVER_LOG, error.getMessage () );
                    }
                } );

        Volley.newRequestQueue ( context ).add ( stringRequest );
    }

    @Override
    public void allowedUser() {
        User user = SharedPrefManager.getInstance ( context ).getUser ();
        if (user.getAdmin ())
            surveyView.addAllowed ();
        else
            surveyView.addNotAllowed ();
    }

    @Override
    public void loadOptions(final UUID questionUUID) {
        StringRequest stringRequest = new StringRequest ( Request.Method.DEPRECATED_GET_OR_POST, UrlEndPoints.LOAD_OPTIONS,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject ( response );

                            if (!jsonObject.getBoolean ( "error" )) {
                                JSONArray jsonArray = jsonObject.getJSONArray ( "options" );
                                for (int i = 0; i < jsonArray.length (); i++) {

                                    JSONObject optionJson = jsonArray.getJSONObject ( i );

                                    optionList.add ( new Option ( UUID.fromString ( optionJson.getString ( "uuid" ) ),
                                            UUID.fromString ( optionJson.getString ( "questionUUID" ) ),
                                            optionJson.getString ( "text" ) ) );
                                }
                                surveyView.setUpOptionAdapter ( optionList );
                            }
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "onResponseError" + e.getMessage () );
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i ( Constant.SERVER_LOG, error.getMessage () );
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put ( "questionUUID", questionUUID.toString () );
                return params;
            }
        };
        Volley.newRequestQueue ( context ).add ( stringRequest );
    }
}
