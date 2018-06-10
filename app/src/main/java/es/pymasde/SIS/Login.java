package es.pymasde.SIS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import es.pymasde.SIS.user_data.Measurements;

public class Login extends Activity {

    Button Login;
    Button Check;
    Button Reg;
    TextView forgot;
    private static String UserName = "not found";

    private static long UserID;
    public static String getUserName() {
        return UserName;
    }

    public static long getUserID() {
        return UserID;
    }

    public static void setUserID(long userID) {
        UserID = userID;
    }
    private static final String Server_url = "http://siswebap.azurewebsites.net/api/Measurments";
    public static String getServer_url() {
        return Server_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = (Button)findViewById(R.id.Login_Button);
        Check = (Button)findViewById(R.id.Check_Connection);
        forgot = (TextView)findViewById(R.id.ForgotPassword);
        setUserID(400);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!searchLocally()) {


                        if (Mongo_check_user().equals("Found")) {
                            EditText Name = (EditText) findViewById(R.id.Login_Name);
                            UserName = Name.getText().toString();
                            Intent i = new Intent(Login.this, BlueTerm.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(getApplicationContext(), "could not find the user", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Intent i = new Intent(Login.this, BlueTerm.class);
                        startActivity(i);
                    }
                }
                catch(Exception ex)
                {
                    Log.e("Error","+could not start check+");
                }


            }
        });
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Check_Elastic();
                }
                catch(Exception ex)
                {
                    Log.e("Error","+could not start check+");
                }
            }
        });
        Reg  = (Button)findViewById(R.id.Login_Register);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent i = new Intent(Login.this, Register.class);
                        startActivity(i);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,forgotPass.class);
                startActivity(i);
            }
        });
    }

    private boolean searchLocally() {
        HashMap<String,Long> Locals = new HashMap<String, Long>();
        Locals.put("Dovi",Long.parseLong("9781527865"));
        Locals.put("Tomer",Long.parseLong("2203871935"));
        Locals.put("Yossi",Long.parseLong("5039103367"));
        Locals.put("Naftali",Long.parseLong("8229019706"));
        EditText Name = (EditText)findViewById(R.id.Login_Name);
        for(String s : Locals.keySet())
        {

            if(Name.getText().toString().equals(s))
            {
                setUserID(Locals.get(s));
                UserName = Name.getText().toString();
                return true;
            }
        }
        return false;
    }

    public void Dialog_Mongo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Click ok to start testing")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.app_name)
                .setCancelable( false )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Mongo_check_user();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String Mongo_check_user() throws ExecutionException, InterruptedException {
        EditText Name = (EditText)findViewById(R.id.Login_Name);
        EditText Pass = (EditText)findViewById(R.id.Login_Password);
        String res = "Not";
        Dialog d = new Dialog(Login.this);
        d.setContentView(R.layout.progress);
        d.show();
        try{
            int p = Integer.parseInt(Pass.getText().toString());
             res = new mongotasker(getApplicationContext(),Name.getText().toString(),p)
                    .execute(Mongo_Auth.Get_Users()).get();

        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Password must only contain digits",Toast.LENGTH_LONG).show();
            d.dismiss();
        }
            d.dismiss();
        return res;

    }
    private void Check_Elastic() throws org.json.JSONException, ExecutionException, InterruptedException, ParseException {
        Measurements measure = new Measurements();
        try {
            String FullText = "1,2,3,4,5\n";
            String[] readings = FullText.split(",");
            measure.setHudimityVal(Integer.parseInt(readings[0]));
            measure.setHumidityPer(Integer.parseInt(readings[1]));
            measure.setIdrVal(Integer.parseInt(readings[2]));
            measure.setWaterVal(Integer.parseInt(readings[3]));
            measure.setBuzzerVal(Integer.parseInt(readings[4].substring(0,1)));
            measure.setUserId(getUserID());
            measure.setUserName(getUserName());
            org.json.JSONObject params = measure.ExtractJson();
            //String d = (String) params.get("Time");
            Elastic_API el = new Elastic_API();
            AsyncTask<String, String, JSONObject> temp = el.execute(Server_url,params.toString());
            //String n = temp.get().toString();
        }
        catch(Exception ex)
        {
            Log.e("Values format","values string was not correctly entered\n"+ex.toString());
            Toast.makeText(Login.this,"Error occured with values extraction from text",Toast.LENGTH_LONG);

        }




    }

}
