package com.example.mynoteenglish.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.LibTextToSpeedCompleted;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_item extends AppCompatActivity implements View.OnClickListener{
    boolean statusPlay=false,statusLike=false,isStatusSaved=false,statusRepeat=false;
    AlertDialog.Builder builder;
    Intent intent;
    DBManager dbManager;
    LibTextToSpeedCompleted Text2Speed;
    Toolbar toolbarAdd;
    EditText edittextTextInput,editName;
    Button buttonTag,buttonSpeed,buttonLanguage,buttonPlay,buttonRepeat;
    FloatingActionButton fabAddVocabulary;
    Menu menuAdd;
    classNoteMain classNoteMain,ObjectIntent;
    MenuItem menuSave,menuFavorite;
    final String[] arrayLang=  {"ENGLISH","TIẾNG VIỆT"};
    final String[] arraySpeed=  {"0.3","0.6","1","1.3","1.7","2","2.5","3","4","5"};
    int indexSpeed=2;
    int indexLang=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initital();
        mapping();
        DrawToobar();
        SetEventClick();
    }

    private void Getintent() {
        intent= getIntent();
        ObjectIntent= (classNoteMain)intent.getSerializableExtra(MainActivity.ITEM_SELECT);
        if (ObjectIntent!=null)
        {
            Show_Intent(ObjectIntent);
        }
    }

    @Override
    protected void onStop() {
        Log.d("BBB","OnStOP");
        super.onStop();
    }
    @Override
    protected void onStart() {
        Log.d("BBB","OnStart");
        super.onStart();
    }
    @Override
    protected void onPause() {
        Log.d("BBB","OnPause");
        super.onPause();
    }
    @Override
    protected void onRestart() {
        Log.d("BBB","OnRestart");
        super.onRestart();
    }
    @Override
    protected void onResume() {
        Log.d("BBB","OnResume");
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        Log.d("BBB","OnDestroy");
       Text2Speed.SetStop();
        super.onDestroy();
    }
    private void initital() {
        Text2Speed = new LibTextToSpeedCompleted(add_item.this);
        dbManager= new DBManager(this);
    }
    private  void Show_Intent(classNoteMain classNoteMain)
    {
        if (classNoteMain.getmFavorite().equals("false")) {
                statusLike=false;
                menuFavorite.setIcon(R.drawable.ic_favorite_border_black_24dp);
        }
        else {
                statusLike=true;
                menuFavorite.setIcon(R.drawable.ic_favorite_red_24dp);
        }
        editName.setText(classNoteMain.getmName());
        edittextTextInput.setText(classNoteMain.getmContent());
        buttonTag.setText(classNoteMain.getmTagName());
        menuSave.setIcon(R.drawable.ic_save_gray_24dp);
        isStatusSaved=false;
    }
    private void SetEventClick()
     {
         buttonPlay.setOnClickListener(this);
         buttonTag.setOnClickListener(this);
         buttonSpeed.setOnClickListener(this);
         buttonLanguage.setOnClickListener(this);
         buttonRepeat.setOnClickListener(this);
         toolbarAdd.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Text2Speed.SetStop();
                 Checksavedata();
             }
         });
         edittextTextInput.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 menuSave= menuAdd.getItem(0);
                 status_save_monitor();
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

     }
     private  void SaveUpdated()
     {
         classNoteMain= CreateNote();
         if(classNoteMain!=null)
         {
             if (ObjectIntent!=null)
             {
                 classNoteMain.setmID(ObjectIntent.getmID());
                 dbManager.updateNotes(classNoteMain);
             }
             else
             {
                 dbManager.addNotes(classNoteMain);
             }
         }
     }
     private  void Checksavedata()
     {
         if (isStatusSaved)
         {
             builder = new AlertDialog.Builder(add_item.this);
             builder.setMessage("Do you want to save ?");
             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     SaveUpdated();
                     dialog.dismiss();
                     intent= new Intent(add_item.this,MainActivity.class);
                     startActivity(intent);
                 }
             });
             builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                         intent= new Intent(add_item.this,MainActivity.class);
                         startActivity(intent);
                 }
             });
             builder.setNeutralButton("Canel", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
             builder.show();
         }

         else
         {
             intent= new Intent(add_item.this,MainActivity.class);
             startActivity(intent);
         }
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menuAdd=menu;
        getMenuInflater().inflate(R.menu.add_menu,menu);
        menuFavorite= menuAdd.getItem(1);
        menuSave= menuAdd.getItem(0);
        Getintent();
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
        buttonRepeat = findViewById(R.id.button_repeat);
        buttonTag= findViewById(R.id.button_Tag);
        edittextTextInput = findViewById(R.id.edittext_inputtext);
        editName= findViewById(R.id.edit_name);
        fabAddVocabulary = findViewById(R.id.fba_add);



    }
    private  void status_save_monitor()
    {
        isStatusSaved=true;
        menuSave.setIcon(R.drawable.ic_save_black_24dp);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_favorite:
                if (!statusLike)
                {
                    item.setIcon(R.drawable.ic_favorite_red_24dp);
                    statusLike=true;
                }
                else
                {
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    statusLike=false;
                }
                status_save_monitor();
                break;
            case R.id.menu_Save:
                if (isStatusSaved)
                {
                    item.setIcon(R.drawable.ic_save_gray_24dp);
                    SaveUpdated();
                    isStatusSaved=false;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private classNoteMain CreateNote()
    {
         String name= editName.getText().toString();
         String content= edittextTextInput.getText().toString();
         SimpleDateFormat dateFormat= new SimpleDateFormat("dd MM,yyyy");
         String datecreate= dateFormat.format(new Date());
         String dateupdate= dateFormat.format(new Date());
         String tagname= buttonTag.getText().toString();
         String favorite= (statusLike==true)?"true":"false";
         return  new classNoteMain(name,content,datecreate,dateupdate,tagname,favorite);

    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_Play:
                if (!statusPlay)
                {
                    Text2Speed.speak(edittextTextInput.getText().toString());
                    buttonPlay.setBackgroundResource(R.drawable.ic_stop_black_24dp);
                    statusPlay=true;
                }
               else
                {
                    Text2Speed.SetStop();
                    statusPlay=false;
                    buttonPlay.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                }
            break;
            case R.id.button_repeat:
                if (!statusRepeat) {
                    Text2Speed.SetRepeat(true);
                    buttonRepeat.setBackgroundResource(R.drawable.ic_repeat_red_24dp);
                    statusRepeat= true;
                }
                else
                {
                    Text2Speed.SetRepeat(false);
                    buttonRepeat.setBackgroundResource(R.drawable.ic_repeat_white_24dp);
                    statusRepeat=false;
                }
                break;
            case R.id.button_Language:
                builder = new AlertDialog.Builder(add_item.this);
                builder.setTitle("Choose your language");
                builder.setSingleChoiceItems(arrayLang, indexLang, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        indexLang=position;
                        if (position==0 )
                        {
                            Text2Speed.SetLanguage(Locale.ENGLISH);
                            buttonLanguage.setText("EN");
                            dialog.dismiss();
                        }
                        else
                        {
                            Text2Speed.SetLanguage(new Locale("vi","VN"));
                            buttonLanguage.setText("VN");
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                break;
            case R.id.button_Speed:
                builder = new AlertDialog.Builder(add_item.this);
                builder.setTitle("Choose your speed");
                builder.setSingleChoiceItems(arraySpeed, indexSpeed, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                            indexSpeed= position;
                            Text2Speed.SetSpeed(Float.valueOf(arraySpeed[position]));
                            buttonSpeed.setText("Speed x"+arraySpeed[position]);
                            dialog.dismiss();
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }
}
