package com.netcracker.edu.tricks.matrix.exceptions;

public class MatrixException extends Exception {
    private String message;

    public MatrixException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
