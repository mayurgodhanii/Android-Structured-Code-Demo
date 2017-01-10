package dev.studentapp.util;

/**
 * Created by Nirav Dangi on 29/06/16.
 *
 * This is a custom Logger class. It is design in order to disable all the logs when app is in production environment.
 * For displaying log, dev.studentapp.util.Log class will be used instead of android.util.Log.
 */
public class Log {

    private static final String TAG = "NIRAV";

    public static void d(String log){
        if (Constants.DEBUG_MODE)
            android.util.Log.d(TAG, log);
    }

    public static void e(String log){
        if (Constants.DEBUG_MODE)
            android.util.Log.e(TAG, log);
    }
}
