package org.reyan.weatherwear.thread;

import android.util.Log;

import org.reyan.weatherwear.activity.MainActivity;
import org.reyan.weatherwear.service.UpdateService;

/**
 * Created by reyan on 11/4/15.
 */
public class AutoUpdateThread extends Thread {

    private static final long INTERVAL = 20000;

    private volatile boolean finished;

    private MainActivity mainActivity;

    public AutoUpdateThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void close() { finished = true; }

    @Override
    public void run() {
        while (!finished) {
            Log.d("AutoThread", "running");
            if (UpdateService.update(mainActivity)) {
                mainActivity.getHandler().sendEmptyMessage(0);
            }

            try {
                sleep(INTERVAL);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }

}
