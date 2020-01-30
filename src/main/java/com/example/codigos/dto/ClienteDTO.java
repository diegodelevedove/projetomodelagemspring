/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.dto;

import com.example.codigos.domain.Cliente;
import com.example.codigos.services.validation.ClienteUpdate;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Diego
 */

@ClienteUpdate
public class ClienteDTO implements Serializable  {
    private static final long serialVersionUID = 1l;
    
    //Mesmo instanciano no construtor parâmetros da classe superior 
    //e sabendo que os gets e sets pegam dele, essa classe ainda precisa ter variáveis
    // ´porque pra voltar um resultado quem guarda em memoria são as variáveis dessa classe e não da classe parent
    private Integer id;
    
    @NotEmpty(message = "Preenchimento Obrigatório")
    @Length(min = 5,max =120,message = "O tamanho deve ser entre 5 e 120 caracteres" )
    private String nome;
    
    @NotEmpty(message = "Preenchimento Obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    
    public ClienteDTO(){
        
    }
    
    //*Ponto Importante
    //O método construtor da entidade DTO  não defini as próprivas variáveis mas as recebe de um entidade
    // ou classe superior da qual ele  faz a referência e instancia-lo
    
    
    public ClienteDTO(Cliente obj) {
       id = obj.getId();
       nome = obj.getNome();
       email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
}
