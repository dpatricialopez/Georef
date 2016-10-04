package inova.georef;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class log2 extends AppCompatActivity {


    private  EditText cedulac, passw;
    Button entrar, registro, cambio;
    private ProgressDialog pDialog;
    private String usuario, clave, cedula, correo;
    private int success;
    JSONParser jParser = new JSONParser();
   private static String url = "http://innmagina.cloudapp.net/seminario/app/movil2/login2.php"; //url donde se aloja el Webserver
   // private static String url = "http://pruebasgeorref.com/poll/movil2/login2.php"; //url donde se aloja el Webserver
   // private static String url = "http://192.168.1.2/poll/movil2/login2.php"; //url donde se aloja el Webserver

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        getSupportActionBar().hide();
        cedulac= (EditText) findViewById(R.id.cedulat);
        passw= (EditText) findViewById(R.id.editText8);
        entrar= (Button) findViewById(R.id.button);
        registro= (Button) findViewById(R.id.button8);
        entrar= (Button) findViewById(R.id.button);
        cambio= (Button) findViewById(R.id.button69);

        cambio.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent i=new Intent(log2.this, recover.class);
                //startActivity(i);
                }
        });

        registro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent i=new Intent(log2.this, registro.class);
                //startActivity(i);
                }
        });


        entrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String message;

                usuario = cedulac.getText().toString();
                clave = passw.getText().toString();

                if (usuario.trim().length() != 0) {
                        CharSequence email=usuario;
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            if (isConnecting(getApplicationContext())) {
                                new login().execute();
                            } else {
                                message= getString(R.string.net);
                                alert(message);
                            }
                        }
                        else {
                            message="Ingrese una cuenta de correo válida";
                            alert(message);
                        }

                    }

                 else {
                    message="El campo usuario no puede estar vacío";
                    alert(message);
                }
            }
        });

    }


    class login extends AsyncTask< String, String, String > {

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(log2.this);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("correo", usuario.trim()));
            params.add(new BasicNameValuePair("clave", clave.trim()));

            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

            try {
                success= json.getInt("success");
                cedula= json.getString("cedula");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String result) {
            String message;
            pDialog.dismiss();//ocultamos progess dialog.
             if (success==1){
                 Intent i=new Intent(log2.this, Opciones.class);
                 i.putExtra("user",cedula);
                 i.putExtra("correo",usuario);
                 // Luego de login se transmite la cédula del usuario
                 startActivity(i);
                 finish();

            }else if (success==2){

                 message="Clave incorrecta";
                 alert(message);

            }
             else if (success==3){

                 message="El usuario ingresado no está registrado.";
                 alert(message);

             }

        }

    }

    public void err_login(){
        Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);

    }

    public void alert(String message){
        err_login();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(message);
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id){

            }
        });
        AlertDialog adialog= alertDialogBuilder.create();
        adialog.show();

    }

    private boolean isConnecting(Context applicationContext){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();
        if (ni==null){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  { //inhabiñita función atrás
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

