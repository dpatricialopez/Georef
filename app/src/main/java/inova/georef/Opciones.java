package inova.georef;

import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public  class Opciones extends AppCompatActivity {

    JSONParser jParser = new JSONParser();
    //private static String url = "http://pruebasgeorref.com/poll/movil2/csaludo.php"; //url donde se aloja el Webserver
    private static String url = "http://innmagina.cloudapp.net/seminario/app/movil2/csaludo.php"; //url donde se aloja el Webserver
    //private static String url = "http://192.168.1.5/poll/movil2/csaludo.php"; //url donde se aloja el Webserver

    final Context context=this;
    Button encuesta, retos,problemas, vermapa;
    TextView nombre, correo;
    public static String name=null;
    public static String apellido=null;
    public static String correox=null;
    static  String cedula, correo2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

// Se definen Widgets
        encuesta= (Button) findViewById(R.id.selencuesta);
        vermapa= (Button) findViewById(R.id.button5);
        retos= (Button) findViewById(R.id.r);
        problemas= (Button) findViewById(R.id.button6);
        nombre= (TextView) findViewById(R.id.name);
        correo= (TextView) findViewById(R.id.Correo);


        Bundle extras = getIntent().getExtras(); // Carga los datos que le manda la actividad anterior
        if (extras != null) {
            cedula  = extras.getString("user");
            correo2  = extras.getString("correo");
        }
        if (isConnecting(getApplicationContext())) {
            new Cargar().execute();//Extrae el nombre, el apellido del encuestador que inicia sesion.
        } else {
            alert();
        }



         encuesta.setOnClickListener(new View.OnClickListener() {

             public void onClick(View view) {
                 Intent i = new Intent(Opciones.this, Menuencuestas.class);
                 i.putExtra("user", cedula);
                 startActivity(i);
                 finish();
             }
         });

        retos.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent i = new Intent(Opciones.this, problemas.class);
                i.putExtra("user",cedula);

                startActivity(i);

            }
        });

        problemas.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent z = new Intent(Opciones.this, Mapa3.class);
                z.putExtra("user", cedula);
                z.putExtra("barrio", "");
                z.putExtra("comuna", "");
                startActivity(z);

            }
        });

         vermapa.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent j=new Intent(Opciones.this, Mapa4.class);
                String b=null;

                j.putExtra("user",cedula);
                j.putExtra("pc","" );
                j.putExtra("comuna","");
                j.putExtra("pn","");
                j.putExtra("unidad","");
                j.putExtra("palabra", "");

                startActivity(j);

            }
        });

    }
    public void salir(View view) { //Cierra Sesion
        Intent a=new Intent(Opciones.this, log2.class);
         startActivity(a);
        finish();

    }

    class Cargar extends AsyncTask<String, String, String> {//Extrae el nombre, el apellido, el numero de encuestas realizadas y sus coordenadas por el encuestador que inicia sesion.

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cedula", cedula));
            JSONObject json3 = jParser.makeHttpRequest(url, "POST", params);

            try {
                name= json3.getString("Nombre");
                apellido= json3.getString("Apellidos");
                correox= json3.getString("correo");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
                     runOnUiThread(new Runnable() {
                      public void run() {
                          String names = name + " " + apellido;
                          nombre.setText(names);
                          correo.setText(correox);
                      }
                  });
                }
            }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {//configura el comportamiento del boton "atras"
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent a=new Intent(Opciones.this, log2.class);
                startActivity(a);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    public void err_login(){
        Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);

    }

    public void alert(){
        err_login();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle( getString(R.string.net));
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
}


