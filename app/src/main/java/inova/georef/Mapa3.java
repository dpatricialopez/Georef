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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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

public class Mapa3 extends ActionBarActivity implements AdapterView.OnItemSelectedListener, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;
    JSONArray coordenadas  = null;
    JSONArray ranking  = null;
    String centrox, centroy;

    int counter;
    ArrayList<String> cantidada= new ArrayList<String>();
    ArrayList<String> latitudaa= new ArrayList<String>();
    ArrayList<String> longitudaa= new ArrayList<String>();
    ArrayList<String> nombrea= new ArrayList<String>();
    ArrayList<String> nita= new ArrayList<String>();
    ArrayList<String> iconoa= new ArrayList<String>();
    ArrayList<String> unidada= new ArrayList<String>();
    ArrayList<String> problemaa= new ArrayList<String>();
    String total;
    LatLngBounds bounds;
    private MyItem clickedClusterItem;
    private ClusterManager<MyItem> mClusterManager;

    Spinner     spbarrio, spcomuna ;
    LinearLayout ly, resultados;

    ArrayAdapter<String> array_spbarrio,  array_comuna;
    //String [] s_comuna= new String[]{" ","Comuna 1-Popular","Comuna 2-Santa Cruz","Comuna 3-Manrique", "Comuna 4-Aranjuez", "Comuna 5-Castilla", "Comuna 6-Doce de octubre", "Comuna 7-Robledo", "Comuna 8-Villa hermosa", "Comuna 9-Buenos Aires", "Comuna 10--La Candelaria", "Comuna 11-Laureles-Estadio", "Comuna 12-La América", "Comuna 13-San Javier", "Comuna 14-Poblado",  "Comuna 15-Guayabal", "Comuna 16-Belén"};

    String [] s_comuna= new String[]{" ","Comuna 1-Popular","Comuna 4-Aranjuez", "Comuna 12-La América", "Comuna 16-Belén"};
    String [] comuna1= new String[]{" ","Aldea Pablo VI","Carpinelo","El Compromiso","Granizal","La Avanzada","La Esperanza Nº 2","Moscú Nº 2" ,"Popular","Santo Domingo Savio Nº 1","Santo Domingo Savio Nº 2","San Pablo","Villa Guadalupe" };
    String [] comuna2= new String[]{" ","Andalucía","El Playón de Los Comuneros",  "La Francia", "La Frontera",  "La Isla","La Rosa", "Moscú Nº 1","Pablo VI", "Santa Cruz","Villa del Socorro", "Villa Niza"};
    String [] comuna3= new String[]{" ","Campo Valdes Nº 2","El Pomar", "El Raizal","La Cruz", "La Honda","La Salle", "Las Granjas", "Manrique Central Nº 2", "Manrique Oriental","Oriente",  "Maria Cano – Carambolas", "San José La Cima Nº 1","San José La Cima Nº 2", "Santa Inés", "Versalles Nº 1", "Versalles Nº 2"};
    String [] comuna4= new String[]{" ","Aranjuez",  "Brasilia","Berlín",  "Bermejal - Los Álamos",  "Las Esmeraldas", "La Piñuela","Palermo", "Campo Valdes Nº 1", "Manrique Central Nº 1","Miranda","Moravia","San Isidro", "San Pedro", "Sevilla"};
    String [] comuna5= new String[]{" ","Alfonso López","Belalcazar","Boyacá","Castilla","Caribe", "El Progreso", "Francisco Antonio Zea","Florencia","Girardot",  "Héctor Abad Gómez",  "La Paralela", "Las Brisas",       "Tejelo", "Toscana","Tricentenario"};
    String [] comuna6= new String[]{" ","Doce de Octubre Nº 1", "Doce de Octubre Nº 2", "El Triunfo", "Kennedy","La Esperanza","Mirador del Doce",  "Pedregal","Picacho", "Picachito",  "Progreso Nº 2", "San Martín de Porres", "Santander"};
    String [] comuna7= new String[]{" ","Altamira","Aures Nº 1", "Aures Nº 2", "Barrio Facultad de Minas","Bello Horizonte", "Bosques de San Pablo", "Cerro El Volador", "Córdoba",  "Cucaracho","El Diamante", "Fuente Clara",  "La Pilarica",   "López de Mesa", "Monteclaro", "Nueva Villa de La Iguaná","Olaya Herrera", "Pajarito","Palenque", "Robledo",  "San Germán",  "Santa Margarita","Villa Flora"};
    String [] comuna8= new String[]{" ","Batallón Girardot", "El Pinal","Enciso", "La Ladera", "La Libertad","Las Estancias", "La Sierra (Santa Lucía - Las Estancias)", "Llanaditas", "La Mansión", "Los Mangos", "Trece de Noviembre",  "San Antonio","San Miguel",   "Sucre",   "Villa Hermosa","Villa Lilliam","Villatina", "Villa Turbay"};
    String [] comuna9= new String[]{" ","Alejandro Echavarría","Asomadera Nº 1", "Asomadera Nº 2", "Asomadera Nº 3","Barrios de Jesús","Bombona Nº 2","Buenos Aires", "Cataluña",   "El Salvador","Gerona", "Juan Pablo II",  "La Milagrosa","Loreto", "Los Cerros El Vergel",  "Miraflores",   "Ocho de Marzo"};
    String [] comuna10= new String[]{" ","Barrio Colón", "Bombona Nº 1", "Boston","Calle Nueva","Corazón de Jesús","El Chagualo", "Estación Villa", "Guayaquil","Jesús Nazareno","La Candelaria","Las Palmas", "Los Ángeles", "Perpetuo Socorro", "Prado",   "San Benito",   "San Diego",   "Villa Nueva"};
    String [] comuna11= new String[]{" ","Bolivariana","Carlos E. Restrepo","Cuarta Brigada","El Velódromo","Estadio","Florida Nueva","La Castellana","Laureles","Las Acacias","Los Colores","Los Conquistadores","Lorena","Naranjal","San Joaquín","Suramericana"};
    String [] comuna12= new String[]{" ","Barrio Cristóbal","Calasanz","Calasanz Parte Alta","Campo Alegre","El Danubio","Ferrini","La América","La Floresta","Los Pinos","Santa Lucía","Santa Mónica","Santa Teresita","Simón Bolívar"};
    String [] comuna13= new String[]{" ","Antonio Nariño","Belencito","Betania","Blanquizal","Eduardo Santos","El Corazón","El Pesebre","El Salado","El Socorro","Juan XIII - La Quiebra","La Divisa","La Pradera","Las Independencias","Los Alcázares","Metropolitano","Nuevos Conquistadores","Peñitas","San Javier Nº 1","San Javier Nº 2","Santa Rosa de Lima","Veinte de Julio"};
    String [] comuna14= new String[]{" ","Alejandría","Altos del Poblado","Astorga","Barrio Colombia","Castropol","El Castillo","El Diamante Nº 2","El Poblado","El Tesoro","La Aguacatala","La Florida","Lalinde","Las Lomas Nº 1","Las Lomas Nº 2","Los Balsos Nº 1","Los Balsos Nº 2","Los Naranjos","Manila","Patio Bonito","San Lucas","Santa María de Los Ángeles","Simesa","Villa Carlota"};
    String [] comuna15= new String[]{" ","Campo Amor, Noel","Cristo Rey","Guayabal","La Colina","Parque Juan Pablo II","San Pablo","Santa Fe","Shellmar","Tenche","Trinidad"};
    String [] comuna16= new String[]{" ","Altavista","Belén","Cerro Nutibara","El Rincón","Diego Echevarria","El Nogal - Los Almendros","Fátima","Granada","La Gloria","La Loma de Los Bernal","La Palma","Las Mercedes","Las Playas","Las Violetas","Los Alpes","Miravalle","Nueva Villa de Aburrá","Rodeo Alto-La Hondonada","Rosales","San Bernardo","La Mota","Tenche","Zafra"};

    String [] sbarrio1= new String[]{" ","Elija una comuna"};
    String [] sbarrio= new String[]{" ","Elija una comuna"};

   //private static String url = "http://192.168.1.2/poll/movil2/ranking.php"; //url donde se aloja el Webserver
    //private static String url = "http://pruebasgeorref.com/poll/movil2/ranking.php"; //url donde se aloja el Webserver
    private static String url = "http://innmagina.cloudapp.net/seminario/app/movil2/ranking.php"; //url donde se aloja el Webserver

      ImageButton filtrar, atras;
    TextView r1,r2,r3,r4,r5,r6,r7,v1,v2,v3,v4,v5,v6,v7;
    Button filtro;
    ProgressBar p1,p2,p3,p4,p5,p6,p7;
    String barrio;


    String comuna;
    private GoogleMap mMap;


    public static Double  latitud, longitud;

    public static  String cedula;

    public static String direccion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problemas_maps);//Interfaz XML  relacionada


        atras=(ImageButton)findViewById(R.id.close2);
        filtrar=(ImageButton)findViewById(R.id.close);
        filtro =(Button)findViewById(R.id.filtrar);
        ly=(LinearLayout)findViewById(R.id.despliega1);
        resultados=(LinearLayout)findViewById(R.id.despliega2);

        p1=(ProgressBar)findViewById(R.id.progressBar1);
        r1=(TextView)findViewById(R.id.textView52);
        p2=(ProgressBar)findViewById(R.id.progressBar2);
        r2=(TextView)findViewById(R.id.textView53);
        p3=(ProgressBar)findViewById(R.id.progressBar3);
        r3=(TextView)findViewById(R.id.textView54);
        p4=(ProgressBar)findViewById(R.id.progressBar4);
        r4=(TextView)findViewById(R.id.textView55);
        p5=(ProgressBar)findViewById(R.id.progressBar5);
        r5=(TextView)findViewById(R.id.textView56);
        p6=(ProgressBar)findViewById(R.id.progressBar6);
        r6=(TextView)findViewById(R.id.textView57);
        p7=(ProgressBar)findViewById(R.id.progressBar7);
        r7=(TextView)findViewById(R.id.textView58);

        v1=(TextView)findViewById(R.id.textView59);
        v2=(TextView)findViewById(R.id.textView60);
        v3=(TextView)findViewById(R.id.textView61);
        v4=(TextView)findViewById(R.id.textView62);
        v5=(TextView)findViewById(R.id.textView63);
        v6=(TextView)findViewById(R.id.textView64);
        v7=(TextView)findViewById(R.id.textView65);


        spcomuna=(Spinner)findViewById(R.id.spinner4);
        spbarrio=(Spinner)findViewById(R.id.spinner6);


        spbarrio.setOnItemSelectedListener(this);
        spcomuna.setOnItemSelectedListener(this);


        array_spbarrio= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sbarrio);
        array_comuna= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_comuna);

        spbarrio.setAdapter(array_spbarrio);
        spcomuna.setAdapter(array_comuna);

        spbarrio.setSelection(0);
        spcomuna.setSelection(0);
        atras.setVisibility(View.VISIBLE);
        ly.setVisibility(View.GONE);
        resultados.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        barrio = extras.getString("barrio");
        comuna=extras.getString("comuna");
        cedula = extras.getString("user");
        total="1";

        if (comuna!=null){int position2= array_comuna.getPosition(comuna);spcomuna.setSelection(position2);}
        if (barrio!=null && barrio!="" && barrio!=" " && barrio.length()>3){int position3= array_spbarrio.getPosition(barrio);spbarrio.setSelection(position3);}
        if (isConnecting(getApplicationContext())) {
            new Cargar().execute();
        } else {
            alert();
        }

        spcomuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int pos = spcomuna.getSelectedItemPosition();
                switch (pos) {
                    case 0:
                        sbarrio = sbarrio1;
                        break;
                    case 1:
                        sbarrio = comuna1;
                        break;
                    case 2:
                        sbarrio = comuna4;
                        break;
                    case 3:
                        sbarrio = comuna12;
                        break;
                    case 4:
                        sbarrio = comuna16;
                        break;


                }

                array_spbarrio = new ArrayAdapter<String>(Mapa3.this, android.R.layout.simple_spinner_dropdown_item, sbarrio);
                spbarrio.setAdapter(array_spbarrio);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });




        filtro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                barrio = spbarrio.getSelectedItem().toString();
                comuna = spcomuna.getSelectedItem().toString();

                Intent j = new Intent(Mapa3.this, Mapa3.class);

                j.putExtra("barrio", barrio);
                j.putExtra("comuna", comuna);
                j.putExtra("user",cedula);
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
        atras.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                if (resultados.getVisibility()==View.VISIBLE) { resultados.setVisibility(view.GONE);}
                else { resultados.setVisibility(view.VISIBLE);}

            }
        });
        r1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
               detalles(r1.getText().toString());
            }
        });
        r2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                detalles(r2.getText().toString());
            }
        });
        r3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                detalles(r3.getText().toString());
            }
        });
        r4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                detalles(r4.getText().toString());
            }
        });
        r5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                detalles(r5.getText().toString());
            }
        });
        r6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                detalles(r6.getText().toString());
            }
        });
        r7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                detalles(r7.getText().toString());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
       // setUpMapIfNeeded();
    }

   void detalles(String pc) {
       Intent a=new Intent(Mapa3.this, detalles.class);
       a.putExtra("pc",pc);
       a.putExtra("barrio",barrio);
       a.putExtra("comuna",comuna);
       a.putExtra("user",cedula);
       startActivity(a);
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

        mClusterManager = new ClusterManager<>(this, mMap);

        mMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mMap.setOnInfoWindowClickListener(mClusterManager); //added

        mMap.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterManager);
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
        LatLng centro;
        float dist;

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,30);
        if (counter>1){
            mMap.moveCamera(cu);
            centro=bounds.getCenter();
            Log.v("centrobound",String.valueOf(centro));
            double a1= bounds.southwest.latitude;
            double a2= bounds.southwest.longitude;
            Location l1=new Location("center");
            l1.setLatitude(a1);
            l1.setLongitude(a2);
            Location l2=new Location("center");
            l2.setLatitude(centro.latitude);
            l2.setLongitude(centro.longitude);
            dist=l1.distanceTo(l2);


            if(mMap.getCameraPosition().zoom<10){  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.22, -75.6), 11));}

        }
        else if (counter==1){
            mMap.moveCamera(cu);
            centro=new LatLng(Double.parseDouble(latitudaa.get(0)),Double.parseDouble(latitudaa.get(0)));
            Log.v("centrobound",String.valueOf(centro));
            dist=350;
           if(mMap.getCameraPosition().zoom<10){  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 11));}

        }
        else if (centrox!=null && centroy!=null && centrox!="null" && centroy!="null"){
            centro= new LatLng(Double.parseDouble(centrox), Double.parseDouble(centroy));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 14));
            dist=350;
             }

    else if (latitud!=0){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 10));
            dist=0;
            centro=new LatLng(0,0);
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.22, -75.6), 11));

        dist=0;
            centro=new LatLng(0,0);
        }

        if (barrio!=null && barrio!="" && barrio!=" " && barrio.length()>3 && (counter>1 || centrox!="NULL")){
            CircleOptions circleOptions = new CircleOptions();
            circleOptions = new CircleOptions().center(centro).radius(dist).fillColor(0x4400ff00).strokeColor(0x4400ff00);
            Circle circle = mMap.addCircle(circleOptions);}


}


    private void addItems() {

        // Set some lat/lng coordinates to start with.
    /*    BDAyuda bda=new BDAyuda(this);
        bda.abrir();
        try {
            arr =bda.consultar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bda.cerrar();
        latituda=arr[0];
        longitudab=arr[1];
        nombreb=arr[2];
        nitb=arr[3];
        iconob=arr[4];*/

        // Add ten cluster items in close proximity, for purposes of this example.
        LatLngBounds.Builder builer=new LatLngBounds.Builder();
        counter=0;
        Log.v("longitud ntes de",String.valueOf(longitudaa));
        if(longitudaa.size()>0){
        for (int i = 0; i < longitudaa.size(); i++) {
            builer.include(new LatLng(Double.parseDouble(latitudaa.get(i)), Double.parseDouble(longitudaa.get(i))));
            counter++;
            MyItem offsetItem = new MyItem(Double.parseDouble(latitudaa.get(i)), Double.parseDouble(longitudaa.get(i)),  nita.get(i)+":"+nombrea.get(i),unidada.get(i),iconoa.get(i));
            mClusterManager.addItem(offsetItem);
        }}
        //Log.v("logb",longitudaa.get(longitudaa.size() - 1));
        if (counter>0){
        bounds = builer.build();
    }}


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
        Intent i = new Intent(Mapa3.this, MyActivity.class);
        String nit=myItem.getTitle();
        String[] separated = nit.split(":");
        String unidad5=clickedClusterItem.getTitle();
        StringTokenizer tokens = new StringTokenizer(unidad5, ":");


        String first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();
        Log.d("htx",second);
        i.putExtra("Nit",  first);

        i.putExtra("cedula", cedula);
        startActivity(i);
    }


    class Cargar extends AsyncTask<String, String, String> {//Extrae el nombre, el apellido, el numero de encuestas realizadas y sus coordenadas por el encuestador que inicia sesion.
        int cont;
        int cont2;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Mapa3.this);
            pDialog.setMessage("Cargando, espere un momento por favor");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.show();

        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("barrio", barrio));

            params.add(new BasicNameValuePair("comuna", comuna));

            JSONObject json3 = jParser.makeHttpRequest(url, "POST", params);
            Log.v("params", String.valueOf(params));
            try {
                total= json3.getString("total");
                Log.v("total",total);
                centrox = json3.getString("centrox");
                centroy= json3.getString("centroy");
                coordenadas = json3.getJSONArray("puntos");
                Log.v("puntos",String.valueOf(coordenadas) );
                cont=0;
                ranking = json3.getJSONArray("ranking");
                Log.v("ranking",String.valueOf(ranking) );

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


                for (int i = 0; i < ranking.length(); i++) {
                    JSONObject d = ranking.getJSONObject(i);
                    problemaa.add(d.getString("problema"));
                    cantidada.add(d.getString("cant"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            runOnUiThread(new Runnable() {
                public void run() {
                    Log.v("cont", String.valueOf(cont));
                    graficar();
                    if (cont==0) {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "No hay resultados para esta busqueda", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                    pDialog.dismiss();
                    setUpMapIfNeeded();

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
            StringTokenizer tokens = new StringTokenizer(unidad5, ":");
            String[] separated = unidad5.split(":");
            String texto=separated[0];
            String first = tokens.nextToken();// this will contain "Fruit"
            String second = tokens.nextToken();
            Log.d("htx",second);
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

    private void graficar() {

       /* for(int k=0; k<problemaa.size();k++){
            if (problemaa.get(k).contains("Cobertura servicios públicos")){
                problemaa.set(k, "Cobertura servicios ");
            }
        }*/

        if(problemaa.size()>0) {
            r1.setText(problemaa.get(0));
            Integer cantidad = Integer.parseInt(cantidada.get(0)) * 100 / (Integer.parseInt(total));
            v1.setText(String.valueOf(cantidad) + "%");
            p1.setProgress(cantidad);
            r2.setText(problemaa.get(1));
            cantidad = Integer.parseInt(cantidada.get(1)) * 100 / (Integer.parseInt(total));
            v2.setText(String.valueOf(cantidad) + "%");
            p2.setProgress(cantidad);
            r3.setText(problemaa.get(2));
            cantidad = Integer.parseInt(cantidada.get(2)) * 100 / (Integer.parseInt(total));
            v3.setText(String.valueOf(cantidad) + "%");
            p3.setProgress(cantidad);
            r4.setText(problemaa.get(3));
            cantidad = Integer.parseInt(cantidada.get(3)) * 100 / (Integer.parseInt(total));
            v4.setText(String.valueOf(cantidad) + "%");
            p4.setProgress(cantidad);
            r5.setText(problemaa.get(4));
            cantidad = Integer.parseInt(cantidada.get(4)) * 100 / (Integer.parseInt(total));
            v5.setText(String.valueOf(cantidad) + "%");
            p5.setProgress(cantidad);
            r6.setText(problemaa.get(5));
            cantidad = Integer.parseInt(cantidada.get(5)) * 100 / (Integer.parseInt(total));
            v6.setText(String.valueOf(cantidad) + "%");
            p6.setProgress(cantidad);
            r7.setText(problemaa.get(6));
            cantidad = Integer.parseInt(cantidada.get(6)) * 100 / (Integer.parseInt(total));
            v7.setText(String.valueOf(cantidad) + "%");
            p7.setProgress(cantidad);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                if (ly.getVisibility()==View.VISIBLE) { ly.setVisibility(View.GONE);}
                else { ly.setVisibility(View.VISIBLE);}
                return true;
            case R.id.op2:

                if (resultados.getVisibility()==View.VISIBLE) { resultados.setVisibility(View.GONE);}
                else { resultados.setVisibility(View.VISIBLE);}
                return true;
            case R.id.op4:
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
        Intent j=new Intent(Mapa3.this, Opciones.class);
        j.putExtra("user", cedula);
        finish();
        startActivity(j);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu5, menu);
        return true;
    }
    private void cerrar() {
        Intent j=new Intent(Mapa3.this, log2.class);

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

