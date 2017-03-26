package com.example.student.pierwsza;

import java.util.ArrayList;

/**
 * Created by rafal on 26.03.2017.
 */

public class User
{
    private static String name = null;
    private static String surname = null;
    private static int gradesCount = -1;
    private static boolean initialized = false;
    private static ArrayList<GradeModel> gradesList = null;

    public static ArrayList<GradeModel> getGradesList()
    {
        return gradesList;
    }

    public static void setGradesList(ArrayList<GradeModel> gradesList)
    {
        User.gradesList = gradesList;
    }

    public static void setUser(String n, String s, int c)
    {
        name = n;
        surname = s;
        gradesCount = c;
        initialized = true;
    }

    public static String getName()
    {
        return name;
    }

    public static String getSurname()
    {
        return surname;
    }

    public static int getGradesCount()
    {
        return gradesCount;
    }

    public static void setInitialized(boolean initialized)
    {
        User.initialized = initialized;
    }

    public static boolean isInitialized()
    {
        return initialized;

    }

    public static String getUser()
    {
        return "\n\n " + getName() + " " + getSurname() + " " + getGradesCount();
    }
}
