package com.example.mynoteenglish.view;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerNotes;
import com.example.mynoteenglish.model.OnlistenerTags;
import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.model.classTag;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.viewmodel.NoteMainAdapter;
import com.example.mynoteenglish.viewmodel.TagAlertAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    public static enum  enum_viewlist {viewtag,viewtagchoose,viewtagnavigation,viewtagnavigationcontrol};
    public  static  final String ITEM_SELECT = "item_select";
    public  static  final String FULL_VOCABULARY = "full_vocabulary";
    FloatingActionButton fabAdd;
    Toolbar toolbarMain;
    RecyclerView recyclerViewMain;
    TextView textViewSumnotes,textViewTagNotes;
    ArrayList<classNoteMain> arrayList;
    NoteMainAdapter noteMainAdapter;
    DBManager dbManager;
    AlertDialog.Builder builder;
    Menu menu;
    ClipData.Item item;
    MenuItem menuFind,menuItem;
    EditText editTextFind;
    //add nagigation
    DrawerLayout drawerLayoutMain;
    NavigationView navigationViewMain;
    NavigationView navigationViewControl;
    ListView listViewNavigation;
    ListView listViewNavigationControl;
   ArrayList<classTag> classTaglistcontrol;
    ArrayList<classTag> classTaglist;
    TagAlertAdapter tagAlertAdapter;
    TagAlertAdapter tagAlertAdaptercontrol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initilize();
        Mapping ();
        DrawToolbar();
        SetEventOnclick();
        viewRecyclerviewMain();
        viewListview();
    }

    private void viewListview() {
        classTaglistcontrol= new ArrayList<>();
        classTaglist= new ArrayList<>();
        classTaglistcontrol.add(new classTag("0","All notes"));
        classTaglistcontrol.add(new classTag("1","Favorite notes"));
        classTaglistcontrol.add(new classTag("2","Vocabulary all"));
        tagAlertAdaptercontrol= new TagAlertAdapter(MainActivity.this,R.layout.layout_item_navigation,classTaglistcontrol, enum_viewlist.viewtagnavigationcontrol);
        listViewNavigationControl.setDividerHeight(0);
        listViewNavigationControl.setAdapter(tagAlertAdaptercontrol);
        classTaglist.add(new classTag("-1","No Tag"));
        classTaglist.addAll(dbManager.GetAllTag());
        tagAlertAdapter= new TagAlertAdapter(MainActivity.this,R.layout.layout_item_navigation,classTaglist, enum_viewlist.viewtagnavigation);
        listViewNavigation.setAdapter(tagAlertAdapter);
        tagAlertAdaptercontrol.SetOnItemListenerTag(new OnlistenerTags() {
            @Override
            public void Onclicklongtag(View view, int position) {

            }

            @Override
            public void Onclicktag(View view, int position) {

            }

            @Override
            public void Onclickshorttag(View view, int position) {
                switch (position)
                {
                    case 0:
                        arrayList.clear();
                        arrayList.addAll(dbManager.GetAllNote());
                        if (arrayList!=null)
                        {
                            textViewSumnotes.setText(arrayList.size() + " Notes");
                        }
                        else { textViewSumnotes.setText("0 Notes");}
                        noteMainAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        arrayList.clear();
                        arrayList.addAll(dbManager.GetAllNote_Favorite());
                        if (arrayList!=null)
                        {
                            textViewSumnotes.setText(arrayList.size() + " Notes");
                        }
                        else { textViewSumnotes.setText("0 Notes");}
                        noteMainAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        Intent intent =new Intent(MainActivity.this,MainVocabulary.class);
                        intent.putExtra(FULL_VOCABULARY,"yes");
                        startActivity(intent);

                        break;
                }
                drawerLayoutMain.closeDrawer(Gravity.LEFT);
            }
        });
        tagAlertAdapter.SetOnItemListenerTag(new OnlistenerTags() {
            @Override
            public void Onclicklongtag(View view, int position) {

            }

            @Override
            public void Onclicktag(View view, int position) {

            }

            @Override
            public void Onclickshorttag(View view, int position) {
                arrayList.clear();
                arrayList.addAll(dbManager.GetAllNote_byTagID(classTaglist.get(position).getId()));
                if (arrayList!=null)
                {
                    textViewSumnotes.setText(arrayList.size() + " Notes");
                }
                else { textViewSumnotes.setText("0 Notes");}
                noteMainAdapter.notifyDataSetChanged();
                drawerLayoutMain.closeDrawer(Gravity.LEFT);
            }
        });
    }

    private void initilize() {
        dbManager= new DBManager(this);
        builder = new AlertDialog.Builder(MainActivity.this);
    }

    protected  void Mapping ()
    {
        fabAdd = findViewById(R.id.fba_main);
        drawerLayoutMain= findViewById(R.id.drawerLayout_navigation);
        navigationViewMain= findViewById(R.id.navigation_Main);
        toolbarMain= findViewById(R.id.toolbar_main);
        recyclerViewMain= findViewById(R.id.recyclerview_Main);
        textViewSumnotes= findViewById(R.id.textview_sumnotes);
        textViewTagNotes= findViewById(R.id.textview_tagnote);
        editTextFind= findViewById(R.id.edit_Find);
        listViewNavigation= findViewById(R.id.listview_navigation);
        listViewNavigationControl=findViewById(R.id.listviewcontrol_navigation);

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
                break;
            case R.id.menu_find :
             //   Toast.makeText(this, "FIND", Toast.LENGTH_SHORT).show();
                editTextFind.setVisibility(View.VISIBLE);
                editTextFind.requestFocus();
                if (editTextFind.getText().toString().length()==0)
                {
                    return true;
                }
                arrayList.clear();
                arrayList.addAll(dbManager.GetAllNote_Search(editTextFind.getText().toString()));
                if (arrayList!=null)
                {
                    textViewSumnotes.setText(arrayList.size() + " Notes");
                }
                else { textViewSumnotes.setText("0 Notes");}
                noteMainAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_refresh:
                arrayList.clear();
                arrayList.addAll(dbManager.GetAllNote());
                if (arrayList!=null)
                {
                    textViewSumnotes.setText(arrayList.size() + " Notes");
                }
                else { textViewSumnotes.setText("0 Notes");}
                noteMainAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void SetEventOnclick()
    {
        fabAdd.setOnClickListener(this);
//         navigationViewMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//             @Override
//             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                 switch (item.getItemId())
//                 {
//                     case R.id.menuitem_allnotes:
//                         arrayList.clear();
//                         arrayList.addAll(dbManager.GetAllNote());
//                         if (arrayList!=null)
//                         {
//                             textViewSumnotes.setText(arrayList.size() + " Notes");
//                         }
//                         else { textViewSumnotes.setText("0 Notes");}
//                         noteMainAdapter.notifyDataSetChanged();
//                         break;
//                     case R.id.menuitem_favoritenotes:
//                         arrayList.clear();
//                         arrayList.addAll(dbManager.GetAllNote_Favorite());
//                         if (arrayList!=null)
//                         {
//                             textViewSumnotes.setText(arrayList.size() + " Notes");
//                         }
//                         else { textViewSumnotes.setText("0 Notes");}
//                         noteMainAdapter.notifyDataSetChanged();
//                         break;
//                     case R.id.menuitem_vocabulary:
//                          Intent intent =new Intent(MainActivity.this,MainVocabulary.class);
//                          intent.putExtra(FULL_VOCABULARY,"yes");
//                          startActivity(intent);
//
//                         break;
//                 }
//                 drawerLayoutMain.closeDrawer(Gravity.LEFT);
//                 return true;
//             }
//         });
    }

    private void DrawToolbar()
    {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarMain.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classTaglist.clear();
                classTaglist.add(new classTag("-1","No Tag"));
                classTaglist.addAll(dbManager.GetAllTag());
                tagAlertAdapter.notifyDataSetChanged();
                drawerLayoutMain.openDrawer(Gravity.LEFT);
            }
        });
        navigationViewMain.setItemIconTintList(null);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fba_main:
                Intent intent= new Intent(MainActivity.this, MainAddItem.class);
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
        ((NoteMainAdapter) Objects.requireNonNull(recyclerViewMain.getAdapter())).SetOnItemListenerNoteMain(new OnlistenerNotes() {
            @Override
            public void OnItemClickNotes(View view, int position) {
                Intent intent= new Intent(MainActivity.this, MainAddItem.class);
                intent.putExtra(ITEM_SELECT,arrayList.get(position));
                startActivity(intent);
            }

            @Override
            public void OnItemLongClickNotes(View view, final int position) {
                builder.setTitle("Do you want delete item ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbManager.Delete(arrayList.get(position).getmID());
                                dbManager.DeleteVocabularyByNotes(arrayList.get(position).getmID());
                                arrayList.remove(position);
                                if (arrayList!=null)
                                {
                                    textViewSumnotes.setText(arrayList.size() + " Notes");
                                }
                                else { textViewSumnotes.setText("0 Notes");}
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
