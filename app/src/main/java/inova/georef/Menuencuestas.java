package inova.georef;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Menuencuestas extends AppCompatActivity {

    //Definicion de variables
    AddressResultReceiver mResultReceiver;
    public static double latitud = 0;
    public static double longitud = 0;

    private String sele;
    Button encuesta1;
    public static String user;

    public static String direccion = null;
    static String mensaje, mensaje2;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    private Timer timer2;

    private int count = 0, count2 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);//Seleccion de Archivo XML relacionado yque conforma la interfaz grafica.
        configgps();
        timer2 = new Timer();
        // Se definen Widgets
        encuesta1 = (Button) findViewById(R.id.button11);

        mResultReceiver = new AddressResultReceiver(null);

        Bundle extras = getIntent().getExtras(); //Informacion recibida desde la actividad anterior.
        user = extras.getString("user");


        mensaje = getString(R.string.espera);//almacenado en archivo strings.xml
        mensaje2 = getString(R.string.falla);//almacenado en archivo strings.xml
        encuesta1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sele = "1";
                //se inicializa temporizador y funcion periodica que cada 5 segundas verifica si ya fueron calculadas las coordenadas y la direccion
                timecoor period = new timecoor();
                timer2.scheduleAtFixedRate(period, 500, 5000);
            }
        });


    }


    private void configgps() { // Se configura e inicializa el proveedor de georreferenciacion
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new Mylocation();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 50, mLocationListener);// actualizaciones cada minuto o  cada 50 metros usando GPS como proveedor
    }

    private void configgps2() { // Se configura e inicializa el proveedor de georreferenciacion
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new Mylocation();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 50, mLocationListener);// actualizaciones cada minuto o  cada 50 metros usando GPS como proveedor
    }

    public class Mylocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Menuencuestas.latitud = location.getLatitude();
            Menuencuestas.longitud = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            //si se tiene como proveedor el GPS solicita la activacion de este.
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    private void Geocode() {//Se llama al GeocodeAdressIntentService como servicio para a partir de las coordenadas calcular la direccion

        Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_LATITUDE_DATA_EXTRA, latitud);
        intent.putExtra(Constants.LOCATION_LONGITUDE_DATA_EXTRA, longitud);
        startService(intent);
    }

    private void transmision(String pos) { //Funcion que se ejecuta cuando ya se han calculado las coordenadas y la direccion y que permite pasar a al siguiente actividad.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            mLocationManager.removeUpdates(mLocationListener);
            return;
        }


        Intent i = new Intent(Menuencuestas.this, MapsActivity.class);
        i.putExtra("adress", direccion);
        i.putExtra("user", user);
        i.putExtra("latitud", String.valueOf(latitud));
        i.putExtra("longitud", String.valueOf(longitud));
        startActivity(i);
        finish();

    }

    class AddressResultReceiver extends ResultReceiver { //funcion que extrae la direccion obtenida del servicio Geocode
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constants.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        direccion = resultData.getString(Constants.RESULT_DATA_KEY);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        direccion = resultData.getString(Constants.RESULT_DATA_KEY);
                    }
                });
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//configura el comportamiento del boton "atras"
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent z = new Intent(Menuencuestas.this, Opciones.class);
            z.putExtra("user", user);
            startActivity(z);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void regresar(View view) { //Cierra Sesion
        Intent a=new Intent(Menuencuestas.this, Opciones.class);
        a.putExtra("user", user);
        startActivity(a);

    }
    class timecoor extends TimerTask {//funcion periodica

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (longitud == 0.0 && latitud == 0.0 || direccion == null) {
                        count = 0;
                        Toast toast1 = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
                        toast1.show();
                        Geocode();
                        if (count2 <= 6) {
                            count2++;
                        } else {
                            configgps2();
                            Toast toast2 = Toast.makeText(getApplicationContext(), "Problemas con el GPS. Cambiando proveedor...", Toast.LENGTH_SHORT);
                            toast2.show();
                            count2 = 0;
                        }
                        Geocode();

                    } else {
                        if (direccion == "Not Found") {
                            if (count <= 3) {
                                Toast toast1 = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
                                toast1.show();
                                Geocode();
                                count++;
                            } else {
                                Toast toast2 = Toast.makeText(getApplicationContext(), mensaje2, Toast.LENGTH_SHORT);
                                toast2.show();
                                transmision(sele);


                                 try {
                                     timer2.cancel();
                                     timer2 = null;

                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                            }

                        }
                            else {
                             transmision(sele);
                             //cuando cambio de actividad se desactiva el timer.
                         try {
                            timer2.cancel();
                            timer2 = null;

                         } catch (Exception e) {
                            e.printStackTrace();
                         }
                        }

                    }
                }
            });
        }
    }

    private void regresar() {
        Intent j=new Intent(Menuencuestas.this, Opciones.class);
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
        Intent j=new Intent(Menuencuestas.this, log2.class);

        finish();
        startActivity(j);
    }
}

