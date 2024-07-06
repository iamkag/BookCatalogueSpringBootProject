package com.crud.pkagioglou.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    private String fieldName;

    private String fieldValue;

    public BookNotFoundException(String fieldName, String fieldValue) {

        super(String.format("Book not found - field %s=[%s]", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {

        return fieldName;
    }

    public String getFieldValue() {

        return fieldValue;
    }
}
