package com.example.nada.Exceptions;

public class EntitiesNotFoundException extends RuntimeException {
    public EntitiesNotFoundException(String sms){
        super(sms);
    }
}
