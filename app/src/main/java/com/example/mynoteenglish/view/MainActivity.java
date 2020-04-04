package com.example.mynoteenglish.view;

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
import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.NoteMainAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    FloatingActionButton fabAdd;
    Toolbar toolbarMain;
    RecyclerView recyclerViewMain;
    TextView textViewSumnotes,textViewTagNotes;
    ArrayList<classNoteMain> arrayList;
    NoteMainAdapter noteMainAdapter;
    DBManager dbManager;
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
    }

    protected  void Mapping ()
    {
        fabAdd = findViewById(R.id.fba_add);
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
            case R.id.menu_setting :
                Toast.makeText(this, "SETING", Toast.LENGTH_SHORT).show();
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
            case R.id.fba_add:
                Intent intent= new Intent(MainActivity.this,add_item.class);
                startActivity(intent);
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
    }
}
