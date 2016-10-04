package inova.georef;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encuesta1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView txt;
    // Variables que representan los Widgets
    RadioButton rd12_1, rd12_2,rdcurso_1, rdcurso_2, rdinternet_1, rdinternet_2,rdgenero_1, rdgenero_2,rdcargo_1,rdcargo_2, rdpart1, rdpart2, rdregi1, rdregi2;
    EditText      eddesc, cce, pqmne,pqmce,ednombre, edrazon, edtel, ednit, adress, edcelu,edcorreo, edbarrio,  edotro1, edotro2;
    CheckBox char7, charea1, charea2, charea3, charea4, charea5, charea6, chproye1, chproye2, chproye3, chproye4, chproye5, chproye6,ch101,ch102, ch103, ch104,ch151, ch152, ch153, ch154, ch155, ch156, ch161, ch162, ch163, ch164, ch165, ch166,  ch167,ch157;
    Spinner    spbarrio,edcomuna,sptamano, spescolaridad, spunidad, sedad,stime,snum,  scambios;
    ImageView  imdesc, cci, pqmni, pqmci, imimage,imgenero,imtamano,imrazon, imnombre,imnit,imtel,imadress, imbarrio, imcargo, imunidad, imescolar,imequipos,iminternet,imconocimiento,  imcurso,immnegocio, immcomunidad,  im28, im27, im26, im23, imemp, imregis, imtime, imedad, imcomuna, imcorreo, imcelu;
