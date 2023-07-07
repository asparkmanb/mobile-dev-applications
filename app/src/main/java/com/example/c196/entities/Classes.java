package com.example.c196.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classes")
public class Classes {
    @PrimaryKey(autoGenerate = true)
    private int classID;
    private String classTitle;
    private String classStart;
    private String classEnd;
    private String classProgress;
    private String classProfName;
    private String classProfPhone;
    private String classProfEmail;
    private int termID;

    public String getClassNotes() {
        return classNotes;
    }

    public void setClassNotes(String classNotes) {
        this.classNotes = classNotes;
    }

    private String classNotes;

    public Classes(int classID, String classTitle, String classStart, String classEnd, String classProgress, String classProfName, String classProfPhone, String classProfEmail, int termID, String classNotes) {
        this.classID = classID;
        this.classTitle = classTitle;
        this.classStart = classStart;
        this.classEnd = classEnd;
        this.classProgress = classProgress;
        this.classProfName = classProfName;
        this.classProfPhone = classProfPhone;
        this.classProfEmail = classProfEmail;
        this.termID = termID;
        this.classNotes = classNotes;
    }

    public Classes() {
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassStart() {
        return classStart;
    }

    public void setClassStart(String classStart) {
        this.classStart = classStart;
    }

    public String getClassEnd() {
        return classEnd;
    }

    public void setClassEnd(String classEnd) {
        this.classEnd = classEnd;
    }

    public String getClassProgress() {
        return classProgress;
    }

    public void setClassProgress(String classProgress) {
        this.classProgress = classProgress;
    }

    public String getClassProfName() {
        return classProfName;
    }

    public void setClassProfName(String classProfName) {
        this.classProfName = classProfName;
    }

    public String getClassProfPhone() {
        return classProfPhone;
    }

    public void setClassProfPhone(String classProfPhone) {
        this.classProfPhone = classProfPhone;
    }

    public String getClassProfEmail() {
        return classProfEmail;
    }

    public void setClassProfEmail(String classProfEmail) {
        this.classProfEmail = classProfEmail;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
