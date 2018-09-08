package com.example.amin.maktabprojectworldcupapp.survey.addSurvey;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amin.maktabprojectworldcupapp.Constant;
import com.example.amin.maktabprojectworldcupapp.UrlEndPoints;
import com.example.amin.maktabprojectworldcupapp.model.Option;
import com.example.amin.maktabprojectworldcupapp.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amin on 8/17/2018.
 */

public class AddSurveyPresenter implements IAddSurveyPresenter {

    private Context context;
    private IAddSurveyView addSurveyView;
    private Question question;
    private List<Option> optionList;

    public AddSurveyPresenter(Context context, IAddSurveyView addSurveyView) {
        this.context = context;
        this.addSurveyView = addSurveyView;
        this.question = new Question ();
        this.optionList = new ArrayList<> ();
    }

    @Override
    public void addOptionDialog() {
        addSurveyView.showAddOptionDialog ();
    }

    @Override
    public void optionAdded(String optionText) {
        optionList.add ( new Option ( question.getUuid (), optionText ) );
    }

    @Override
    public void getOptionList() {
        addSurveyView.setRecyclerAdapter ( optionList );
    }

    @Override
    public void validInput(String questionText) {
        if (questionText.length () > 10 && optionList.size () > 1) {
            question.setText ( questionText );
            uploadOption ();
        } else {
            if (questionText.length () <= 10)
                addSurveyView.showQuestionError ( "متن سوال نباید کمتر از 11 حرف داشته باشد!" );
            if (optionList.size () <= 1)
                addSurveyView.showOptionsCountError ( "تعداد گزینه ها حداقل باید دوتا باشد!" );
        }
    }

    private void uploadOption() {
        for (int i = 0; i < optionList.size (); i++) {
            final Option option = optionList.get ( i );
            StringRequest optionRequest = new StringRequest ( Request.Method.POST, UrlEndPoints.UPLOAD_OPTION,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject ( response );
                                if (obj.getString ( "message" ).equals ( "گزینه با موفقیت ثبت شد!" ))
                                    Log.i ( Constant.SERVER_LOG, obj.getString ( "message" ) );
                                else
                                    addSurveyView.uploadFailed ( obj.getString ( "message" ) );
                            } catch (JSONException e) {
                                Log.i ( Constant.SERVER_LOG, "error in onResponse -> " + e.getMessage () );
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i ( Constant.SERVER_LOG, "error in onErrorResponse -> " + error.getMessage () );
                        }
                    } ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put ( "uuid", option.getUuid ().toString () );
                    params.put ( "questionUUID", option.getQuestionUUID ().toString () );
                    params.put ( "text", option.getText () );
                    Log.i ( Constant.SERVER_LOG, "getParams()" );
                    return params;
                }
            };

            Volley.newRequestQueue ( context ).add ( optionRequest );
        }
        uploadQuestion ();
    }

    private void uploadQuestion() {
        StringRequest questionRequest = new StringRequest ( Request.Method.POST, UrlEndPoints.UPLOAD_QUESTION,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject ( response );
                            if (obj.getString ( "message" ).equals ( "نظرسنجی با موفقیت ثبت شد!" ))
                                addSurveyView.uploadOk ( obj.getString ( "message" ) );
                            else
                                addSurveyView.uploadFailed ( obj.getString ( "message" ) );
                        } catch (JSONException e) {
                            Log.i ( Constant.SERVER_LOG, "error in onResponse -> " + e.getMessage () );
                        }
                    }
                },
                new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i ( Constant.SERVER_LOG, "error in onErrorResponse -> " + error.getMessage () );
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<> ();
                params.put ( "uuid", question.getUuid ().toString () );
                params.put ( "text", question.getText () );
                Log.i ( Constant.SERVER_LOG, "getParams()" );
                return params;
            }
        };

        Volley.newRequestQueue ( context ).add ( questionRequest );
    }

}
