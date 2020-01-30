/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services.validation;

/**
 *
 * @author Diego
 */
import com.example.codigos.domain.Cliente;
import com.example.codigos.domain.enums.TipoCliente;
import com.example.codigos.dto.ClienteNewDTO;
import com.example.codigos.repositories.ClienteRepository;
import com.example.codigos.resources.Exceptions.FieldMessage;
import com.example.codigos.services.validation.utils.BR;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert,ClienteNewDTO> { //<> ClienteInsert é o nome da nossa anotation e o tipo de daods é CLienteNewDTO 
    
    @Autowired
    private ClienteRepository repo;
    
    
    public void initialize(ClienteNewDTO ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        // inclua os testes aqui, inserindo erros na lista
        
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CPF Invalido"));
        }
        
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CNPJ Invalido"));
        }
        
        // Vamos testar se o email do cliente já existe - Ver ClienteRepository (Regra = Ver se o cliente já registrou o email anteriormente)
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null){// um tratamento de erro ficou aqui
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
