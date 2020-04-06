package com.example.mynoteenglish.model;

public class classTag {
    private String id;
    private String tagname;
    public classTag(String id, String tagname) {
        this.id = id;
        this.tagname = tagname;
    }

    public classTag(String tagname) {
        this.tagname = tagname;
    }

    public classTag() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}
