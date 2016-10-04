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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Login de Prueba

public class registro extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, View.OnClickListener{

AlertDialog dialog;
    private String cedula, born, passw1,passw2, respuesta, nombre, apellido, correo, tel;
    Button button;
    Spinner sppregunta;
    String pregunta, preguntaver;
    int count;
    private DatePicker datepicker;
    int mes1;
    private String year, month,day;
    private ProgressDialog pDialog;
   EditText ednombre, edcedula, edcorreo, edpassw1, pass, edrespuesta, edapellido, edtel;
    private TextView edborn;
    private int success;
    JSONParser jParser = new JSONParser();
    String [] ask= new String[]{" ","Nombre de su primera mascota", "Ciudad de origen de su madre", "Nombre de su primer novio/novia","Nombre de su hijo/hija mayor","Año de nacimiento de su primer hijo/hija", "Color favorito", "Comida favorita"};
    ArrayAdapter<String> array_ask;
   //private static String url = "http://pruebasgeorref.com/poll/movil2/registro.php";
    private static String url = "http://innmagina.cloudapp.net/app/movil2/registro.php";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        ednombre= (EditText) findViewById(R.id.editText11);
        edcedula= (EditText) findViewById(R.id.editText12);
        edcorreo= (EditText) findViewById(R.id.correo);
        edpassw1= (EditText) findViewById(R.id.editText8);
        pass= (EditText) findViewById(R.id.editText4);
        edtel= (EditText) findViewById(R.id.editText15);
        edrespuesta= (EditText) findViewById(R.id.editText10);
        edborn= (TextView) findViewById(R.id.editText13);
        edapellido= (EditText) findViewById(R.id.editText14);
        sppregunta=(Spinner) findViewById(R.id.pregunta);

        button = (Button) findViewById(R.id.button);

     edborn.setOnClickListener(this);

        sppregunta.setOnItemSelectedListener(this);
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ednombre.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edcedula.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edcorreo.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edpassw1.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(pass.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edtel.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edrespuesta.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edborn.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edapellido.getWindowToken(), 0);

        array_ask= new ArrayAdapter<String>(registro.this, android.R.layout.select_dialog_item,ask);
        sppregunta.setAdapter(array_ask);
        sppregunta.setSelection(0);

        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                nombre=ednombre.getText().toString();
                apellido=edapellido.getText().toString();
                passw1=edpassw1.getText().toString();
                Log.d("pass1",passw1);

                passw2=pass.getText().toString();
                Log.d("pass2",passw2);
                correo=edcorreo.getText().toString();
                cedula=edcedula.getText().toString();
                pregunta= String.valueOf(sppregunta.getSelectedItemPosition());

                tel=edtel.getText().toString();
                born=edborn.getText().toString();
                respuesta=edrespuesta.getText().toString();

