package org.reyan.weatherwear.thread;

import android.os.Handler;

import org.reyan.weatherwear.domain.Dressing;
import org.reyan.weatherwear.domain.Setting;
import org.reyan.weatherwear.domain.Weather;
import org.reyan.weatherwear.service.UpdateService;
import org.reyan.weatherwear.activity.MainActivity;

/**
 * Created by reyan on 11/4/15.
 */
public class ManualUpdateThread extends Thread {

    private Handler handler;
    private Setting setting;
    private Weather weather;
    private Dressing dressing;

    public ManualUpdateThread(Handler handler,
                              Setting setting, Weather weather, Dressing dressing) {
        this.handler = handler;
        this.setting = setting;
        this.weather = weather;
        this.dressing = dressing;
    }

    @Override
    public void run() {
        if (UpdateService.update(setting, weather, dressing)) {
            handler.sendEmptyMessage(0);
        }
    }

}
