package com.bank.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardsAlreadyExistsException extends RuntimeException{
    public CardsAlreadyExistsException(String message){
        super(message); // To invoke the parent class constructor.
    }
}
