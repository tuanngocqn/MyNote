package com.example.mynoteenglish.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mynoteenglish.model.classNoteMain;
import com.example.mynoteenglish.model.classTag;
import com.example.mynoteenglish.model.classVocabulary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="notes_manager";
    private static final String TABLE_NAME="notes_table";
    private static final String TABLE_NAME_TAG= "tag_table";
    private static final String TABLE_NAME_VOCABULARY= "vocabulary_table";
    private static final String ID="id";
    private static final String IDNOTE="idnote";
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
            NAME +" TEXT UNIQUE)";
    private String SQLQuery_vocabulary= "CREATE TABLE " +TABLE_NAME_VOCABULARY+" ("+
            ID+ " integer primary key, "+
            IDNOTE +" integer, "+
            NAME +" TEXT, "+
            CONTENT +" TEXT)";
    public DBManager(@Nullable Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
        db.execSQL(SQLQuery_tag);
        db.execSQL(SQLQuery_vocabulary);
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
    public boolean addNotes_tag(classTag tag)
     {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, tag.getTagname());
            db.insert(TABLE_NAME_TAG, null, contentValues);
            db.close();
            return  true;
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
    public void UpdateChooseTag(String idnotes,String idTag)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAGNAME,idTag);
        db.update(TABLE_NAME,contentValues,ID+"=?", new String[]{idnotes});
        db.close();
    }
    public  void updateNotesTag(classTag note)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,note.getTagname());
        db.update(TABLE_NAME_TAG,contentValues,ID+"=?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public int GetID(classNoteMain noteMain)
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
    public ArrayList<classNoteMain> GetAllNote_Search(String itemfind) {
        ArrayList<classNoteMain> classNoteMains= new ArrayList<>();
        String selectQuery= "SELECT * FROM "+ TABLE_NAME+ " WHERE "+
                NAME +" LIKE "+ " '%"+itemfind+ "%' OR "+
                CONTENT +" LIKE "+ " '%"+itemfind+ "%'  OR " +
                TAGNAME +" LIKE "+ " '%"+itemfind+ "%'  ";

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
        if (cursor.moveToFirst())
        {
            do {
                classTag classTagg= new classTag();
                classTagg.setTagname(cursor.getString(1));
                classTagg.setId(cursor.getString(0));
                alltag.add(classTagg);
            }
            while (cursor.moveToNext());
        }
        return alltag;
    }
    public String GetNameTagbyID(String id) {
        String nametag= "";
        String selectQuery="SELECT * from "+ TABLE_NAME_TAG + " WHERE "+
                ID + " =?";
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,new String[]{id});
        if (cursor.moveToFirst())
        {
            do {
                nametag=cursor.getString(1);
            }
            while (cursor.moveToNext());
        }
        return nametag ;
    }

    public void Delete(String getmID) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID+" =?",new String[]{getmID});
    }
    public void DeleteTag(String getmID) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_TAG,ID+" =?",new String[]{getmID});
    }
    //////vocabulary
    public void addVocabulary(classVocabulary vocabulary)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDNOTE,vocabulary.getIdnote());
        contentValues.put(NAME,vocabulary.getName());
        contentValues.put(CONTENT,vocabulary.getContent());
        db.insert(TABLE_NAME_VOCABULARY,null,contentValues);
        db.close();
    }
    public void DeleteVocabulary(String getmID) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_VOCABULARY,ID+" =?",new String[]{getmID});
    }
    public  void updateVocabulary(classVocabulary vocabulary)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDNOTE,vocabulary.getIdnote());
        contentValues.put(NAME,vocabulary.getName());
        contentValues.put(CONTENT,vocabulary.getContent());
        db.update(TABLE_NAME_VOCABULARY,contentValues,ID+"=?", new String[]{String.valueOf(vocabulary.getId())});
        db.close();
    }
    public ArrayList<classVocabulary> GetAllVocabulary() {
        ArrayList<classVocabulary> classVocabularies= new ArrayList<>();
        String selectQuery= "SELECT * FROM "+ TABLE_NAME_VOCABULARY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                classVocabulary classNoteMain= new classVocabulary();
                classNoteMain.setId(cursor.getString(0));
                classNoteMain.setIdnote(cursor.getString(1));
                classNoteMain.setName(cursor.getString(2));
                classNoteMain.setContent(cursor.getString(3));
                classVocabularies.add(classNoteMain);
            }
            while (cursor.moveToNext());
        }
        return classVocabularies;
    }
    public ArrayList<classVocabulary> GetAllVocabularyByID(String id) {
        ArrayList<classVocabulary> classVocabularies= new ArrayList<>();
        String selectQuery="SELECT * from "+ TABLE_NAME_VOCABULARY + " WHERE "+
                IDNOTE + " =?";
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,new String[]{id});
        if (cursor.moveToFirst())
        {
            do {
                classVocabulary classNoteMain= new classVocabulary();
                classNoteMain.setId(cursor.getString(0));
                classNoteMain.setIdnote(cursor.getString(1));
                classNoteMain.setName(cursor.getString(2));
                classNoteMain.setContent(cursor.getString(3));
                classVocabularies.add(classNoteMain);
            }
            while (cursor.moveToNext());
        }
        return classVocabularies;
    }

}
