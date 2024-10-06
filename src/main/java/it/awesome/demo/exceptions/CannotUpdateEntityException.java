package it.awesome.demo.exceptions;

public class CannotUpdateEntityException extends RuntimeException{
    public CannotUpdateEntityException(String message) {
        super(message);
    }
}
