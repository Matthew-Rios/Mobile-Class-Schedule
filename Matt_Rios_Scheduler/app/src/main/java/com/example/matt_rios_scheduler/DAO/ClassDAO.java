package com.example.matt_rios_scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.matt_rios_scheduler.Entity.ClassEntity;

import java.util.List;

@Dao
public interface ClassDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClassEntity addClass);

    @Delete
    void delete(ClassEntity classToDelete);

    @Query("DELETE FROM class_table")
    void deleteAllClasses();

    @Query ("SELECT * FROM class_table ORDER BY classID ASC")
    List<ClassEntity> getAllClasses();
}
