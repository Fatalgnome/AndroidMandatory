package com.example.heightcalculator;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetJSON extends AsyncTask<Void,Void,Void>
{

    private LatLng position = new LatLng(21, -30);



    private int height;
    private String elevationBaseUrl = "https://maps.googleapis.com/maps/api/elevation/json?";
    private String curLocation = "locations=" + new LatLng(position.latitude,position.longitude);
    private String key = "&key=" + R.string.google_maps_key;
    private String completeUrl = elevationBaseUrl + curLocation + key;
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(completeUrl).build();

    @Override
    protected Void doInBackground(Void... arg0)
    {
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("debug",response.body().string());
                        JSONArray results = jsonObject.getJSONArray("results");

                        height = (int)results.get(0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
        return null;
    }

    public LatLng getPosition() { return position;}
    public void setPosition(LatLng position) { this.position = position; }

    public int getHeight() { return height; }

}
