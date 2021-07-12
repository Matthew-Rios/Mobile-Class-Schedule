package com.example.matt_rios_scheduler.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matt_rios_scheduler.Database.SchedulerRepository;
import com.example.matt_rios_scheduler.Entity.AssessmentEntity;
import com.example.matt_rios_scheduler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    public static int numAlert;
    static int id2;
    static int id3;
    int id;
    String assessmentTitle;
    String endDate;
    String assessmentType;
    int classID;
    EditText editAssessmentTitle;
    EditText editEndDate;
    EditText editAssessmentType;
    Calendar endCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDateListener;
    Long endDateLong;
    AssessmentEntity currentAssessment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        id = getIntent().getIntExtra("assessmentID", -1);
        assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        endDate = getIntent().getStringExtra("endDate");
        assessmentType = getIntent().getStringExtra("assessmentType");
        classID = getIntent().getIntExtra("classID", -1);
        id2 = classID;

        schedulerRepository = new SchedulerRepository(getApplication());
        List<AssessmentEntity> allAssessments = schedulerRepository.getAllAssessments();

        for(AssessmentEntity a : allAssessments) {
            if(a.getAssessmentID() == id) {
                currentAssessment = a;
                assessmentType = a.getAssessmentType();
            }
        }

        editAssessmentTitle = findViewById(R.id.assessmentTitle);
        editEndDate = findViewById(R.id.endDate);
        editAssessmentType = findViewById(R.id.assessmentType);
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
                new DatePickerDialog(AssessmentDetail.this, endDateListener,
                        endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if (id != -1) {
            editAssessmentTitle.setText(assessmentTitle);
            editEndDate.setText(endDate);
            editAssessmentType.setText(assessmentType);
        }
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(endCalendar.getTime()));
    }

    public void addAssessmentFromScreen(View view) {
        AssessmentEntity a;

        if (classID == -1) {
            Toast.makeText(getApplicationContext(),"There is no selected class to associate " +
                    "with this assessment.",Toast.LENGTH_LONG).show();
        } else if (id != -1) {
            a = new AssessmentEntity(id, editAssessmentTitle.getText().toString(),
                    editEndDate.getText().toString(), editAssessmentType.getText().toString(),
                    classID);

            assessmentTitle = editAssessmentTitle.getText().toString();
            endDate = editEndDate.getText().toString();
            schedulerRepository.insert(a);
            AssessmentActivity.currentActivity.recreate();
        } else {
            List<AssessmentEntity> allAssessments = schedulerRepository.getAllAssessments();
            if (allAssessments.size() == 0) {
                id = 0;
            } else {
                id = allAssessments.get(allAssessments.size() - 1).getAssessmentID();
            }
            a = new AssessmentEntity(++id, editAssessmentTitle.getText().toString(),
                    editEndDate.getText().toString(), editAssessmentType.getText().toString(),
                    classID);

            assessmentTitle = editAssessmentTitle.getText().toString();
            endDate = editEndDate.getText().toString();
            schedulerRepository.insert(a);
            AssessmentActivity.currentActivity.recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification && endDate != null) {
            Intent intent = new Intent (AssessmentDetail.this, MyReceiver.class);
            intent.putExtra("key",  assessmentTitle + " is scheduled for today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this,
                    ++numAlert, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            endDateLong = endCalendar.getTimeInMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP, endDateLong, sender);
            return true;
        }

        if (id == R.id.delete) {
            if(currentAssessment == null) {
                Toast.makeText(getApplicationContext(),"No term selected to delete",Toast.LENGTH_LONG).show();
            } else {
                schedulerRepository.delete(currentAssessment);
                AssessmentActivity.currentActivity.recreate();
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
