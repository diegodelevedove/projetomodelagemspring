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
import com.example.codigos.dto.CategoriaDTO;
import com.example.codigos.services.PedidoService;
import java.net.URI;
import java.util.ArrayList;// Esses imports se devem a mudanças que fizemos
import java.util.List;
import java.util.Optional;
import javafx.scene.media.Media;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 *
 * @author Diego
 * Lembre do nome da classe de dominio e de onde esta
 * Aqui está o rest ou a classe endpoint
 * 
 */

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
    
    @Autowired
    private PedidoService service;
    
    
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET ) // Acesse localhost:8081/categorias 
    // 1 - versão public List<Categoria> find(@PathVariable Integer id){ //Precido apontar qual o id ele vai pegar com a anotation
    // mudadmos para responseEntity (ja encapsula uma resposta http melhor que a lista)encontra entity com esse id
    //public ResponseEntity<?> find(@PathVariable Integer id){
    public ResponseEntity<Pedido> find(@PathVariable Integer id){
        
        //Optional<Categoria> obj = service.buscar(id); // Busquei essa categoria lá no service que tem o id que queremos
       Pedido obj = service.find(id); // Busquei essa categoria lá no service que tem o id que queremos
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
    
    @RequestMapping(method = RequestMethod.POST)// inserindo nova categoria
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){ //@RequestBody faz o jason Ser convertido para o objeto java altomáticamente
           //Como estamos definindo uma DTO precisamos mudar para um objeto entity isso valida ele antes 
           // de passar pra fretnte além da anotation           
           obj = service.insert(obj);// implementar o insert no Pedido Service
           //Essa definição é pré definida pelo padrão de boas práticas
           URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();//esse é o id do objeto que inserimos 
           
        return ResponseEntity.created(uri).build();   
       }
    
    
    
}//fim da classe
