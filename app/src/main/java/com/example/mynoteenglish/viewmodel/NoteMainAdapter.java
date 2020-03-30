package com.example.mynoteenglish.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            holder.txtName.setText(classNoteMain.getName());
            holder.txtDate.setText(classNoteMain.getDateCreate());
            holder.txtContent.setText(classNoteMain.getContent());
    }

    @Override
    public int getItemCount() {
        return arrayList!=null? arrayList.size():0;
    }

    public  class  NoteMainHolder extends  RecyclerView.ViewHolder
    {
        TextView txtName,txtDate,txtContent;
        public NoteMainHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.idtxtName);
            txtDate= itemView.findViewById(R.id.idtxtDate);
            txtContent= itemView.findViewById(R.id.idtxtContent);

        }
    }
}
