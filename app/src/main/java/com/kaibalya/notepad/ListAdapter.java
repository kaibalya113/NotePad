package com.kaibalya.notepad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<String> notes;
    private Context context;
    ListListener listListener;
    public ListAdapter(ArrayList<String> notes, Context context,  ListListener listListener){
        this.notes = notes;
        this.context = context;
        this.listListener = listListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        return new ViewHolder(view, listListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = notes.get(position);
        holder.textView.setText(text);
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        //holder.textView.setText(text);
//
//    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;
        ListListener listListener;
        public ViewHolder(@NonNull View itemView,  ListListener listListener) {
            super(itemView);
            this.listListener  = listListener;
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.noteid);
//            textView.setOnClickListener((v) -> {
//                if(listAdapter != null){
//                    int position = getAdapterPosition();
//                }
//            });
            textView.setOnLongClickListener((v) -> {
                if(listListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listListener.deleteData(position);
                    }
                }
                return true;
            });
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, CreateNote.class);
            intent.putExtra("data",textView.getText().toString());
            intent.putExtra("postion", getAdapterPosition());
            context.startActivity(intent);
        }
    }
    public interface ListListener{
        void deleteData(int position);
    }
}
