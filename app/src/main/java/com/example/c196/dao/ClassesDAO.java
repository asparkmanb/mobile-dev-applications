package com.example.c196.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Classes;
import com.example.c196.entities.Term;

import java.util.List;

@Dao
public interface ClassesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Classes classes);
    @Update
    void update(Classes classes);
    @Delete
    void delete(Classes classes);
    @Query("SELECT * FROM classes ORDER BY classID ASC")
    List<Classes> getAllClasses();

    @Query("SELECT * FROM CLASSES WHERE termID = :termID")
    List<Classes> getAllAssociatadClasses(int termID);
}
