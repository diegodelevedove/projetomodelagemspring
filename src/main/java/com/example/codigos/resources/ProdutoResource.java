/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.resources;

import com.example.codigos.domain.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.codigos.domain.Pedido;
import com.example.codigos.domain.Produto;
import com.example.codigos.dto.CategoriaDTO;
import com.example.codigos.dto.ProdutoDTO;
import com.example.codigos.resources.utils.URL;
import com.example.codigos.services.PedidoService;
import com.example.codigos.services.ProdutoService;
import java.util.ArrayList;// Esses imports se devem a mudanças que fizemos
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author Diego
 * Lembre do nome da classe de dominio e de onde esta
 * Aqui está o rest ou a classe endpoint
 * 
 * O método Get não aceita parâmetros no corpo da requisição tem que enviar via url 
 * ex:/?produto="cerveja"&preco=1.0
 * 
 */

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
    
    @Autowired
    private ProdutoService service;
    
    
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET ) // Acesse localhost:8081/categorias 
    // 1 - versão public List<Categoria> find(@PathVariable Integer id){ //Precido apontar qual o id ele vai pegar com a anotation
    // mudadmos para responseEntity (ja encapsula uma resposta http melhor que a lista)encontra entity com esse id
    //public ResponseEntity<?> find(@PathVariable Integer id){
    public ResponseEntity<Produto> find(@PathVariable Integer id){
               
       //Optional<Categoria> obj = service.buscar(id); // Busquei essa categoria lá no service que tem o id que queremos
       Produto obj = service.find(id); // Busquei essa categoria lá no service que tem o id que queremos
       return ResponseEntity.ok().body(obj);
       
        
                
                
        /*
        O método anterior retornava uma string
        public String listar(){
        return "Rest Funcionando"
        quando criamos uma lista, o tipo de retorno também é convertido pra lista e 
        portanto o método é uma lista do tipo Array<list>
        
        }
        
        */
        
        /*
        Categoria cat1 = new Categoria(1,"Informática");
        Categoria cat2 = new Categoria(2,"Escritório");
        List<Categoria> lista = new ArrayList<>();
        lista.add(cat1);
        lista.add(cat2);
        */
        
        
        
        
    }
    
   
    
    //Busca Paginada - Vamos copiar lá de categorias
    //Nessa busca pagina como padrão do Rest usamos parametros passados via url
    //Vjea esse método ele e complicado
    //A lista de produtoDTO é geradda a partir da lista de produtos
    @RequestMapping(method = RequestMethod.GET) 
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            //Essa anotation é o equivalente a dizer categorias/page?0 ou um o retorno da uri
            @RequestParam(value = "nome",defaultValue = "")String nome,
            @RequestParam(value = "categorias",defaultValue = "")String categorias,
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage, // 24 padrão bom pra layout de tela
            @RequestParam(value = "orderBy",defaultValue = "nome")String orderBy,
            @RequestParam(value = "page",defaultValue = "ASC")String direction){
        String nomeDecoded = URL.decodeParam(nome);//Acrescentado
        List<Integer> ids = URL.decodeIntList(categorias);//Gerou a lista então pra baixo passa como argumento - Acrescentado
        Page<Produto> list = service.search(nomeDecoded,ids,page,linesPerPage, orderBy,direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));// convertendo uma lista pra outra lista
        return ResponseEntity.ok().body(listDto);
    }
   
    
    
    
    
    
    
    
}//fim da classe
