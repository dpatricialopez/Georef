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
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class detalles extends ActionBarActivity {


    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;

    TextView txt1,  txt2, txt3, r1, t1;


    LinearLayout comentario;


     ArrayList<HashMap<String, String>> commentList;

    View myView;


    // url to get all products list
    //private static String url = "http://pruebasgeorref.com/poll/movil2/detalle.php";
    private static String url = "http://innmagina.cloudapp.net/seminario/app/movil2/detalle.php";

    private static String pc, comuna, barrio, user;



    // products JSONArray
    JSONArray comments = null;

    LinearLayout comentarios;

    JSONObject c;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle);

        commentList = new ArrayList<HashMap<String, String>>();


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
           pc  = extras.getString("pc");
            user  = extras.getString("user");
            barrio = extras.getString("barrio");
            comuna=extras.getString("comuna");
                   }
        Log.v("probd", pc);
        comentario=(LinearLayout)findViewById(R.id.layout);
        r1= (TextView) findViewById(R.id.r1);
        t1= (TextView) findViewById(R.id.t1);
        r1.setText(pc);

        new Loaddetalles().execute();


    }

    class Loaddetalles extends AsyncTask<String, String, String> {
        int cont;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(detalles.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("ind",pc));
            params.add(new BasicNameValuePair("comuna",comuna));
            params.add(new BasicNameValuePair("barrio",barrio));

            // getting JSON string from URL
            JSONObject json3 = jParser.makeHttpRequest(url, "POST", params);

            try {
                // Checking for SUCCESS TAG

                comments = json3.getJSONArray("problemas");

                cont=0;
                    for (int i = 0; i < comments.length(); i++) {
                        cont++;
                         c = comments.getJSONObject(i);

//                        // Storing each json item in variable
                        String problema = c.getString("problema");

                        HashMap<String, String> map = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
                        map.put("problema", problema);
                       //
                        commentList.add(map);
                    }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
// dismiss the dialog after getting all products
            if (cont==0) {
               t1.setVisibility(View.VISIBLE);
            }
            for (int i=0; i<commentList.size(); i++){

                comentarios = (LinearLayout)findViewById(R.id.layout);
                LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.single_post, null);
                txt1 = (TextView ) myView.findViewById(R.id.nombre);
                txt2 = (TextView ) myView.findViewById(R.id.fecha);
                txt3 = (TextView ) myView.findViewById(R.id.mensaje);
                comentarios.addView(myView);
                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.GONE);
                txt3.setText(commentList.get(i).get("problema"));}


            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {



                }
            });
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                finish();
                return true;
            case R.id.op2:
                cerrar();
                return true;
            case R.id.op3:
                regresar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void regresar() {
        Intent j=new Intent(detalles.this, Opciones.class);
        j.putExtra("user", user);
        finish();
        startActivity(j);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    private void cerrar() {
        Intent j=new Intent(detalles.this, log2.class);

        finish();
        startActivity(j);
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


