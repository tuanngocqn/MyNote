package com.example.mynoteenglish.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.KeyEventDispatcher;

import android.os.Bundle;
import android.view.View;

import com.example.mynoteenglish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    FloatingActionButton flbAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping ();
        SetEventOnclick();
    }
    protected void SetEventOnclick()
    {
        flbAdd.setOnClickListener(this);
    }
    protected  void Mapping ()
    {
        flbAdd = findViewById(R.id.fba_add);
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
