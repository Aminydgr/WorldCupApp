package com.example.amin.maktabprojectworldcupapp;

import android.app.Application;

import com.example.amin.maktabprojectworldcupapp.model.DaoMaster;
import com.example.amin.maktabprojectworldcupapp.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Amin on 8/9/2018.
 */

public class App extends Application {

    private static final String DB_NAME = "advertising_db";

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate ();

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper ( this, DB_NAME );
        Database database = openHelper.getWritableDb ();
        daoSession = new DaoMaster ( database ).newSession ();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
