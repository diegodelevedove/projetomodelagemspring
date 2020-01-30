/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.dto;

import java.io.Serializable;
import com.example.codigos.domain.Produto;
/**
 *
 * @author Diego
 */
public class ProdutoDTO implements Serializable{
    public static final long serialVersionUID = 1l;
    
    
    private Integer id;
    private String nome;
    private Double preco;
    
    public ProdutoDTO(){
        
    }

    
    public ProdutoDTO(Produto obj){
        id = obj.getId();
        nome = obj.getNome();
        preco = obj.getPreco();
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
}
