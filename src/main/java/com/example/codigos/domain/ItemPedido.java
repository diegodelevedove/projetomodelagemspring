/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Diego
 */
@Entity
public class ItemPedido implements Serializable{
    private static final long serialVersionUID = 1l;
    
    @JsonIgnore //ignorar a serialização dessa classe
    @EmbeddedId //Id embarcado em uma classe 
    private ItemPedidoPK id = new ItemPedidoPK(); //Chave composta ou seja a classe faz a relação
    
    
    private Double desconto;
    private Integer quantidade;
    private Double preco;
    
    public ItemPedido(){
        
    }

    public ItemPedido(Pedido pedido,Produto produto,Double desconto, Integer quantidade, Double preco) {
        id.setPedido(pedido);//nesse caso não pego item pedido e sim o id do pedido e do produto
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    
    // Criano dois gets na mão porque assim podemos acessar diretamente o pedido e produtpo
    //
    
    //Nova Regra - Subtotal
    //Criando um método para calcular o subtotal do pedido
    //Fizemos esse método aqui por conta da quantidade do preço e do desconto que são dessa classe
    public double getSubTotal(){
        return (preco - desconto)*quantidade;
    }
    
    
    
    
    
   
    public Produto getProduto(){
        return id.getProduto();
    }
    
    // Nova Regra - Precisamos associar pedido com o produto e por isso precisamos criar uma serialização de itendePedido ou um id pra ele
    public void setProduto(Produto produto){
        id.setProduto(produto);
    }
    @JsonIgnore//Tudo que começa com get ele entende que tem que serializar isso evita o loop de chamada 
    public Pedido getPedido(){
        return id.getPedido();
    }
    
    
    // Nova Regra - Precisamos associar pedido com o produto e por isso precisamos criar uma serialização de itendePedido ou um id pra ele
    public void setPedido(Pedido pedido){
        id.setPedido(pedido);
    }
    
    
        
    public ItemPedidoPK getId() {
        return id;
    }

    public void setId(ItemPedidoPK id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final ItemPedido other = (ItemPedido) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

  

    
    
    
    
    
    
}
