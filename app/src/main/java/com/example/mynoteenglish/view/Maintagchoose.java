package com.example.mynoteenglish.view;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerTags;
import com.example.mynoteenglish.model.classTag;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.TagAlertAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Maintagchoose extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fabTagchoose;
    ListView listViewTagChoose;
    DBManager dbManager;
    AlertDialog.Builder builder;
    Toolbar toolbarTagChoose;
    ArrayList<classTag> classTags ;
    ///
    Button buttonalertYes,buttonalertNo;
    EditText editTextalertInput;
    TextView textViewTitle;
    ///
    TagAlertAdapter tagAlertAdapter;
    ArrayList<String> checkdulicate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagnamechoose);
        Initilize();
        Mapping ();
        DrawToolbar();
        SetEventOnclick();
        viewListViewMain();
        ChooseRun();
    }

    private void Initilize() {
        dbManager= new DBManager(this);
        classTags = new ArrayList<>();
        builder=new AlertDialog.Builder(this);
    }
    private void Mapping() {
        fabTagchoose= findViewById(R.id.fbatag_choose);
        listViewTagChoose= findViewById(R.id.listviewtag_choose);
        toolbarTagChoose= findViewById(R.id.toolbartag_choose);
    }
    private void DrawToolbar() {
        setSupportActionBar(toolbarTagChoose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTagChoose.setTitle("List your Tag");
    }


    private void SetEventOnclick() {
        fabTagchoose.setOnClickListener(this);
        toolbarTagChoose.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Maintagchoose.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private  void ChooseRun()
    {
        classTags= dbManager.GetAllTag();
        toolbarTagChoose.setTitle("List your tag ("+ classTags.size()+" )" );
        tagAlertAdapter= new TagAlertAdapter(Maintagchoose.this,R.layout.layout_item_tag,classTags,false);
        listViewTagChoose.setAdapter(tagAlertAdapter);
        tagAlertAdapter.SetOnItemListenerTag(new OnlistenerTags() {
            @Override
            public void Onclicklongtag(View view, final int position) {
                builder.setTitle("Do you want delete item ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbManager.DeleteTag(classTags.get(position).getId());
                                classTags.clear();
                                classTags.addAll(dbManager.GetAllTag());
                                tagAlertAdapter.notifyDataSetChanged();
                                toolbarTagChoose.setTitle("List your tag ("+ classTags.size()+" )" );
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }

            @Override
            public void Onclicktag(View view, final int position) {
                View viewEdit = View.inflate(Maintagchoose.this, R.layout.alertaddtag, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(Maintagchoose.this);
                alert.setView(viewEdit);
                final AlertDialog ad= alert.show();
                textViewTitle= viewEdit.findViewById(R.id.alert_Title);
                textViewTitle.setText("Edit your tag");
                buttonalertNo= viewEdit.findViewById(R.id.buttonalert_no);
                buttonalertYes= viewEdit.findViewById(R.id.buttonalert_yes);
                buttonalertYes.setText("Update");
                editTextalertInput= viewEdit.findViewById(R.id.editextalert_input);
                editTextalertInput.setText(classTags.get(position).getTagname());
                buttonalertYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String contentget= editTextalertInput.getText().toString();
                        for(classTag d : classTags){
                            if(d.getTagname() != null && d.getTagname().equals(contentget))
                            {
                                editTextalertInput.setError("Your tag is exist!");
                                return;
                            }
                        }
                        dbManager.updateNotesTag(new classTag(classTags.get(position).getId(),editTextalertInput.getText().toString()));
                        classTags.clear();
                        classTags.addAll(dbManager.GetAllTag()) ;
                        tagAlertAdapter.notifyDataSetChanged();
                        toolbarTagChoose.setTitle("List your tag ("+ classTags.size()+" )" );
                        ad.dismiss();
                        Toast.makeText(Maintagchoose.this,"Tag added sucess!",Toast.LENGTH_SHORT).show();
                    }
                });
                buttonalertNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
            }

            @Override
            public void Onclickshorttag(View view, int position) {

            }
        });
    }
    private void viewListViewMain() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbatag_choose:
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
                        int checduplicate= classTags.size();
                        dbManager.addNotes_tag(new classTag(editTextalertInput.getText().toString()));
                        classTags.clear();
                        classTags.addAll(dbManager.GetAllTag()) ;
                        tagAlertAdapter.notifyDataSetChanged();
                        if ( checduplicate==classTags.size())
                        {
                            editTextalertInput.setError("Your tag is exist!");
                            return;
                        }
                        toolbarTagChoose.setTitle("List your tag ("+ classTags.size()+" )" );
                        ad.dismiss();
                        Toast.makeText(Maintagchoose.this,"Tag added sucess!",Toast.LENGTH_SHORT).show();
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

    }
}
