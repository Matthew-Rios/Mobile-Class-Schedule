package com.example.matt_rios_scheduler.Database;

import android.app.Application;

import com.example.matt_rios_scheduler.DAO.AssessmentDAO;
import com.example.matt_rios_scheduler.DAO.ClassDAO;
import com.example.matt_rios_scheduler.DAO.TermDAO;
import com.example.matt_rios_scheduler.Entity.AssessmentEntity;
import com.example.matt_rios_scheduler.Entity.ClassEntity;
import com.example.matt_rios_scheduler.Entity.TermEntity;

import java.util.List;

public class SchedulerRepository {
    private TermDAO mTermDAO;
    private ClassDAO mClassDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<TermEntity> mAllTerms;
    private List<ClassEntity> mAllClasses;
    private List<AssessmentEntity> mAllAssessments;
    private int termID;

    public SchedulerRepository(Application application){
        SchedulerDatabase db = SchedulerDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mClassDAO = db.classDAO();
        mAssessmentDAO = db.assessmentDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<TermEntity> getAllTerms() {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
           mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<ClassEntity> getAllClasses() {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllClasses = mClassDAO.getAllClasses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllClasses;
    }

    public List<AssessmentEntity> getAllAssessments() {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert (TermEntity termEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
           mTermDAO.insert(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert (ClassEntity classEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mClassDAO.insert(classEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert (AssessmentEntity assessmentEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.insert(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (TermEntity termEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()-> {
            mTermDAO.delete(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (ClassEntity classEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()-> {
            mClassDAO.delete(classEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (AssessmentEntity assessmentEntity) {
        SchedulerDatabase.databaseWriteExecutor.execute(()-> {
            mAssessmentDAO.delete(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
