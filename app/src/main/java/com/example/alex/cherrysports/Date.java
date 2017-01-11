package com.example.alex.cherrysports;

/**
 * Created by Alex on 11/01/2017.
 * Classe Date pour le Calendrier
 */

class Date {
    private long id;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return date;
    }
}

