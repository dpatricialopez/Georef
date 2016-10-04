package inova.georef;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyActivity extends ActionBarActivity {


    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;
    EditText ed;
    //View v2,v4,v3,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27;

    TextView txt1, rea, txt2, txt3, r1, r2, r3, r4, r5, r6,r7,r8,r9,r10,r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30;
   TabHost TbH;
   // LinearLayout  comentario,p2a, p3a, p4a, p5a, p6a,p7a,p8a,p9a,p10a,p11a, p12a, p13a, p14a, p15a, p16a, p17a, p18a, p19a, p20a, p21a, p22a, p23a, p24a, p25a, p26a, p27a;

    LinearLayout  peac, contacto, comentario,p3a,  p6a,p7a,p8a,p9a, p12a,  p15a, p25a, p26a, p27a;

    ImageButton comentar;
    Button contactar;
    private String conocimiento,img, tel,direccion,unidad,tamano,equipos,internet,mn,pqmn, name, nit;
   private String mc,aec,pqmc,correo,tiempo,barrio,comuna,registro,cambios,celular, empleados, curso, areas, edad, proyectos, participa, escolaridad,genero,cargo;

     ArrayList<HashMap<String, String>> commentList;

    View myView;


    ImageView ima;

    // url to get all products list
   // private static String url = "http://192.168.1.4/poll/movil2/perfil.php";
    //private static String url2 = "http://192.168.1.4/poll/movil2/comment.php";
    //private static String url = "http://pruebasgeorref.com/poll/movil2/perfil.php";
    //private static String url2 = "http://pruebasgeorref.com/poll/movil2/comment.php";
    private static String url = "http://innmagina.cloudapp.net/seminario/app/movil2/perfil.php";
    private static String url2 = "http://innmagina.cloudapp.net/seminario/app/movil2/comment.php";
    private static String cedula,comentarioin;



    // products JSONArray
    JSONArray comments = null;

    LinearLayout comentarios;
    Bitmap myBitmap;
    JSONObject c;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        // Hashmap para el ListView
        commentList = new ArrayList<HashMap<String, String>>();
         // new LoadAllProducts().execute();
        // Cargar los productos en el Background Thread

        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) {
            nit  = extras.getString("Nit");//usuario
            cedula  = extras.getString("cedula");//usuario\
            Log.v("nitd",nit);

        }

        comentario=(LinearLayout)findViewById(R.id.layout);
        contactar=(Button)findViewById(R.id.buttonc);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        ed=(EditText)findViewById(R.id.editText9);
       // v2=(View)findViewById(R.id.v2);
        //v4=(View)findViewById(R.id.v4);
        //v5=(View)findViewById(R.id.v5);
        //v6=(View)findViewById(R.id.v6);
        //v7=(View)findViewById(R.id.v7);
        //v9=(View)findViewById(R.id.v9);
       // v10=(View)findViewById(R.id.v10);
        //v11=(View)findViewById(R.id.v11);
        //v12=(View)findViewById(R.id.v12);
       // v13=(View)findViewById(R.id.v13);
        //v14=(View)findViewById(R.id.v14);
        //v15=(View)findViewById(R.id.v15);
       // v16=(View)findViewById(R.id.v16);
        //v17=(View)findViewById(R.id.v17);
        //v18=(View)findViewById(R.id.v18);
       // v19=(View)findViewById(R.id.v19);
        //v20=(View)findViewById(R.id.v20);
        //v21=(View)findViewById(R.id.v21);
       // v22=(View)findViewById(R.id.v22);
       // v23=(View)findViewById(R.id.v23);
        //v24=(View)findViewById(R.id.v24);
        //v8=(View)findViewById(R.id.v8);
       // v3=(View)findViewById(R.id.v3);
       // v25=(View)findViewById(R.id.v25);
        //v26=(View)findViewById(R.id.v26);
        //v27=(View)findViewById(R.id.v27);



        r1= (TextView) findViewById(R.id.r1);
       // r2= (TextView) findViewById(R.id.r2);
        r3= (TextView) findViewById(R.id.r3);
       // r4= (TextView) findViewById(R.id.r4);
        //r5= (TextView) findViewById(R.id.r5);
        r6= (TextView) findViewById(R.id.r6);
        r7= (TextView) findViewById(R.id.r7);
        r8= (TextView) findViewById(R.id.r8);
        r9= (TextView) findViewById(R.id.r9);
       // r10= (TextView) findViewById(R.id.r10);
       // r11= (TextView) findViewById(R.id.r11);
        r12= (TextView) findViewById(R.id.r12);
        //r13= (TextView) findViewById(R.id.r13);

        //r14= (TextView) findViewById(R.id.r14);
        r15= (TextView) findViewById(R.id.r15);
        //r16= (TextView) findViewById(R.id.r16);

        //r17= (TextView) findViewById(R.id.r17);
        //r18= (TextView) findViewById(R.id.r18);
        //r19= (TextView) findViewById(R.id.r19);
        //r20= (TextView) findViewById(R.id.r20);
        //r21= (TextView) findViewById(R.id.r21);
        //r22= (TextView) findViewById(R.id.r22);
        //r23= (TextView) findViewById(R.id.r23);
        //r24= (TextView) findViewById(R.id.r24);
        r25= (TextView) findViewById(R.id.r25);
        r26= (TextView) findViewById(R.id.r26);
        r27= (TextView) findViewById(R.id.r27);
        rea= (TextView) findViewById(R.id.rea);

        //p2a= (LinearLayout) findViewById(R.id.p2a);
        p3a= (LinearLayout) findViewById(R.id.p3a);
       // p4a= (LinearLayout) findViewById(R.id.p4a);
        //p5a= (LinearLayout) findViewById(R.id.p5a);
        p6a= (LinearLayout) findViewById(R.id.p6a);
        p7a= (LinearLayout) findViewById(R.id.p7a);
        p8a= (LinearLayout) findViewById(R.id.p8a);
        p9a= (LinearLayout) findViewById(R.id.p9a);
        //p10a= (LinearLayout) findViewById(R.id.p10a);
       // p11a= (LinearLayout) findViewById(R.id.p11a);
        p12a= (LinearLayout) findViewById(R.id.p12a);
       // p13a= (LinearLayout) findViewById(R.id.p13a);
       // p14a= (LinearLayout) findViewById(R.id.p14a);
        p15a= (LinearLayout) findViewById(R.id.p15a);
        //p16a= (LinearLayout) findViewById(R.id.p16a);
        //p17a= (LinearLayout) findViewById(R.id.p17a);
        //p18a= (LinearLayout) findViewById(R.id.p18a);
        //p19a= (LinearLayout) findViewById(R.id.p19a);
        //p20a= (LinearLayout) findViewById(R.id.p20a);
        //p21a= (LinearLayout) findViewById(R.id.p21a);
        //p22a= (LinearLayout) findViewById(R.id.p22a);
        //p23a= (LinearLayout) findViewById(R.id.p23a);
        //p24a= (LinearLayout) findViewById(R.id.p24a);
        p25a= (LinearLayout) findViewById(R.id.p25a);
        p26a= (LinearLayout) findViewById(R.id.p26a);
        p27a= (LinearLayout) findViewById(R.id.p27a);
        peac= (LinearLayout) findViewById(R.id.pea);
        ima=(ImageView)findViewById(R.id.imageView2);

        comentar=(ImageButton) findViewById(R.id.button4);
        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestana)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");

        tab1.setIndicator("", getResources().getDrawable(R.drawable.tab1));

        tab1.setContent(R.id.ejemplo1);

        tab2.setIndicator("", getResources().getDrawable(R.drawable.tab2));
        tab2.setContent(R.id.ejemplo2);

        tab3.setIndicator("", getResources().getDrawable(R.drawable.tab3));
        tab3.setContent(R.id.linearLayout9);


        TbH.addTab(tab1);
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setPadding(16, 16, 16, 16);
        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setBackgroundResource(R.drawable.fondo3);
        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setPadding(16, 16, 16, 16);
        TbH.getTabWidget().getChildAt(1).setPadding(16, 16, 16, 16);
        TbH.getTabWidget().getChildAt(0).setPadding(16, 16, 16, 16);
        TbH.getTabWidget().getChildAt(2).setPadding(16, 16, 16, 16);


        TbH.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {

                switch (tabId){

                        case "tab1":
                            TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setBackgroundResource(R.drawable.fondo3);
                            TbH.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.fondo31);
                            TbH.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.fondo31);
                            TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setPadding(16, 16, 16, 16);
                            TbH.getTabWidget().getChildAt(1).setPadding(16, 16, 16, 16);
                            TbH.getTabWidget().getChildAt(0).setPadding(16, 16, 16, 16);
                            TbH.getTabWidget().getChildAt(2).setPadding(16, 16, 16, 16);


                            break;
                    case "tab2":
                        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setBackgroundResource(R.drawable.fondo3);
                        TbH.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.fondo31);
                        TbH.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.fondo31);
                        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setPadding(16, 16, 16, 16);
                        TbH.getTabWidget().getChildAt(0).setPadding(16, 16, 16, 16);
                        TbH.getTabWidget().getChildAt(1).setPadding(16, 16, 16, 16);
                        TbH.getTabWidget().getChildAt(2).setPadding(16, 16, 16, 16);


                        break;
                    case "tab3":
                        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setBackgroundResource(R.drawable.fondo3);
                        TbH.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.fondo31);
                        TbH.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.fondo31);
                        TbH.getTabWidget().getChildAt(TbH.getCurrentTab()).setPadding(16, 16, 16, 16);
                        TbH.getTabWidget().getChildAt(0).setPadding(16, 16, 16, 16);
                        TbH.getTabWidget().getChildAt(1).setPadding(16, 16, 16, 16);
                        TbH.getTabWidget().getChildAt(2).setPadding(16, 16, 16, 16);


                        break;

                }


            }});

       // p2a.setVisibility(View.GONE);
        //p3a.setVisibility(View.GONE);
      //  p4a.setVisibility(View.GONE);
      //  p5a.setVisibility(View.GONE);
        // p6a.setVisibility(View.GONE);
        // p7a.setVisibility(View.GONE);
        // p8a.setVisibility(View.GONE);
        //  p9a.setVisibility(View.GONE);
        //  peac.setVisibility(View.GONE);
      //  p10a.setVisibility(View.GONE);
        //p11a.setVisibility(View.GONE);
        //  p12a.setVisibility(View.GONE);
        //p13a.setVisibility(View.GONE);
      //  p14a.setVisibility(View.GONE);
        //  p15a.setVisibility(View.GONE);
