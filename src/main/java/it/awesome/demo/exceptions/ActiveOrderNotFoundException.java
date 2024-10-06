package it.awesome.demo.exceptions;

public class ActiveOrderNotFoundException extends RuntimeException{
    public ActiveOrderNotFoundException(String message) {
        super(message);
    }
}
