package com.example.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.entities.Classes;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    class ClassViewHolder extends RecyclerView.ViewHolder {

        private final TextView classItemView;
        private ClassViewHolder(View itemView){
            super(itemView);
            classItemView=itemView.findViewById(R.id.classNameTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int classPosition = getAdapterPosition();
                    final Classes currentClass = mClasses.get(classPosition);
                    Intent intent = new Intent(context, ClassDetails.class);
                    intent.putExtra("classID", currentClass.getClassID());
                    intent.putExtra("classTitle", currentClass.getClassTitle());
                    intent.putExtra("classStart", currentClass.getClassStart());
                    intent.putExtra("classEnd", currentClass.getClassEnd());
                    intent.putExtra("classProgress", currentClass.getClassProgress());
                    intent.putExtra("classProfName", currentClass.getClassProfName());
                    intent.putExtra("classProfPhone", currentClass.getClassProfPhone());
                    intent.putExtra("classProfEmail", currentClass.getClassProfEmail());
                    intent.putExtra("notes", currentClass.getClassNotes());
                    intent.putExtra("termID", currentClass.getTermID());
                    intent.putExtra("classStatus", currentClass.getClassProgress());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ClassAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View classItemView = mInflater.inflate(R.layout.class_list_item, parent, false);
        return new ClassViewHolder(classItemView);
    }

    private List<Classes> mClasses;
    private final Context context;
    private final LayoutInflater mInflater;
    public ClassAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ClassViewHolder holder, int position) {

        if(mClasses == null){
            holder.classItemView.setText("There are no classes for this term yet");
        }
        else{
            Classes currentClass = mClasses.get(position);
            String className = currentClass.getClassTitle();
            holder.classItemView.setText(className);
        }

    }

    public void setClasses(List<Classes> classes){
        mClasses = classes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }


}
