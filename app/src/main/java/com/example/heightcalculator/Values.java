package com.example.heightcalculator;

public class Values
{
    //You could add a location value, but this requires too much work for now
    private long id;
    private String value;

    public Values(){}

    public Values(long id, String value)
    {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ID: " + id +"\nHeight: " + value;
    }

    public void setID(long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
