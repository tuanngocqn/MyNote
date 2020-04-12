package com.example.mynoteenglish.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerVocabulary;
import com.example.mynoteenglish.model.Onlistennertexttospeed;
import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.model.classVocabulary;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.LibTextToSpeedCompleted;
import com.example.mynoteenglish.viewmodel.VocabularyAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class MainVocabulary extends AppCompatActivity  {

    Toolbar toolbarVocabularyMain;
    ArrayList<classVocabulary> classVocabularies;
    VocabularyAdapter vocabularyAdapter;
    DBManager dbManager;
    ListView listViewMainVocabulary;
    AlertDialog.Builder builder;
    LibTextToSpeedCompleted Text2Speed;
    boolean status_language=false;
    int positionspeak=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vocabulary);
        Initilize();
        Mapping ();
        DrawToolbar();
        SetEventOnclick();
        ViewListviewAdaptor();
    }
    private void Initilize() {
        classVocabularies= new ArrayList<>();
        dbManager= new DBManager(this);
        builder = new AlertDialog.Builder(MainVocabulary.this);
        Text2Speed = new LibTextToSpeedCompleted(MainVocabulary.this);

    }
    private String getIdVocabulary()
    {
        Intent intent= getIntent();
        String id=  intent.getStringExtra(MainAddItem.getIdVocabulary);
        return id;
    }
    private void Mapping() {
        toolbarVocabularyMain= findViewById(R.id.toolbar_vocab);
        listViewMainVocabulary= findViewById(R.id.listview_mainvocab);
    }
    private void DrawToolbar()
    {
        setSupportActionBar(toolbarVocabularyMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarVocabularyMain.setTitle("List your vocabulary");
    }
    @Override
    public void onBackPressed() {
        Text2Speed.SetStop();
        super.onBackPressed();
    }
    private void SetEventOnclick()
    {
        toolbarVocabularyMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
        Text2Speed.SetOnEventOnSucess(new Onlistennertexttospeed() {
            @Override
            public void Onlistensucess(Activity view) {
                if (!status_language)
                {
                    Text2Speed.speak(classVocabularies.get(positionspeak).getContent());
                    status_language=true;
                }
            }
        });

    }
    private void ViewListviewAdaptor() {
        classVocabularies= dbManager.GetAllVocabularyByID(getIdVocabulary());
        vocabularyAdapter= new VocabularyAdapter(MainVocabulary.this,R.layout.layout_vocabulary,classVocabularies,false);
        listViewMainVocabulary.setAdapter(vocabularyAdapter);
        vocabularyAdapter.SetOnItemListenerTag(new OnlistenerVocabulary() {
            @Override
            public void OnItemClickVocabulary(View view, int position) {
                Text2Speed.SetLanguage(Locale.ENGLISH);
                Text2Speed.speak(classVocabularies.get(position).getName());
                positionspeak=position;
                Text2Speed.SetLanguage(new Locale("vi","VN"));
                status_language=false;
            }

            @Override
            public void OnItemLongClickVocabulary(View view, final int position) {
                builder.setTitle("Do you want delete this?");
                builder.setMessage( "Your vocabulary: "+classVocabularies.get(position).getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          dbManager.DeleteVocabulary(classVocabularies.get(position).getId());
                          classVocabularies.clear();
                          classVocabularies.addAll( dbManager.GetAllVocabularyByID(getIdVocabulary()));
                          vocabularyAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

}
