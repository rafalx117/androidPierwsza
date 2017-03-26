package com.example.student.pierwsza;

/**
 * Created by rafal on 26.03.2017.
 */

public class User
{
    private static String name = null;
    private static String surname = null;
    private static int gradesCount = -1;

    public static void setUser(String n, String s, int c)
    {
        name = n;
        surname = s;
        gradesCount = c;
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

    public static String getUser()
    {
        return "\n\n " + getName() + " " + getSurname() + " " + getGradesCount();
    }
}
