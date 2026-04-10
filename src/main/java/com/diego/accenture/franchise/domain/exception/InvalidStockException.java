package com.diego.accenture.franchise.domain.exception;

public class InvalidStockException extends RuntimeException{
    public InvalidStockException(Integer stock){
        super("Invalid stock value: " + stock);
    }
}
