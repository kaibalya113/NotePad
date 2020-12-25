package com.kaibalya.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

public class CreateNote extends AppCompatActivity {

    EditText view;
    int position ;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        view = findViewById(R.id.data);

        try {
            Intent intent = getIntent();
            String data = intent.getStringExtra("data");
            position = intent.getIntExtra("postion", -1);
            if(position != -1){
                view.setText(data.toString());
            }else {
                MainActivity.notes.add("");
                position = MainActivity.notes.size() -1;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(position,view.getText().toString());
                MainActivity.listAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.kaibalya.notepad", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

   // @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if(position != -1){
//            MainActivity.notes.set(position,view.getText().toString());
//           // listAdapter.notifyItemChanged(position);
//            MainActivity.listAdapter.notifyItemChanged(position);
//        }else{
//            MainActivity.notes.add(view.getText().toString());
//            MainActivity.listAdapter.notifyItemChanged( MainActivity.notes.size()+1);
//        }
//
//
//
//        Toast.makeText(CreateNote.this, "data: "+ position, Toast.LENGTH_SHORT).show();
//    }

}