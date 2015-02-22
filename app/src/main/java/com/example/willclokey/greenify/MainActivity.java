package com.example.willclokey.greenify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


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
        Button sixthbutton = (Button) findViewById(R.id.button6);


        firstbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/1/" + latitude + "/" + longitude);
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

        secondbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/2/" + latitude + "/" + longitude);
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
        thirdbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/3/" + latitude + "/" + longitude);
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
        fourthbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/4/" + latitude + "/" + longitude);
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });
        fifthbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute("http://greenify.mybluemix.net/5/" + latitude + "/" + longitude);
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

        sixthbutton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(context, AddLocation.class);
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
            String urlString = uri[0]; // URL to call
            String result = "";
            HttpURLConnection urlConnection;

            // HTTP Get
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String data = reader.readLine();
                while (data != null){
                    result += data;
                    data = reader.readLine();
                }
            } catch (Exception e ) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }

    }


}
