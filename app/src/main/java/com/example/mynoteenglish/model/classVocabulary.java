package com.example.mynoteenglish.model;

public class classVocabulary {
    String id;
    String idnote;
    String name;
    String content;

    public classVocabulary(String id, String idnote, String name, String content) {
        this.id = id;
        this.idnote = idnote;
        this.name = name;
        this.content = content;
    }

    public classVocabulary(String idnote, String name, String content) {
        this.idnote = idnote;
        this.name = name;
        this.content = content;
    }

    public classVocabulary() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdnote() {
        return idnote;
    }

    public void setIdnote(String idnote) {
        this.idnote = idnote;
    }
}
