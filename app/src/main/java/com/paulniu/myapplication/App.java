package com.paulniu.myapplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Coder: niupuyue
 * Date: 2019/7/15
 * Time: 11:58
 * Desc:
 * Version:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("office.realm")
                .schemaVersion(3)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
