package com.github.tothc.todolist.dal.weather;

public class WeatherData
{
    private List[] list;

    public List[] getList ()
    {
        return list;
    }

    public void setList (List[] list)
    {
        this.list = list;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [list = "+list+"]";
    }
}
