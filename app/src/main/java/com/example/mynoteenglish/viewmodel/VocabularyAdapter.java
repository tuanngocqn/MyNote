package com.example.mynoteenglish.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerTags;
import com.example.mynoteenglish.model.OnlistenerVocabulary;
import com.example.mynoteenglish.model.classVocabulary;

import java.util.ArrayList;

public class VocabularyAdapter extends ArrayAdapter<classVocabulary> {
    private int resoure;
    private ArrayList<classVocabulary> classVocabularies= new ArrayList<>();
    OnlistenerVocabulary onlistenerVocabulary;
    private Context context;
    public VocabularyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<classVocabulary> objects, @Nullable  boolean hide) {
        super(context, resource, objects);
        this.classVocabularies= objects;
        this.context= context;
        this.resoure= resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TagHolder tagHolder;
        if (convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(this.resoure, parent, false);
            tagHolder= new TagHolder();
            tagHolder.textViewName= convertView.findViewById(R.id.textviewVocabMain_input);
            tagHolder.textViewContent= convertView.findViewById(R.id.textviewVocabMain_inputdetail);
            tagHolder.buttonplay= convertView.findViewById(R.id.buttonvocab_Play);
            convertView.setTag(tagHolder);
        }
        else
        {
            tagHolder= (TagHolder) convertView.getTag();
        }
        tagHolder.textViewName.setText(classVocabularies.get(position).getName());
        tagHolder.textViewContent.setText(classVocabularies.get(position).getContent());
        tagHolder.buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistenerVocabulary.OnItemClickVocabulary(v,position);
            }
        });

//        convertView.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   onlistenerVocabulary.OnItemClickVocabulary(v,position);
//
//               }
//           }
//        );
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                   onlistenerVocabulary.OnItemLongClickVocabulary(v,position);
                return true;
            }
        });
        return  convertView;
    }


    public class TagHolder
    {
        TextView textViewName;
        TextView textViewContent;
        ImageButton buttonplay;
    }
    public void SetOnItemListenerTag(OnlistenerVocabulary onlistenerVocabulary)
    {
        this.onlistenerVocabulary= onlistenerVocabulary;
    }
}
