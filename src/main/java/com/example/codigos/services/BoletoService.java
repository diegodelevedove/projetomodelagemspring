/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services;

import com.example.codigos.domain.PagamentoComBoleto;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author Diego
 */
@Service
public class BoletoService {
    
    //Essa Adaptação é apenas pra criar uma data simulada do pagamento
    //no mundo real é usando um web service que gera um boleto
    
    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto,Date instanteDoPedido){
        //Instanciando a Classe Calendário
        Calendar cal = Calendar.getInstance();
        cal.setTime(instanteDoPedido);
        cal.add(Calendar.DAY_OF_MONTH,7);
        pagto.setDataVencimento(cal.getTime());
    }
}
