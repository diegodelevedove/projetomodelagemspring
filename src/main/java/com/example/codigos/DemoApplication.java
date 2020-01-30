package com.example.codigos;

import com.example.codigos.domain.Categoria;
import com.example.codigos.domain.Cidade;
import com.example.codigos.domain.Cliente;
import com.example.codigos.domain.Endereco;
import com.example.codigos.domain.Produto;
import com.example.codigos.repositories.CategoriaRepository;
import com.example.codigos.repositories.CidadeRepository;
import com.example.codigos.repositories.EstadoRepository;
import com.example.codigos.repositories.ProdutoRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.codigos.domain.Estado;
import com.example.codigos.domain.ItemPedido;
import com.example.codigos.domain.Pagamento;
import com.example.codigos.domain.PagamentoComBoleto;
import com.example.codigos.domain.PagamentoComCartao;
import com.example.codigos.domain.Pedido;
import com.example.codigos.domain.enums.EstadoPagamento;
import com.example.codigos.domain.enums.TipoCliente;
import com.example.codigos.repositories.ClienteRepository;
import com.example.codigos.repositories.EnderecoRepository;
import com.example.codigos.repositories.ItemPedidoRepository;
import com.example.codigos.repositories.PagamentoRepository;
import com.example.codigos.repositories.PedidoRepository;
import java.text.SimpleDateFormat;
        

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{ //Sem o ComandLinerRunner ele não grava no banco
        //Ele obriga instanciar todos os métodos abstratos

        //Precisamos criar uma dependência pra guarda os dados de cat1 e cat2 no banco(quem é a classe responsãvel por isso - CategoryRepository)
        //Aqui fazemos referÊncia a classe repository e não a classe no domain
        @Autowired//Autowired é criar a dependência do repositorio que serve para chamar o JPA e persistir o objeto no banco
        private CategoriaRepository categoriaRepository; 
        @Autowired
        private ProdutoRepository produtoRepository;
        @Autowired
        private EstadoRepository estadoRepository;
        @Autowired
        private CidadeRepository cidadeRepository;
        @Autowired
        private ClienteRepository clienteRepository;
        @Autowired
        private EnderecoRepository enderecoRepository;
        @Autowired
        private PedidoRepository pedidoRepository;
        @Autowired
        private PagamentoRepository pagamentoRepository;
        @Autowired
        private ItemPedidoRepository itemPedidoRepository;
        
        
        
    
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
        // Daqui pra cima é na implementação padrão
        //Quando implemento o CommandLineRunner ele me obriga a implementar um método aqui
    @Override
    public void run(String... args) throws Exception {
        
        //Vou comentar apenas para lembrar que na construção do projeto essa classe foi responsável fazer o deploy
        //não é necessário mais para ele ter essas chamadas agora - Motivo: Criação dos profiles de Dev teste e Produção
        //Fica então nessa classe o main e o método run
        
        
        
        
        //*************************************************************************************************************//
        
            // Observe os comentário daqui pra baixo
        
        //*************************************************************************************************************//
        /*
        //Categoria
        Categoria cat1 = new Categoria(null,"Informática");
        Categoria cat2 = new Categoria(null,"Escritório");
        Categoria cat3 = new Categoria(null,"Cama Mesa e Banho");
        Categoria cat4 = new Categoria(null,"Eletrônicos");
        Categoria cat5 = new Categoria(null,"Jardinagem");
        Categoria cat6 = new Categoria(null,"Decoração");
        Categoria cat7 = new Categoria(null,"Perfumaria");
        
        
        //Produto
        Produto p1 = new Produto (null,"Computador",2000.00);
        Produto p2 = new Produto (null,"Impressora",3000.00);
        Produto p3 = new Produto (null,"Monitor",100.00);
        Produto p4 = new Produto (null,"Mesa de Escritório",300.00);
        Produto p5 = new Produto (null,"Toalha",50.00);
        Produto p6 = new Produto (null,"Colcha",30.00);
        Produto p7 = new Produto (null,"Tv 50 pol",1800.00);
        Produto p8 = new Produto (null,"Roçadeira",800.00);
        Produto p9 = new Produto (null,"Abajur",100.00);
        Produto p10 = new Produto (null,"Luminária",100.00);
        Produto p11 = new Produto (null,"Shampoo",10.00);
        
        
         //Associando Categoria e Produto(ver relações na modelageM)
        //Para Determinado produto tenho uma categoria que pode pertecer a duas ou uma ao mesmo tempo
        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2,p4));
        cat3.getProdutos().addAll(Arrays.asList(p5,p5));
        cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3,p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9,p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));
        
        
        
        
        p1.getCategoria().addAll(Arrays.asList(cat1,cat4));
        p2.getCategoria().addAll(Arrays.asList(cat1,cat2,cat4));
        p3.getCategoria().addAll(Arrays.asList(cat1,cat4));
        p4.getCategoria().addAll(Arrays.asList(cat2));
        p5.getCategoria().addAll(Arrays.asList(cat3));
        p6.getCategoria().addAll(Arrays.asList(cat3));
        p7.getCategoria().addAll(Arrays.asList(cat4));
        p8.getCategoria().addAll(Arrays.asList(cat5));
        p9.getCategoria().addAll(Arrays.asList(cat6));
        p10.getCategoria().addAll(Arrays.asList(cat6));
        p11.getCategoria().addAll(Arrays.asList(cat7));
        
        
        //Estado
        Estado est1 = new Estado(null,"Minas Gerais");
        Estado est2 = new Estado(null,"São Paulo");
        
        //Cidade
        Cidade c1 = new Cidade(null,"Uberlândia",est1);
        Cidade c2 = new Cidade(null,"São Paulo",est2);
        Cidade c3 = new Cidade(null,"Campinas",est2);
        
        //Vamos relacionar as Duas Classes e pegar ou associar o que elas tem
        
        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));
        
        
        //Cliente    
        Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","36715544",TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("33389578","33381215"));//Telefones do cliente
        
        

        //Endereço
       
       Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 203","Jardim","3677702",c1,cli1);
       Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 600","Centro","38225252",c2,cli1);
       
       //**Perceba que a ordem importa bastante aqui por conta das dependencias
       // Vamos associar o cliente com o endereço
       
       cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
        
        
        //Pedido
        //Formatando a Data do instante
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        
        Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"),cli1, e1);
        Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 19:35"),cli1, e2);
        
        //Pagamento 
        
        // PErceba que como a classe é abstrata instanciei o construtor da classe filha não podendo instanciar o construtor da classe abstrata
        Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped1,6);
        ped1.setPagamento(pagto1);
        
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sdf.parse("20/10/2017 00:00"),null);
        ped2.setPagamento(pagto2);   
        
        cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
        
        // ItemPedido
        
        ItemPedido ip1 = new ItemPedido(ped1, p1,0.00,1,2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3,0.00,2,80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2,100.00,1,800.00);
        ped1.getItens().addAll(Arrays.asList(ip1,ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));
        //Conhecendo o produto também dos pedidos (Veja entidades e relacionamentos)
        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));
        
         // A ordem de salvar os elementos importa
        //Observer que Ele anda na medida das dependências e associações é como se fosse o fluxo de dados
        
        //Persistindo Categoria
        categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7)); //saveAll é da versão 2.0 do spring
        //Persistindo Produto
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
        //Persistindo Estado
        estadoRepository.saveAll(Arrays.asList(est1,est2));
        //Tem que salvar o estado antes porque cidade depende dos atributos dele  
        //****** Agora salvamos cidade
        //Persistindo Cidade
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
        //Persistindo Cliente 
        clienteRepository.saveAll(Arrays.asList(cli1));
        //Persistindo o Endereco
        enderecoRepository.saveAll(Arrays.asList(e1,e2));
        //Persistindo Pedidos
        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
        //Persistindo Pagamento
        pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
        //Persistindo ItemPedido
        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
        */
        
        
        
    }//fim do run   

   

}//fim da classe
