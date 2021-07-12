package com.example.matt_rios_scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.matt_rios_scheduler.Entity.ClassEntity;
import com.example.matt_rios_scheduler.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    class ClassViewHolder extends RecyclerView.ViewHolder {
        private final TextView classItemView;
        private final TextView classItemView2;

        private ClassViewHolder(View itemView) {
            super(itemView);
            classItemView = itemView.findViewById(R.id.classTextView);
            classItemView2 = itemView.findViewById(R.id.classTextView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final ClassEntity current = mClass.get(position);
                    Intent intent = new Intent(context, AssessmentActivity.class);
                    intent.putExtra("classTitle", current.getClassTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("classID", current.getClassID());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<ClassEntity> mClass;

    public ClassAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.class_list_item, parent, false);

        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        if (mClass != null) {
            ClassEntity current = mClass.get(position);
            holder.classItemView.setText(current.getClassTitle());
            holder.classItemView2.setText(Integer.toString(current.getTermID()));
        } else {
            holder.classItemView.setText("None");
            holder.classItemView2.setText("None");
        }
    }

    public void setClasses(List<ClassEntity> classes) {
        mClass = classes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mClass != null) {
            return mClass.size();
        } else {
            return 0;
        }
    }
}
