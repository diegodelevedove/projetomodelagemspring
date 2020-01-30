/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.resources.Exceptions;

import com.example.codigos.services.exceptions.DataIntegrityException;
import com.example.codigos.services.exceptions.ObjectNotFoundException;
import static java.lang.System.err;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Diego
 */
public class ResourceExceptionHandler {
    
    //Para not found o erro padrão é 500
    
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request){
    
        
            StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
    
    
    // Excessão lá do pacote de caterias com relação em produto
    // não posso dar delete na categoria sem antes excluir produto
    //Tem que ser o bad request - Boas práticas 
    //Retorno padroa bad resquest 400
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
    
        
            StandartError err = new StandartError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    
    
    //Criando outra excessão pra melhorar o retorno do erro no endpoint
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
                     
            ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de Validação",System.currentTimeMillis());
            
            
            for(FieldError x :e.getBindingResult().getFieldErrors()){
               err.addError(x.getField(),x.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
            
    }
    
    
    
    
}
