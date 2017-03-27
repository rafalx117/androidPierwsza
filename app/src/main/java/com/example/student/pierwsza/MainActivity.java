package com.example.student.pierwsza;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static int gradesCount = 0;
    private Button mainButton;
    private float gradesAverage;
    boolean wrongNumber; //zmienna, która mówi czy dane do pola gradesEditText zostały wprowadzone poprawnie
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this; //kontekst będzie potrzebny do metody changeMainButtonProperties do wyświetlenia komunikatu za pomocą makeToast
        mainButton = (Button) findViewById(R.id.mainButton);
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        final EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        final EditText gradesEditText = (EditText) findViewById(R.id.gradesEditText);

        //------------------------ odbieramy dane z aktywności DisplayMessage ---------------------------
        if (User.isInitialized()) //jeśli użytkownik zostal zapisany w aktywnosci DisplayMeessage - zostaje on wyświetlony
        {
            nameEditText.setText(User.getName().toString());
            surnameEditText.setText(User.getSurname().toString());
            gradesEditText.setText(String.valueOf(User.getGradesCount()));
        } else //w przeciwnym wypadku pola formularza są czysczone
        {
//            Toast toast = Toast.makeText(this, "Witaj!", Toast.LENGTH_LONG);
//            toast.show();
            nameEditText.setText("");
            surnameEditText.setText("");
            gradesEditText.setText("");
        }

        try //próbujemy odczytać średnią przekazaną z aktywności DisplayMessageActivity
        {
            Intent intent = getIntent();
            gradesAverage = intent.getFloatExtra("gradesAverage", 1); //1 to wartość domyślna średniej ocen
            TextView gradesAvg = (TextView) findViewById(R.id.averageTextView);
            gradesAvg.setText("Średnia wynosi: " + String.valueOf(gradesAverage));

            if (gradesAverage == 1) //domyślna (pierwotna) wartość średniej wynosi 1 - wówczas chowamy labelke
            {
                gradesAvg.setVisibility(View.INVISIBLE);
            }
            else if (gradesAverage < 3) //jeśli średnia nie wynosi 1 - oznacza to, że średnia została ustawiona w aktywności DisplayMessage
            {//wówczas zmieniamy kolor labelki ze średnią na zielony, ustawiamy ja jako widoczną oraz zmieniamy właściwości przycisku
                gradesAvg.setVisibility(View.VISIBLE);
                gradesAvg.setTextColor(Color.RED);
                changeMainButtonProperties("Ups!"); //metoda odpowiadająca za zmianę właściwości przycisku na stronie głównej (pod formularzem)

            } else//jeśli średnia >= 3, zmieniamy kolor labelki na czerwon, ustawiamy jej widocznośc i zmieniamy właściwości przycisku
            {
                gradesAvg.setVisibility(View.VISIBLE);
                gradesAvg.setTextColor(Color.GREEN);
                changeMainButtonProperties("Super!");

            }
        } catch (Exception ex) //jeśli odczytanie danych nie powiodło się - wyświetlamy odpowiedni komunikat
        {
            Toast toast = Toast.makeText(this,"Aplikacja napotkała na problem podczas odczytywania danych uzytkownika. Uruchom ją ponownie.", Toast.LENGTH_LONG);
            System.out.println(ex.getMessage());
        }
        //------------------ koniec odbierania danych -------------------------------------------------------------------------------




        TextWatcher textWatcher = new TextWatcher() //tworzymy prosty TextWatcher, który będzie sprawdzał podczas wprowadzaniua tekstu, czy należy pokazac przycisk
        {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                setButtonVisibility(); //metoda sprawdzająca czy należy pokazać przycisk
            }

            // -------- poniższe metody są nieużywane, ale musialy zostać wprowadzone ze względu na TextWatcher -----------------
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
            //-------------------------------------------------------------------------------------------------------------------
        };

        nameEditText.addTextChangedListener(textWatcher); /// dodaję TextWatchery do pól name i surname, żeby można było ewentualnie wyświetlić przycisk już przy wprowadzaniu tekstu, a nie dopiero po zmianie focusu
        surnameEditText.addTextChangedListener(textWatcher);

        gradesEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                int grade = 0; //tu będzie przetrzymywana wartość odczytana z kontrolki gradesEditText

                try //próbujemy wyłuskać ilość ocen z pola gradesEditText
                {
                    grade = Integer.parseInt(gradesEditText.getText().toString());
                } catch (NumberFormatException ex) //w przypadku niepowodzenia - wyświetlamy stosowny komunikat
                {
                    numberErrorToast();
                }

                if (grade < 5 || grade > 15) //jeśli udało odczytać się ocenę, ale jest ona sprzeczna z zalożeniami - wyświetlamy odpowiedni komunikat
                {
                    numberErrorToast();
                    wrongNumber = true; //wrongnumber potrzebujemy w metodzie setButtonVisibility
                } else
                {
                    wrongNumber = false;
                }

                setButtonVisibility();
            }

            // -------- poniższe metody są nieużywane, ale musialy zostać wprowadzone ze względu na TextWatcher -----------------
            @Override
            public void afterTextChanged(Editable s)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }
            //-------------------------------------------------------------------------------------------------------------------

        });

        // ustawiam listener na zmiane focusu dla gradesEditText
        gradesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() //ustawiamy listener na zmianę focusu dla gradesEditText
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (gradesEditText.getText().toString().isEmpty()) //jeśli po opuszczeniu pola jest ono puste - wyświetlamy stosowny komunikat
                    {
                        validationEmptyErrorToast();
                    }

                }
                setButtonVisibility();
            }
        });

        //analogicznie ustawiam listener na zmiane focusu dla nameEditText
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

        //analogicznie ustawiam listener na zmiane focusu dla surnameEditText
        surnameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (surnameEditText.getText().toString().isEmpty())
                    {
                        validationEmptyErrorToast();
                    }
                }
                setButtonVisibility();
            }
        });

    }

    public void sendMessage(View view) //metoda wysyłająca dane do drugiej aktywności
    {
        Intent intent = new Intent(this, DisplayMessageActivity.class); //tworzę nową intencję
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        EditText gradesEditText = (EditText) findViewById(R.id.gradesEditText);

        gradesCount = Integer.parseInt(gradesEditText.getText().toString());
        User.setUser(nameEditText.getText().toString(), surnameEditText.getText().toString(), gradesCount); //zapisujemy użytkownika w statycznej klasie USer
        intent.putExtra("gradesCount", gradesCount); //przekazujemy informacje o ilości ocen do drugiej aktywności
        startActivity(intent); //uruchamiamy drugą intencję
    }


    private void setButtonVisibility() //metoda sprawdza, czy wszystkie wymagane pola są wypełnione oraz czy dane są ok, a następnie pokazuje lub chowa przycisk
    {
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        EditText gradesEditText = (EditText) findViewById(R.id.gradesEditText);

        if ( //sprawdzamy, czy pola formularza są wypelnione
                nameEditText.getText().toString().isEmpty() ||
                        surnameEditText.getText().toString().isEmpty() ||
                        gradesEditText.getText().toString().isEmpty() ||
                        wrongNumber //aplikacja ustawi wrongNumber = true jeśli liczba ocen nie nalezy do odpowiedniego przedziału
                )
        {
            mainButton.setVisibility(View.INVISIBLE); //uukrywamy przycisk
        } else
        {
            mainButton.setVisibility(View.VISIBLE); //pokazujemy przycisk
        }

    }


    public void validationEmptyErrorToast() //metoda wyświetlająca komunikat o niewypełnionym polu
    {
        Toast toast = Toast.makeText(this, "Pole nie może być puste", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void numberErrorToast() //metoda wyświetlająca komunikat o nieprawidłowym wypełnieniu gradesEditText
    {
        wrongNumber = true;
        Toast toast = Toast.makeText(this, "Liczba musi być z zakresu 5-15", Toast.LENGTH_SHORT);
        toast.show();
    }

    //metoda odpowiadająca za zmianę właściwości przycisku po powrocie do aktywności MainActivity z aktywności DisplayMessageActivity
    public void changeMainButtonProperties(final String text)
    {
        //zmieniamy tekst, widocznośc i widocznośc przycisku oraz ustawiamy listener reagujący na kliknięcie, które wyświetli komunikat adekwatny do uzyskanej średniej ocen
        mainButton.setText(text);
        mainButton.setVisibility(View.VISIBLE);
        mainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String message;
                if(text.equals("Super!"))
                    message = "Gratulacje!";
                else
                    message = "Wysyłam podanie o poprawkę.";

                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();

                User.setInitialized(false); //uznajemy, że aplikacja kończy działanie, więc 'zapominamy' użytkownika, tzn nie będzie on wczytywany do aplikacji
                finish();//zamykamy okno aktywności

            }
        });
    }

}
