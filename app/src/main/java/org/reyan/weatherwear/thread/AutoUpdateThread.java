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
public class AutoUpdateThread extends Thread {

    private static final long INTERVAL = 20000;

    private volatile boolean finished;

    private Handler handler;
    private Setting setting;
    private Weather weather;
    private Dressing dressing;

    public AutoUpdateThread(Handler handler,
                            Setting setting, Weather weather, Dressing dressing) {
        this.handler = handler;
        this.setting = setting;
        this.weather = weather;
        this.dressing = dressing;
    }

    public void close() { finished = true; }

    @Override
    public void run() {
        while (!finished) {
            if (UpdateService.update(setting, weather, dressing)) {
                handler.sendEmptyMessage(0);
            }

            try {
                sleep(INTERVAL);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }

}