/*        p16a.setVisibility(View.GONE);
        p17a.setVisibility(View.GONE);
        p18a.setVisibility(View.GONE);
        p19a.setVisibility(View.GONE);
        p20a.setVisibility(View.GONE);
        p21a.setVisibility(View.GONE);
        p22a.setVisibility(View.GONE);
        p23a.setVisibility(View.GONE);
        p24a.setVisibility(View.GONE);*/
        //p25a.setVisibility(View.GONE);
        //p26a.setVisibility(View.GONE);
        //p27a.setVisibility(View.GONE);


        if (isConnecting(getApplicationContext())) {
            new LoadComments().execute();
        } else {
            alert();
        }


        comentar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (ed.getText().length() > 1) {
                    comentarioin = String.valueOf(ed.getText());
                    if (isConnecting(getApplicationContext())) {
                        new comentar().execute();
                    } else {
                        alert();
                    }

                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Ingrese un comentario.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
        contactar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                if (contacto.getVisibility()==View.VISIBLE) { contacto.setVisibility(view.GONE);}
                else { contacto.setVisibility(view.VISIBLE);}

            }
        });
    }



    private void imprimir() {

        if (img.length()>2 && img!="sin"&& img!=null && img!=""){
            if (isConnecting(getApplicationContext())) {
                new ImageLoadTask().execute();
            } else {
                alert();
            }


            Log.d("si tengo imagen", img);
        }
        else {
            pDialog.dismiss();}

        if (pqmc.length()>1 && pqmc!=null){
            r3.setText(pqmc);
            Log.d("si tengo pqmc",pqmc);
            p3a.setVisibility(View.VISIBLE);

        }

        if (correo.length()>5){
            r6.setText(correo);
            //v6.setVisibility(View.VISIBLE);
            p6a.setVisibility(View.VISIBLE);
        }
        if (direccion.length()>1){
            r7.setText(direccion);
            //v7.setVisibility(View.VISIBLE);
            p7a.setVisibility(View.VISIBLE);
        }
        if (barrio.length()>1){
            r8.setText(barrio);
            //v8.setVisibility(View.VISIBLE);
            p8a.setVisibility(View.VISIBLE);
        }
        if (comuna.length()>1){
            r9.setText(comuna);
            //v9.setVisibility(View.VISIBLE);
            p9a.setVisibility(View.VISIBLE);
        }

        if (unidad.length()>1){
            r12.setText(unidad);
           // v12.setVisibility(View.VISIBLE);
            p12a.setVisibility(View.VISIBLE);
        }

        if (tamano.length()>1){
            r15.setText(tamano);
           // v15.setVisibility(View.VISIBLE);
            p15a.setVisibility(View.VISIBLE);
        }

        if (mn.length()>1){
            r25.setText(mn);
           // v25.setVisibility(View.VISIBLE);
            Log.d("si tengo mn", mn);
            p25a.setVisibility(View.VISIBLE);
        }
      /*  if (areas.length()>1){
            r24.setText(areas);
            v24.setVisibility(View.VISIBLE);
            p24a.setVisibility(View.VISIBLE);
        }*/
        if (pqmn.length()>1 && pqmn!=null){
            r26.setText(pqmn);
            //v6.setVisibility(View.VISIBLE);
            Log.d("si tengo pqmn", pqmn);
            p26a.setVisibility(View.VISIBLE);
        }
        else
        { p26a.setVisibility(View.GONE);}


        if (mc.length()>1){
            r27.setText(mc);
            //v27.setVisibility(View.VISIBLE);
            p27a.setVisibility(View.VISIBLE);
        }

        if (aec.length()>4 && aec!=null){
            rea.setText(aec);
            //v27.setVisibility(View.VISIBLE);
            Log.d("si tengo rea", aec);
            peac.setVisibility(View.VISIBLE);
        }

        r1.setText(name);
        r1.setVisibility(View.VISIBLE);


    }



    class LoadComments extends AsyncTask<String, String, String> {

          @Override
        protected void onPreExecute() {
            super.onPreExecute();
              pDialog = new ProgressDialog(MyActivity.this);
              pDialog.setMessage("Cargando...");
              pDialog.setIndeterminate(false);
              pDialog.setCancelable(false);
              pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("nit",nit ));

            // getting JSON string from URL
            JSONObject json3 = jParser.makeHttpRequest(url, "POST", params);

            try {
                // Checking for SUCCESS TAG

                img= json3.getString("img");

                name= json3.getString("nombre");
                tel= json3.getString("telefono");
                direccion= json3.getString("direccion");
                unidad= json3.getString("unidad_productiva");
                tamano= json3.getString("tamano");
                mn= json3.getString("Mejoras_negocio");
                pqmn= json3.getString("pqmn");
                mc= json3.getString("Mejoras");
                pqmc= json3.getString("pqmn");
                correo= json3.getString("correo");

                barrio= json3.getString("barrio");
                comuna= json3.getString("comuna");

                celular= json3.getString("celular");

                aec= json3.getString("desc");



                    comments = json3.getJSONArray("comments");

                    // looping through All Products
                commentList.clear();
                    for (int i = 0; i < comments.length(); i++) {
                         c = comments.getJSONObject(i);

//                        // Storing each json item in variable
                        String nombre = c.getString("nombre");
                        String mensaje = c.getString("comentario");
                        String fecha = c.getString("fecha");

                        HashMap<String, String> map = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
                        map.put("nombre", nombre);
                        map.put("mensaje", mensaje);
                        map.put("fecha", fecha);
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

                imprimir();
// dismiss the dialog after getting all products
            comentarios = (LinearLayout)findViewById(R.id.layout);
           comentarios.removeView(myView);
            for (int i=0; i<commentList.size(); i++){


                LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.single_post, null);
                txt1 = (TextView ) myView.findViewById(R.id.nombre);
                txt2 = (TextView ) myView.findViewById(R.id.fecha);
                txt3 = (TextView ) myView.findViewById(R.id.mensaje);
                comentarios.addView(myView);
                 txt1.setText(commentList.get(i).get("nombre"));
                txt2.setText(commentList.get(i).get("fecha"));
                txt3.setText(commentList.get(i).get("mensaje"));}


            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {



                }
            });
        }
    }

    class comentar extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nit",nit ));
            params.add(new BasicNameValuePair("cedula", cedula));

            params.add(new BasicNameValuePair("comment",comentarioin ));

            // getting JSON string from URL
            JSONObject json3 = jParser.makeHttpRequest(url2, "POST", params);
            String s;
            try {
                // Checking for SUCCESS TAG

                s= json3.getString("success");
                Log.v("succes",s);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {


// dismiss the dialog after getting all products


            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Mensaje Publicado.", Toast.LENGTH_SHORT);
                    toast1.show();
                    ed.setText(null);
                    if (isConnecting(getApplicationContext())) {
                        new LoadComments().execute();
                    } else {
                        alert();
                    }


                }
            });
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

       // private String urle="http://pruebasgeorref.com/poll/movil2/images/"+img;
        private String urle= "http://innmagina.cloudapp.net/seminario/app/movil2/images/"+img;


        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(urle);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                 myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            ima.setImageBitmap(myBitmap);
            Log.v("visibilidad", String.valueOf(ima.getVisibility()));
            Drawable drawable = new BitmapDrawable(getResources(), myBitmap);
            pDialog.dismiss();

        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
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
        Intent j=new Intent(MyActivity.this, Opciones.class);
        j.putExtra("user", cedula);
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
        Intent j=new Intent(MyActivity.this, log2.class);

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


