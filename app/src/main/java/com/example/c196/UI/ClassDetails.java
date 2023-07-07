package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.c196.R;
import com.example.c196.database.Repo;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Classes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClassDetails extends AppCompatActivity {

EditText editName;
String className;
String classStartDate;
String classEndDate;
String classStatus;
String instructorName;
String instructorPhone;
String instructorEmail;
EditText classNotesEdit;
String classNotes;

int classID;
int termID;
Classes classes;
Repo repo;
EditText instructorNameEdit;
EditText instructorEmailEdit;
EditText instructorPhoneEdit;
DatePickerDialog.OnDateSetListener classDateStart;
DatePickerDialog.OnDateSetListener classDateEnd;
final Calendar currentCalendar = Calendar.getInstance();
final Calendar currentCalendar2 = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        RecyclerView recyclerView = findViewById(R.id.termrecyclerview);
        repo = new Repo(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button saveButton = findViewById(R.id.classSaveButton);
        Button startDateButton = findViewById(R.id.classStartButton);
        Button endDateButton = findViewById(R.id.classEndButton);

        editName = findViewById(R.id.classNameEditText);
        className = getIntent().getStringExtra("classTitle");
        editName.setText(className);
        instructorNameEdit = findViewById(R.id.instructorNameEditText);
        instructorName = getIntent().getStringExtra("classProfName");
        instructorNameEdit.setText(instructorName);
        instructorEmailEdit = findViewById(R.id.instructorEmailEditText);
        instructorEmail = getIntent().getStringExtra("classProfEmail");
        instructorEmailEdit.setText(instructorEmail);
        instructorPhoneEdit = findViewById(R.id.instructorPhoneEditText);
        instructorPhone = getIntent().getStringExtra("classProfPhone");
        instructorPhoneEdit.setText(instructorPhone);
        classNotesEdit = findViewById(R.id.notesEditText);
        classNotes = getIntent().getStringExtra("notes");
        classNotesEdit.setText(classNotes);
        classStartDate = getIntent().getStringExtra("classStart");
        classEndDate = getIntent().getStringExtra("classEnd");
        classStatus = getIntent().getStringExtra("classStatus");

        classID = getIntent().getIntExtra("classID", -1);
        termID = getIntent().getIntExtra("termID", -1);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton4);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassDetails.this, AssessmentsDetails.class);
                intent.putExtra("classID", classID);
                startActivity(intent);
            }
        });

        repo = new Repo(getApplication());

        startDateButton.setText(classStartDate);
        endDateButton.setText(classEndDate);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat newDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        String currentDaysDate = newDateFormat.format(new Date());

        List<Assessments> associatedAssessments = new ArrayList<>();
        for (Assessments assessments : repo.getAllAssessments()) {
            if (assessments.getCourseID() == classID) {
                associatedAssessments.add(assessments);
            }
        }

        assessmentAdapter.setAssessments(associatedAssessments);


         if (classID < 0){
             startDateButton.setText(currentDaysDate);
             endDateButton.setText(currentDaysDate);
         }
         else{
             startDateButton.setText(classStartDate);
             endDateButton.setText(classEndDate);
         }

         startDateButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Date date;
                 String stringFromButton = startDateButton.getText().toString();
                 try{
                     currentCalendar.setTime(newDateFormat.parse(stringFromButton));
                 }
                 catch (ParseException e){
                     throw new RuntimeException(e);
                 }
                 new DatePickerDialog(ClassDetails.this, classDateStart, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show();
             }
         });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String stringFromButton = endDateButton.getText().toString();
                try{
                    currentCalendar2.setTime(newDateFormat.parse(stringFromButton));
                }
                catch (ParseException e){
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(ClassDetails.this, classDateEnd, currentCalendar2.get(Calendar.YEAR), currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        classDateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentCalendar.set(Calendar.YEAR, year);
                currentCalendar.set(Calendar.MONTH, month);
                currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDateButton.setText(newDateFormat.format(currentCalendar.getTime()));
            }
        };

        classDateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentCalendar2.set(Calendar.YEAR, year);
                currentCalendar2.set(Calendar.MONTH, month);
                currentCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDateButton.setText(newDateFormat.format(currentCalendar2.getTime()));

            }
        };

        Spinner progressSpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> progressArray=ArrayAdapter.createFromResource(this,R.array.progress_list,android.R.layout.simple_spinner_item);
        progressArray.setDropDownViewResource(android.R.layout.simple_spinner_item);
        progressSpinner.setAdapter(progressArray);
        if(classStatus == null){
            progressSpinner.setSelection(1);
        }
        else if(classStatus.equals("In Progress")){
            progressSpinner.setSelection(0);
        }
        else if(classStatus.equals("Dropped")){
            progressSpinner.setSelection(2);
        }
        else if(classStatus.equals("Plan to Take")){
            progressSpinner.setSelection(3);
        }
        else if(classStatus.equals("Completed")){
            progressSpinner.setSelection(1);
        }

        else{
            return;
        }



         saveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 classStartDate = startDateButton.getText().toString();
                 classEndDate = endDateButton.getText().toString();
                 classStatus = progressSpinner.getSelectedItem().toString();
                 if (classID < 0){
                     classes = new Classes(0, editName.getText().toString(), classStartDate, classEndDate,  classStatus, instructorNameEdit.getText().toString(), instructorPhoneEdit.getText().toString(), instructorEmailEdit.getText().toString(), termID, classNotesEdit.getText().toString());
                     repo.insert(classes);
                 }
                 else{
                     classes = new Classes(classID,editName.getText().toString(), classStartDate, classEndDate, classStatus, instructorNameEdit.getText().toString(), instructorPhoneEdit.getText().toString(), instructorEmailEdit.getText().toString(), termID, classNotesEdit.getText().toString());
                     repo.update(classes);
                 }
             }
         });

    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerViewOnResume = findViewById(R.id.termrecyclerview);
        final AssessmentAdapter assessmentAdapterOnResume = new AssessmentAdapter(this);
        recyclerViewOnResume.setAdapter(assessmentAdapterOnResume);
        recyclerViewOnResume.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> assessmentsListOnResume = new ArrayList<>();
        for (Assessments assessments : repo.getAllAssessments()) {
            if (assessments.getCourseID() == classID) {
                assessmentsListOnResume.add(assessments);
            }
        }
        assessmentAdapterOnResume.setAssessments(assessmentsListOnResume);
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_class_details, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if (itemId == R.id.classShareMenu) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, classNotesEdit.getText().toString());
            intent.setType("text/plain");
            Intent intent2 = Intent.createChooser(intent, null);
            startActivity(intent2);
            return true;
        } else if(itemId == R.id.classDeleteMenu){
            for (Classes classes : repo.getAllClasses()){
                if (classes.getClassID() == classID){
                    Classes currentClass = classes;
                    repo.delete(currentClass);
                }
            }
            return true;
        }
        else if(itemId == R.id.classStartMenu){
            String formatForDate = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatForDate, Locale.US);
            Date date = null;
            try {
                date = sdf.parse(classStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = date.getTime();
            Intent intent = new Intent(ClassDetails.this, NotificationReceiver.class);
            intent.putExtra("key", className + " begins today");
            PendingIntent sender = PendingIntent.getBroadcast(ClassDetails.this, ++MainActivity.numberOfAlerts, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }  else if(itemId == R.id.classEndMenu){
            String formatForDate = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatForDate, Locale.US);
            Date date = null;
            try {
                date = sdf.parse(classEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = date.getTime();
            Intent intent = new Intent(ClassDetails.this, NotificationReceiver.class);
            intent.putExtra("key", className + " ends today");
            PendingIntent sender = PendingIntent.getBroadcast(ClassDetails.this, ++MainActivity.numberOfAlerts, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}