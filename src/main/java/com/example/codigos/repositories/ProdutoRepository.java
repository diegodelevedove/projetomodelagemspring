/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.repositories;

import com.example.codigos.domain.Categoria;
import com.example.codigos.domain.Produto;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Diego
 * 
 *Repositório = Cliente
 * autowiring é a ligação entre cliente e serviço
 * ver Spring data no google metodos de consulta
 */

@Repository
@Transactional(readOnly = true)
public interface ProdutoRepository extends JpaRepository<Produto,Integer>{
    
    //Implementando um método de paginação aqui
    //Lmebrando que essa classe não tem corpo de maneira geral,mas como temos um método genérico a interface obriga todomo mundo que herda
    // é o mesmo método da classe filha só que com parâmetros gerais(DEFINIÇÃO),em outras palavras um CONSTRUTOR
    //Verifique no google por Spring Data lá tem expressões de referência que o spring entende,exemplo a 
    //Expressão @Query onde ele criar a busca automaticamente apenas declarando um findById por exemplo
    //junto da anotação temso uma query em JPQL veja como fazer essas querys mais específicas do banco SQL para java no framework
    
    //Primeiro método
    //Page<Produto> search (String nome,List<Categoria> categorias,Pageable pageRequest);
    //Na forma abaixo especifico como o método irá buscar pelo nome usando a anotação @Param
    //Page<Produto> search (@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
    
    
    
    //@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    Page<Produto> findDistinctByNomeContainingAndCategoriaIn(String nome,List<Categoria> categorias,Pageable pageRequest);
    //Essa expressão findDistinctByNomeContainingAndCategoriaIn é uma query também
    //Ela substitiui a Query de modo que o findDistinct é um select e assim por diante
    //Busque no google por spring data expressions
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface
    //Exemplo de busca lá no postman
    //http://localhost:8081/produtos/?nome=or&categorias=1,4
    
    
    
    
    
    
    
    /*
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    //Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest); 
    Page<Produto> search(String nome,List<Categoria> categorias,Pageable pageRequest);
    //Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
    //Esse método tá la na service 
    */
}



/*

    O conflito está entre
    ProdutoRepository
    ProdutoResorces 
    
    


*/