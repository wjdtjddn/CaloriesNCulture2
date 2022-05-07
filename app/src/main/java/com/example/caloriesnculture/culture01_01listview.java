package com.example.caloriesnculture;

public class culture01_01listview {
    String mnameli;
    String mnoli;
    String mreviewli;

    public String getMreviewli() {
        return mreviewli;
    }

    public void setMreviewli(String mreviewli) {
        this.mreviewli = mreviewli;
    }

    public culture01_01listview(String mreviewli) {
        this.mreviewli = mreviewli;
    }

    public culture01_01listview(String mnameli, String mnoli) {
        this.mnameli = mnameli;
        this.mnoli = mnoli;
    }

    public String getMnameli() {
        return mnameli;
    }

    public void setMnameli(String mnameli) {
        this.mnameli = mnameli;
    }

    public String getMnoli() {
        return mnoli;
    }

    public void setMnoli(String mnoli) {
        this.mnoli = mnoli;
    }
}
