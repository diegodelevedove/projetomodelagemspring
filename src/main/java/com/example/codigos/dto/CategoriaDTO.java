/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.dto;

import com.example.codigos.domain.Categoria;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Diego
 * 
 * O que é o DTO 
 * 
 * DTO = Data Transfer object
 * Agrupar um certo numero de atributos pra facilitar a comunicação
 * é uma classe responsável por só retornar os dados necessários em um conjunto de dados maior 
 */
public class CategoriaDTO implements Serializable  {
    private static final long serialVersionUID = 1l;
    
        
    private Integer id;
    
    //Validações
    //Vamos usar algumas anotations para validar campos na camada de serviço 
    // Porém para os campos que eu quero validar as anotations vão na classe DTO
    
    
    @NotEmpty(message = "Preenchimento Obrigatório")
    @Length(min=5,max=80,message="O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;
    
    public CategoriaDTO(){
        
    }
    
    // Como usamos uma lista lá no resource ele pede que eu defina uma forma de retornar os atributos lá
    //Então criamos um construtor da categoria aqui - Pode haver outro tipo de implementação
    public CategoriaDTO(Categoria obj){
        id = obj.getId();
        nome = obj.getNome();
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
