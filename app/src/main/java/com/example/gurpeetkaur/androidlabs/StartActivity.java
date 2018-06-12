package com.example.gurpeetkaur.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME="StartActivity";
 Button btn;
 Button btn2;
 Button weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button3);
        weather = (Button) findViewById(R.id.weather);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivityForResult(intent, 2);
                Log.i(ACTIVITY_NAME, "User Clicked Start Chat");


            }
        });

        weather.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivityForResult(intent, 7);
            }

        });
    }
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (requestCode == 1 && resultCode == RESULT_OK) {

                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(getApplicationContext(),messagePassed,Toast.LENGTH_SHORT).show();
            }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }


}
