package com.example.strukov.Get;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SyncManagerGET {
    private static SyncManagerGET INSTANCE;
    private static String serverPath = "http://nba-kgpk.somee.com/api/";

    private SyncManagerGET() {

    }

    public static SyncManagerGET GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SyncManagerGET();
        }
        return INSTANCE;
    }

    public static String getData(String method) {
        try {
            URL url = new URL(serverPath + method);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream str = connection.getInputStream();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(str));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = rdr.readLine()) != null) {
                result.append(line);
            }
            rdr.close();
            connection.disconnect();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String deleteData(String method, Integer id){
        try{
            URL url = new URL(serverPath + method + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            InputStream str = connection.getInputStream();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(str));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = rdr.readLine()) != null) {
                result.append(line);
            }
            rdr.close();
            connection.disconnect();
            return result.toString();
        }
        catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
            return null;
        }

    }
}


