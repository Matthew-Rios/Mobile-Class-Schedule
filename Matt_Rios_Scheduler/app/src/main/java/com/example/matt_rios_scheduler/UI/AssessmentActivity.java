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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matt_rios_scheduler.Database.SchedulerRepository;
import com.example.matt_rios_scheduler.Entity.AssessmentEntity;
import com.example.matt_rios_scheduler.Entity.ClassEntity;
import com.example.matt_rios_scheduler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    int id;
    static int id2;
    public static int numAlert;
    String classTitle;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String note;
    int termID;
    EditText editClassTitle;
    EditText editStartDate;
    Calendar startCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateListener;
    long startDateLong;
    EditText editEndDate;
    Calendar endCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDateListener;
    long endDateLong;
    EditText editInstructorName;
    EditText editStatus;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editNote;
    ClassEntity currentClass = null;
    public static int numAssessments;
    public static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        currentActivity = this;
        id = getIntent().getIntExtra("classID", -1);
        classTitle = getIntent().getStringExtra("classTitle");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        note = getIntent().getStringExtra("note");
        termID = getIntent().getIntExtra("termID", -1);
        id2 = termID;
        if (id == -1) {
            id = AssessmentDetail.id2;
        }
        schedulerRepository = new SchedulerRepository(getApplication());
        List<ClassEntity> allClasses = schedulerRepository.getAllClasses();

        for(ClassEntity c : allClasses) {
            if(c.getClassID() == id) {
                currentClass = c;
            }
        }
        editClassTitle = findViewById(R.id.classTitle);
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
                new DatePickerDialog(AssessmentActivity.this, startDateListener,
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
                new DatePickerDialog(AssessmentActivity.this, endDateListener,
                        endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editInstructorName = findViewById(R.id.instructorName);
        editStatus = findViewById(R.id.status);
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editNote = findViewById(R.id.note);

        if(currentClass != null) {
            classTitle = currentClass.getClassTitle();
            startDate = currentClass.getStartDate();
            endDate = currentClass.getEndDate();
            status = currentClass.getStatus();
            instructorName = currentClass.getInstructorName();
            instructorPhone = currentClass.getInstructorPhone();
            instructorEmail = currentClass.getInstructorEmail();
            note = currentClass.getNote();
            termID = currentClass.getTermID();
        }

        if (id != -1) {
            editClassTitle.setText(classTitle);
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
            editInstructorName.setText(instructorName);
            editStatus.setText(status);
            editInstructorPhone.setText(instructorPhone);
            editInstructorEmail.setText(instructorEmail);
            editNote.setText(note);
        }

        schedulerRepository = new SchedulerRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.associated_assessments);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity a : schedulerRepository.getAllAssessments()) {
            if(a.getClassID() == id) {
                filteredAssessments.add(a);
            }
        }
        numAssessments = filteredAssessments.size();
        adapter.setAssessments(filteredAssessments);
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

    public void addAssessment(View view) {
        if (numAssessments < 5) {
            Intent intent = new Intent(AssessmentActivity.this, AssessmentDetail.class);
            intent.putExtra("classID", id);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"Can't add more than 5 assessments to a class.",Toast.LENGTH_LONG).show();
        }
    }

    public void addClassFromScreen(View view) {
        ClassEntity c;

        if (termID == -1) {
            Toast.makeText(getApplicationContext(),"There is no selected term to associate " +
                    "with this class.",Toast.LENGTH_LONG).show();
        } else if (id != -1) {
            c = new ClassEntity(id, editClassTitle.getText().toString(),
                    editStartDate.getText().toString(), editEndDate.getText().toString(),
                    editStatus.getText().toString(), editInstructorName.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editInstructorEmail.getText().toString(), editNote.getText().toString(),
                    termID);
            schedulerRepository.insert(c);
            classTitle = editClassTitle.getText().toString();
            note = editNote.getText().toString();
            startDate = editStartDate.getText().toString();
            endDate = editEndDate.getText().toString();
            ClassActivity.currentActivity.recreate();
        } else {
            List<ClassEntity> allClasses = schedulerRepository.getAllClasses();
            if (allClasses.size() == 0) {
                id = 0;
            } else {
                id = allClasses.get(allClasses.size() - 1).getClassID();
            }
            c = new ClassEntity(++id, editClassTitle.getText().toString(),
                    editStartDate.getText().toString(), editEndDate.getText().toString(),
                    editStatus.getText().toString(), editInstructorName.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editInstructorEmail.getText().toString(), editNote.getText().toString(),
                    termID);
            schedulerRepository.insert(c);
            classTitle = editClassTitle.getText().toString();
            note = editNote.getText().toString();
            startDate = editStartDate.getText().toString();
            endDate = editEndDate.getText().toString();
            ClassActivity.currentActivity.recreate();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, note);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for " + classTitle);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (id == R.id.notifyStartDate && startDate != null) {
            Intent intent = new Intent (AssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", classTitle + " is starting today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this,
                    ++numAlert, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startDateLong = startCalendar.getTimeInMillis();

            alarmManager.set(AlarmManager.RTC_WAKEUP, startDateLong, sender);
            return true;
        }

        if (id == R.id.notifyEndDate && endDate != null) {
            Intent intent = new Intent (AssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", classTitle + " is ending today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this,
                    ++numAlert, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            endDateLong = endCalendar.getTimeInMillis();

            alarmManager.set(AlarmManager.RTC_WAKEUP, endDateLong, sender);
            return true;
        }

        if (id == R.id.delete) {
            if(currentClass == null) {
                Toast.makeText(getApplicationContext(),"No term selected to delete",Toast.LENGTH_LONG).show();
            } else if(numAssessments==0) {
                schedulerRepository.delete(currentClass);
                ClassActivity.currentActivity.recreate();
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Can't delete a class with associated assessments",Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
