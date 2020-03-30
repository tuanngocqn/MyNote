package com.example.mynoteenglish.model;

import java.io.Serializable;

public class classNoteMain implements Serializable {
    private  String id;
    private  String name;
    private  String dateCreate;
    private  String content;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public classNoteMain(String name, String content, String dateCreate) {
        this.name = name;
        this.dateCreate = dateCreate;
        this.content = content;
    }

    public classNoteMain(String id, String name, String content, String dateCreate) {
        this.id = id;
        this.name = name;
        this.dateCreate = dateCreate;
        this.content = content;
    }

    public classNoteMain() {
    }
}
