package com.example.matt_rios_scheduler.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.matt_rios_scheduler.DAO.AssessmentDAO;
import com.example.matt_rios_scheduler.DAO.ClassDAO;
import com.example.matt_rios_scheduler.DAO.TermDAO;
import com.example.matt_rios_scheduler.Entity.AssessmentEntity;
import com.example.matt_rios_scheduler.Entity.ClassEntity;
import com.example.matt_rios_scheduler.Entity.TermEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, ClassEntity.class, AssessmentEntity.class}, version = 3)
public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract ClassDAO classDAO();
    public abstract AssessmentDAO assessmentDAO();
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile SchedulerDatabase INSTANCE;

    static SchedulerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchedulerDatabase.class, "scheduler_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            /* The below code is for testing purposes only

            databaseWriteExecutor.execute(() -> {

                TermDAO mTermDao = INSTANCE.termDAO();
                ClassDAO mClassDao = INSTANCE.classDAO();
                AssessmentDAO mAssessmentDAO = INSTANCE.assessmentDAO();

                mTermDao.deleteAllTerms();
                mClassDao.deleteAllClasses();
                mAssessmentDAO.deleteAllAssessments();

                TermEntity term = new TermEntity(1, "Term 1",
                    "2022-06-01", "2022-07-01");
                mTermDao.insert(term);
                term = new TermEntity(2, "Term 2",
                    "2022-07-01", "2022-08-01");
                mTermDao.insert(term);
                term = new TermEntity(3, "Term 3",
                        "2022-08-01", "2022-09-01");
                mTermDao.insert(term);

                ClassEntity classEntity = new ClassEntity(1, "Databases",
                        "2022-07-05","2022-07-22",
                        "registered", "TeacherName", "555-555-5555",
                        "instructor@email.com", "Optional note", 2);
                mClassDao.insert(classEntity);
                classEntity = new ClassEntity(2, "Algorithms",
                        "2022-08-12", "2022-08-17",
                        "registered", "TeacherName", "555-555-5555",
                        "instructor@email.com", "Optional note", 3);
                mClassDao.insert(classEntity);
                classEntity = new ClassEntity(3, "Java",
                        "2022-06-01", "2022-06-22",
                        "in progress", "TeacherName", "555-555-5555",
                        "instructor@email.com", null, 1);
                mClassDao.insert(classEntity);

                AssessmentEntity assessmentEntity = new AssessmentEntity(1,
                        "Assessment 1", "2022-06-22", 3);
                mAssessmentDAO.insert(assessmentEntity);
                assessmentEntity = new AssessmentEntity(2,
                        "Assessment 2", "2022-07-22", 1);
                mAssessmentDAO.insert(assessmentEntity);
                assessmentEntity = new AssessmentEntity(3,
                        "Assessment 3", "2022-08-17", 2);
                mAssessmentDAO.insert(assessmentEntity);
            }); */
        }
    };
}
