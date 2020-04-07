package com.example.mynoteenglish.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private Context context;
    public TagAlertAdapter(@NonNull Context context, int resource, @NonNull ArrayList<classTag> objects) {
        super(context, resource, objects);
        this.classTags= objects;
        this.context= context;
        this.resoure= resource;
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
            convertView.setTag(tagHolder);
        }
        else
        {
            tagHolder= (TagHolder) convertView.getTag();
        }
        tagHolder.textViewTagName.setText(classTags.get(position).getTagname());
        tagHolder.textViewTagName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onlistenerTags.Onclicklongtag(v,position);
                return true;
            }
        });
        return  convertView;
    }


    public class TagHolder
    {
        TextView textViewTagName;
    }
    public void SetOnItemListenerTag(OnlistenerTags onlistenerTags)
    {
        this.onlistenerTags= onlistenerTags;
    }
}
