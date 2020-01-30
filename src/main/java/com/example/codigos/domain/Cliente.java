/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;


import com.example.codigos.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Diego
 * 
 * Regra 1: Cliente com Pedido não pode ser deletado
 * ->Cliente com endereço apenas
 * 
 * 
 */

@Entity
public class Cliente implements Serializable{
    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(unique = true) // impede dois emails iguais
    private String email;
    private String cpfOuCnpj;
    private Integer tipo;
    //Daqui pra baixo até o construtor incluimos as anotations e injeções de dependencia ou relações
    //Para fazer relações é preciso existir,primeiramente,as classes que irão se relacionar
    
    //Agora controlando a referência cíclica porque temos um endpoint para cliente
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)//Regra nova: Se apago os dados do cliente apago também dos endereços se o cliente não tiver pedido - basta usar a anotoation
    private List<Endereco> enderecos = new ArrayList<>();
    //Vamos tratar o telefone como um conjunto sem repetição de elementos - Set<>
    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();
    
    //Iniciando o 4 bloco
    
    @JsonBackReference
    @OneToMany(mappedBy = "cliente")
    private List<Pedido>  pedidos = new ArrayList<>();
    
    
    public Cliente(){
        
    }
    
    public Cliente(Integer id,String nome,String email,String cpfOuCnpj,TipoCliente tipo){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = (tipo==null) ? null : tipo.getCod(); //se receber um nulo então é nulo e não exception caso contrário é o proprio valor recebido
        
        
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public Integer getId(){
        return id;
              
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

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCod();
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}//fim da classe
