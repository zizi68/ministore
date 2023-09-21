package com.ministore.android.adapter;

public class ResponseTestString {
    String messenge;
    String data;

    public String getMessenge() {
        return messenge;
    }

    public void setMessenge(String messenge) {
        this.messenge = messenge;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResponseTestString() {
    }

    public ResponseTestString(String messenge, String  data) {
        this.messenge = messenge;
        this.data = data;
    }
}
