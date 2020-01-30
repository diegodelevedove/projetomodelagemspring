/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Diego
 */

@Entity
public class Produto implements Serializable{
    private static final long serialVersionUID = 1l;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Double preco;
    //Criando associação com Categoria (Coleções)
    //Como é uma relação de muitos pra muitos usamos anotations pra especificar o relacionamento
    //Escolher uma classe forte e definir nela as anotations
    //no jointable incluimos uma relação entre as duas classes
    
    //Referencia Cíclica = Quando fazemos a associação buscar indefinidamente entre classes - Solução - > @JsonManagedReference
    //Lado Fraco - Então omito tudo lá pro categoria - Lado fraco @JsonBackReference
    
    @JsonIgnore
    //@JsonBackReference não tá usando mais essa
    @ManyToMany
    @JoinTable(name = "Produto_Categoria",
                       joinColumns = @JoinColumn(name = "produto_id"),
                       inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categoria = new ArrayList<>();
    
    
    @JsonIgnore //POrque já serializamos a lista de produtos (tá estourando um erro la de utf 8) dai comentei
    @OneToMany(mappedBy = "id.produto")// tá lá em pedido
    private Set<ItemPedido> itens = new HashSet<>();
    
    
    
    
    public Produto(){
        
    }

    public Produto(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    //O produto deve conhecer seus pedidos
    //Por isso precisamos varrer os itens
    
       @JsonIgnore
       private List<Pedido> getPedidos(){ // todo método por padrão deve começar com o nome getNome
        List<Pedido> lista = new ArrayList<>();
        for(ItemPedido x : itens){
            lista.add(x.getPedido());
        }
        return lista;
    }
    
    
    
    
    //Get e set do ItemPedido
   
    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
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

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
