/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.codigos.domain.Cliente;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Diego
 * Repository (Camada de acesso aos dados)
 * O spring acessa os dados atraves de uma interface que usa os parâmetros do JPA do tipo(nome do objeto e atributor identificador)
 *  
 */



@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    
    //Nova Regra
    //Tratando um busca por email (Lembrando que essa classe é a responsável pela busca no banco
    
    
    
    @Transactional(readOnly = true) // Essa anotation simplifica a consulta no banco e diminui o locking no gerenciamento da consulta no banco de dados
    Cliente findByEmail(String email);// método responsável pela busca
    //o spring entende que se criarmos um método dessa maneira:
    // Entidade findByAtributo(tipo atributo) 
    //Ele vai entender que é preciso implementar porque é uma interface buscando um cliente passando um
    // email como argumento
    
    //Temos que pra isso também relacionar lá no ClienteInsertValidator
    
}
//Nada mais é necessário,esse objeto é o suficiente para (buscar,alterar,deletar,atualizar)de acesso aos dados referentes
//ao objeto categoria