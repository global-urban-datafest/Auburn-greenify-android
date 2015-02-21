package com.example.willclokey.greenify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MainActivity extends Activity implements LocationListener {
    Context context = this;
    double latitude = 0;
    double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firstbutton = (Button) findViewById(R.id.button1);
        Button secondbutton = (Button) findViewById(R.id.button2);
        Button thirdbutton = (Button) findViewById(R.id.button3);
        Button fourthbutton = (Button) findViewById(R.id.button4);
        Button fifthbutton = (Button) findViewById(R.id.button5);

        firstbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/hydration/lat/lon");
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

        secondbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/wifi/lat/lon");
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
        thirdbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/chargers/lat/lon");
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
        fourthbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/recycling/lat/lon");
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
        fifthbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/bikerental/lat/lon");
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = (location.getLatitude());
        longitude = (location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }


}
