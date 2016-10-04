package inova.georef;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class educa extends ActionBarActivity {

    TextView txp1;
    LinearLayout lp1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.educa);
        txp1=(TextView)findViewById(R.id.txp1);

        lp1=(LinearLayout)findViewById(R.id.preg1);


        txp1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (lp1.getVisibility() == View.VISIBLE) {
                    lp1.setVisibility(view.GONE);
                } else {
                    lp1.setVisibility(view.VISIBLE);
                }


            }
        });



    }



}


