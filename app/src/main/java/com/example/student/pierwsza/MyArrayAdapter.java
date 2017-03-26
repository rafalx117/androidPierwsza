package com.example.student.pierwsza;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyArrayAdapter extends ArrayAdapter<GradeModel> {

    private ArrayList<GradeModel> gradesList;
    private Activity context;

    public MyArrayAdapter(Activity context, ArrayList<GradeModel> gradesList) {
        super(context, R.layout.grade, gradesList);
        this.gradesList = gradesList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.grade, null);

            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            radioGroup.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {

                       updateGradesModel(group, checkedId);

                        }
                    }
            );
            radioGroup.setTag(gradesList.get(position));
        }
        else
        {
            view = convertView;
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            radioGroup.setTag(gradesList.get(position));
        }

        TextView label = (TextView) view.findViewById(R.id.gradeLabel);
        label.setText(gradesList.get(position).getName());
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        setGrade(radioGroup, position);
        return view;

     }

    private void setGrade(RadioGroup radioGroup,
                            int rowNumber) {
        switch (gradesList.get(rowNumber).getGrade()) {
            case 2:
                radioGroup.check(R.id.grade2RadioButton);
                break;
            case 3:
                radioGroup.check(R.id.grade3RadioButton);
                break;
            case 4:
                radioGroup.check(R.id.grade4RadioButton);
                break;
            case 5:
                radioGroup.check(R.id.grade5RadioButton);
                break;
        }
    }

    private void updateGradesModel(
            RadioGroup grupaOceny,int idWybranegoButtona) {
        GradeModel element =(GradeModel) grupaOceny.getTag();
        switch (idWybranegoButtona) {
            case R.id.grade2RadioButton:
                element.setGrade(2);
                break;
            case R.id.grade3RadioButton:
                element.setGrade(3);
                break;
            case R.id.grade4RadioButton:
                element.setGrade(4);
                break;
            case R.id.grade5RadioButton:
                element.setGrade(5);
                break;
        }
    }


}