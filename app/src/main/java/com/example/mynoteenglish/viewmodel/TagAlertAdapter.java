package com.example.mynoteenglish.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.OnlistenerNotes;
import com.example.mynoteenglish.model.OnlistenerTags;
import com.example.mynoteenglish.model.classTag;
import com.example.mynoteenglish.repository.DBManager;
import com.example.mynoteenglish.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TagAlertAdapter extends ArrayAdapter<classTag> {
    private int resoure;
    private ArrayList<classTag> classTags= new ArrayList<>();
    OnlistenerTags onlistenerTags;
    Boolean hide=false;
    MainActivity.enum_viewlist enum_viewlist =MainActivity.enum_viewlist.viewtag;

    private Context context;
    public TagAlertAdapter(@NonNull Context context, int resource, @NonNull ArrayList<classTag> objects, @Nullable MainActivity.enum_viewlist enum_viewlist) {
        super(context, resource, objects);
        this.classTags= objects;
        this.context= context;
        this.resoure= resource;
        this.hide=hide;
        this.enum_viewlist= enum_viewlist;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TagHolder tagHolder;
        if (convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(this.resoure, parent, false);
            tagHolder= new TagHolder();
            if(this.enum_viewlist==MainActivity.enum_viewlist.viewtagnavigation||this.enum_viewlist==MainActivity.enum_viewlist.viewtagnavigationcontrol)
            {
                tagHolder.textViewTagName= convertView.findViewById(R.id.textviewnavigation_name);;
                tagHolder.imageView= convertView.findViewById(R.id.imagenavigation_image);
            }
            else
            {
                tagHolder.textViewTagName= convertView.findViewById(R.id.textviewtag_name);
                tagHolder.buttontagEdit= convertView.findViewById(R.id.buttontag_edit);
                tagHolder.viewtagDevideline= convertView.findViewById(R.id.viewtag_devideline);
                tagHolder.imageView= convertView.findViewById(R.id.imageview_tag);
            }

            convertView.setTag(tagHolder);
        }
        else
        {
            tagHolder= (TagHolder) convertView.getTag();
        }
        tagHolder.textViewTagName.setText(classTags.get(position).getTagname());
        if (enum_viewlist==MainActivity.enum_viewlist.viewtagnavigationcontrol)
        {
            if (position==0)
            {
                tagHolder.imageView.setImageResource(R.drawable.ic_view_list_black_24dp);
            }
            else if(position==1)
            {
                tagHolder.imageView.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
            else if(position==2)
            {
                tagHolder.imageView.setImageResource(R.drawable.ic_checklist);
            }
            else
            {
                tagHolder.imageView.setImageResource(R.drawable.ic_english);
            }
        }
        else
        {
            tagHolder.imageView.setImageResource(R.drawable.ic_english);
        }
        if (enum_viewlist!=MainActivity.enum_viewlist.viewtagnavigation&&enum_viewlist!=MainActivity.enum_viewlist.viewtagnavigationcontrol ) {
            tagHolder.buttontagEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onlistenerTags.Onclicktag(v, position);
                }
            });
        }
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
        if (enum_viewlist==MainActivity.enum_viewlist.viewtagchoose)
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
        ImageView imageView;
    }
    public void SetOnItemListenerTag(OnlistenerTags onlistenerTags)
    {
        this.onlistenerTags= onlistenerTags;
    }
}
