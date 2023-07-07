package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.c196.R;
import com.example.c196.database.Repo;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Classes;
import com.example.c196.entities.Term;

public class MainActivity extends AppCompatActivity {

    public static int numberOfAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        Term term = new Term(1, "First Term", "05/10/23", "10/10/23");
        Term term2 = new Term(2, "Second Term", "01/05/23", "05/22/23");
        Repo repo = new Repo(getApplication());
        repo.insert(term);
        repo.insert(term2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });
    }
}