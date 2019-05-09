package com.example.heightcalculator;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetJSON
{

    private LatLng position;
    private double height;
    private TextView text;
    private Activity activity;
    private String rounded;

    public GetJSON(Activity activity, TextView text, LatLng position)
    {
        this.activity = activity;
        this.text = text;
        this.position = position;
    }



    public void StartClient()
    {
        OkHttpClient client = new OkHttpClient();
        String elevationBaseUrl = "https://maps.googleapis.com/maps/api/elevation/json?";
        String curLocation = "locations=" + position.latitude + "," + position.longitude;
        String key = "&key=" + "AIzaSyDcUC_tIE-tFIV1HieNXe5j6C5vgy6q_yQ";
        String completeUrl = elevationBaseUrl + curLocation + key;
        Request request = new Request.Builder().url(completeUrl).build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                try {
                    //Saving response in variables, because response can only be used once
                    ResponseBody body = response.body();
                    String bodyString = body.string();
                    JSONObject jsonObject = new JSONObject(bodyString);

                    //Getting the Array with the results from the API
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject elevation = (JSONObject)results.get(0);

                    //Getting the elevation within the array
                    height = elevation.getDouble("elevation");

                    //Restricting the number to show only 2 digits
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    rounded = nf.format(height);

                    //Since we cant update UI in a "do in background", we need to use a runnable
                    SetHeight();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }
        });

    }
    private void SetHeight(){
        activity.runOnUiThread(HeightRun);
    }

    public Runnable HeightRun = new Runnable() {
        @Override
        public void run() {
            text.setText(rounded);
        }
    };

}


