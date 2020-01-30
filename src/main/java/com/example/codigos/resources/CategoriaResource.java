/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.codigos.domain.Categoria;
import com.example.codigos.dto.CategoriaDTO;
import com.example.codigos.services.CategoriaService;
import java.net.URI;
import java.util.ArrayList;// Esses imports se devem a mudanças que fizemos
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.Servlet;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 *
 * @author Diego
 * Lembre do nome da classe de dominio e de onde está a classe
 * Devemos instanciar nas duas pontas:
 * ->Na Classe de Recursos que vou disponibilizar
 * private CategoriaService service; 
 * -> Na Classe de Serviços que vou Definir(Métodos de Percistência)
 * private CategoriaResource resources ou repo;
 * Uma Chama o objeto da outra
 * 
 * --> Anotations importnates
 * @RestControler
 * @RequestMapping
 *
 * Aqui está o rest ou a classe endpoint
 * 
 */

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
    
    @Autowired //Preciso Associar as duas classes em Repository(Rest) com a Service(Métodos de ação)
    private CategoriaService service;
    
    
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET ) // Acesse localhost:8081/categorias 
    // 1 - versão public List<Categoria> find(@PathVariable Integer id){ //Precido apontar qual o id ele vai pegar com a anotation
    // mudadmos para responseEntity (ja encapsula uma resposta http melhor que a lista)encontra entity com esse id
    //public ResponseEntity<?> find(@PathVariable Integer id){
    public ResponseEntity<Categoria> find(@PathVariable Integer id){
        
        //Vamos incluir um try catch aqui pois definimos uma implementação de excessão
        
      
        
       //Optional<Categoria> obj = service.buscar(id); // Busquei essa categoria lá no service que tem o id que queremos
       Categoria obj = service.find(id); // Busquei essa categoria lá no service que tem o id que queremos
            
       
       
       
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
        
        
        
        
    }// Fim do getMethod
    
    
    // Agora vamos criar um endpoint do tipo post
    
    
    
    //uma respota http sem corpo
    //O método post é o mesmo que um método de envio ou de mandar dados como postar uma carta envio enformações
    //diferente do get que pego as informações
    
    
    /*
    
    Em um bloco sem validação, chamammos a classe diretamente como endpoint
    quando usamos validação de campos passamos essa responsabilidade para as Classes DTO
    no caso dessa no comentário está sem abordagem ao dto,vou deixar só para fims didaticos
    
    @RequestMapping(method = RequestMethod.POST)// inserindo nova categoria
    public ResponseEntity<Void> insert(@RequestBody Categoria obj){ //@RequestBody faz o jason Ser convertido para o objeto java altomáticamente
        
           obj = service.insert(obj);
           //Essa definição é pré definida pelo padrão de boas práticas
           URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();//esse é o id do objeto que inserimos 
           
        return ResponseEntity.created(uri).build();   
       }
    
    
    
    
    */
    
    
        
    @RequestMapping(method = RequestMethod.POST)// inserindo nova categoria
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){ //@RequestBody faz o jason Ser convertido para o objeto java altomáticamente
           //Como estamos definindo uma DTO precisamos mudar para um objeto entity isso valida ele antes 
           // de passar pra fretnte além da anotation           
           Categoria obj =  service.fromDTO(objDto);
           obj = service.insert(obj);
           //Essa definição é pré definida pelo padrão de boas práticas
           URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();//esse é o id do objeto que inserimos 
           
        return ResponseEntity.created(uri).build();   
       }
    
    /*
        Essa é a versão sem o uso do DTO como retorno do endpoint
        A validação é feita a partir do uso de classes DTO e portanto o método de criação é diferente um pocuo
        Adcionamos mais uma classe e métodos auxiliares para não acessar diretamente a classe do DOMAIN e
        mexer apenas na camada de negócio; Existem outras implementaç~ções
    
        @RequestMapping(method = RequestMethod.POST)// inserindo nova categoria
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){ //@RequestBody faz o jason Ser convertido para o objeto java altomáticamente
           //Como estamos definindo uma DTO precisamos mudar para um objeto entity isso valida ele antes 
           // de passar pra fretnte além da anotation           
           Categoria obj =  service.fromDTO(objDto);
           obj = service.insert(obj);
           //Essa definição é pré definida pelo padrão de boas práticas
           URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();//esse é o id do objeto que inserimos 
           
        return ResponseEntity.created(uri).build();   
       }
    
    */
    
    // Agora vamos criar um put que é um update do protocolo http
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto,@PathVariable Integer id){
        //Converter o obj para objDTO
        Categoria obj =  service.fromDTO(objDto); 
        obj.setId(id);//Testizinho pra verificar se é o id recebido
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
    
    
    //Fazendo o delete de Categoria
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE) 
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
        
    }
    
    
    // Listando todas as categorias DTO
    //Retornar apenas as categorias e nãos os dados do registro completo
    @RequestMapping(method = RequestMethod.GET) 
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());// convertendo uma lista pra outra lista
        return ResponseEntity.ok().body(listDto);
    }
    
    // Endpoint de paginação dos elementos do banco
    //Observe os parametros no postman da uri
    
    //http://localhost:8081/categorias/pages?linesPerPage=3&pages=1&direction=DESC
    // parametros opcionais na requisição
    //pages - volta tudo
    //pages?linesPerPage = 3 retorna 3 paginas 
    //&pages=1&direction=DESC = Retornar a página 1 do banco descentende nos resultados 
    
    @RequestMapping(value = "/pages",method = RequestMethod.GET) 
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            //Essa anotation é o equivalente a dizer categorias/page?0 ou um o retorno da uri
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage, // 24 padrão bom pra layout de tela
            @RequestParam(value = "orderBy",defaultValue = "nome")String orderBy,
            @RequestParam(value = "page",defaultValue = "ASC")String direction){
        Page<Categoria> list = service.findPage(page,linesPerPage, orderBy,direction);
        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));// convertendo uma lista pra outra lista
        return ResponseEntity.ok().body(listDto);
    }
    
    
    
}//fim da classe
