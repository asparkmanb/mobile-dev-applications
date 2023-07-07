package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.database.Repo;
import com.example.c196.entities.Classes;
import com.example.c196.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    EditText editTitle;
    String termTitle;
    int termID;
    Term term;
    Repo repo;
    String termStartDate;
    String termEndDate;
    DatePickerDialog.OnDateSetListener termDates;
    DatePickerDialog.OnDateSetListener termDatesEnd;
    final Calendar currentCalendar = Calendar.getInstance();
    final Calendar currentCalendar2 = Calendar.getInstance();
    Term currentTerm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        RecyclerView recyclerView = findViewById(R.id.classRecyclerView);
        repo = new Repo(getApplication());
        final ClassAdapter classAdapter = new ClassAdapter(this);
        recyclerView.setAdapter(classAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button button = findViewById(R.id.saveTerm);
        Button startDateButton = findViewById(R.id.startDateButton);
        Button endDateButton = findViewById(R.id.endDateButton);

        editTitle = findViewById(R.id.termTitle);
        termTitle = getIntent().getStringExtra("title");
        editTitle.setText(termTitle);
        termID = getIntent().getIntExtra("id", -1);
        repo = new Repo(getApplication());
        termStartDate = getIntent().getStringExtra("start date");
        termEndDate = getIntent().getStringExtra("end date");

        endDateButton.setText(termEndDate);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat newDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        String currentDaysDate = newDateFormat.format(new Date());

        List<Classes> associatedClasses = new ArrayList<>();
        for (Classes classes : repo.getAllClasses()) {
            if (classes.getTermID() == termID) {
                associatedClasses.add(classes);
            }
        }

        classAdapter.setClasses(associatedClasses);

        if (termID < 0) {
            startDateButton.setText(currentDaysDate);
            endDateButton.setText(currentDaysDate);
        } else {
            startDateButton.setText(termStartDate);
            endDateButton.setText(termEndDate);
        }

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String stringFromButton = startDateButton.getText().toString();
                try {
                    currentCalendar.setTime(newDateFormat.parse(stringFromButton));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(TermDetails.this, termDates, currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String stringFromButton = endDateButton.getText().toString();
                try {
                    currentCalendar2.setTime(newDateFormat.parse(stringFromButton));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(TermDetails.this, termDatesEnd, currentCalendar2.get(Calendar.YEAR),
                        currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termDates = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentCalendar.set(Calendar.YEAR, year);
                currentCalendar.set(Calendar.MONTH, month);
                currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDateButton.setText(newDateFormat.format(currentCalendar.getTime()));
            }
        };

        termDatesEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentCalendar2.set(Calendar.YEAR, year);
                currentCalendar2.set(Calendar.MONTH, month);
                currentCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDateButton.setText(newDateFormat.format(currentCalendar2.getTime()));

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termStartDate = startDateButton.getText().toString();
                termEndDate = endDateButton.getText().toString();
                if (termID < 0) {
                    term = new Term(0, editTitle.getText().toString(), termStartDate, termEndDate);
                    repo.insert(term);
                } else {
                    term = new Term(termID, editTitle.getText().toString(), termStartDate, termEndDate);
                    repo.update(term);
                }
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, ClassDetails.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerViewOnResume = findViewById(R.id.classRecyclerView);
        final ClassAdapter classAdapterOnResume = new ClassAdapter(this);
        recyclerViewOnResume.setAdapter(classAdapterOnResume);
        recyclerViewOnResume.setLayoutManager(new LinearLayoutManager(this));
        List<Classes> classesListOnResume = new ArrayList<>();
        for (Classes classes : repo.getAllClasses()) {
            if (classes.getTermID() == termID) {
                classesListOnResume.add(classes);
            }
        }
        classAdapterOnResume.setClasses(classesListOnResume);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if (itemId == R.id.deleteTermMenu) {
            for(Term terms : repo.getAllTerms()){
                if(terms.getTermID() == termID){
                    currentTerm = terms;
                }
            }
            int associatedCourses = 0;
            for(Classes classes : repo.getAllClasses()) {
                if (classes.getTermID() == termID) {
                    associatedCourses += 1;
                }
            }
                if(associatedCourses == 0){
                    repo.delete(currentTerm);
                }
                else{
                    Toast.makeText(TermDetails.this, "You cannot delete a term that has classes in it", Toast.LENGTH_LONG).show();
                }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}