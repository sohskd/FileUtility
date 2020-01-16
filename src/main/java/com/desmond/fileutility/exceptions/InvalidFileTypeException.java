package com.desmond.fileutility.exceptions;

import java.io.IOException;

public class InvalidFileTypeException extends IOException {

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
