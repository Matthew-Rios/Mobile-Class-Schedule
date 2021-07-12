package com.example.matt_rios_scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.matt_rios_scheduler.Entity.AssessmentEntity;
import com.example.matt_rios_scheduler.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private final TextView assessmentItemView2;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);
            assessmentItemView2 = itemView.findViewById(R.id.assessmentTextView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("classID", current.getClassID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessment;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if (mAssessment != null) {
            AssessmentEntity current = mAssessment.get(position);
            holder.assessmentItemView.setText(current.getAssessmentTitle());
            holder.assessmentItemView2.setText(Integer.toString(current.getClassID()));
        } else {
            holder.assessmentItemView.setText("None");
            holder.assessmentItemView2.setText("None");
        }
    }

    public void setAssessments(List<AssessmentEntity> assessment) {
        mAssessment = assessment;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessment != null) {
            return mAssessment.size();
        } else {
            return 0;
        }
    }
}
