/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 *
 * @author Diego
 * Entidade ou a classe bean 
 */
@Entity
public class Categoria implements Serializable  {
    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    //Criando associações com outras classes
    //Agora na tabela "filha" definimos o mapeamento em cima do atributo
    
    //Referencia Cíclica = Quando fazemos a associação buscar indefinidamente entre classes - Solução - > @JsonManagedReference
    //No lado que quer os objetos associados
    
    //@JsonManagedReference - não tá usando mais essa dá problema na hora do post
    @ManyToMany(mappedBy = "categoria")
    private List<Produto> produtos = new ArrayList<>();
    
    public Categoria(){
        
    }
    
    
    public Categoria(Integer id,String nome){
        super();
        this.id = id;
        this.nome = nome;
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

    //Criar os gets sets da nova lista que associamos

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    
    
    
    
    
    
    
    //O hascode equals equivale a dizer para o java comparar os objetos a partir do seu conteúdo e não pelo ponteiro de memoria
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
            
            
            
    
    
}
  