package com.example.matt_rios_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.matt_rios_scheduler.Database.SchedulerRepository;
import com.example.matt_rios_scheduler.R;

public class TermActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AssessmentDetail.id3 = -1;
        AssessmentActivity.id2 = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        schedulerRepository = new SchedulerRepository(getApplication());
        schedulerRepository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final TermAdapter adapter = new TermAdapter (this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(schedulerRepository.getAllTerms());
    }

    public void openClasses(View view) {
        Intent intent = new Intent(TermActivity.this, ClassActivity.class);
        startActivity(intent);
    }
}