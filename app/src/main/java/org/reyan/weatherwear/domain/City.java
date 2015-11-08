package org.reyan.weatherwear.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reyan.weatherwear.service.JSONService;

/**
 * Created by reyan on 11/7/15.
 */
public class City {

    private long id;

    private double latitude;
    private double longitude;

    private String cityName;
    private String stateName = "";
    private String countryName;

    public City(long id, double latitude, double longitude,
                String cityName, String countryName) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public long getId() { return id; }
    public String getCityName() { return cityName; }
    public String getStateName() { return stateName; }
    public String getCountryName() { return countryName; }

    @Override
    public String toString() {
        if ("".equals(stateName)) {
            return cityName + ", " + countryName;
        }
        return cityName + ", " + stateName + ", " + countryName;
    }

    public static City getCity(long id, double latitude, double longitude,
                               String cityName, String countryName) {
        City city = new City(id, latitude, longitude, cityName, countryName);
        String stateName = null;
        try {
            /*
            Need to modify later
             */
            JSONArray jsonArray = JSONService.getJSONObject(
                    String.valueOf(latitude) + "," + String.valueOf(longitude),
                    JSONService.ACQUIRE_CITY)
                    .getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONArray("address_components");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray types = jsonObject.getJSONArray("types");
                if (types.length() == 2
                        && "administrative_area_level_1".equals(types.getString(0))
                        && "political".equals(types.getString(1))) {
                    stateName = jsonObject.getString("short_name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (stateName != null) {
            city.stateName = stateName;
        }
        return city;
    }
}
