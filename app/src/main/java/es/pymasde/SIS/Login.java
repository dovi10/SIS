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
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import es.pymasde.SIS.user_data.Measurements;

public class Login extends Activity {

    Button Login;
    Button Check;
    Button Reg;
    TextView forgot;
    private final String Server_url = "xxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = (Button)findViewById(R.id.Login_Button);
        Check = (Button)findViewById(R.id.Check_Connection);
        forgot = (TextView)findViewById(R.id.ForgotPassword);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Mongo_check_user().equals("Found")) {

                        Intent i = new Intent(Login.this, BlueTerm.class);
                        startActivity(i);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"could not find the user",Toast.LENGTH_LONG).show();
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
        org.json.JSONObject params = new Measurements().ExtractJson();
        String d = (String) params.get("Time");
        Elastic_API el = new Elastic_API();
            AsyncTask<String, String, JSONObject> temp = el.execute(Server_url,params.toString());
        //String n = temp.get().toString();



    }

}
