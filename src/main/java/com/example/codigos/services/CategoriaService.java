/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services;

import com.example.codigos.domain.Categoria;
import com.example.codigos.dto.CategoriaDTO;
import com.example.codigos.repositories.CategoriaRepository;
import com.example.codigos.services.exceptions.DataIntegrityException;
import com.example.codigos.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

/**
 *
 * @author Diego
 * 
 * Serviço que ofereçe consulta das categorias no repositório(aqui vamos instanciar o objeto de fato)
 * A Classe service é a que exige a especificação do método de ação(salvar,buscar,deletar...)
 * então primeiro eu implemento os métodos no Resourcers e depois na service;
 */



@Service
public class CategoriaService {
    
    @Autowired //em teoria essa anotation exclui a nessecidade de declarar o método na interface 
    private CategoriaRepository repo; // Guardei a requisição em uma variável la da classe CategoriaRepository
    
    public Categoria find(Integer id){
        
        Optional<Categoria> obj = repo.findById(id);
        //Fazendo um testizinho nesse obj
        //Criamos uma classe para lidar com a Excessão está no pacote exceptions
        //Não conseguir ainda fazer retornar o 404 not found
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado ID" + id + "Tipo de Objeto:" + Categoria.class.getName());
        }
        
        
        //return obj;
        //O return tá usando expressão lambda
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! Id: "+id + ", Tipo" + Categoria.class.getName()));
         
    }// Fim do método de Categoria
    
    
    //Service save do método insert do POST
    //Veja a chamada lá no service do post para inserir cujo método é esse daqui
    
    public Categoria insert(Categoria obj){
        obj.setId(null);// no momento da inserção o objeto id precisa ser nulo senão ele considera como update -Garantia
        return repo.save(obj);
    }
    
    /*
    
    Implementação básica do método update do put no http
    Vamos fazer uma implementação mais completa Abaixo
    
        //Service save do método update do PUT
       
    public Categoria update(Categoria obj){
        find(obj.getId());//Observe que é o mesmo método do find,ele verifica se o id existe ou não
        // aproveitamos a implementação do ´método find como uma verificação
           
           
           
        return repo.save(obj);
    }
    
    */
    
    public Categoria update(Categoria obj) {
        Categoria newObj = find(obj.getId());
        updateData(newObj,obj);
        return repo.save(newObj);
    }
    
    
    
        // Service delete do método lá na categoria resource
        
        public void delete(Integer id){
            find(id);
            try{
            repo.deleteById(id);
            }catch(DataIntegrityViolationException e){
                throw new DataIntegrityException("Não é Possível excluir categoria que possui produtos");
                
            }
                        
        }
    
        
        // Retorando todas as categorias
        public List<Categoria> findAll(){
            return repo.findAll();
        }
        
        
        // Páginando - Para evitar muito processamento e uso de memória podemos paginar o retorno
        
        public Page<Categoria> findPage(Integer page,Integer linesPerPage,String orderBy, String direction){
            PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
        return repo.findAll(pageRequest);
        }
        
        
        //Método auxiliar do update DTO
             
        // DTO´s
        
        //Transformamos a requisição de Categoria para CategoriaDTO e por isso precisamos criar um novo método
        //Método auxiliar que instancia uma categoria a partir de um DTO
        
        public Categoria fromDTO(CategoriaDTO objDto){
            return new Categoria(objDto.getId(),objDto.getNome());
        }
        
        
        
     //Método auxiliar do update
    //LEmbrando que no update precisamos buscar os dados e atualizar e por isso um método auxiliar
    private void updateData(Categoria newObj,Categoria obj){
        newObj.setNome(obj.getNome());
        
                
    }
        
        
        
    
}//fim da classe

