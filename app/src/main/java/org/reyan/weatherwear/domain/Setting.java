package org.reyan.weatherwear.domain;

/**
 * Created by reyan on 11/4/15.
 */
public class Setting {

    private boolean local;
    private boolean gender;
    private boolean isFormal;

    private float sensitivity;

    private double latitude;
    private double longitude;

    public boolean isLocal() { return local; }
    public void setLocal(boolean local) { this.local = local; }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public String getKey() { return "lat=" + latitude + "&lon=" + longitude; }

    public static Setting mock() {
        Setting setting = new Setting();
        setting.setLocal(true);
        return setting;
    }

}
