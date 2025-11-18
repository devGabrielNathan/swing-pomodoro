package br.com.exceptions;

import java.util.HashMap;

public class ValidationException extends RuntimeException {

    private final HashMap<String, String> errors;

    public ValidationException(HashMap<String, String> errors) {
        this.errors = errors;
    }
}