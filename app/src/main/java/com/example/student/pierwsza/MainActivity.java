package com.example.student.pierwsza;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static int gradesCount = 0;
    private Button button;
    private float gradesAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try //próbujemy odczytać średnią z aktywności DisplayMessageActivity
        {
            Intent intent = getIntent();
            gradesAverage = intent.getFloatExtra("gradesAverage", 1);
            TextView gradesAvg = (TextView) findViewById(R.id.averageTextView);
            gradesAvg.setText("Średnia wynosi: " + String.valueOf(gradesAverage));

            if (gradesAverage == 1) //domyślna (pierwotna) wartość średniej wynosi 1 - wówczas chowamy labelke
            {
                gradesAvg.setVisibility(View.INVISIBLE);
            } else if (gradesAverage < 3) //jeśli średnia nie wynosi 1 - oznacza to, że średnia została ustawiona w aktywności DisplayMessage
            {
                gradesAvg.setVisibility(View.VISIBLE);
                gradesAvg.setTextColor(Color.RED);
            } else
            {
                gradesAvg.setVisibility(View.VISIBLE);
                gradesAvg.setTextColor(Color.GREEN);
            }
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }


        button = (Button) findViewById(R.id.button);
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        final EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        final EditText gradesEditText = (EditText) findViewById(R.id.gradesEditText);

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (nameEditText.getText().toString().isEmpty())
                    {
                        validationEmptyErrorToast();
                    }
                }
                setButtonVisibility();
            }
        });


        surnameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (nameEditText.getText().toString().isEmpty())
                    {
                        validationEmptyErrorToast();
                    }
                }
                setButtonVisibility();
            }
        });

        TextWatcher textWatcher = new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                setButtonVisibility();
            }

            // -------- poniższe metody są nieużywane, ale musialy zostać wprowadzone ze względu na TextWatcher -----------------
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void afterTextChanged(Editable editable){}
            //-------------------------------------------------------------------------------------------------------------------
        };

        nameEditText.addTextChangedListener(textWatcher); /// dodaję TextWatchery do pól name i surname, żeby można było ewentualnie wyświetlić przycisk już przy wprowadzaniu tekstu, a nie dopiero po zmianie focusu
        surnameEditText.addTextChangedListener(textWatcher);

        gradesEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                boolean parseFailure = true; //zmienna sterująca, która pozwoli nam wyświetlić ewentualny komunikat o nieudanej próbie rzutowania wartosci pola gradesEditText na liczbe
                int grade = 0;

                try //próbujemy wyłuskać ilość ocen z pola gradesEditText
                {
                    grade = Integer.parseInt(gradesEditText.getText().toString());
                    parseFailure = false;
                } catch (NumberFormatException ex)
                {
                    parseFailure = true;
                }

                if (nameEditText.getText().toString().isEmpty())
                {
                    validationEmptyErrorToast();
                } else if (parseFailure || grade < 5 || grade > 15)
                {
                    numberErrorToast();
                }

                setButtonVisibility();
            }

            // -------- poniższe metody są nieużywane, ale musialy zostać wprowadzone ze względu na TextWatcher -----------------
            @Override
            public void afterTextChanged(Editable s){}
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            //-------------------------------------------------------------------------------------------------------------------

        });


//        gradesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//
//                boolean parseFailure = true;
//                int grade = 0;
//                try
//                {
//                    grade = Integer.parseInt(gradesEditText.getText().toString());
//                    parseFailure = false;
//                }
//                catch (NumberFormatException ex)
//                {
//                    parseFailure = true;
//                }
//
//                if(!hasFocus){
//                    if(nameEditText.getText().toString().isEmpty()) {
//                        validationEmptyErrorToast();
//                        gradesTextFilled = false;
//                    }
//                    else if(parseFailure || grade < 5 || grade > 15  ) {
//                        numberErrorToast();
//                        gradesTextFilled = false;
//                    }
//                    else
//                        gradesTextFilled = true;
//                }
//
//            buttonVisibility();
//            }
//        });


//
//        button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(
//                new View.OnClickListener(){
//                    public void onClick(View v){
////                        Intent intent = new Intent(this, DisplayMessageActivity.class);
////                        startActivity(intent);
//                    }
//                }
//        );


    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.nameEditText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        gradesCount = Integer.parseInt(((EditText) findViewById(R.id.gradesEditText)).getText().toString());
        intent.putExtra("gradesCount", gradesCount);
        startActivity(intent);
    }


    private void setButtonVisibility()
    {
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        EditText gradesEditText = (EditText) findViewById(R.id.gradesEditText);

        if (
                nameEditText.getText().toString().isEmpty() ||
                        surnameEditText.getText().toString().isEmpty() ||
                        gradesEditText.getText().toString().isEmpty())
        {
            button.setVisibility(View.INVISIBLE);
        } else
        {
            button.setVisibility(View.VISIBLE);
        }

    }


    public void validationEmptyErrorToast()
    {
        Toast toast = Toast.makeText(this, "Pole nie może być puste", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void numberErrorToast()
    {
        Toast toast = Toast.makeText(this, "Liczba musi być z zakresu 5-15", Toast.LENGTH_SHORT);
        toast.show();
    }

//    public void clearEditText(View view) {
//        EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
//        EditText surnameEditText = (EditText)findViewById(R.id.surnameEditText);
//        EditText gradesEditText = (EditText)findViewById(R.id.gradesEditText);
//
//        if(nameEditText.getText().toString().equals("Wprowadź imię") && nameEditText.hasFocus())
//            nameEditText.setText("");
//        if(surnameEditText.getText().toString().equals("Wprowadź nazwisko") && surnameEditText.hasFocus())
//            surnameEditText.setText("");
//        if(gradesEditText.getText().toString().equals("Wprowadź ilość ocen") && gradesEditText.hasFocus())
//            gradesEditText.setText("");
//    }
}
