/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



/**
 *
 * @author Diego
 */
@Entity
public class Pedido implements Serializable{
    private static final long serialVersionUID = 1l;
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)      // aqui eu já preciso serializar os pedidos
        private Integer id;
        
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") // Formatando a saida do json
        private Date instante;
        
        //Mapeamento bi direcional
        //PAra impedidr que o pagamento também seja serializado
        
        @JsonIgnore//Ignore para que o Post do Pedido possa Gravar (Mas pode sair)
        @JsonManagedReference
        @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")//ou seja,evitamos um estado de transição 1 transação será sempre 1 pedido
        private Pagamento pagamento;
        
        @JsonIgnore////Ignore para que o Post do Pedido possa Gravar (Mas pode sair)
        @JsonManagedReference
        @ManyToOne
        @JoinColumn(name = "cliente_id")
        private Cliente cliente;
       
        @ManyToOne
        @JoinColumn(name = "endereco_de_entrega_id")
        private Endereco enderecoDeEntrega; //Outra Relação com Endereço
        
        //Coleções
        //Veja que precisamos associar o iten pedido aqqui
        //Associação inversa
        
        @OneToMany(mappedBy = "id.pedido")// Tá lá em produto
        private Set<ItemPedido> itens = new HashSet<>();
        
        
        public Pedido(){
            
        }

    public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    
    //Nova Regra - Valor total do pedido
    public double getValorTotal(){
        double soma = 0.0;//Valor dummy pra guardar a soma
        for(ItemPedido ip: itens){//For Each
            soma = soma + ip.getSubTotal();
        }
        return soma;
    }
    
    
    
    
    
    
    //Iten pedido tem gets e sets também,

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

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return enderecoDeEntrega;
    }

    public void setEndereco(Endereco endereco) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Pedido other = (Pedido) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
        
        
        
        
        
        
        
}
