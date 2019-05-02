package com.example.heightcalculator;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Console;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetJSON extends AsyncTask<Void,Void,Void>
{

    private LatLng position;
    private int height;
    private Request request;

    //Fix Memory leak

    private TextView text;

    public GetJSON(TextView text, LatLng position)
    {
        this.text = text;
        this.position = position;
    }

    @Override
    protected void onPreExecute()
    {
        String elevationBaseUrl = "https://maps.googleapis.com/maps/api/elevation/json?";
        String curLocation = "locations=" + position.latitude + "," + position.longitude;
        String key = "&key=" + "AIzaSyDcUC_tIE-tFIV1HieNXe5j6C5vgy6q_yQ";
        String completeUrl = elevationBaseUrl + curLocation + key;
        request = new Request.Builder().url(completeUrl).build();
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback()
        {
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

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }


        });
        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid)
    {
        if (isCancelled())
        {
            text.setText(R.string.failed);
        }
        else
        {
            text.setText(height);
        }

    }

    /*@Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }*/


    public LatLng getPosition() { return position;}
    public void setPosition(LatLng position) { this.position = position; }

    public int getHeight() { return height; }

}
