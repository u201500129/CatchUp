package pe.edu.upc.catchup;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by Proyecto on 16/09/2017.
 */

public class CatchUpApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
