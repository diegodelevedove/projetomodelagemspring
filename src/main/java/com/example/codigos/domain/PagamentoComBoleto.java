/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.domain;

import com.example.codigos.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Diego
 */
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {
    private static final long serialVersionUID = 1l;
    
    @JsonFormat(pattern = "dd/MM/yyyy") // Formatando a saida do json
    private Date dataVencimento;
    @JsonFormat(pattern = "dd/MM/yyyy") // Formatando a saida do json
    private Date dataPagamento;
    
    public PagamentoComBoleto(){
        
    }

    
    public PagamentoComBoleto(Integer id,EstadoPagamento estado,Pedido pedido,Date dataVencimento,Date dataPagamento){
        super(id,estado,pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        
    }
      

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    
    
    //Não precisa de Hashcode e equals aqui porque a classe já tá herdando da super
    
    
}//Fim da classe
