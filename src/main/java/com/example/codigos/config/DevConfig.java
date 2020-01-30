/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.config;

import com.example.codigos.services.DBService;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Profile("dev")//Essa anotation diz que todos os beans que estiverem dentro dessa classe serão ativados dentro desse profile de test ativo no .properties
public class DevConfig {
    
    @Autowired //Agora ligando essa classe com a classe do banco(injetando dependências)
    private DBService dbService;
    
    /*
        Situação importante:
    A seguinte linha no arquivo: application-dev.properties é: spring.jpa.hibernate.ddl-auto=create
    Ela quer dizer que toda vez que rodamos a aplicação o banco é criado mas não destruido
    *Ver na documentação as outras variações:https://docs.spring.io/autorepo/docs/spring-boot/1.1.0.M1/reference/html/howto-database-initialization.html
    Portanto precisamos garantir que ao rodar a aplicação ela não seja duplicada
    por isso vamos pegar o valor da chave aqui nessa classe :spring.jpa.hibernate.ddl-auto=create
    */
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;
    
    
    @Bean
    public Boolean instantiateTestDatabase() throws ParseException{// não pode ser um método void
        //Vamos testar a variável de criação - Vela lá em properties
        if(!"create".equals(strategy)){
            return false;//no caso de haver a mesma alteração ele não atualiza o banco
        }        
        dbService.instantiateTestDatabase();//no caso de haver uma alteração ele permanece o banco
        return true;
    }
     
    
}
