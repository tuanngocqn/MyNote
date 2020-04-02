package com.example.mynoteenglish.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynoteenglish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class add_item extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbarAdd;
    EditText edittextTextInput;
    Button buttonTag,buttonSpeed,buttonLanguage,buttonPlay;
    FloatingActionButton fabAddVocabulary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mapping();
        DrawToobar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void DrawToobar()
    {
        setSupportActionBar(toolbarAdd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    private void mapping()
    {
        toolbarAdd= findViewById(R.id.toolbar_add);
        buttonLanguage=findViewById(R.id.button_Language);
        buttonPlay = findViewById(R.id.button_Play);
        buttonSpeed = findViewById(R.id.button_Speed);
        buttonTag= findViewById(R.id.button_Tag);
        edittextTextInput = findViewById(R.id.edittext_inputtext);
        fabAddVocabulary = findViewById(R.id.fba_add);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_favorite:
                Toast.makeText(getApplicationContext(),"Clicked favorite",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
}
