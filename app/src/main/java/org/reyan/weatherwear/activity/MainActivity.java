package org.reyan.weatherwear.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.reyan.weatherwear.R;
import org.reyan.weatherwear.domain.Dressing;
import org.reyan.weatherwear.domain.Setting;
import org.reyan.weatherwear.domain.Weather;
import org.reyan.weatherwear.thread.AutoUpdateThread;
import org.reyan.weatherwear.thread.ManualUpdateThread;

public class MainActivity extends AppCompatActivity {

    private static final long MIN_TIME = 1000;
    private static final float MIN_DISTANCE = 1000;

    private LocationManager locationManager;
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null && setting.isLocal()) {
                setting.setLatitude(location.getLatitude());
                setting.setLongitude(location.getLongitude());
                new ManualUpdateThread(handler, setting, weather, dressing).start();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private AutoUpdateThread autoUpdateThread;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                // ui update here
            }
        }

    };

    private Setting setting = Setting.mock();
    private Weather weather = Weather.mock();
    private Dressing dressing = Dressing.mock();

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        test = (TextView) findViewById(R.id.test);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null && setting.isLocal()) {
            setting.setLatitude(location.getLatitude());
            setting.setLongitude(location.getLongitude());
            new ManualUpdateThread(handler, setting, weather, dressing).start();
        }
        locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, locationListener);

        autoUpdateThread = new AutoUpdateThread(handler, setting, weather, dressing);
        autoUpdateThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
        autoUpdateThread.close();
    }
}
