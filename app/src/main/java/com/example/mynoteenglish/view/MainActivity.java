package com.example.mynoteenglish.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynoteenglish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    FloatingActionButton fabAdd;
    Toolbar toolbarMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Mapping ();
        DrawToolbar();
        SetEventOnclick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
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
    protected  void Mapping ()
    {
        fabAdd = findViewById(R.id.fba_add);
        toolbarMain= findViewById(R.id.toolbar_main);
        toolbarMain.setNavigationIcon(R.drawable.ic_home_24px);
    }
    private void DrawToolbar()
    {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fba_add:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
