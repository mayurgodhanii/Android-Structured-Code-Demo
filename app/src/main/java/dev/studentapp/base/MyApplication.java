package dev.studentapp.base;

import android.app.Application;

import dev.studentapp.util.PrefData;

/**
 * Created by Nirav Dangi on 05/07/16.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        PrefData.updatePreferances(this);

        //*** Test Crash - Fabric ****//

//        int x = Integer.parseInt("crash");

        //***
    }
}
