/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.resources;

import com.example.codigos.domain.Categoria;
import com.example.codigos.domain.Cliente;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.codigos.domain.Cliente;
import com.example.codigos.dto.CategoriaDTO;
import com.example.codigos.dto.ClienteDTO;
import com.example.codigos.dto.ClienteNewDTO;
import com.example.codigos.services.ClienteService;
import java.net.URI;
import java.util.ArrayList;// Esses imports se devem a mudanças que fizemos
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 *
 * @author Diego
 * Lembre do nome da classe de dominio e de onde esta
 * Aqui está o rest ou a classe endpoint
 * 
 */

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
    
    @Autowired
    private ClienteService service;
    
    
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET ) // Acesse localhost:8081/categorias 
    // 1 - versão public List<Categoria> find(@PathVariable Integer id){ //Precido apontar qual o id ele vai pegar com a anotation
    // mudadmos para responseEntity (ja encapsula uma resposta http melhor que a lista)encontra entity com esse id
    //public ResponseEntity<?> find(@PathVariable Integer id){
    public ResponseEntity<Cliente> find(@PathVariable Integer id){
        
        //Vamos incluir um try catch aqui pois definimos uma implementação de excessão
        
      
        
       //Optional<Categoria> obj = service.buscar(id); // Busquei essa categoria lá no service que tem o id que queremos
       Cliente obj = service.find(id); // Busquei essa categoria lá no service que tem o id que queremos
            
       
       
       
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
    
    //***Atenção 
    //Todos os métodos são iguais aos do CategoriaResource e os comments estão lá;
    @RequestMapping(method = RequestMethod.POST)// inserindo nova categoria
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){ //@RequestBody faz o jason Ser convertido para o objeto java altomáticamente
           //Como estamos definindo uma DTO precisamos mudar para um objeto entity isso valida ele antes 
           // de passar pra fretnte além da anotation           
           Cliente obj =  service.fromDTO(objDto);
           obj = service.insert(obj);
           //Essa definição é pré definida pelo padrão de boas práticas
           URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();//esse é o id do objeto que inserimos 
           
        return ResponseEntity.created(uri).build();   
       }
    
    
    
   
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto,@PathVariable Integer id){
        Cliente obj =  service.fromDTO(objDto); 
        obj.setId(id);//Testizinho pra verificar se é o id recebido
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
        
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE) 
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
        
    }
      
    @RequestMapping(method = RequestMethod.GET) 
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());// convertendo uma lista pra outra lista
        return ResponseEntity.ok().body(listDto);
    }
       
    @RequestMapping(value = "/pages",method = RequestMethod.GET) 
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage, // 24 padrão bom pra layout de tela
            @RequestParam(value = "orderBy",defaultValue = "nome")String orderBy,
            @RequestParam(value = "page",defaultValue = "ASC")String direction){
        Page<Cliente> list = service.findPage(page,linesPerPage, orderBy,direction);
        Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));// convertendo uma lista pra outra lista
        return ResponseEntity.ok().body(listDto);
    }
    
    
    
    
    
    
}//fim da classe
