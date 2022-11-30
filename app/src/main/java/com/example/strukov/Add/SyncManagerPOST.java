package com.example.strukov.Add;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SyncManagerPOST {
    private static SyncManagerPOST INSTANCE;
    private static  String serverPath = "http://nba-kgpk.somee.com/api/players";

    private SyncManagerPOST()
    {}
    public static SyncManagerPOST getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new SyncManagerPOST();
        }
        return INSTANCE;
    }
    public String postData(String pathData, JSONObject param)
    {
        try {
            URL url = new URL(serverPath + pathData);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream otptStr = connection.getOutputStream();
            otptStr.write(param.toString().getBytes("UTF-8"));
            otptStr.flush();

            InputStream inptStr;
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_CREATED)
                inptStr = connection.getInputStream();
            else
                inptStr = connection.getErrorStream();

            BufferedReader rdr = new BufferedReader(new InputStreamReader(inptStr));
            String line = "";
            StringBuilder res = new StringBuilder();
            while    ((line = rdr.readLine()) != null )
            {
                res.append(line);
            }
            rdr.close();
            connection.disconnect();
            return res.toString();
        }
        catch (Exception ex)
        {
            return null;
        }
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
}
