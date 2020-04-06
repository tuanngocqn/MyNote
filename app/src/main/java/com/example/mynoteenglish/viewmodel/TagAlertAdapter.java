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
import com.example.mynoteenglish.model.classTag;

import java.util.ArrayList;
import java.util.List;

public class TagAlertAdapter extends ArrayAdapter<classTag> {
    private int resoure;
    private ArrayList<classTag> classTags= new ArrayList<>();
    private Context context;
    public TagAlertAdapter(@NonNull Context context, int resource, @NonNull ArrayList<classTag> objects) {
        super(context, resource, objects);
        this.classTags= objects;
        this.context= context;
        this.resoure= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
//        TextView textView= convertView.findViewById(R.id.textviewtag_name);
//        textView.setText(classTags.get(position).getTagname());
//        Log.d("tuanngoc",(position+1)+"");
        return  convertView;
    }


    public class TagHolder
    {
        TextView textViewTagName;
    }
}
