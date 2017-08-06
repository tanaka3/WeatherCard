package net.masaya3.weather;

import android.app.Application;

import net.masaya3.weather.model.RealmManager;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class WeatherCardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmManager.init(getApplicationContext());
    }
}
