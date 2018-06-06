package es.pymasde.SIS;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import es.pymasde.SIS.user_data.User;

/**
 * Created by Tomer on 08-Mar-18.
 */

public class Elastic_API extends AsyncTask<String,String,JSONObject>{

protected JSONObject doInBackground(String... params)
{

    try {
        return new JSONObject(new String(executePost(params[0],params[1])));
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return null;
}
@Override
protected void onPostExecute(JSONObject result)
{
    String str = result.toString();
}
public String executePost(String targetURL,String urlParameters) {
    int timeout=5000;
    java.net.URL url;
    HttpURLConnection connection = null;
    try {
        // Create connection

        url = new java.net.URL(targetURL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/json");

        connection.setRequestProperty("Content-Length",
                "" + Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

        // Send request
        DataOutputStream wr = new java.io.DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        // Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new java.io.InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();

    } catch (java.net.SocketTimeoutException ex) {
        ex.printStackTrace();

    } catch (MalformedURLException ex) {
        java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException ex) {

        java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {

        if (connection != null) {
            connection.disconnect();
        }
    }
    return null;
}

}
