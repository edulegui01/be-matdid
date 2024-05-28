package com.app.bematdid.error;

public class SaldoNegativeException extends Exception{
    public SaldoNegativeException(String message) {
        super(message);
    }
}
