package org.reyan.weatherwear.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by reyan on 11/4/15.
 */
public class JSONService {

    public static final int ACQUIRE_WEATHER = 0;
    public static final int ACQUIRE_CITY = 1;
    public static final int SEARCH_CITY = 2;

    // Acquire weather by id or coordinate: BASE + "id or ordinate" + API_KEY
    private static final String BASE_URL_FOR_ACQUIRING_WEATHER =
            "http://api.openweathermap.org/data/2.5/weather?";
    private static final String API_KEY = "&appid=2cc497cafac898b5b9aae020a2040dc2";

    // Acquire city by coordinate: HEAD + "latitude, longitude" + TAIL
    private static final String HEAD_URL_FOR_ACQUIRING_CITY =
            "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
    private static final String TAIL_URL_FOR_ACQUIRING_CITY = "&sensor=true";

    // Search city: BASE + "keyword" + "&cnt=" + "restricted num" + API_KEY
    private static final String BASE_URL_FOR_SEARCHING_CITY =
            "http://api.openweathermap.org/data/2.5/find?mode=json&type=like&q=";
    private static final int RESTRICTED_NUM = 8;

    public static JSONObject getJSONObject(String key, int type) throws JSONException {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            switch (type) {
                case ACQUIRE_WEATHER:
                    con = (HttpURLConnection) new URL(BASE_URL_FOR_ACQUIRING_WEATHER
                            + key
                            + API_KEY).openConnection();
                    break;
                case ACQUIRE_CITY:
                    con = (HttpURLConnection) new URL(HEAD_URL_FOR_ACQUIRING_CITY
                            + key
                            + TAIL_URL_FOR_ACQUIRING_CITY).openConnection();
                    break;
                case SEARCH_CITY:
                    con = (HttpURLConnection) new URL(BASE_URL_FOR_SEARCHING_CITY
                            + key + "&cnt=" + RESTRICTED_NUM
                            + API_KEY).openConnection();
                    break;
                default:
                    return null;
            }
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\r\n");
            }
            is.close();
            con.disconnect();

            return new JSONObject(buffer.toString());
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;
    }

}
