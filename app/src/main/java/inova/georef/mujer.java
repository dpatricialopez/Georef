package inova.georef;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mujer extends ActionBarActivity {

    TextView txp1,txp2,txp3;
    LinearLayout lp1,lp2,lp3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mujer);

        txp1=(TextView)findViewById(R.id.txp1);
        txp2=(TextView)findViewById(R.id.txp2);

        lp1=(LinearLayout)findViewById(R.id.preg1);
        lp2=(LinearLayout)findViewById(R.id.preg2);


        txp1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (lp1.getVisibility() == View.VISIBLE) {
                    lp1.setVisibility(view.GONE);
                } else {
                    lp1.setVisibility(view.VISIBLE);
                }


            }
        });
        txp2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (lp2.getVisibility() == View.VISIBLE) {
                    lp2.setVisibility(view.GONE);
                } else {
                    lp2.setVisibility(view.VISIBLE);
                }


            }
        });




    }



}