               verificar();
                if (count==11){
                    if (isConnecting(getApplicationContext())) {
                        new register().execute();
                    } else {
                        alert();
                    }


                }
                else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Verifique los campos señalados", Toast.LENGTH_SHORT);
                    toast1.show();
                    err_login();
                }
            }
        });

     }


    @Override
    public void onClick(View v) {
        if (v==edborn){
            LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
            View customview= inflater.inflate(R.layout.date,null);

            final DatePicker dp =(DatePicker)customview.findViewById(R.id.datePicker);
            if (year!=null){
            dp.updateDate(Integer.parseInt(year),mes1,Integer.parseInt(day));}
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setView(customview);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    year = Integer.toString(dp.getYear());
                     mes1=dp.getMonth();
                    int mes=dp.getMonth() + 1;
                    month = String.format("%02d",mes);
                    day = String.format("%02d", dp.getDayOfMonth());
                    edborn.setText((day) + "-" + month + "-" + year);


                    dialog.dismiss();

                }
            });
            builder.create().show();

           /* final Calendar calendar=Calendar.getInstance();
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH);
            day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog date = new DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    edborn.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);}
            },year,month,day);
            date.getDatePicker().setSpinnersShown(true);
            date.getDatePicker().setCalendarViewShown(false);

        date.show();*/
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void verificar() {
        count=0;
        verificaredit(ednombre);
        verificaredit(edapellido);
        verificarcelu(edtel);
        verificacorreo(edcorreo);
        verificaredit(edrespuesta);
       verificartext(edborn);
        verificaredit(edcedula);

        verificaredit(edcorreo);
       verificarclave(edpassw1, pass);
       Log.v( "passw", String.valueOf(pass.getText().toString().length()));
        verificarspinner(sppregunta);
        Log.d("count", String.valueOf(count));

    }

    private void verificartext(TextView et ) {
        // im.setVisibility(View.VISIBLE);
        if (et.getText().toString().trim().length() == 10){
            count=count+1;
            et.setBackgroundResource(R.drawable.fondo);
        } else { et.setBackgroundResource(R.drawable.fondo4);
        }
        return;
    }

    private void verificaredit(EditText et ) {
        // im.setVisibility(View.VISIBLE);
        if (et.getText().toString().trim().length() == 10){
            count=count+1;
            et.setBackgroundResource(R.drawable.fondo);
        } else { et.setBackgroundResource(R.drawable.fondo4);
        }
        return;
    }

    private void verificarclave(EditText et, EditText et2 ) {


        Toast toastclave;

        if ((et.getText().toString().trim().length() > 5 || et.getText().toString().trim().length() < 11) &&(et2.getText().toString().trim().length() > 5 || et2.getText().toString().trim().length() < 11)) {
            boolean yes = (et.getText()==et2.getText());
            Log.d("claves", String.valueOf(yes));
            Log.d("claves1", String.valueOf(et.getText().toString()));
            Log.d("claves2", String.valueOf(et2.getText().toString()));
            if (yes){
                et.setBackgroundResource(R.drawable.fondo);
                et2.setBackgroundResource(R.drawable.fondo);
                count=count+1;}
            else
            { toastclave = Toast.makeText(getApplicationContext(), "Las contraseñas ingresadas no coinciden", Toast.LENGTH_SHORT);
                toastclave.show();
                err_login();
                et2.setBackgroundResource(R.drawable.fondo4);
                et.setBackgroundResource(R.drawable.fondo4);
               }
        }
        else {    toastclave = Toast.makeText(getApplicationContext(), "La contraseña ingresada debe tener de 6-10 carácteres", Toast.LENGTH_SHORT);
            toastclave.show();
            err_login();

            et2.setBackgroundResource(R.drawable.fondo4);
            et.setBackgroundResource(R.drawable.fondo4);
        }
        return;
    }

    private void verificarcelu(EditText et ) {

        if (et.getText().toString().trim().length() == 7 || et.getText().toString().trim().length() == 10 ){
            et.setBackgroundResource(R.drawable.fondo);
            count=count+1;
        }
        else { et.setBackgroundResource(R.drawable.fondo4);
        }
        return;
    }


    private void verificacorreo(EditText et ) {
        String text=et.getText().toString();

        if (text.trim().length() != 0){

            CharSequence email=text;
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                count=count+1;
                et.setBackgroundResource(R.drawable.fondo);}
        }
        else { et.setBackgroundResource(R.drawable.fondo4);}
        return;
    }
    //Verifica que se haya seleccionado un opción en los spinners
    private void verificarspinner( Spinner spi ) {

        if (sppregunta.getSelectedItem().toString() != " "){

            count=count+1;
            spi.setBackgroundResource(R.drawable.fondo);
        }
        else {
            spi.setBackgroundResource(R.drawable.fondo4);}
        return;
    }




    class register extends AsyncTask< String, String, String > {

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(inova.georef.registro.this);
            pDialog.setMessage("Registrando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nombre", nombre));
            params.add(new BasicNameValuePair("apellido", apellido));
            params.add(new BasicNameValuePair("email", correo));
            params.add(new BasicNameValuePair("passw1", passw2));
            params.add(new BasicNameValuePair("pregunta", pregunta));
            params.add(new BasicNameValuePair("cedula", cedula));
            params.add(new BasicNameValuePair("tel",tel ));
            params.add(new BasicNameValuePair("born", born));
            params.add(new BasicNameValuePair("respuesta", respuesta));

            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

            try {
                success= json.getInt("success");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String result) {
            Toast toast1;
            pDialog.dismiss();//ocultamos progess dialog.
             if (success==1){
                 toast1= Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT);
                 toast1.show();
                 Intent i=new Intent(registro.this, log2.class);
                 i.putExtra("user",cedula);
                 startActivity(i);
                 finish();

            }else if (success==2){
                 toast1= Toast.makeText(getApplicationContext(), "Ya existe un usuario registrado con el número de cédula ingresado", Toast.LENGTH_SHORT);
                 toast1.show();
                err_login();

            }
             else if (success==3){
                 toast1 = Toast.makeText(getApplicationContext(), "Ya existe un usuario registrado con el correo ingresado", Toast.LENGTH_SHORT);
                 toast1.show();
                 err_login();

             }

        }

    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Alternativa 1
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  { //inhabiñita función atrás
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            return true;
        }

        return super.onKeyDown(keyCode, event);
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

