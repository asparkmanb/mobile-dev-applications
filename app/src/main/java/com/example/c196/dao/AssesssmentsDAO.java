package com.example.c196.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Assessments;
import com.example.c196.entities.Term;

import java.util.List;

@Dao
public interface AssesssmentsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessments assessments);
    @Update
    void update(Assessments assessments);
    @Delete
    void delete(Assessments assessments);
    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessments> getAllAssessments();
}
