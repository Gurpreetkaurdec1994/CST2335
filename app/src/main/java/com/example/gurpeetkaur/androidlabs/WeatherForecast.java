package com.example.gurpeetkaur.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {


    TextView current_temp;
    TextView max_temp;
    TextView min_temp;
    TextView wind_speed;
    ProgressBar progressBar;
    ImageView Weather_Imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        current_temp = (TextView)findViewById(R.id.currenttemp);
        max_temp = (TextView)findViewById(R.id.max_temp);
        min_temp = (TextView) findViewById(R.id.min_temp);
        wind_speed = (TextView) findViewById(R.id.wind_speed);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Weather_Imageview = (ImageView) findViewById(R.id.weather);

        progressBar.setVisibility(View.VISIBLE);        // seting visibilty of progress bar
        ForecastQuery fQuery = new ForecastQuery();  // creating object for inner class
        fQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }

    protected static Bitmap getImage(URL url) {

        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String mintemp;
        String maxtemp;
        String currenttemp;
        String iconName;
        String wind;
        Bitmap current_temperature;

        @Override
        protected String doInBackground(String... string) {
            try {
                URL url = new URL(string[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // establishing connection
                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser(); // creating parser object to parse xml
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    else if (parser.getName().equals("temperature")) {
                        currenttemp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        mintemp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxtemp = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    else if(parser.getName().equals("speed")) {
                        wind = parser.getAttributeValue(null, "value");
                    }
                    else  if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName+".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(iconFile);
                                fis = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));

                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            current_temperature = BitmapFactory.decodeStream(fis);


                        } else {

                            URL imageURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            current_temperature = getImage(imageURL);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            current_temperature.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                        publishProgress(100);  // publishing progress for progress bar
                        // Log.i(ACTIVITY_NAME, "publishprogress"+progressBar);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return "value";
        }

        public void onProgressUpdate(Integer... data) {
            progressBar.setVisibility(View.VISIBLE);        // seting visibilty of progress bar

            progressBar.setProgress(data[0]);  // progress for progressbar


        }

        public void onPostExecute(String result) {

            current_temp.setText("current temperture:  " + currenttemp + " °C"); // passing values to textview
            min_temp.setText("min temp:  " + mintemp + " °C");
            max_temp.setText("max temp:  " + maxtemp + " °C");
            wind_speed.setText("Wind speed:  " + wind);
            Weather_Imageview.setImageBitmap(current_temperature);
            progressBar.setVisibility(View.INVISIBLE);        // seting visibilty of progress bar

        }

    }
}