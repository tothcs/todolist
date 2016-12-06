package com.github.tothc.todolist.dal.weather;

public class List
{
    private Weather[] weather;

    private Temp temp;

    public Weather[] getWeather ()
    {
        return weather;
    }

    public void setWeather (Weather[] weather)
    {
        this.weather = weather;
    }

    public Temp getTemp ()
    {
        return temp;
    }

    public void setTemp (Temp temp)
    {
        this.temp = temp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [weather = "+weather+", temp = "+temp+"]";
    }
}