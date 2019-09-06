package com.example.mynotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<String>();
    SharedPreferences sharedPreferences ;
   static ArrayAdapter arrayAdapter;
    TextView textView2,textView3,textView4,textView5;
    /*public void LanguagePreference(String note)
    {
        ArrayList<String> notes = new ArrayList<String>();
        notes.add(note);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.languageset", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("New note",note).apply();
        //String languag =  sharedPreferences.getString("Language","");

        Log.i("new Note",note);
        textView2 =  findViewById(R.id.textView2);
        textView2.setText(notes.get(0).toString());

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.settings:
                Log.i("Item Selected","Settings");
                return true;
            case R.id.addItem:
                Log.i("Item Selected","Add");
               // EditText editText = findViewById(R.id.editText);
               // String temp = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                startActivity(intent);
               // LanguagePreference(temp);
                return true;
            default :
                return false;
        }

    }
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      sharedPreferences  = getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
    HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
      if(set==null){
          notes.add("Example");
      }

      else
          notes = new ArrayList<>(set);
    notes.add("CN");
        ListView  listView = (ListView)findViewById(R.id.listView) ;
 arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
    listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("noteid",i);
               startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final  int itemTodelete =i;
                new  AlertDialog.Builder(MainActivity.this).
                       setIcon(android.R.drawable.ic_input_delete)
                       .setTitle("Delete")
                        .setMessage("Are you Sure?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               notes.remove(itemTodelete);
                               arrayAdapter.notifyDataSetChanged();
                               SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
                               HashSet<String> set = new HashSet<String>(MainActivity.notes);
                               sharedPreferences.edit().putString("notes", String.valueOf(set)).apply();

                           }
                       })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });


    }
}
