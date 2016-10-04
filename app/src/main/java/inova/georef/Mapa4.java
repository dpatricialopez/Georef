package inova.georef;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Mapa4 extends ActionBarActivity implements AdapterView.OnItemSelectedListener, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    JSONParser jParser = new JSONParser();

    private MyItem clickedClusterItem;
    private ClusterManager<MyItem> mClusterManager;

     private ProgressDialog pDialog;
    JSONArray coordenadas  = null;

    Spinner     spunidad, scomunidad ,snegocio, spcomuna;

    ArrayAdapter<String> array_spunidad,  array_pn, array_spc, array_comuna;

    String [] s_unidad= new String[]{"","Almacén de prendas de vestir","café Internet","Cafetería/ Panadería"," Carnicería","Comercio al detal", "Comidas Rápidas", "Confecciones","Construcción","Ebanistería", "Expendio de licores",  "Estética",   "Ferretería", "Financiero","Hotelería","Industria Manufacturera", "Juegos de Azar",   "Legumbrería",  "Marroquinería","Papeleria","Reparación de electrodomésticos","Restaurantes", "Salud","Telecomunicaciones","Tienda de mascotas", "Transporte", "Turismo",    "Ventas de alimentos procesados",  "Zapatería"};
    String [] s_pc= new String[]{"",   "Seguridad ",   "Movilidad",   "Acceso",   "Salud ",   "Educación  ",   "Cobertura de servicios públicos",   "Diversión"};
    String [] s_pn= new String[]{"",   "Distribución",    "Contabilidad",   "Capacitación ",   "Ventas",   "Mejoras físicas",   "Imagen",   "Diversificar productos/servicios"};
    String [] s_comuna= new String[]{" ","Comuna 1-Popular","Comuna 2-Santa Cruz","Comuna 3-Manrique", "Comuna 4-Aranjuez", "Comuna 5-Castilla", "Comuna 6-Doce de octubre", "Comuna 7-Robledo", "Comuna 8-Villa hermosa", "Comuna 9-Buenos Aires", "Comuna 10--La Candelaria", "Comuna 11-Laureles-Estadio", "Comuna 12-La América", "Comuna 13-San Javier", "Comuna 14-Poblado",  "Comuna 15-Guayabal", "Comuna 16-Belén"};

    ArrayList<String> latitudaa= new ArrayList<String>();
    ArrayList<String> longitudaa= new ArrayList<String>();
    ArrayList<String> nombrea= new ArrayList<String>();
    ArrayList<String> nita= new ArrayList<String>();
    ArrayList<String> iconoa= new ArrayList<String>();
    ArrayList<String> unidada= new ArrayList<String>();

    LinearLayout ly;
    private static String url = "http://innmagina.cloudapp.net/seminario/app/movil2/mapa.php"; //url donde se aloja el Webserver
    //private static String url = "http://pruebasgeorref.com/poll/movil2/mapa.php"; //url donde se aloja el Webserver
   //private static String url = "http://192.168.1.2/poll/movil2/mapa.php"; //url donde se aloja el Webserver
   LatLngBounds bounds;
    ImageButton filtrar;
    Button filtro;

    EditText ed;
    String palabra;
    String unid;
    String pc;
    String pn;
    String comuna;

    private GoogleMap mMap;
    int counter;
    Double latitud= Double.valueOf(0);
    Double longitud=Double.valueOf(0);

    public static  String cedula;

    public static String direccion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.encuesta_maps);//Interfaz XML  relacionada

        filtrar=(ImageButton)findViewById(R.id.close);
        filtro =(Button)findViewById(R.id.filtrar);
        ly=(LinearLayout)findViewById(R.id.despliega);
        spunidad=(Spinner)findViewById(R.id.spinner4);
        scomunidad=(Spinner)findViewById(R.id.spinner6);
        snegocio=(Spinner)findViewById(R.id.spinner5);
        spcomuna=(Spinner)findViewById(R.id.spinner7);
        ed=(EditText)findViewById(R.id.editText7);

        spunidad.setOnItemSelectedListener(this);
        scomunidad.setOnItemSelectedListener(this);
        snegocio.setOnItemSelectedListener(this);
        spcomuna.setOnItemSelectedListener(this);

        array_spunidad= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_unidad);
        array_pn= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_pn);
        array_spc= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_pc);
        array_comuna= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_comuna);

        spunidad.setAdapter(array_spunidad);
        scomunidad.setAdapter(array_spc);
        snegocio.setAdapter(array_pn);
        spcomuna.setAdapter(array_comuna);

        spunidad.setSelection(0);
        scomunidad.setSelection(0);
        snegocio.setSelection(0);
        spcomuna.setSelection(0);


        Bundle extras = getIntent().getExtras();
        cedula = extras.getString("user");
        comuna=extras.getString("comuna");
        palabra = extras.getString("palabra");
        pc = extras.getString("pc");
        pn = extras.getString("pn");
        unid = extras.getString("unidad");
        ly.setVisibility(View.GONE);

        if (palabra!=null){ed.setText(palabra);}
        if (pc!=null){int position= array_spc.getPosition(pc);
            scomunidad.setSelection(position);}
        if (comuna!=null){int position2= array_comuna.getPosition(comuna);spcomuna.setSelection(position2);}
        if (unid!=null){int position3= array_spunidad.getPosition(unid);spunidad.setSelection(position3);}
        if (pn!=null){int position4= array_pn.getPosition(pn);snegocio.setSelection(position4);}
        if (isConnecting(getApplicationContext())) {
            new Cargar().execute();
        } else {
            alert();
        }


        filtro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                palabra = String.valueOf(ed.getText());
                unid = spunidad.getSelectedItem().toString();
                pc = scomunidad.getSelectedItem().toString();
                pn = snegocio.getSelectedItem().toString();
                comuna = spcomuna.getSelectedItem().toString();

                Intent j = new Intent(Mapa4.this, Mapa4.class);
                j.putExtra("user", cedula);
                j.putExtra("pc", pc);
                j.putExtra("comuna", comuna);
                j.putExtra("pn", pn);
                j.putExtra("unidad", unid);
                j.putExtra("palabra", palabra);
                finish();
                startActivity(j);


            }
        });
        filtrar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                if (ly.getVisibility()==View.VISIBLE) { ly.setVisibility(view.GONE);}
                else { ly.setVisibility(view.VISIBLE);}

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
       // setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                setUpMap();
            }
        }
    }


    private void setUpClusterer() {
               // Position the map.


        mClusterManager = new ClusterManager<>(this, mMap);

        mMap.setOnCameraChangeListener(mClusterManager);

        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mMap.setOnInfoWindowClickListener(mClusterManager); //added

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setRenderer(new MyClusterRenderer(this, mMap, mClusterManager));

        mClusterManager.setOnClusterItemInfoWindowClickListener(this); //added



        mClusterManager
                .setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
                    @Override
                    public boolean onClusterItemClick(MyItem item) {
                        clickedClusterItem = item;
                        return false;
                    }
                });


        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                new MyCustomAdapterForItems());
        addItems();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,0);
        if (counter>1){
            mMap.moveCamera(cu);
            if(mMap.getCameraPosition().zoom<10){  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.22, -75.6), 11));}
        }

         else if (latitud!=0){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 10));
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.22, -75.6), 11));

        }


    }


    private void addItems() {

        // Set some lat/lng coordinates to start with.
       /* BDAyuda bda=new BDAyuda(this);
        bda.abrir();
        try {
            arr =bda.consultar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bda.cerrar();
        latitudab=arr[0];
        longitudab=arr[1];
        nombreb=arr[2];
        nitb=arr[3];
        iconob=arr[4];
        Log.v("size", String.valueOf(longitudab.size()));
        Log.v("latitudb", String.valueOf(latitudab));*/
        // Add ten cluster items in close proximity, for purposes of this example.
        counter=0;
        LatLngBounds.Builder builer=new LatLngBounds.Builder();

        for (int i = 0; i < longitudaa.size(); i++) {
            counter++;
            builer.include(new LatLng(Double.parseDouble(latitudaa.get(i)), Double.parseDouble(longitudaa.get(i))));
            MyItem offsetItem = new MyItem(Double.parseDouble(latitudaa.get(i)), Double.parseDouble(longitudaa.get(i)), nita.get(i)+":"+nombrea.get(i),unidada.get(i),iconoa.get(i));
            mClusterManager.addItem(offsetItem);
        }
            if (counter>0){
                bounds = builer.build();
            }
    }


    private void setUpMap() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.getUiSettings().setZoomControlsEnabled(true);
            // Create a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // Get the name of the best provider
            String provider;
            provider = locationManager.getBestProvider(criteria, true);

            Location location = locationManager.getLastKnownLocation(provider);

            mMap.setMyLocationEnabled(true);

           /* for (int i = 0; i < latituda.size(); i++) {

                latitu = Double.valueOf(latituda.get(i));
                longitu = Double.valueOf(longituda.get(i));
                String name=nombre.get(i);
                String nit=Nit.get(i);
                int  dr;
               LatLng encuest = new LatLng(latitu, longitu);
                dr = this.getResources().getIdentifier(icono.get(i), "drawable", this.getPackageName());

               if (dr<10){
                     dr=this.getResources().getIdentifier("red_diamond","drawable",this.getPackageName());
                }

                int height=40;
                int widht=40;
                BitmapDrawable bmp=(BitmapDrawable)getResources().getDrawable(dr);
                Bitmap b=bmp.getBitmap();
                Bitmap sbmp=Bitmap.createScaledBitmap(b,widht,height,false);
                mMap.addMarker(new MarkerOptions().position(encuest).title(name).snippet("Nit: "+nit).icon(BitmapDescriptorFactory.fromBitmap(sbmp)));
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                String id= marker.getId();
                                int y= Integer.parseInt(id.substring(1));
                                String Nitcc =Nit.get(y);

                                Intent a=new Intent(Mapa3.this, MyActivity.class);
                                a.putExtra("Nit", Nitcc);
                                a.putExtra("cedula",cedula);
                                startActivity(a);
                            }
                        });
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(encuest, 14));

            }*/
            if (location != null) {

                LatLng  target = new LatLng(location.getLatitude(), location.getLongitude());
                longitud=location.getLongitude();
                latitud = location.getLatitude();

               // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(target, 14));

            }

        }
        setUpClusterer();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {
        Intent i = new Intent(Mapa4.this, MyActivity.class);
        String nit=myItem.getTitle();;
        String unidad7=clickedClusterItem.getTitle();
        StringTokenizer tokens = new StringTokenizer(unidad7, ":");

        String first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();
        Log.d("htx",second);


        i.putExtra("Nit",  first);
        Log.v("nita", nit);
        i.putExtra("cedula", cedula);
        startActivity(i);
    }


    class Cargar extends AsyncTask<String, String, String> {//Extrae el nombre, el apellido, el numero de encuestas realizadas y sus coordenadas por el encuestador que inicia sesion.
        int cont;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Mapa4.this);
            pDialog.setMessage("Cargando, espere un momento por favor");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.show();

        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("palabra", palabra));
            params.add(new BasicNameValuePair("unidad", unid));
            params.add(new BasicNameValuePair("pc", pc));

            params.add(new BasicNameValuePair("comuna", comuna));
            params.add(new BasicNameValuePair("pn", pn));
            Log.v("params", String.valueOf(params));
            JSONObject json3 = jParser.makeHttpRequest(url, "POST", params);

            try {

                coordenadas = json3.getJSONArray("puntos");
                Log.v("puntos", String.valueOf(coordenadas));
                cont=0;
                for (int i = 0; i < coordenadas.length(); i++) {
                    cont++;
                    JSONObject c = coordenadas.getJSONObject(i);
                    latitudaa.add(c.getString("latitud"));
                    longitudaa.add(c.getString("longitud"));
                    nombrea.add(c.getString("nombre"));
                    nita.add(c.getString("nit"));
                    unidada.add(c.getString("unidad"));

                    iconoa.add(c.getString("icono"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            runOnUiThread(new Runnable() {
                public void run() {
                    if (cont==0) {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "No hay resultados para esta busqueda", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                    setUpMapIfNeeded();
                    pDialog.dismiss();


                }
            });

        }

    }
    public class MyCustomAdapterForItems implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyCustomAdapterForItems() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.info_windows, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView
                    .findViewById(R.id.txtTitle));
            TextView unidad = ((TextView) myContentsView
                    .findViewById(R.id.unidad));


            String unidad5=clickedClusterItem.getTitle();
            Log.d("unidad5",unidad5);
            String[] separated = unidad5.split(":");
            String unidad7=clickedClusterItem.getTitle();
            StringTokenizer tokens = new StringTokenizer(unidad7, ":");

            String first = tokens.nextToken();// this will contain "Fruit"
            String second = tokens.nextToken();
            Log.d("htx",second);
            String texto=separated[0];

            tvTitle.setText(second);
            unidad.setText(clickedClusterItem.getSnippet());


            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {


               public MyClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);


        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item,
                                                   MarkerOptions markerOptions) {

            int  dr;
            String icono=item.getIcono();
            int height=60;
            int widht=60;
            BitmapDescriptor markerDescriptor;
            BitmapDrawable bmp;
            dr = getResources().getIdentifier(icono, "drawable", getPackageName());

            if (dr==0){
                bmp=(BitmapDrawable)getResources().getDrawable(R.drawable.lgrn_blank);

            }
            else {

                bmp=(BitmapDrawable)getResources().getDrawable(dr);
            }


            Bitmap b=bmp.getBitmap();
            Bitmap sbmp=Bitmap.createScaledBitmap(b,widht,height,false);
            markerDescriptor = BitmapDescriptorFactory.fromBitmap(sbmp);

            markerOptions.icon(markerDescriptor);
        }

        @Override
        protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
        }



    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                if (ly.getVisibility()==View.VISIBLE) { ly.setVisibility(View.GONE);}
                else { ly.setVisibility(View.VISIBLE);}
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
        Intent j=new Intent(Mapa4.this, Opciones.class);
        j.putExtra("user", cedula);
        finish();
        startActivity(j);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu6, menu);
        return true;
    }
    private void cerrar() {
        Intent j=new Intent(Mapa4.this, log2.class);

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

