package com.example.gurpeetkaur.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
  public Switch switch2;
  public CheckBox checkBox;
  public RadioButton rb;
public ImageButton img;

    protected static final String ACTIVITY_NAME="StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        switch2 =(Switch) findViewById(R.id.switch1);
         checkBox = (CheckBox)findViewById(R.id.checkBox);
         img = (ImageButton)findViewById(R.id.imageButton);

         img.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent takepicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(takepicIntent,26);
             }
         });




        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                      CharSequence text = getResources().getString(R.string.switch_on);
                    Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                }
                else{
                    CharSequence text = getResources().getString(R.string.switch_off);

                    Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();


                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){


                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
// 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Intent intent1 = new Intent(ListItemsActivity.this,StartActivity.class);
                                    Intent resultIntent = new Intent(  );
                                    resultIntent.putExtra("Response", "Here is my response");
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();


                                  //  startActivityForResult(intent1,3);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkBox.setChecked(false);
                                    // User cancelled the dialog
                                }
                            })
                            .show();



                }


            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 26 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }
}

