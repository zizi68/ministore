package com.ministore.android.model;

public class ResponseCastObject<T> {

    String messenge;

    T data;

    public String getMessenge() {
        return messenge;
    }

    public void setMessenge(String messenge) {
        this.messenge = messenge;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseCastObject() {
    }

    public ResponseCastObject(String messenge, T data) {
        this.messenge = messenge;
        this.data = data;
    }
}
