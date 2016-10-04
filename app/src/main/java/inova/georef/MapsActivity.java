package inova.georef;

import android.app.AlertDialog;
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
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mMap;
    Button guardar,cancelar;
    EditText adress;
    public static Double  latitud, longitud;
    public static String longitude,latitude;
    public static  String user;

    public static String direccion;
    public static String seleccion;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_maps);//Interfaz XML  relacionada

        setUpMapIfNeeded();

        // Se definene Widgets
        adress= (EditText) findViewById(R.id.editText1);
        guardar= (Button) findViewById(R.id.actionButton);
        cancelar= (Button) findViewById(R.id.button2);

        //Datos recibidos de la actividad Anterior
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        direccion = extras.getString("adress");
        latitude=extras.getString("latitud");
        longitude=extras.getString("longitud");
        seleccion=extras.getString("seleccion");


        adress.setText(direccion); //Escribi la dirección en el Edittext para que el susuario pueda visualizarla y corregirla

        guardar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, Encuesta1.class);
                String direccion2 = adress.getText().toString(); //Captura la nueva dirección verificada para transmitirla
                i.putExtra("adress", direccion2);
                i.putExtra("user", user);
                i.putExtra("latitud", latitude);
                i.putExtra("longitud", longitude);



                startActivity(i);
                    finish();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent j=new Intent(MapsActivity.this, Opciones.class);
                j.putExtra("user", user);

                startActivity(j);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
           // Location location = locationManager.getLastKnownLocation(NETWORK_STATS_SERVICE);
                if (location != null) {

                   // LatLng  target = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    //   longitud=Double.parseDouble(latitude);
                    //   latitud = Double.parseDouble(longitude);
                    LatLng  target = new LatLng(location.getLatitude(), location.getLongitude());
                   longitud=location.getLongitude();
                    latitud = location.getLatitude();
                    CameraPosition position = this.mMap.getCameraPosition();

                    CameraPosition.Builder builder = new CameraPosition.Builder();
                    builder.zoom(18);
                    builder.target(target);
                    int  dr;

                    BitmapDescriptor markerDescriptor;
                    BitmapDrawable bmp;
                   bmp=(BitmapDrawable)getResources().getDrawable(R.drawable.lgrn_blank);

                    Bitmap b=bmp.getBitmap();
                    Bitmap sbmp=Bitmap.createScaledBitmap(b,60,60,false);
                    markerDescriptor = BitmapDescriptorFactory.fromBitmap(sbmp);


                    this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).icon(markerDescriptor));

                }
                else
                {
                   mMap.setMyLocationEnabled(true);
                   LatLng currentCoordinates = new LatLng(6.2,-75.5);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 10));
                     Location location2 = locationManager.getLastKnownLocation(NETWORK_STATS_SERVICE);
                    if (location != null) {

                        // LatLng  target = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        //   longitud=Double.parseDouble(latitude);
                        //   latitud = Double.parseDouble(longitude);
                        LatLng  target = new LatLng(location2.getLatitude(), location2.getLongitude());
                        longitud=location2.getLongitude();
                        latitud = location2.getLatitude();
                        CameraPosition position = this.mMap.getCameraPosition();

                        CameraPosition.Builder builder = new CameraPosition.Builder();
                        builder.zoom(18);
                        builder.target(target);

                        this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)));

                    }

                    boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    // getting network status
                    boolean isNWEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}

            }




    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void regresar() {
        Intent j=new Intent(MapsActivity.this, Opciones.class);
        j.putExtra("user", user);
        finish();
        startActivity(j);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op2:
                cerrar();
                return true;
            case R.id.op1:
                regresar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu4, menu);
        return true;
    }
    private void cerrar() {
        Intent j=new Intent(MapsActivity.this, log2.class);

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
        alertDialogBuilder.setTitle("No hay conexión a internet");
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