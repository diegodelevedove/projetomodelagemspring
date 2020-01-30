/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services.validation;

/**
 *
 * @author Diego
 * Nova Regra: Para Atualização não usamos o ClienteNewDto 
 * e sim o ClienteDto - Porque vamos consultar algo que já existe
 * ***Atenção : Lembre de colocar a anotation lá na classa DTO tanto do insert quanto do Update
 * 
 * 
 * 
 */
import com.example.codigos.domain.Cliente;
import com.example.codigos.domain.enums.TipoCliente;
import com.example.codigos.dto.ClienteDTO;
import com.example.codigos.dto.ClienteNewDTO;
import com.example.codigos.repositories.ClienteRepository;
import com.example.codigos.resources.Exceptions.FieldMessage;
import com.example.codigos.services.validation.utils.BR;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate,ClienteDTO> { //<> ClienteInsert é o nome da nossa anotation e o tipo de daods é CLienteNewDTO 
    
    //Na atualização temos que buscar comparar pela id da uri
    @Autowired
    private HttpServletRequest request;
     
    
    
    @Autowired
    private ClienteRepository repo;
    
    
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        // Relativo ao request vamos criar um map que é uma estrutura de dados do tipo chave - valor
        Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);// Map<String, String> Casting - Convertendo um tipo genérico para um tipo específico
        Integer uriId = Integer.parseInt(map.get("id"));//Depois que pegamos ele vai vir ainda como map e por isso o parse
        
        
        
        List<FieldMessage> list = new ArrayList<>();
        
          
        //Para o update ficou apenas esse método e um if apenas
        // Vamos testar se o email do cliente já existe - Ver ClienteRepository (Regra = Ver se o cliente já registrou o email anteriormente)
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)){// Comparando agora(entre um cliente e todos os emails dos demais clientes(ou seja um email tem que ser pra um cliente apenas não pode ser compartilhado por isso ele compara com toda a base
            list.add(new FieldMessage("email","O email já existe"));
        }
        
        
        
        
        
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
