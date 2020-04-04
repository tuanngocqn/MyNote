package com.example.mynoteenglish.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynoteenglish.R;
import com.example.mynoteenglish.model.classNoteMain;

import java.util.ArrayList;

public class NoteMainAdapter extends RecyclerView.Adapter<NoteMainAdapter.NoteMainHolder> {
    ArrayList<classNoteMain> arrayList;
    public NoteMainAdapter(ArrayList<classNoteMain> arrayList) {
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public NoteMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_recyclerview_main,null);
        return new NoteMainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteMainHolder holder, final int position) {
            classNoteMain classNoteMain= arrayList.get(position);
            holder.textViewName.setText(classNoteMain.getmName());
            holder.textViewDate.setText(classNoteMain.getmDateCreate());
            holder.textViewContent.setText(classNoteMain.getmContent());
            if (classNoteMain.getmFavorite().equals("false"))
            {
                holder.imagebuttonFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
            else
            {
                holder.imagebuttonFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
    }

    @Override
    public int getItemCount() {
        return arrayList!=null? arrayList.size():0;
    }

    public  class  NoteMainHolder extends  RecyclerView.ViewHolder
    {
        TextView textViewName,textViewDate,textViewContent;
        ImageButton imagebuttonFavorite;
        public NoteMainHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textviewrecy_name);
            textViewDate= itemView.findViewById(R.id.textviewrecy_datecreate);
            textViewContent= itemView.findViewById(R.id.textviewrecy_content);
            imagebuttonFavorite= itemView.findViewById(R.id.imagebutton_Favorite);
        }
    }
}
