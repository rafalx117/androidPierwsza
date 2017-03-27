package com.example.student.pierwsza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class DisplayMessageActivity extends AppCompatActivity
{
    private ArrayList<GradeModel> gradesList;
    int gradesCount;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent(); //pobieramy intencję, która zainicjowała bieżącą aktywność
        gradesCount = (int) intent.getIntExtra("gradesCount", 1); //pobieramy przekazane dane (ilość ocen); 1 w drugim parametrze to domyślna wartość
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Ilośc ocen: " + String.valueOf(gradesCount)); //ustwiamy tekst w lebelce informacyjnej

        listView = (ListView) findViewById(R.id.gradesListView); //zmienna odpowiadająca wizualnej liście ocen
        gradesList = new ArrayList<GradeModel>(); //lista przechowująca wartosci ocen

        if (User.getGradesList() == null) //jeśli lista ocen w kontenerze jest pusta - uzupłniamy randomowymi ocenami;
        {
            for (int i = 0; i < gradesCount; i++)
            {
                Random random = new Random();
                int grade = random.nextInt(4) + 2; //generujemy ocenę z zakresu 2-5
                System.out.println("OCENA" + i + " : " + grade);
                gradesList.add(new GradeModel("ocena " + (i + 1), grade));
            }
            User.setGradesList(gradesList); //usupelnioną listę ocen zapisujemy w kontenerze w celu późniejszego użycia
        } else
            gradesList = User.getGradesList(); //jesli lista jes uzupełniona - ładujemy ją do pamięci

        MyArrayAdapter adapter = new MyArrayAdapter(this, gradesList); //tworzymy instancję interaktywnego adaptera
        listView.setAdapter(adapter); //bindujemy adapter z widokiem listy

    }

    //metoda przesyłająca dane do aktywności MainActivity (prześlemy średnią ocen)
    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, MainActivity.class); //tworzymy nową intencję
        float gradesAverage = 0;

        for (GradeModel g : gradesList) //obliczamy średnią ocen na podstawie listy gradesList
            gradesAverage += g.getGrade();

        gradesAverage = gradesAverage / gradesCount;
        intent.putExtra("gradesAverage", gradesAverage); //ustawiamy daną do przesłania
        startActivity(intent); //startujemy intencję
        finish(); //zamkyamy bieżącą aktywność
    }

}
