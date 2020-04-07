package com.example.mynoteenglish.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerNotes;
import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.NoteMainAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    public  static  final String ITEM_SELECT = "item_select";
    FloatingActionButton fabAdd;
    Toolbar toolbarMain;
    RecyclerView recyclerViewMain;
    TextView textViewSumnotes,textViewTagNotes;
    ArrayList<classNoteMain> arrayList;
    NoteMainAdapter noteMainAdapter;
    DBManager dbManager;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initilize();
        Mapping ();
        DrawToolbar();
        SetEventOnclick();
        viewRecyclerviewMain();
    }

    private void initilize() {
        dbManager= new DBManager(this);
        builder = new AlertDialog.Builder(MainActivity.this);
    }

    protected  void Mapping ()
    {
        fabAdd = findViewById(R.id.fba_main);
        toolbarMain= findViewById(R.id.toolbar_main);
        recyclerViewMain= findViewById(R.id.recyclerview_Main);
        textViewSumnotes= findViewById(R.id.textview_sumnotes);
        textViewTagNotes= findViewById(R.id.textview_tagnote);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_management :
                Intent intent= new Intent(MainActivity.this, Maintagchoose.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_find :
                Toast.makeText(this, "FIND", Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void SetEventOnclick()
    {
        fabAdd.setOnClickListener(this);
    }

    private void DrawToolbar()
    {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarMain.setNavigationIcon(R.drawable.ic_home_24px);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fba_main:
                Intent intent= new Intent(MainActivity.this, MainAddItem.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPostResume() {

        arrayList.clear();
        arrayList.addAll(dbManager.GetAllNote());
        if (arrayList!=null)
        {
            textViewSumnotes.setText(arrayList.size() + " Notes");
        }
        else { textViewSumnotes.setText("0 Notes");}
        noteMainAdapter.notifyDataSetChanged();
        super.onPostResume();
    }

    private  void viewRecyclerviewMain()
    {   arrayList= new ArrayList<>();
        noteMainAdapter= new NoteMainAdapter(arrayList);
        recyclerViewMain.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerViewMain.setAdapter(noteMainAdapter);
        ((NoteMainAdapter) Objects.requireNonNull(recyclerViewMain.getAdapter())).SetOnItemListenerNoteMain(new OnlistenerNotes() {
            @Override
            public void OnItemClickNotes(View view, int position) {
                Intent intent= new Intent(MainActivity.this, MainAddItem.class);
                intent.putExtra(ITEM_SELECT,arrayList.get(position));
                startActivity(intent);
                finish();
            }

            @Override
            public void OnItemLongClickNotes(View view, final int position) {
                builder.setTitle("Do you want delete this item ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbManager.Delete(arrayList.get(position).getmID());
                                arrayList.remove(position);
                                noteMainAdapter.notifyDataSetChanged();
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
        });
    }
}
