package org.reyan.weatherwear.domain;

/**
 * Created by reyan on 11/4/15.
 */
public class Weather {

    private float temp;

    public float getTemp() { return temp; }
    public void setTemp(float temp) { this.temp = temp; }

    public static Weather mock() {
        return new Weather();
    }

}
