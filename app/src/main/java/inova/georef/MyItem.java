package inova.georef;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by UNE on 11/06/2016.
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private final String micono;

    public MyItem (double lat, double lng, String t, String s, String icono) {
        mPosition= new LatLng(lat, lng);
        mTitle = t;
        mSnippet = s;
        micono= icono;
    }
    @Override
    public LatLng getPosition() {

        return mPosition;
    }
    public String getTitle(){
        return mTitle;
    }

    public String getSnippet(){
        return mSnippet;
    }

    public String getIcono(){
        return micono;
    }
}