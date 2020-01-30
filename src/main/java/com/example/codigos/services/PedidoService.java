/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services;

import com.example.codigos.domain.ItemPedido;
import com.example.codigos.domain.PagamentoComBoleto;
import com.example.codigos.domain.Pedido;
import com.example.codigos.domain.Produto;
import com.example.codigos.domain.enums.EstadoPagamento;
import com.example.codigos.repositories.ItemPedidoRepository;
import com.example.codigos.repositories.PagamentoRepository;
import com.example.codigos.repositories.PedidoRepository;
import com.example.codigos.repositories.ProdutoRepository;
import com.example.codigos.services.exceptions.ObjectNotFoundException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Diego
 * 
 * Serviço que ofereçe consulta das categorias no repositório(aqui vamos instanciar o objeto de fato)
 */



@Service
public class PedidoService {
    
    @Autowired //em teoria essa anotation exclui a nessecidade de declarar o método na interface 
    private PedidoRepository repo; // Guardei a requisição em uma variável la da classe CategoriaRepository
    
    
    @Autowired
    private BoletoService boletoService;// Dependencia de boleto
    
    @Autowired
    private PagamentoRepository pagamentoRepository; //Dependencia de pagamento
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    
    
    public Pedido find(Integer id){
        
        Optional<Pedido> obj = repo.findById(id);
        //Fazendo um testizinho nesse obj
        //Criamos uma classe para lidar com a Excessão está no pacote exceptions
        //Não conseguir ainda fazer retornar o 404 not found
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado ID" + id + "Tipo de Objeto:" +Pedido.class.getName());
        }
        
        
        //return obj;
        //O return tá usando expressão lambda
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! Id: "+id + ", Tipo" + Pedido.class.getName()));
         
    }
    
    // Insert do PedidoResource
    
    //Observe que nesse caso abaixo consideramos o pagamento gerado pelo boleto com uma semana
    //O set padrão do pagamento é Pendente por conta do pagamento com Boleto
    
    public Pedido insert(Pedido obj){//Todos esses parametros estão lá na classe pedido
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento()instanceof PagamentoComBoleto){ //Essa condição é para acrescentar uma semana no boleto
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            //Cria uma classe
            boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());
            
        }
        obj = repo.save(obj);//salvando o obj
        pagamentoRepository.save(obj.getPagamento());//Salvando o pagamento
        //Desconto
        for(ItemPedido ip:obj.getItens()){
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());//na outra versão é produtoRepository
            //ip.setPreco(produtoRepository);
            ip.setPedido(obj);
            
        }
        itemPedidoRepository.saveAll(obj.getItens());//Salvando tudo 
        return obj;//Retornando o objeto do for each
    }
    
    
}
