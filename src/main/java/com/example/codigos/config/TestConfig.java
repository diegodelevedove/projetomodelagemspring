/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.config;

import com.example.codigos.services.DBService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; //A anotation e não a beans
import org.springframework.context.annotation.Profile;

/**
 *
 * @author Diego
 * 
 * NEsse arquivo iremos guardar as configurações específicas para o profile de test
 * Resumindo
 * Criamos um novo documento properties e nomeamos no padrão
 * Depois criamos uma classe no pacote Config para receber a configuração
 * nessa classe criamos um método Boolean para retornar apenas a chamada de outro método na classe dbService
 * Fazemos a injetação de dependenceia autowired entre as classes dos pacotes
 * Criamos a classe DBSerice com a antoaçõe service pegamos todos os códigos da classe app e injetamos nela dentro do método instantiateTestDatabase() - Também copiar os autowired
 * Chamamos o método nessa classe através do autowired 
 * limpamos a app deixando apenas o main e o run
 * Colocamos o main pra compilar
 * Temos agora uma configuração de teste
 */

@Configuration
@Profile("test")//Essa anotation diz que todos os beans que estiverem dentro dessa classe serão ativados dentro desse profile de test ativo no .properties
public class TestConfig {
    
    @Autowired //Agora ligando essa classe com a classe do banco(injetando dependências)
    private DBService dbService;
    
    
    @Bean
    public Boolean instantiateTestDatabase() throws ParseException{// não pode ser um método void
        dbService.instantiateTestDatabase();
        return true;
    }
     
    
}
