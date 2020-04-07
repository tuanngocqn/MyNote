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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.model.classTag;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.LibTextToSpeedCompleted;
import com.example.mynoteenglish.viewmodel.TagAlertAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainAddItem extends AppCompatActivity implements View.OnClickListener{
    boolean statusPlay=false,statusLike=false,isStatusSaved=false,statusRepeat=false;
    AlertDialog.Builder builder;
    DBManager dbManager;
    LibTextToSpeedCompleted Text2Speed;
    Toolbar toolbarAdd;
    EditText edittextTextInput,editName;
    Button buttonTag,buttonSpeed,buttonLanguage,buttonPlay,buttonRepeat;
    Button buttonalertYes,buttonalertNo;
    EditText editTextalertInput;
    FloatingActionButton fabAddVocabulary;
    Menu menuAdd;
    classNoteMain classNoteMain,ObjectIntent;
    MenuItem menuSave,menuFavorite;
    TextView textViewTitle;
    final String[] arrayLang=  {"ENGLISH","TIẾNG VIỆT"};
    final String[] arraySpeed=  {"0.3","0.6","1","1.3","1.7","2","2.5","3","4","5"};
    List<String> listtag;
    int indexSpeed=2;
    int indexLang=0;
    ArrayList<classTag> classTags ;
    TagAlertAdapter tagAlertAdapter;
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
        Intent intent= getIntent();
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
        Text2Speed.Shutdown();
        super.onDestroy();
    }
    private void initital() {
        Text2Speed = new LibTextToSpeedCompleted(MainAddItem.this);
        dbManager= new DBManager(this);
        classTags = new ArrayList<>();
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
         fabAddVocabulary.setOnClickListener(this);
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
         editName.addTextChangedListener(new TextWatcher() {
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
         if (ObjectIntent!=null)
             {
                 classNoteMain= CreateNoteUpdate();
                 dbManager.updateNotes(classNoteMain);
                 Toast.makeText(MainAddItem.this,"Updated success!",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 classNoteMain= CreateNote();
                 dbManager.addNotes(classNoteMain);
                 ObjectIntent= classNoteMain;
                 ObjectIntent.setmID(String.valueOf(dbManager.GetID(classNoteMain)));
                 Toast.makeText(MainAddItem.this,"Add new success!",Toast.LENGTH_SHORT).show();
             }
     }
     private  void Checksavedata()
     {
         if (isStatusSaved)
         {
             builder = new AlertDialog.Builder(MainAddItem.this);
             builder.setMessage("Do you want to save ?");
             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     SaveUpdated();
                     dialog.dismiss();
                     Intent intent= new Intent(MainAddItem.this,MainActivity.class);
                     startActivity(intent);
                     finish();
                 }
             });
             builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     Intent intent= new Intent(MainAddItem.this,MainActivity.class);
                     startActivity(intent);
                     finish();
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
             Intent intent= new Intent(MainAddItem.this,MainActivity.class);
             startActivity(intent);
             finish();
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
            case R.id.menu_tag:
                View view = View.inflate(this, R.layout.alertaddtag, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setView(view);
                final AlertDialog ad= alert.show();
                textViewTitle= view.findViewById(R.id.alert_Title);
                textViewTitle.setText("Add new your tag");
                buttonalertNo= view.findViewById(R.id.buttonalert_no);
                buttonalertYes= view.findViewById(R.id.buttonalert_yes);
                editTextalertInput= view.findViewById(R.id.editextalert_input);
                buttonalertYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int checduplicate= dbManager.GetAllTag().size();
                        dbManager.addNotes_tag(new classTag(editTextalertInput.getText().toString()));
                        classTags.clear();
                        classTags.addAll(dbManager.GetAllTag()) ;
                        if ( checduplicate==classTags.size())
                        {
                            editTextalertInput.setError("Your tag is exist!");
                            return;
                        }
                        ad.dismiss();
                        Toast.makeText(MainAddItem.this,"Tag added sucess!",Toast.LENGTH_SHORT).show();
                    }
                });
                buttonalertNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       ad.dismiss();

                    }
                });

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

    }   private classNoteMain CreateNoteUpdate()
    {
         String id = ObjectIntent.getmID();
         String name= editName.getText().toString();
         String content= edittextTextInput.getText().toString();
         SimpleDateFormat dateFormat= new SimpleDateFormat("dd MM,yyyy");
         String datecreate= ObjectIntent.getmDateCreate();
         String dateupdate= dateFormat.format(new Date());
         String tagname= buttonTag.getText().toString();
         String favorite= (statusLike==true)?"true":"false";
         return  new classNoteMain(id,name,content,datecreate,dateupdate,tagname,favorite);

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
                builder = new AlertDialog.Builder(MainAddItem.this);
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
                builder = new AlertDialog.Builder(MainAddItem.this);
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
            case R.id.fba_add:
//                View view = View.inflate(this, R.layout.alertaddtag, null);
//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                alert.setView(view);
//                buttonalertNo= view.findViewById(R.id.buttonalert_no);
//                buttonalertYes= view.findViewById(R.id.buttonalert_yes);
//                editTextalertInput= view.findViewById(R.id.editextalert_input);
//                buttonalertYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(add_item.this,"Yes nhe",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                buttonalertNo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(add_item.this,"No nhe",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alert.show();
                break;
            case  R.id.button_Tag:
              //  listtag= dbManager.GetAllTag();
//                String[] temp= listtag.toArray(new String[0]);
//                builder = new AlertDialog.Builder(add_item.this);
//                builder.setTitle("Choose your language");
//                builder.setItems(temp, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.show();
//                View view = View.inflate(add_item.this,R.layout.alertchoosetag,null);
//                LayoutInflater inflater = LayoutInflater.from(add_item.this);
//                View view_adapter = inflater.inflate(R.layout.alertchoosetag,null);
//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                classTags= dbManager.GetAllTag();
//                tagAlertAdapter = new TagAlertAdapter(view_adapter.getContext(),R.layout.layout_item_tag,classTags);
//                ListView listView= view.findViewById(R.id.listviewalert_choose);
//                listView.setAdapter(tagAlertAdapter);
//                alert.setView(view);
//                alert.show();
//                tagAlertAdapter=new TagAlertAdapter(add_item.this,R.layout.layout_item_tag,classTags);
//                AlertDialog.Builder builder = new AlertDialog.Builder(add_item.this);
//
//                builder.setTitle("title");
//
//                builder.setAdapter(tagAlertAdapter, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // ---
//                    }
//
//                });
//
//              AlertDialog aler=   builder.create();
//                aler.show();
                break;
            default:
                break;
        }
    }
}
