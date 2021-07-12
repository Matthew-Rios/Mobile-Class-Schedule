package com.example.matt_rios_scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matt_rios_scheduler.Database.SchedulerRepository;
import com.example.matt_rios_scheduler.Entity.ClassEntity;
import com.example.matt_rios_scheduler.Entity.TermEntity;
import com.example.matt_rios_scheduler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClassActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    public static Activity currentActivity;

    int id;
    String termTitle;
    String startDate;
    String endDate;
    EditText editTermTitle;
    EditText editEndDate;
    Calendar endCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDateListener;
    EditText editStartDate;
    Calendar startCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateListener;
    TermEntity currentTerm = null;
    public static int numClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentActivity = this;
        AssessmentDetail.id2 = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        id = getIntent().getIntExtra("termID",-1);
        if (id == -1) {
            id = AssessmentActivity.id2;
            if (id == -1) {
                id = AssessmentDetail.id3;
            }
        }

        schedulerRepository = new SchedulerRepository(getApplication());
        List<TermEntity> allTerms = schedulerRepository.getAllTerms();

        for(TermEntity t : allTerms) {
            if(t.getTermID() == id) {
                currentTerm = t;
            }
        }

        editTermTitle = findViewById(R.id.title);
        editStartDate = findViewById(R.id.startDate);
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateStartLabel();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ClassActivity.this, startDateListener,
                        startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate = findViewById(R.id.endDate);
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateEndLabel();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ClassActivity.this, endDateListener,
                        endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if(currentTerm != null) {
            termTitle = currentTerm.getTermTitle();
            startDate = currentTerm.getStartDate();
            endDate = currentTerm.getEndDate();
            AssessmentDetail.id3 = currentTerm.getTermID();
        }

        if(id != -1) {
            editTermTitle.setText(termTitle);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
        }

        schedulerRepository = new SchedulerRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.associated_classes);
        final ClassAdapter adapter = new ClassAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ClassEntity> filteredClasses = new ArrayList<>();
        for(ClassEntity c : schedulerRepository.getAllClasses()) {
            if(c.getTermID() == id) {
                filteredClasses.add(c);
            }
        }
        numClasses = filteredClasses.size();
        adapter.setClasses(filteredClasses);
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(endCalendar.getTime()));
    }

    public void addClass(View view) {
        Intent intent = new Intent(ClassActivity.this, AssessmentActivity.class);
        intent.putExtra("termID", id);
        startActivity(intent);
    }

    public void addTermFromScreen(View view) {
        TermEntity t;

        if(id != -1) {
            t = new TermEntity(id, editTermTitle.getText().toString(),
                    editStartDate.getText().toString(), editEndDate.getText().toString());
        } else {
            List<TermEntity> allTerms = schedulerRepository.getAllTerms();
            if (allTerms.size() == 0) {
                id = 0;
            } else {
                id = allTerms.get(allTerms.size() - 1).getTermID();
            }
            t = new TermEntity(++id, editTermTitle.getText().toString(),
                    editStartDate.getText().toString(), editEndDate.getText().toString());
        }
        schedulerRepository.insert(t);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            if(currentTerm == null) {
                Toast.makeText(getApplicationContext(),"No term selected to delete",Toast.LENGTH_LONG).show();
            } else  if(numClasses==0) {
                schedulerRepository.delete(currentTerm);
                finish();
            } else{
                Toast.makeText(getApplicationContext(),"Can't delete a term with associated classes",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}