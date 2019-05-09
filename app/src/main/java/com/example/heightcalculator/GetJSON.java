package com.example.heightcalculator;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    private Context context;

    public GetJSON(Context context, TextView text, LatLng position)
    {
        this.context = context;
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
                    ResponseBody body = response.body();
                    String bodyString = body.string();
                    JSONObject jsonObject = new JSONObject(bodyString);
                    Log.d("debug",bodyString);
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject elevation = (JSONObject)results.get(0);
                    height = elevation.getDouble("elevation");
                    Log.d("debug", ""+height);

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
        //context.runOnUiThread(HeightRun);
    }
    public Runnable HeightRun = new Runnable() {
        @Override
        public void run() {
            text.setText("" + height);
        }
    };

    public double getHeight() {
        return height;
    }
}


