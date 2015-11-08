package org.reyan.weatherwear.domain;

/**
 * Created by reyan on 11/4/15.
 */
public class Weather {

    private String cityName = "";
    private double temperature;

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public static Weather mock() {
        return new Weather();
    }

}
