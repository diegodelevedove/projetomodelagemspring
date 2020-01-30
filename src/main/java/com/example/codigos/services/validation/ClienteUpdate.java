/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services.validation;

/**
 *
 * @author Diego
 * 
 * Nova Regra: Em cliente fizemos a validação por email no momento do insert (Se existir o email = Retorna Erro)
 * Agora faremos com o Update para que valide na atualizaçã de cliente(Tem que buscar o email do banco
 * e ele já existir e for o mesmo - ok 
 * 
 * Para fazer uma Validação personalizada é necessário dois arquivos
 * 1 - Classe personalizada
 * 2 - Validator
 * 
 * 
 * 
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ClienteUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteUpdate {

    String message() default "Erro de validação";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}//Fim da classe fica só isso aqui mesmo
