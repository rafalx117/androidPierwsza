package com.example.student.pierwsza;

public class GradeModel
{
    private int grade;
    private String name;

    public GradeModel(String name, int grade)
    {
        this.name = name;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
