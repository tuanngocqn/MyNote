package com.example.mynoteenglish.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerNotes;
import com.example.mynoteenglish.model.OnlistenerTags;
import com.example.mynoteenglish.model.classTag;
import com.example.mynoteenglish.repository.DBManager;

import java.util.ArrayList;
import java.util.List;

public class TagAlertAdapter extends ArrayAdapter<classTag> {
    private int resoure;
    private ArrayList<classTag> classTags= new ArrayList<>();
    OnlistenerTags onlistenerTags;
    Boolean hide=false;
    private Context context;
    public TagAlertAdapter(@NonNull Context context, int resource, @NonNull ArrayList<classTag> objects,@Nullable  boolean hide) {
        super(context, resource, objects);
        this.classTags= objects;
        this.context= context;
        this.resoure= resource;
        this.hide=hide;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TagHolder tagHolder;
        if (convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_item_tag, parent, false);
            tagHolder= new TagHolder();
            tagHolder.textViewTagName= convertView.findViewById(R.id.textviewtag_name);
            tagHolder.buttontagEdit= convertView.findViewById(R.id.buttontag_edit);
            tagHolder.viewtagDevideline= convertView.findViewById(R.id.viewtag_devideline);
            convertView.setTag(tagHolder);
        }
        else
        {
            tagHolder= (TagHolder) convertView.getTag();
        }
        tagHolder.textViewTagName.setText(classTags.get(position).getTagname());
        tagHolder.buttontagEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistenerTags.Onclicktag(v,position);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onlistenerTags.Onclicklongtag(v,position);
                return true;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onlistenerTags.Onclickshorttag(v,position);

               }
           }
        );
        if (hide)
        {
            tagHolder.buttontagEdit.setVisibility(View.INVISIBLE);
            tagHolder.viewtagDevideline.setVisibility(View.VISIBLE);

        }
        return  convertView;
    }


    public class TagHolder
    {
        TextView textViewTagName;
        Button buttontagEdit;
        View viewtagDevideline;
    }
    public void SetOnItemListenerTag(OnlistenerTags onlistenerTags)
    {
        this.onlistenerTags= onlistenerTags;
    }
}
