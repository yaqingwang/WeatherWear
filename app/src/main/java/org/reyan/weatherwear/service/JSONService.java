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

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String API_KEY = "&appid=2cc497cafac898b5b9aae020a2040dc2";

    public static JSONObject getJSONObject(String key) throws JSONException {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) new URL(BASE_URL + key + API_KEY).openConnection();
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
