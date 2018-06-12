package com.example.gurpeetkaur.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "Activity_chat_window";
    static  ChatAdapter messageAdapter;
    ArrayList<String> list = new ArrayList<>();
    Button button;
    ListView lView;
    EditText edittext;
    static SQLiteDatabase sqlite;
    static Cursor cursor;
    static ChatDatabaseHelper object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        object = new ChatDatabaseHelper(this);
        messageAdapter = new ChatAdapter(this);
        sqlite=object.getWritableDatabase();
        list.clear();
        moveCursor();


        button = (Button) findViewById(R.id.send);
        lView = (ListView) findViewById(R.id.listview);
        edittext = (EditText) findViewById(R.id.editText3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.clear();
                ContentValues cv= new ContentValues();
                cv.put(ChatDatabaseHelper.KEY_MESSAGE,edittext.getText().toString());
                sqlite.insert(ChatDatabaseHelper.TABLE_NAME,null,cv);
                moveCursor();
                edittext.setText("");
            }
        });

        lView.setAdapter(messageAdapter);


    }




    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }


        @Override
        public int getCount() {
            return list.size();
        }


        public String getItem(int position) {
            return list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);


            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;


        }
    }
protected void onDestroy() {

    super.onDestroy();
    sqlite.close();
}
    private void moveCursor() {

        cursor = sqlite.query(true, ChatDatabaseHelper.TABLE_NAME,
                new String[] { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                ChatDatabaseHelper.KEY_MESSAGE + " Not Null" , null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            list.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            messageAdapter.notifyDataSetChanged();
            cursor.moveToNext();

        }

        messageAdapter.notifyDataSetChanged();
    }

}