package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.R;
import com.example.c196.database.Repo;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentsDetails extends AppCompatActivity {


    Assessments assessments;
    EditText editTitle;
    String assessmentName;
    String assessmentStartDate;
    String assessmentEndDate;
    String assessmentType;
    DatePickerDialog.OnDateSetListener assessmentDateStart;
    DatePickerDialog.OnDateSetListener assessmentDateEnd;
    final Calendar currentCalendar = Calendar.getInstance();
    final Calendar currentCalendar2 = Calendar.getInstance();
    Repo repo;
    int classID;
    int assessmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_details);
        repo = new Repo(getApplication());
        Button saveButton = findViewById(R.id.assessmentSaveButton);
        Button startDateButton = findViewById(R.id.assessmentStartDateButton);
        Button endDateButton = findViewById(R.id.assessmentEndDateButton);

        editTitle = findViewById(R.id.assessmentTitleEditText);
        assessmentName = getIntent().getStringExtra("assessmentName");
        editTitle.setText(assessmentName);
        assessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        classID = getIntent().getIntExtra("classID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentType = getIntent().getStringExtra("assessmentType");

        repo = new Repo(getApplication());
        startDateButton.setText(assessmentStartDate);
        endDateButton.setText(assessmentEndDate);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat newDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        String currentDaysDate = newDateFormat.format(new Date());

        if ( assessmentID < 0){
            startDateButton.setText(currentDaysDate);
            endDateButton.setText(currentDaysDate);
        }
        else{
            startDateButton.setText(assessmentStartDate);
            endDateButton.setText(assessmentEndDate);
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
                new DatePickerDialog(AssessmentsDetails.this, assessmentDateStart, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(AssessmentsDetails.this, assessmentDateEnd, currentCalendar2.get(Calendar.YEAR), currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assessmentDateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentCalendar.set(Calendar.YEAR, year);
                currentCalendar.set(Calendar.MONTH, month);
                currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDateButton.setText(newDateFormat.format(currentCalendar.getTime()));
            }
        };

        assessmentDateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentCalendar2.set(Calendar.YEAR, year);
                currentCalendar2.set(Calendar.MONTH, month);
                currentCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDateButton.setText(newDateFormat.format(currentCalendar2.getTime()));

            }
        };

        Spinner typeSpinner = (Spinner)findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<CharSequence> typeArray=ArrayAdapter.createFromResource(this,R.array.type_list,android.R.layout.simple_spinner_item);
        typeArray.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(typeArray);

        if(assessmentType == null){
            typeSpinner.setSelection(0);
        }
        else if(assessmentType.equals("Objective")){
            typeSpinner.setSelection(0);
        }
        else if(assessmentType.equals("Performance")){
            typeSpinner.setSelection(1);
        }
        else{
            return;
        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assessmentStartDate = startDateButton.getText().toString();
                assessmentEndDate = endDateButton.getText().toString();
                assessmentType = typeSpinner.getSelectedItem().toString();
                if (assessmentID < 0){
                    assessments = new Assessments(0, editTitle.getText().toString(), assessmentType,  assessmentStartDate, assessmentEndDate, classID);
                    repo.insert(assessments);
                }
                else{
                    assessments = new Assessments(assessmentID,editTitle.getText().toString(), assessmentType, assessmentStartDate, assessmentEndDate, classID);
                    repo.update(assessments);
                }
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if(itemId == R.id.assessmentDeleteMenu){
            for (Assessments assessments : repo.getAllAssessments()){
                if (assessments.getAssessmentID() == assessmentID){
                    Assessments currentAssessment = assessments;
                    repo.delete(currentAssessment);
                }
            }
            return true;
        }
        else if(itemId == R.id.assessmentStartMenu){
            String formatForDate = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatForDate, Locale.US);
            Date date = null;
            try {
                date = sdf.parse(assessmentStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = date.getTime();
            Intent intent = new Intent(AssessmentsDetails.this, NotificationReceiver.class);
            intent.putExtra("key", assessmentName + " begins today");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentsDetails.this, ++MainActivity.numberOfAlerts, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }  else if(itemId == R.id.assessmentEndMenu){
            String formatForDate = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatForDate, Locale.US);
            Date date = null;
            try {
                date = sdf.parse(assessmentEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = date.getTime();
            Intent intent = new Intent(AssessmentsDetails.this, NotificationReceiver.class);
            intent.putExtra("key", assessmentName + " ends today");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentsDetails.this, ++MainActivity.numberOfAlerts, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}