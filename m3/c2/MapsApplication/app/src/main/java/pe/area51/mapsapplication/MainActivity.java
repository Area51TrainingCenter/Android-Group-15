package pe.area51.mapsapplication;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends Activity implements MapFragment.MapFragmentInterface {

    private Location lastLocation;

    private final static String REVERSE_GEOCODING_API_URL = "http://nominatim.openstreetmap.org/reverse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MapFragment mapFragment = new MapFragment();
        mapFragment.setMapFragmentInterface(this);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_place_holder, mapFragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reverse_geocoding:
                doReverseGeocoding();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLocationChange(Location location) {
        this.lastLocation = location;
    }

    private void doReverseGeocoding() {
        if (lastLocation != null) {
            try {
                final String latitude = URLEncoder.encode(String.valueOf(lastLocation.getLatitude()), "utf-8");
                final String longitude = URLEncoder.encode(String.valueOf(lastLocation.getLongitude()), "utf-8");
                final String url = REVERSE_GEOCODING_API_URL + "?format=json" + "&lat=" + latitude + "&lon=" + longitude;
                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            return HttpConnection.doJsonHttpGet(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(String response) {
                        if (response != null) {
                            try {
                                final Address address = Parser.parse(response);
                                Toast.makeText(MainActivity.this, address.getName(), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, R.string.parse_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.no_last_location, Toast.LENGTH_SHORT).show();
        }
    }

}
