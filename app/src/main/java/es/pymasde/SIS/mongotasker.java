package es.pymasde.SIS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.apache.http.entity.InputStreamEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tomer on 07-Mar-18.
 */

public class mongotasker extends AsyncTask<String, Void, String> {

    Context c;
    String msg;
    StringBuilder res = new StringBuilder();
    public mongotasker(Context _c,String s)
    {
        this.c = _c;
        msg = s;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(c,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        //Init and config
        try {
            return  GetData(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String GetData(String Url) throws IOException{
        try{
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(1000);
            connection.setConnectTimeout(1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type","Application/json");
            connection.connect();
            //Read From Server
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine())!=null)
            {
                res.append(line).append("\n");
            }

        }
        catch(Exception ex)
        {
            Log.e("Error","+Network Error+");
            return "";

        }
        return res.toString();
    }
}
