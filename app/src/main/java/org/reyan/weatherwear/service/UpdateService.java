package org.reyan.weatherwear.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.reyan.weatherwear.domain.Dressing;
import org.reyan.weatherwear.domain.Setting;
import org.reyan.weatherwear.domain.Weather;

/**
 * Created by reyan on 11/4/15.
 */
public class UpdateService {

    public static boolean update(Setting setting, Weather weather, Dressing dressing) {
        //TODO
            /* several steps:
            1. get json object, if null, not update
            2. else update weather based on json
            3. and call algorithm to update dressing
             */
        // need synchronized here for domain?
        JSONObject json = null;
        try {
            json = JSONService.getJSONObject(setting.getKey());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (json != null) {
            // ??? synchonized(this) {}
            // update weather based on json
            // update dressing using algorithm
            return true;
        }

        return false;
    }

}
