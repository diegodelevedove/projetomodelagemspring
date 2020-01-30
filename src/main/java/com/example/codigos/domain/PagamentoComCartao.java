/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.example.codigos.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.persistence.Entity;

/**
 *
 * @author Diego
 */

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1l;
    
    private Integer numeroDeParcelas;
          
    public PagamentoComCartao(){
        
    }

    public PagamentoComCartao(Integer id,EstadoPagamento estado,Pedido pedido,Integer numeroDeParcelas){
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }
       
        
    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
    
    
}
