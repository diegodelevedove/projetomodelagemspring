/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.resources.Exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 * 
 * Essa classe está relacionada com FieldMassage
 * e com o metodo de validation lá no pacote resourceException handler
 * 
 * 
 */
public class ValidationError extends StandartError{
    private static final long serialVersionUID = 1l;
    
    private List<FieldMessage> errors = new ArrayList<>();
    
    
    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    
    
    // Se ficar o método de setar ele me retorna uma lista completa e não é isso que queremos
    // então vamos pegar um erro por vez usadno um metodo de add 
    /*public void setList(List<FieldMessage> list) {
        this.list = list;
    }
    */
    
    public void addError(String fieldName, String message) {
       errors.add(new FieldMessage(fieldName,message));
    
    }
    
    
    
}
