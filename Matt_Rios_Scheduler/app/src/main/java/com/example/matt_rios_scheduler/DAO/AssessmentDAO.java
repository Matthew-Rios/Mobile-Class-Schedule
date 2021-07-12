package com.example.matt_rios_scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.matt_rios_scheduler.Entity.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessment);

    @Query ("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query ("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    List<AssessmentEntity> getAllAssessments();
}
