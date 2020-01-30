/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.example.codigos.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonBackReference; //Pacote jackson
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

/**
 *
 * @author Diego
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED ) // estabelecendo heraça como uma tabela pra cada 
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type") //permitir a instanciação de subclasses a partir de dados JSON
public abstract class Pagamento implements Serializable{
    private static final long serialVersionUID = 1l;
        
        
    
        @Id // O id tem que ter o mesmo do pedido nao vamos gerar outro
        private Integer id;
        private Integer estado;
        
        //Veja lá no pedido que impedimos uma serialização ou o conhecimento do pagamento no pedido
        @JsonBackReference
        @OneToOne //um pedido é  = um pagamento
        @JoinColumn(name = "pedido_id")
        @MapsId
        private Pedido pedido;
        
       public Pagamento(){
           
       }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        super();
        this.id = id;
        this.estado = (estado == null)? null: estado.getCod();// Porque senão retorna Zero lembrando que aqui pegamos o código do Enum e não pelo parametro
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado); // Armazenando o número inteiro lá do enums 
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCod();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Pagamento other = (Pagamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
                
        
       
        
        
        
        
    
}
