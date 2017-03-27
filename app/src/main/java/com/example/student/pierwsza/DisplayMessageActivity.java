package com.example.student.pierwsza;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DisplayMessageActivity extends AppCompatActivity {

    private ArrayList<GradeModel> gradesList;
    int gradesCount;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);

        gradesCount = (int) intent.getIntExtra("gradesCount", 1);
        textView.setText("Ilośc ocen: " + String.valueOf(gradesCount));

        listView = (ListView) findViewById(R.id.gradesListView);
        gradesList = new ArrayList<GradeModel>();

        if( User.getGradesList() == null) //jeśli lista ocen w kontenerze jest pusta - uzupłniamy randomowymi ocenami
        {
            for(int i = 0; i< gradesCount; i++)
            {
                Random random = new Random();
                int grade = random.nextInt(4)+2; //generujemy ocenę z zakresu 2-5
                System.out.println("OCENA" + i + " : " + grade);
                gradesList.add(new GradeModel("ocena " + (i+1), grade));
            }
        }
        else
            gradesList = User.getGradesList(); //jesli lista jes uzupełniona - ładujemy ją do pamięci

        User.setGradesList(gradesList);
        MyArrayAdapter adapter = new MyArrayAdapter(this, gradesList);
        listView.setAdapter(adapter);

    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        float gradesAverage = 0;
        for(GradeModel g : gradesList)
            gradesAverage+=g.getGrade();

        gradesAverage = gradesAverage/gradesCount;
        intent.putExtra("gradesAverage", gradesAverage);
        startActivity(intent);

        finish();
    }

}
