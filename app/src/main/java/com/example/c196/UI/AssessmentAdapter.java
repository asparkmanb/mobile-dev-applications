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
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Classes;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemview){
            super(itemview);
            assessmentItemView = itemview.findViewById(R.id.assessmentNameTextView);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessments currentAssessment = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentsDetails.class);
                    intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                    intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
                    intent.putExtra("assessmentStartDate", currentAssessment.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", currentAssessment.getAssessmentEndDate());
                    intent.putExtra("assessmentType", currentAssessment.getAssessmentType());
                    intent.putExtra("classID", currentAssessment.getCourseID());
                    intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View assessmentItemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(assessmentItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {

        if(mAssessments == null){
            holder.assessmentItemView.setText("There are no assessments for this class yet");
        }
        else{
            Assessments currentAssessment = mAssessments.get(position);
            String assessmentName = currentAssessment.getAssessmentName();
            holder.assessmentItemView.setText(assessmentName);
        }

    }

    @Override
    public int getItemCount() {
            return mAssessments.size();
    }

    List<Assessments> mAssessments;
    private Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    public void setAssessments(List<Assessments> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

}
