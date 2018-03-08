package es.pymasde.SIS;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import es.pymasde.SIS.user_data.User;

/**
 * Created by Tomer on 07-Mar-18.
 */

public class mongotasker extends AsyncTask<String, Void, String> {

    Context c;
    String name;
    List<User> users = new ArrayList<User>();
    StringBuilder res = new StringBuilder();
    private int Pass;

    public mongotasker(Context _c,String s,int p)
    {
        this.c = _c;
        name = s;
        Pass = p;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // Toast.makeText(c,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("App_Debug","+finished check+");
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
        return "Not Connected";
    }
    private String GetData(String Url) throws IOException{
        try{
            Log.d("App_Debug","+Entered Checking+");
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("Content-type","Application/json");
            connection.connect();
            //Read From Server
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine())!=null)
            {
                res.append(line);
            }
            connection.disconnect();

        }
        catch(Exception ex)
        {
            Log.e("App_Debug","+Network Error+");
            return "";

        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>(){}.getType();
        users=gson.fromJson(res.toString(),listType);
        User[] it = users.toArray(new User[users.size()]);
        boolean checked_out = false;
        for(User u : it)
        {
            if(u.getName().equals(name))
            {
               // Toast.makeText(c,"found User",Toast.LENGTH_LONG).show();
                if(u.getPassword() == Pass)
                {
                    checked_out = true;
                }
            }
        }
        if(checked_out)
        {
            return "Found";
        }
        else
        {
            return "Not";
        }
    }
}
