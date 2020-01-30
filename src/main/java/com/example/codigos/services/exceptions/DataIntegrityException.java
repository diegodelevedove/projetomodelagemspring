/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services.exceptions;

import javassist.SerialVersionUID;

/**
 *
 * @author Diego
 */
public class DataIntegrityException extends RuntimeException{
    
    public static final long SerialVersionUID = 1L;
    
    public DataIntegrityException(String msg){
        super(msg);
    }
    
    public DataIntegrityException(String msg, Throwable cause){
        super(msg,cause);
    }
    
}
