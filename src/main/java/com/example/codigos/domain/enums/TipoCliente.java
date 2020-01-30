/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain.enums;

/**
 *
 * @author Diego
 * 
 * enum é uma classe que guarda valores fixos de constantes(static final)
 * em java a classe fica public enum NomedAClasse;
 * lEMBRE-SE que para enums quando na hora da gravação é boa prática definir nos valores para que se numa futura
 * adição de valores aqui nessa classe não quebrar no banco
 * 
 * 
 * 
 */



public enum TipoCliente {
    /*
    PESSOAFISICA, - é valido vai gravar uma string
    PESSOAJURIDICA, - é valido vai gravar uma string
    
    PESSOAFISICA(100), - é valido vai gravar e associar 100 a string
    PESSOAJURIDICA(200),- é valido vai gravar e associar 100 a string
    */
    //Enumerando
    PESSOAFISICA(1,"Pessoa Física"),
    PESSOAJURIDICA(2,"Pessoa Júridica");
    
    
    private int cod; // o numero inteiro da declaração acima
    private String descricao; // a string da declaração acima
    
    
    //é sempre necessário um construtor
    private TipoCliente(int cod,String descricao){
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
    public static TipoCliente toEnum(Integer cod){
       if (cod == null){
           return null;
       } 
       
       //Varrer os valores do cliente e comparar
       for(TipoCliente x : TipoCliente.values()){
           if(cod.equals(x.getCod())){
           return x;
           }
           
           
       }
        
       throw new IllegalArgumentException("Id inválido: " + cod);
    }
    
    
    
    
}//fim da classe
