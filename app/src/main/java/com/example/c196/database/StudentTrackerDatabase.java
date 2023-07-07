package com.example.c196.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.dao.AssesssmentsDAO;
import com.example.c196.dao.ClassesDAO;
import com.example.c196.dao.TermDAO;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Classes;
import com.example.c196.entities.Term;

@Database(entities = {Term.class, Classes.class, Assessments.class}, version = 1, exportSchema = false)
public abstract class StudentTrackerDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract ClassesDAO classesDAO();
    public abstract AssesssmentsDAO assesssmentsDAO();

    private static volatile StudentTrackerDatabase INSTANCE;
    static StudentTrackerDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (StudentTrackerDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentTrackerDatabase.class, "StudentDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
