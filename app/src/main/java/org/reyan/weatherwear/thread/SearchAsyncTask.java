package org.reyan.weatherwear.thread;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reyan.weatherwear.activity.SearchableActivity;
import org.reyan.weatherwear.domain.City;
import org.reyan.weatherwear.service.JSONService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reyan on 11/7/15.
 */
public class SearchAsyncTask extends AsyncTask<String, Void, List<City>> {

    private SearchableActivity searchableActivity;

    public SearchAsyncTask(SearchableActivity searchableActivity) {
        this.searchableActivity = searchableActivity;
    }

    @Override
    protected List<City> doInBackground(String... params) {
        List<City> result = new ArrayList<City>();
        try {
            JSONArray jsonArray = JSONService
                    .getJSONObject(params[0], JSONService.SEARCH_CITY)
                    .getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject coord = item.getJSONObject("coord");
                JSONObject sys = item.getJSONObject("sys");
                long id = item.getLong("id");
                double latitude = coord.getDouble("lat");
                double longitude = coord.getDouble("lon");
                String cityName = item.getString("name");
                String countryName = sys.getString("country");
                if (cityName != null
                        && countryName != null
                        && !"".equals(cityName)
                        && !"".equals(countryName)) {
                    result.add(new City(id, latitude, longitude, cityName, countryName));
                    // result.add(City.getCity(id, latitude, longitude, cityName, countryName));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(List<City> result) {
        searchableActivity.show(result);
    }
}
