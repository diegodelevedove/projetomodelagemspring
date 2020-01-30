/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services;


import com.example.codigos.domain.Categoria;
import com.example.codigos.domain.Produto;
import com.example.codigos.repositories.CategoriaRepository;
import com.example.codigos.repositories.ProdutoRepository;
import com.example.codigos.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

/**
 *
 * @author Diego
 * 
 * Serviço que ofereçe consulta das categorias no repositório(aqui vamos instanciar o objeto de fato)
 */



@Service
public class ProdutoService {
    
    @Autowired //em teoria essa anotation exclui a nessecidade de declarar o método na interface 
    private ProdutoRepository repo; // Guardei a requisição em uma variável la da classe CategoriaRepository
    
    @Autowired
    private CategoriaRepository categoriaRepository; // Ele que gera pega a listinha de categorias
    //Tendo em vista que iremos associar categorias e produtos(pelo id)
    
    
    
    public Produto find(Integer id){
        
        Optional<Produto> obj = repo.findById(id);
        //Fazendo um testizinho nesse obj
        //Criamos uma classe para lidar com a Excessão está no pacote exceptions
        //Não conseguir ainda fazer retornar o 404 not found
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado ID" + id + "Tipo de Objeto: " +Produto.class.getName());
        }
        
        
        //return obj;
        //O return tá usando expressão lambda
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! Id: "+id + ", Tipo" + Produto.class.getName()));
      }
    
    //Método de busca do produto busca paginada
    /*
        public Page<Produto> search(String nome,List<Integer> ids,Integer page,Integer linesPerPage,String orderBy, String direction){
            PageRequest pageRequest = new PageRequest(page,linesPerPage,Sort.Direction.valueOf(direction),orderBy); 
            List<Categorias> categorias = categoriaRepository.findAllById(ids);
        return repo.search(nome,categorias,pageRequest);
        }   
    
    */
    
    
    //Atenção esse método está diferente na versão anterior 
    //
    /*   
    public Page<Produto> search(String nome, List<Integer> ids, Integer page,Integer linesPerPage,String orderBy, String direction){
            PageRequest pageRequest = new PageRequest(page,linesPerPage,Direction.valueOf(direction),orderBy);
            List<Categoria> categorias = categoriaRepository.findAllById(ids);
            return repo.search(nome,categorias,pageRequest);
    
    */  
    
            
    
    
    public Page<Produto> search(String nome, List<Integer> ids, Integer page,Integer linesPerPage,String orderBy, String direction){
            PageRequest pageRequest = PageRequest.of(page,linesPerPage,Sort.Direction.valueOf(direction),orderBy);
            List<Categoria> categorias = categoriaRepository.findAllById(ids);
            //return repo.search(nome,categorias,pageRequest);
            return repo.findDistinctByNomeContainingAndCategoriaIn(nome,categorias,pageRequest);
            
            //Perceba que poderiamos retornar o search mas nesse caso estamos usando uma expressão do spring data que
            //Substitui a query do jpQL os nomes de métodos que já fazem as funções 
        
      
        
    }
        
    
    
    
    
}
