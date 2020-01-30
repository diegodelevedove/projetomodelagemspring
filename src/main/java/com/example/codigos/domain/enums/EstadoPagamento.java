/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain.enums;

/**
 *
 * @author Diego
 */
public enum EstadoPagamento {
    
    PENDENTE(1,"PENDENTE"),
    QUITADO(2, "QUITADO"),
    CANCELADO(3, "CANCELADO");
    
    
    private int cod; // o numero inteiro da declaração acima
    private String descricao; // a string da declaração acima
    
    
    //é sempre necessário um construtor
    private EstadoPagamento(int cod,String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }
    
    public int getCod(){
        return cod;
    }
    
    public String getDescricao(){
        return descricao;
    }
    
    
    //Implementando um método pra testar nossas enumerações
    public static EstadoPagamento toEnum(Integer cod){
       if (cod == null){
           return null;
       } 
       
       //Varrer os valores do cliente e comparar
       for(EstadoPagamento x : EstadoPagamento.values()){
           if(cod.equals(x.getCod())){
           return x;
           }
           
           
       }
        
       throw new IllegalArgumentException("Id inválido: " + cod);
    }
    
    
    
}
