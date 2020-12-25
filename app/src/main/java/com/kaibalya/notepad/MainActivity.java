package com.kaibalya.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListListener {
    RecyclerView recyclerView;
    static ArrayList<String> notes = new ArrayList<>();
    static ListAdapter listAdapter;

    SharedPreferences sharedPreferences;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case  R.id.settings:
                Intent intent = new Intent(MainActivity.this, CreateNote.class);
                startActivity(intent);
                return true;
            case R.id.help:
                Toast.makeText(MainActivity.this, "help clicked...", Toast.LENGTH_SHORT).show();
                return true;
             default:
                 return false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.kaibalya.notepad", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        if(set == null){
            notes.add("demo");
            notes.add("Long press to delete");
        }else{
            notes = new ArrayList<>(set);
        }



        // notepad
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ListAdapter(notes, MainActivity.this, MainActivity.this);
        recyclerView.setAdapter(listAdapter);


    }

    @Override
    public void deleteData(int position) {
        Toast.makeText(MainActivity.this, "lonf", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        listAdapter.notifyDataSetChanged();


                        HashSet<String> set = new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes", set).apply();
                    }
                })
                .setNegativeButton("No", null).show();
    }
}