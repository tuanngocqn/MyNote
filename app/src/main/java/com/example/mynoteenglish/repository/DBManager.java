package com.example.mynoteenglish.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.model.classTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="notes_manager";
    private static final String TABLE_NAME="notes_table";
    private static final String TABLE_NAME_TAG= "tag_table";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String CONTENT="content";
    private static final String DATECREATE="datecreate";
    private static final String DATEUPDATE="dateupdate";
    private static final String TAGNAME="tagname";
    private static final String FAVORITE="favorite";
    private static final int VERSION=2;
    private String SQLQuery= "CREATE TABLE " +TABLE_NAME+" ("+
            ID+ " integer primary key, "+
            NAME +" TEXT, "+
            CONTENT +" TEXT, "+
            DATECREATE +" TEXT, "+
            DATEUPDATE +" TEXT, "+
            TAGNAME +" TEXT, "+
            FAVORITE +" TEXT)";

    private String SQLQuery_tag= "CREATE TABLE " +TABLE_NAME_TAG+" ("+
            ID+ " integer primary key, "+
            NAME +" TEXT)";
    public DBManager(@Nullable Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
        db.execSQL(SQLQuery_tag);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addNotes(classNoteMain note)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,note.getmName());
        contentValues.put(CONTENT,note.getmContent());
        contentValues.put(DATECREATE,note.getmDateCreate());
        contentValues.put(DATEUPDATE,note.getmDateUpdate());
        contentValues.put(TAGNAME,note.getmTagName());
        contentValues.put(FAVORITE,note.getmFavorite());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }
    public void addNotes_tag(classTag tag)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,tag.getTagname());
        db.insert(TABLE_NAME_TAG,null,contentValues);
        db.close();
    }
    public  void updateNotes(classNoteMain note)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,note.getmName());
        contentValues.put(CONTENT,note.getmContent());
        contentValues.put(DATECREATE,note.getmDateCreate());
        contentValues.put(DATEUPDATE,note.getmDateUpdate());
        contentValues.put(TAGNAME,note.getmTagName());
        contentValues.put(FAVORITE,note.getmFavorite());
        db.update(TABLE_NAME,contentValues,ID+"=?", new String[]{String.valueOf(note.getmID())});
        db.close();

    }
    public int
    GetID(classNoteMain noteMain)
    {
        int id=-1;
        String selectQuery="SELECT * from "+ TABLE_NAME + " WHERE "+ NAME + " = ?"+" AND "+
                CONTENT + " =? "+" AND "+
                DATECREATE + " =?";
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,new String[]{noteMain.getmName(),noteMain.getmContent(),noteMain.getmDateCreate()});
        if (cursor.moveToFirst())
        {
            do {
                id=cursor.getInt(0);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return  id;
    }

    public ArrayList<classNoteMain> GetAllNote() {
        ArrayList<classNoteMain> classNoteMains= new ArrayList<>();
        String selectQuery= "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                classNoteMain classNoteMain= new classNoteMain();
                classNoteMain.setmID(cursor.getString(0));
                classNoteMain.setmName(cursor.getString(1));
                classNoteMain.setmContent(cursor.getString(2));
                classNoteMain.setmDateCreate(cursor.getString(3));
                classNoteMain.setmDateUpdate(cursor.getString(4));
                classNoteMain.setmTagName(cursor.getString(5));
                classNoteMain.setmFavorite(cursor.getString(6));
                classNoteMains.add(classNoteMain);
             }
            while (cursor.moveToNext());
        }
        return classNoteMains;
    }
    public ArrayList<classTag> GetAllTag() {
        ArrayList<classTag> alltag= new ArrayList<>();
        String selectQuery= "SELECT * FROM "+ TABLE_NAME_TAG;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst())
        {
            do {
                classTag classTagg= new classTag();
                classTagg.setTagname(cursor.getString(1));
                classTagg.setId(i+"");
                alltag.add(classTagg);
                i++;
            }
            while (cursor.moveToNext());
        }
        return alltag;
    }

    public void Delete(String getmID) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID+" =?",new String[]{getmID});
    }
}
