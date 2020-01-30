/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.codigos.domain.Endereco;

/**
 *
 * @author Diego
 * Repository (Camada de acesso aos dados)
 * O spring acessa os dados atraves de uma interface que usa os parâmetros do JPA do tipo(nome do objeto e atributor identificador)
 *  
 */



@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {

    
    
}
//Nada mais é necessário,esse objeto é o suficiente para (buscar,alterar,deletar,atualizar)de acesso aos dados referentes
//ao objeto categoria