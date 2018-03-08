package es.pymasde.SIS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Login extends Activity {

    Button Login;
    Button Check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = (Button)findViewById(R.id.Login_Button);
        Check = (Button)findViewById(R.id.Check_Connection);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(Login.this,BlueTerm.class);
                startActivity(i);

            }
        });
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Mongo();
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
                            Mongo_check();
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

    private void Mongo_check() throws ExecutionException, InterruptedException {
        String res = new mongotasker(getApplicationContext(),"Checking Connection on: 192.168.1.10")
                .execute("http://192.168.1.10:1000/api/status").get();
        if(res!="") {
            Toast.makeText(getApplicationContext(), "connection succeeded!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "could not connect!", Toast.LENGTH_LONG).show();

        }
    }
}
