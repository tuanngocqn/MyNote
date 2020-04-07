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
        tagAlertAdapter= new TagAlertAdapter(Maintagchoose.this,R.layout.layout_item_tag,classTags);
        listViewTagChoose.setAdapter(tagAlertAdapter);
        listViewTagChoose.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Maintagchoose.this,String.valueOf(position) +"  "+ id,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
//        tagAlertAdapter.SetOnItemListenerTag(new OnlistenerTags() {
//            @Override
//            public void Onclicklongtag(View view, final int position) {
//                builder.setTitle("Do you want delete this item ?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dbManager.Delete(classTags.get(position).getId());
//                                classTags.remove(position);
//                                tagAlertAdapter.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                builder.show();
//            }
//        });
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