ImageButton fotosi;
    //Adaptadores y arreglos para configurar los spinners
    ArrayAdapter<String>  array_barrio,array_spcomuna, array_spunidad,  array_spescolaridad, array_sptamano, array_sedad, array_time, array_num, array_area, array_proye, array_cambios;

    String [] s_unidad= new String[]{" ","Almacén de prendas de vestir","café Internet","Cafetería/ Panadería"," Carnicería","Comercio al detal", "Comidas Rápidas", "Confecciones","Construcción","Ebanistería", "Expendio de licores",  "Estética",   "Ferretería", "Financiero","Hotelería","Industria Manufacturera", "Juegos de Azar",   "Legumbrería",  "Marroquinería","Papeleria","Reparación de electrodomésticos","Restaurantes", "Salud","Telecomunicaciones","Tienda de mascotas", "Transporte", "Turismo",    "Ventas de alimentos procesados",  "Zapatería"};
    String [] s_comuna= new String[]{" ","Comuna 1-Popular","Comuna 4-Aranjuez", "Comuna 12-La América", "Comuna 16-Belén"};
    //String [] s_comuna= new String[]{" ","Comuna 1-Popular","Comuna 2-Santa Cruz","Comuna 3-Manrique", "Comuna 4-Aranjuez", "Comuna 5-Castilla", "Comuna 6-Doce de octubre", "Comuna 7-Robledo", "Comuna 8-Villa hermosa", "Comuna 9-Buenos Aires", "Comuna 10--La Candelaria", "Comuna 11-Laureles-Estadio", "Comuna 12-La América", "Comuna 13-San Javier", "Comuna 14-Poblado",  "Comuna 15-Guayabal", "Comuna 16-Belén"};
    String [] s_escolaridad= new String[]{" ","Primaria","Secundaria","Universitaria", "Posgrado", "Ninguno"};
    String [] s_tamano= new String[]{" ", "Micro", "Pequeña", "Mediana", "Grande"};
    String [] s_edad= new String[]{" ", "16 a 18 años","19 a 25 años", "26 a 40 años", "41 años en adelante "};
    String [] s_time= new String[]{" ", "Menos de 1 año", "Entre 1 año y 5 años", "Entre 5 años y 10 años", "Más de 10 años"};
    String [] s_area= new String[]{" ", "Tecnología","Comunicaciones", "Logística", "Financiera", "Mercadeo", "Otros"};
    String [] s_proye= new String[]{" ", "Educativos","Comunales","Seguridad", "Financiera","Ninguno","otros"};
    String [] s_num= new String[]{" ", "1 Empleado", "2 Empleados", "3 Empleados", "Entre 3 y 10 Empleados","Entre 10 y 50 empleados","Más de 50 empleados"};
    String [] s_cambios= new String[]{" ","Muy poco","Poco","Regular","Mucho"};
    String [] comuna1= new String[]{"","Aldea Pablo VI","Carpinelo","El Compromiso","Granizal","La Avanzada","La Esperanza Nº 2","Moscú Nº 2" ,"Popular","Santo Domingo Savio Nº 1","Santo Domingo Savio Nº 2","San Pablo","Villa Guadalupe" };
    String [] comuna2= new String[]{"","Andalucía","El Playón de Los Comuneros",  "La Francia", "La Frontera",  "La Isla","La Rosa", "Moscú Nº 1","Pablo VI", "Santa Cruz","Villa del Socorro", "Villa Niza"};
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

    String [] sbarrio1= new String[]{" ","Elija un barrio"};
    String [] sbarrio= new String[]{" ","Elija una comuna"};


    //Otras variables
    String latitudeEdit,longitudeEdit;
    private Uri output;

    private File file;
    private String desc, barrio, cambios, edad, numeroempl, direccion, nombre, cargo,unidad, genero, escolaridad,tamano ,equipos, internet, nit, razon,telefono, conocimientos,  curso_anterior, mejoras_negocio, mejoras_comunidad, comuna, celular, registro, areas, proyectos,participa,  correo, tiempo, cc, pqmn, pqmc;
    public static String cedula;
    public static String  imagen_name=null;
    private String foto=null;

    static int count=0;
    boolean flag=false;

    JSONParser jParser = new JSONParser();
  //  String url = "http://pruebasgeorref.com/poll/movil2/guardarencuesta.php";//Url del server
  //  String url_image = "http://pruebasgeorref.com/poll/movil2/upload.php";  //Url para guardar imagen
    String url = "http://innmagina.cloudapp.net/seminario/app/movil2/guardarencuesta.php";//Url del server
   String url_image = "http://innmagina.cloudapp.net/seminario/app/movil2/upload.php";  //Url para guardar imagen


    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    protected void onPause() {

        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta2);

        Bundle extras = getIntent().getExtras();//Obtiene los datos recibidos de la actividad anterior
        if (extras != null) {
            direccion = extras.getString("adress");
            cedula = extras.getString("user");
            latitudeEdit=extras.getString("latitud");
            longitudeEdit=extras.getString("longitud");


        }

        // Para conformar el nombre de la imagen se concatena la cedula del usuario y el numero ordinal correspondiente a la encuesta, para ello se incrementa en una unidad.
        imagen_name=cedula+latitudeEdit.substring(4);


        //Se definen los Widgets



        adress=(EditText)findViewById(R.id.Edittext04);
        ednombre=(EditText) findViewById(R.id.editText2);
        ednit=(EditText) findViewById(R.id.editText5);
        edtel=(EditText) findViewById(R.id.editText03);
        edrazon=(EditText) findViewById(R.id.razon);
        edcomuna=(Spinner) findViewById(R.id.spcomuna);
        spbarrio=(Spinner) findViewById(R.id.spbarrio);
        edcorreo=(EditText) findViewById(R.id.editText6);

        edcelu=(EditText) findViewById(R.id.editText);
        eddesc=(EditText)findViewById(R.id.desc);


        pqmne=(EditText) findViewById(R.id.pqmne);
        pqmce=(EditText) findViewById(R.id.pqmce);
        cce=(EditText) findViewById(R.id.cce);

        edotro1=(EditText) findViewById(R.id.otro23);
        edotro2=(EditText) findViewById(R.id.otro27);


        imnit=(ImageView)findViewById(R.id.imageView9);
        imtel=(ImageView)findViewById(R.id.ImageView3);
        imnombre=(ImageView)findViewById(R.id.imageView11);
        imrazon=(ImageView)findViewById(R.id.imrazon);

        imgenero=(ImageView)findViewById(R.id.ImageView07);
        imtamano=(ImageView)findViewById(R.id.imageView6);
        txt=(TextView)findViewById(R.id.textView);
        imadress=(ImageView)findViewById(R.id.ImageView04);
        imcargo=(ImageView)findViewById(R.id.imageView8);
        imunidad=(ImageView)findViewById(R.id.imageView4);
        imescolar=(ImageView)findViewById(R.id.ImageView08);
        imequipos=(ImageView)findViewById(R.id.imeq);
        iminternet=(ImageView)findViewById(R.id.ImageView11);
        imconocimiento=(ImageView)findViewById(R.id.ImageView12);

        imcurso=(ImageView)findViewById(R.id.ImageView14);
        immnegocio=(ImageView)findViewById(R.id.ImageView15);
        immcomunidad=(ImageView)findViewById(R.id.ImageView16);
        imequipos=(ImageView)findViewById(R.id.imeq);
        imcomuna=(ImageView)findViewById(R.id.imcomun);
        imcorreo=(ImageView)findViewById(R.id.imageView10);
        imregis=(ImageView)findViewById(R.id.imregis);
        im28=(ImageView)findViewById(R.id.im28);
        im27=(ImageView)findViewById(R.id.im27);
        im26=(ImageView)findViewById(R.id.im26);
        im23=(ImageView)findViewById(R.id.im23);
        imemp=(ImageView)findViewById(R.id.imemp);
        imedad=(ImageView)findViewById(R.id.imedad);
        imbarrio=(ImageView)findViewById(R.id.imbarrio);
        imcelu=(ImageView)findViewById(R.id.imcelu);
        imtime=(ImageView)findViewById(R.id.imageView7);
        imdesc= (ImageView)findViewById(R.id.imdesc);
        cci=(ImageView)findViewById(R.id.cci);
        pqmni=(ImageView)findViewById(R.id.pqmni);
        pqmci=(ImageView)findViewById(R.id.pqmci);

       fotosi=(ImageButton)findViewById(R.id.imageButton3);

        spunidad=(Spinner)findViewById(R.id.spinner);
        spescolaridad=(Spinner)findViewById(R.id.Spinner08);
        sptamano=(Spinner)findViewById(R.id.spinner2);
        sedad=(Spinner)findViewById(R.id.spinneredad);
        scambios=(Spinner)findViewById(R.id.spinnerc);

        snum=(Spinner)findViewById(R.id.spinnernum);
        stime=(Spinner)findViewById(R.id.spinner3);

        rd12_1=(RadioButton)findViewById(R.id.rd12_1);
        rd12_2=(RadioButton)findViewById(R.id.rd_12_2);

        rdinternet_1=(RadioButton)findViewById(R.id.rdinternet_1);
        rdinternet_2=(RadioButton)findViewById(R.id.rdinternet_2);
        rdcurso_1=(RadioButton)findViewById(R.id.rdcurso_1);
        rdcurso_2=(RadioButton)findViewById(R.id.rdcurso_2);
        rdcargo_1=(RadioButton)findViewById(R.id.radioButton3);
        rdcargo_2=(RadioButton)findViewById(R.id.radioButton4);

        rdgenero_1=(RadioButton)findViewById(R.id.rdgenero_1);
        rdgenero_2=(RadioButton)findViewById(R.id.rdgenero_2);

        rdregi1=(RadioButton)findViewById(R.id.rdregi1);
        rdregi2=(RadioButton)findViewById(R.id.rdregi2);

        rdpart1=(RadioButton)findViewById(R.id.rdpart1);
        rdpart2=(RadioButton)findViewById(R.id.rdpart2);

        charea1=(CheckBox)findViewById(R.id.checkBox7);
        charea2=(CheckBox)findViewById(R.id.checkBox8);
        charea3=(CheckBox)findViewById(R.id.checkBox9);
        charea4=(CheckBox)findViewById(R.id.checkBox10);
        charea5=(CheckBox)findViewById(R.id.checkBox11);
        charea6=(CheckBox)findViewById(R.id.checkBox12);
        char7=(CheckBox)findViewById(R.id.char7);

        chproye1=(CheckBox)findViewById(R.id.checkBox13);
        chproye2=(CheckBox)findViewById(R.id.checkBox14);
        chproye3=(CheckBox)findViewById(R.id.checkBox15);
        chproye4=(CheckBox)findViewById(R.id.checkBox16);
        chproye5=(CheckBox)findViewById(R.id.checkBox17);
        chproye6=(CheckBox)findViewById(R.id.checkBox18);

        ch101=(CheckBox)findViewById(R.id.ch101);
        ch102=(CheckBox)findViewById(R.id.ch102);
        ch103=(CheckBox)findViewById(R.id.ch103);
        ch104=(CheckBox)findViewById(R.id.ch104);

        ch161=(CheckBox)findViewById(R.id.ch161);
        ch162=(CheckBox)findViewById(R.id.ch162);
        ch163=(CheckBox)findViewById(R.id.ch163);
        ch164=(CheckBox)findViewById(R.id.ch164);
        ch165=(CheckBox)findViewById(R.id.ch165);
        ch166=(CheckBox)findViewById(R.id.ch166);
        ch167=(CheckBox)findViewById(R.id.ch167);

        ch151=(CheckBox)findViewById(R.id.ch151);
        ch152=(CheckBox)findViewById(R.id.ch152);
        ch153=(CheckBox)findViewById(R.id.ch153);
        ch154=(CheckBox)findViewById(R.id.ch154);
        ch155=(CheckBox)findViewById(R.id.ch155);
        ch156=(CheckBox)findViewById(R.id.ch156);
        ch157=(CheckBox)findViewById(R.id.ch57);

        // Se asignan el listener a los spinners

        spunidad.setOnItemSelectedListener(this);
        spescolaridad.setOnItemSelectedListener(this);
        sedad.setOnItemSelectedListener(this);
        scambios.setOnItemSelectedListener(this);

        stime.setOnItemSelectedListener(this);
        snum.setOnItemSelectedListener(this);
        sptamano.setOnItemSelectedListener(this);
        spbarrio.setOnItemSelectedListener(this);



        charea6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (charea6.isChecked()) {
                    edotro1.setVisibility(View.VISIBLE);

                } else {
                    edotro1.setVisibility(View.GONE);
                }
            }
        });

            chproye6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (chproye6.isChecked()) {
                        edotro2.setVisibility(View.VISIBLE);

                    } else {
                        edotro2.setVisibility(View.GONE);

                    }
                }

            });

        edcomuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int pos = edcomuna.getSelectedItemPosition();
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
                array_barrio= new ArrayAdapter<String>(Encuesta1.this, android.R.layout.simple_spinner_dropdown_item,sbarrio);
                spbarrio.setAdapter(array_barrio);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        array_barrio= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sbarrio);
        array_spunidad= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_unidad);
        array_spcomuna= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_comuna);
        array_spescolaridad= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_escolaridad);
        array_sptamano= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_tamano);
        array_sedad= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_edad);
        array_num= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_num);
        array_area= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_area);
        array_time= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_time);
        array_proye= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_proye);
        array_cambios= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,s_cambios);


        spunidad.setAdapter(array_spunidad);
        edcomuna.setAdapter(array_spcomuna);
        spescolaridad.setAdapter(array_spescolaridad);
        sptamano.setAdapter(array_sptamano);
        sedad.setAdapter(array_sedad);
        stime.setAdapter(array_time);
        snum.setAdapter(array_num);
        scambios.setAdapter(array_cambios);
        spbarrio.setAdapter(array_barrio);

        //se inicializan los spinners
        spunidad.setSelection(0);
        spescolaridad.setSelection(0);
        sptamano.setSelection(0);
        sedad.setSelection(0);
        stime.setSelection(0);
        snum.setSelection(0);
        scambios.setSelection(0);
        edcomuna.setSelection(0);
        spbarrio.setSelection(0);

        //Se imprime la direccion en la pregunta 4 y se cambia la imagen.
        adress.setText(direccion);
        ednombre.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edotro2.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edotro1.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(ednit.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edcelu.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edtel.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edrazon.getWindowToken(), 0);


        inputMethodManager.hideSoftInputFromWindow(edcorreo.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(ednombre.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(eddesc.getWindowToken(), 0);

        inputMethodManager.hideSoftInputFromWindow(adress.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(cce.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(pqmce.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(pqmne.getWindowToken(), 0);
        imadress.setImageResource(R.drawable.check);
    }



    //funcion que se ejecita cuando se presiona el botón "capturar imagen"
    public void imagenClicked(View view) {
        flag=true;
        imagen_name=imagen_name+".jpg";
        foto = Environment.getExternalStorageDirectory() +"/"
                +imagen_name;
        file=new File(foto);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        output = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, 1);
    }

    // Recibe la imagen capturada y configura su orientacion
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver cr=this.getContentResolver();
        Bitmap bit = null;
        try {
            bit = MediaStore.Images.Media.getBitmap(cr, output);

            int rotate = 0;
            try {
                ExifInterface exif = new ExifInterface(
                        file.getAbsolutePath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            bit = Bitmap.createBitmap(bit , 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
            fotosi.setImageBitmap(bit);
            txt.setVisibility(
                    View.GONE
            );
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void uploadFoto(String imag){
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(url_image);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody foto = new FileBody(file, "image/jpeg");
        mpEntity.addPart("fotoUp", foto);
        httppost.setEntity(mpEntity);
        try {
            httpclient.execute(httppost);
            httpclient.getConnectionManager().shutdown();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void serverUpdate(){
        if (file.exists())new ServerUpdate().execute();
    }

    class ServerUpdate extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... arg0) {
            uploadFoto(foto);
             return null;
        }
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

    }

    //Funcion que verifica que todos los campos estén diligenciado y captura los datos
    private void verificar() {
        nombre = ednombre.getText().toString(); Log.v("name", nombre);

        nit = ednit.getText().toString();
        telefono = edtel.getText().toString();
        razon = edrazon.getText().toString();
        direccion = adress.getText().toString();
        celular = edcelu.getText().toString();
        correo = edcorreo.getText().toString();
        desc=eddesc.getText().toString();
        barrio=spbarrio.getSelectedItem().toString();
        comuna=edcomuna.getSelectedItem().toString();
        pqmc=pqmce.getText().toString();
        pqmn=pqmne.getText().toString();
        cc=cce.getText().toString();

        unidad = spunidad.getSelectedItem().toString();
        escolaridad=spescolaridad.getSelectedItem().toString();
        tamano=sptamano.getSelectedItem().toString();
        edad=sedad.getSelectedItem().toString();
        tiempo=stime.getSelectedItem().toString();
        numeroempl=snum.getSelectedItem().toString();
        cambios=scambios.getSelectedItem().toString();

        conocimientos = verificarradio(rd12_1, rd12_2, imconocimiento);
        participa=verificarradio(rdpart1, rdpart2, im26);
        internet=verificarradio(rdinternet_1, rdinternet_2, iminternet);

        curso_anterior = verificarradio(rdcurso_1, rdcurso_2, imcurso);
        genero = verificarradio(rdgenero_1, rdgenero_2, imgenero);
        cargo = verificarradio(rdcargo_1, rdcargo_2, imcargo);
        registro = verificarradio(rdregi1, rdregi2, imregis);

        equipos = verificarchec4(ch101, ch102, ch103, ch104, imequipos);
        mejoras_negocio = verificarchec7(ch151, ch152, ch153, ch154, ch155, ch156, ch157, immnegocio);
        mejoras_comunidad = verificarchec7(ch161, ch162, ch163, ch164, ch165, ch166, ch167, immcomunidad);
        areas = verificarchec7e(charea1, charea2, charea3, charea4, charea5, char7, charea6, im23, edotro1);
        proyectos = verificarchec6(chproye1, chproye2, chproye3, chproye4, chproye5, chproye6, im27, edotro2);

        verificaredit(ednombre, imnombre);
        verificaredit(edrazon, imrazon);

        verificaredit(cce, cci);
        verificaredit(ednit, imnit);
        verificaredit(eddesc, imdesc);
        verificarnumero(edtel, imtel);
        verificaredit(adress, imadress);
        verificarcelu(edcelu, imcelu);
        verificacorreo(edcorreo, imcorreo);
        verificarspinner(barrio, imbarrio, spbarrio);
        verificarspinner(unidad, imunidad, spunidad);
        verificarspinner(escolaridad, imescolar, spescolaridad);
        verificarspinner(tamano, imtamano, sptamano);
        verificarspinner(numeroempl, imemp, snum);
        verificarspinner(edad,imedad,sedad);
        verificarspinner(tiempo, imtime,stime);
        verificarspinner(cambios, im28,scambios);
        verificarspinner(comuna, imcomuna,edcomuna);

        if (flag){

//            imimage.setImageResource(R.drawable.check);
  //          imimage.setVisibility(View.VISIBLE);
        }
    }

    //Funcion que se activa al presionar el botón guardar
    public void hide(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edotro2.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edotro1.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(ednit.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edcelu.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edtel.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edbarrio.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(eddesc.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(edcorreo.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(ednombre.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(adress.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(cce.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(pqmce.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(pqmne.getWindowToken(), 0);

    }

//Funcion que se activa al presionar el botón guardar
    public void guardar(View view) {
        count=0;
        verificar();
        Log.v("count", String.valueOf(count));
        if (count>=30){
            new Guardar().execute();
            regresar();
            Toast toast1 = Toast.makeText(getApplicationContext(), "Encuesta almacenada exitosamente", Toast.LENGTH_SHORT);
            toast1.show();
        }
        else {
            Toast toast1 = Toast.makeText(getApplicationContext(), "Verifique los campos señalados", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }
    //Funcion que se activa al presionar el boton cancelar
    public void cancelar(View view) {
        regresar();
        Toast toast1 = Toast.makeText(getApplicationContext(), "cancelando", Toast.LENGTH_SHORT);
        toast1.show();
    }

    private String verificarradio(RadioButton rd1,RadioButton rd2,ImageView im) {
        boolean bandera=false;
        String resultado="";

        //im.setVisibility(View.VISIBLE);


        if (rd1.isChecked()) {
            bandera=true;
            resultado= String.valueOf(rd1.getText());
        }
        if (rd2.isChecked()) {
            bandera=true;
            resultado= String.valueOf(rd2.getText());
        }

        if (bandera) {
            im.setImageResource(R.drawable.check);
            rd1.setTextColor(getResources().getColor(R.color.negro));
            rd2.setTextColor(getResources().getColor(R.color.negro));
            count++;}
        else {im.setImageResource(R.drawable.uncheck);

            rd1.setTextColor(getResources().getColor(R.color.red));
            rd2.setTextColor(getResources().getColor(R.color.red));
        }
        return resultado;
    }


    //Regresa a la actividad principal enviando datos de usuario para mantener la sesión abierta
    private void regresar() {
        Intent j=new Intent(Encuesta1.this, Opciones.class);
        j.putExtra("user", cedula);
        finish();
       startActivity(j);
    }


    //Verifica y Construye la respuesta multiple de 6 opciones
    private String verificarchec7e(CheckBox ch1,CheckBox ch2,CheckBox ch3,CheckBox ch4,CheckBox ch5,CheckBox ch6,CheckBox ch7, ImageView im, EditText ed) {
        boolean bandera=false;
        String sentencia="";
       //im.setVisibility(View.VISIBLE);

        if (ch1.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch1.getText())+",";
        }
        if (ch2.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch2.getText())+",";
        }
        if (ch3.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch3.getText())+",";
        }
        if (ch4.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch4.getText())+",";
        }
        if (ch5.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch5.getText())+",";
        }
        if (ch6.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch6.getText())+",";
        }
        if (ch7.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch7.getText())+": "+ed.getText().toString()+".";
        }

        if (bandera) {
            im.setImageResource(R.drawable.check);

            ch1.setTextColor(getResources().getColor(R.color.negro));
            ch2.setTextColor(getResources().getColor(R.color.negro));
            ch3.setTextColor(getResources().getColor(R.color.negro));
            ch4.setTextColor(getResources().getColor(R.color.negro));
            ch5.setTextColor(getResources().getColor(R.color.negro));
            ch6.setTextColor(getResources().getColor(R.color.negro));
            ch7.setTextColor(getResources().getColor(R.color.negro));
            count++;}
        else {
            ch1.setTextColor(getResources().getColor(R.color.red));
            ch2.setTextColor(getResources().getColor(R.color.red));
            ch3.setTextColor(getResources().getColor(R.color.red));
            ch4.setTextColor(getResources().getColor(R.color.red));
            ch5.setTextColor(getResources().getColor(R.color.red));
            ch6.setTextColor(getResources().getColor(R.color.red));
            ch7.setTextColor(getResources().getColor(R.color.red));
            im.setImageResource(R.drawable.uncheck);}
        return sentencia;



        }



    private String verificarchec7(CheckBox ch1,CheckBox ch2,CheckBox ch3,CheckBox ch4,CheckBox ch5,CheckBox ch6,CheckBox ch7, ImageView im) {
        boolean bandera=false;
        String sentencia="";
        //im.setVisibility(View.VISIBLE);

       if (ch1.isChecked()) {
           bandera=true;
           sentencia=sentencia+" "+String.valueOf(ch1.getText())+",";
       }
        if (ch2.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch2.getText())+",";
        }
        if (ch3.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch3.getText())+",";
        }
        if (ch4.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch4.getText())+",";
        }
        if (ch5.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch5.getText())+",";
        }
        if (ch6.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch6.getText())+",";
        }
        if (ch7.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch7.getText())+".";
        }

        if (bandera) {
            im.setImageResource(R.drawable.check);

            ch1.setTextColor(getResources().getColor(R.color.negro));
            ch2.setTextColor(getResources().getColor(R.color.negro));
            ch3.setTextColor(getResources().getColor(R.color.negro));
            ch4.setTextColor(getResources().getColor(R.color.negro));
            ch5.setTextColor(getResources().getColor(R.color.negro));
            ch6.setTextColor(getResources().getColor(R.color.negro));
            ch7.setTextColor(getResources().getColor(R.color.negro));
            count++;}
        else {
            ch1.setTextColor(getResources().getColor(R.color.red));
            ch2.setTextColor(getResources().getColor(R.color.red));
            ch3.setTextColor(getResources().getColor(R.color.red));
            ch4.setTextColor(getResources().getColor(R.color.red));
            ch5.setTextColor(getResources().getColor(R.color.red));
            ch6.setTextColor(getResources().getColor(R.color.red));
            ch7.setTextColor(getResources().getColor(R.color.red));
            im.setImageResource(R.drawable.uncheck);}
        return sentencia;
    }

    private String verificarchec6(CheckBox ch1,CheckBox ch2,CheckBox ch3,CheckBox ch4,CheckBox ch5,CheckBox ch6, ImageView im, EditText ed) {
        boolean bandera=false;
        String sentencia="";
       // im.setVisibility(View.VISIBLE);

        if (ch1.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch1.getText())+",";
        }
        if (ch2.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch2.getText())+",";
        }
        if (ch3.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch3.getText())+",";
        }
        if (ch4.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch4.getText())+",";
        }
        if (ch5.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch5.getText())+",";
        }
        if (ch6.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch6.getText())+": "+ed.getText().toString()+".";




        }


        if (bandera) {
            im.setImageResource(R.drawable.check);

            ch1.setTextColor(getResources().getColor(R.color.negro));
            ch2.setTextColor(getResources().getColor(R.color.negro));
            ch3.setTextColor(getResources().getColor(R.color.negro));
            ch4.setTextColor(getResources().getColor(R.color.negro));
            ch5.setTextColor(getResources().getColor(R.color.negro));
            ch6.setTextColor(getResources().getColor(R.color.negro));

            count++;}
        else {
            ch1.setTextColor(getResources().getColor(R.color.red));
            ch2.setTextColor(getResources().getColor(R.color.red));
            ch3.setTextColor(getResources().getColor(R.color.red));
            ch4.setTextColor(getResources().getColor(R.color.red));
            ch5.setTextColor(getResources().getColor(R.color.red));
            ch6.setTextColor(getResources().getColor(R.color.red));

            im.setImageResource(R.drawable.uncheck);}
        return sentencia;
    }

    //Verifica y Construye la respuesta multiple de 4 opciones
    private String verificarchec4(CheckBox ch1,CheckBox ch2,CheckBox ch3,CheckBox ch4, ImageView im) {
        boolean bandera=false;
        String sentencia="";
       // im.setVisibility(View.VISIBLE);

        if (ch1.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch1.getText())+",";
        }
        if (ch2.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch2.getText())+",";
        }
        if (ch3.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch3.getText())+",";
        }
        if (ch4.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch4.getText())+".";
        }

        if (bandera) {
            im.setImageResource(R.drawable.check);

            ch1.setTextColor(getResources().getColor(R.color.negro));
            ch2.setTextColor(getResources().getColor(R.color.negro));
            ch3.setTextColor(getResources().getColor(R.color.negro));
            ch4.setTextColor(getResources().getColor(R.color.negro));

            count++;}
        else {
            ch1.setTextColor(getResources().getColor(R.color.red));
            ch2.setTextColor(getResources().getColor(R.color.red));
            ch3.setTextColor(getResources().getColor(R.color.red));
            ch4.setTextColor(getResources().getColor(R.color.red));

            im.setImageResource(R.drawable.uncheck);}
        return sentencia;
    }

    //Verifica y Construye la respuesta multiple de 3 opciones
    private String verificarchec3(CheckBox ch1,CheckBox ch2,CheckBox ch3, ImageView im) {
        boolean bandera=false;
        String sentencia="";
        //im.setVisibility(View.VISIBLE);

        if (ch1.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch1.getText())+",";
        }
        if (ch2.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch2.getText())+",";
        }
        if (ch3.isChecked()) {
            bandera=true;
            sentencia=sentencia+" "+String.valueOf(ch3.getText())+".";
        }
        if (bandera) {
            im.setImageResource(R.drawable.check);
            ch1.setTextColor(getResources().getColor(R.color.negro));
            ch2.setTextColor(getResources().getColor(R.color.negro));
            ch3.setTextColor(getResources().getColor(R.color.negro));


            count++;}
        else {
            ch1.setTextColor(getResources().getColor(R.color.red));
            ch2.setTextColor(getResources().getColor(R.color.red));
            ch3.setTextColor(getResources().getColor(R.color.red));

            im.setImageResource(R.drawable.uncheck);}
        return sentencia;

    }

    //Verifica que se haya introducido texto a los Edittext
    private void verificaredit(EditText et, ImageView im ) {
       // im.setVisibility(View.VISIBLE);
        if (et.getText().toString().trim().length() != 0){
            im.setImageResource(R.drawable.check);
            count=count+1;
            et.setBackgroundResource(R.drawable.fondo);
        } else {im.setImageResource(R.drawable.uncheck);
            et.setBackgroundResource(R.drawable.fondo4);
            }
        return;
    }


    private void verificarcelu(EditText et, ImageView im ) {
        //im.setVisibility(View.VISIBLE);
        if (et.getText().toString().trim().length() == 10){
            im.setImageResource(R.drawable.check);
            et.setBackgroundResource(R.drawable.fondo);
            count=count+1;
        }
        else {im.setImageResource(R.drawable.uncheck);
            et.setBackgroundResource(R.drawable.fondo4);}
        return;
    }

    private void verificarnumero(EditText et, ImageView im ) {
      //  im.setVisibility(View.VISIBLE);
        if (et.getText().toString().trim().length() == 7){
            im.setImageResource(R.drawable.check);
            et.setBackgroundResource(R.drawable.fondo);
            count=count+1;
        }
        else {im.setImageResource(R.drawable.uncheck);
            et.setBackgroundResource(R.drawable.fondo4);}
        return;
    }
    private void verificacorreo(EditText et, ImageView im ) {
       String text=et.getText().toString();
       // im.setVisibility(View.VISIBLE);
        if (text.trim().length() != 0){

            CharSequence email=text;
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            im.setImageResource(R.drawable.check);
            count=count+1;
             et.setBackgroundResource(R.drawable.fondo);}
        }
        else {im.setImageResource(R.drawable.uncheck); et.setBackgroundResource(R.drawable.fondo4);}
        return;
    }
    //Verifica que se haya seleccionado un opción en los spinners
    private void verificarspinner(String sp, ImageView im, Spinner spi ) {
        //im.setVisibility(View.VISIBLE);
        if (sp != " "){
            im.setImageResource(R.drawable.check);
            count=count+1;
            spi.setBackgroundResource(R.drawable.fondo);
        }
        else {im.setImageResource(R.drawable.uncheck);
            spi.setBackgroundResource(R.drawable.fondo4);}

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

//inhabilita la función del botó atrás
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    class Guardar extends AsyncTask<String, String, String> {
        List params = new ArrayList();
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {

            if (flag){

                params.add(new BasicNameValuePair("imagen", imagen_name));
            }
            else {

                params.add(new BasicNameValuePair("imagen", "sin"));
            }

            params.add(new BasicNameValuePair("direccion",direccion ));
            params.add(new BasicNameValuePair("nombre",nombre));
            params.add(new BasicNameValuePair("cedula",cedula ));
            params.add(new BasicNameValuePair("latitud",latitudeEdit ));
            params.add(new BasicNameValuePair("longitud",longitudeEdit ));
            params.add(new BasicNameValuePair("nit",nit ));
            params.add(new BasicNameValuePair("telefono",telefono ));
            params.add(new BasicNameValuePair("razon",razon ));
            params.add(new BasicNameValuePair("celu",celular));

            params.add(new BasicNameValuePair("barrio", barrio));
            params.add(new BasicNameValuePair("correo", correo));
            params.add(new BasicNameValuePair("comuna", comuna ));

            params.add(new BasicNameValuePair("edad", edad));
            params.add(new BasicNameValuePair("cargo",cargo ));
            params.add(new BasicNameValuePair("unidad_productiva",unidad ));
            params.add(new BasicNameValuePair("genero",genero));
            params.add(new BasicNameValuePair("escolaridad",escolaridad));
            params.add(new BasicNameValuePair("tamano",tamano));

            params.add(new BasicNameValuePair("desc",desc ));

            params.add(new BasicNameValuePair("tiempo", tiempo));
            params.add(new BasicNameValuePair("registro",registro));
            params.add(new BasicNameValuePair("empleados",numeroempl));
            params.add(new BasicNameValuePair("equipos",equipos ));
            params.add(new BasicNameValuePair("internet",internet ));
            params.add(new BasicNameValuePair("conocimiento",conocimientos ));

            params.add(new BasicNameValuePair("curso_anterior",curso_anterior));
            params.add(new BasicNameValuePair("areas",areas));
            params.add(new BasicNameValuePair("mejoras_negocio",mejoras_negocio));
            params.add(new BasicNameValuePair("mejoras_comunidad",mejoras_comunidad));
            params.add(new BasicNameValuePair("participa",participa));
            params.add(new BasicNameValuePair("proyectos",proyectos));
            params.add(new BasicNameValuePair("cambios",cambios));
            params.add(new BasicNameValuePair("pqmc",pqmc));
            params.add(new BasicNameValuePair("pqmn",pqmn));
            params.add(new BasicNameValuePair("cc",cc));

            JSONObject json3 = jParser.makeHttpRequest(url, "POST", params);

            try {
                String success = json3.getString("success");
                Log.d("h",success);
            } catch (JSONException e) {
                e.printStackTrace();      }
            return null;   }

        protected void onPostExecute(String file_url) {
            if (flag){

                serverUpdate();




            }



        }
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
        Intent j=new Intent(Encuesta1.this, log2.class);

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
