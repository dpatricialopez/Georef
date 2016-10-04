package inova.georef;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class problemas extends AppCompatActivity {


    Button ver,aseo,salud,droga,nino,mujer,educa,empleo;
    TextView txtaseo,txtsalud,txtparti,txtempleo;
    LinearLayout problemas2;
    AlertDialog dialog;
    LayoutInflater lyinf;
    View popup;
    PopupWindow pop;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problematicas);//Seleccion de Archivo XML relacionado yque conforma la interfaz grafica.

        ver = (Button) findViewById(R.id.ver);
        droga = (Button) findViewById(R.id.btndroga);

        aseo = (Button) findViewById(R.id.btnaseo);
        empleo = (Button) findViewById(R.id.btnempleo);
        nino = (Button) findViewById(R.id.btnnino);
       mujer= (Button) findViewById(R.id.btnmujer);
       educa = (Button) findViewById(R.id.btneduca);

        txtaseo=(TextView) findViewById(R.id.txtaseo);
        txtempleo=(TextView) findViewById(R.id.txtempleo);
        txtsalud=(TextView) findViewById(R.id.txtsalud);
        txtparti=(TextView) findViewById(R.id.txtparti);
        problemas2=(LinearLayout)findViewById(R.id.problemas2);

       ver.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               if (problemas2.getVisibility() == View.VISIBLE) {
                   problemas2.setVisibility(view.GONE);
               } else {
                   problemas2.setVisibility(view.VISIBLE);
               }

           }
       });
       /*  empleo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (txtempleo.getVisibility() == View.VISIBLE) {
                    txtempleo.setVisibility(view.GONE);
                } else {
                    txtempleo.setVisibility(view.VISIBLE);
                }

            }
        });
        parti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (txtparti.getVisibility()==View.VISIBLE) { txtparti.setVisibility(view.GONE);}
                else { txtparti.setVisibility(view.VISIBLE);}

            }
        });
        salud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (txtsalud.getVisibility()==View.VISIBLE) { txtsalud.setVisibility(view.GONE);}
                else { txtsalud.setVisibility(view.VISIBLE);}

            }
        });
        aseo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (txtaseo.getVisibility()==View.VISIBLE) { txtaseo.setVisibility(view.GONE);}
                else { txtaseo.setVisibility(view.VISIBLE);}

            }
        });
*/
        mujer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent j = new Intent(problemas.this, mujer.class);
                startActivity(j);

            }
        });
        empleo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent j = new Intent(problemas.this, empleo.class);
                startActivity(j);

            }
        });
        aseo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent j = new Intent(problemas.this, aseo.class);
                startActivity(j);
                /*lyinf=(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popup=lyinf.inflate(R.layout.aseo,null);
                pop= new PopupWindow(popup, 300,370,true);
                pop.showAtLocation(popup,Gravity.CENTER,0,0);*/

            }
        });
        droga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent j = new Intent(problemas.this, droga.class);
                startActivity(j);

            }
        });
        educa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent j = new Intent(problemas.this, educa.class);
                startActivity(j);

            }
        });

        nino.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent j = new Intent(problemas.this, nino.class);
                startActivity(j);

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                finish();
                return true;
            case R.id.op2:
                cerrar();
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
        Intent j=new Intent(problemas.this, log2.class);

        finish();
        startActivity(j);
    }


}
