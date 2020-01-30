/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.codigos.services;


import com.example.codigos.domain.Cidade;
import com.example.codigos.domain.Cliente;
import com.example.codigos.domain.Endereco;
import com.example.codigos.domain.enums.TipoCliente;
import com.example.codigos.dto.ClienteDTO;
import com.example.codigos.dto.ClienteNewDTO;
import com.example.codigos.repositories.CidadeRepository;
import com.example.codigos.repositories.ClienteRepository;
import com.example.codigos.repositories.EnderecoRepository;
import com.example.codigos.services.exceptions.DataIntegrityException;
import com.example.codigos.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Diego
 * 
 * O service é quem faz o serviço, aqui estão os métodos de fato que irão Fazer as operações de CRUD
 */



@Service
public class ClienteService {
    
    @Autowired //em teoria essa anotation exclui a nessecidade de declarar o método na interface 
    private ClienteRepository repo; // Guardei a requisição em uma variável la da classe CategoriaRepository
    
    @Autowired
    private CidadeRepository cidadeRepository; // Lembrando que cliente está associado a endereço e cidade,precisamos juntas as coisas aqui também
    
    @Autowired
    private EnderecoRepository enderecoRepository; //Lembrando que cliente está associado a endereço e cidade,precisamos juntas as coisas aqui também
    
    
    public Cliente find(Integer id){
        
        Optional<Cliente> obj = repo.findById(id);
        //Fazendo um testizinho nesse obj
        //Criamos uma classe para lidar com a Excessão está no pacote exceptions
        //Não conseguir ainda fazer retornar o 404 not found
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado ID" + id + "Tipo de Objeto:" + Cliente.class.getName());
        }
        
        
        //return obj;
        //O return tá usando expressão lambda
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não Encontrado! Id: "+id + ", Tipo" + Cliente.class.getName()));
         
    }// Fim do find
    
    
    
    //Inserir Cliente
    //Sempre que implementamos um método de Crud aqui em serviço é necessário também criar uma sobrecarga do método
    //Essa sobrecarga se deve ao uso de duas classes uma padrãoe outra DTO
    //
    @Transactional //Define que existe uma transição individual dos atributos (porque usaremos duas classes na mesma transação do banco de dados)
    public Cliente insert(Cliente obj){
        obj.setId(null);// no momento da inserção o objeto id precisa ser nulo senão ele considera como update -Garantia
        obj =  repo.save(obj);
        //Temos que salvar também o endereço junto com o cliente, o telefone tá sendo salvo lá no enum
        enderecoRepository.saveAll(obj.getEnderecos());//(save = Apenas para a versão 1.2 aqui é saveAll)
        return obj;
    }
    
    //Mesmas coisas do Categoria só que agora no service de cliente
    
    //Veja o método auxiliar lá embaixo
    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj,obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é Possível excluir Cliente que possui Pedidos");

        }

    }

    // Retorando todas as categorias
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    // Páginando - Para evitar muito processamento e uso de memória podemos paginar o retorno
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    // DTO´s
    //Transformamos a requisição de Categoria para CategoriaDTO e por isso precisamos criar um novo método
    //Método auxiliar que instancia uma categoria a partir de um DTO
    public Cliente fromDTO(ClienteDTO objDto) {
        //Ainda não fizemos essa
        //throw new UnsupportedOperationException();
       return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);
        
    }
    
    
    //Método auxiliar do update
    //LEmbrando que no update precisamos buscar os dados e atualizar e por isso um método auxiliar
    private void updateData(Cliente newObj,Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
                
    }
    
    //Sobrecarga do Insert - Veja lá em cima
    public Cliente fromDTO(ClienteNewDTO objDto){
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo())); // -- Atente-se para o tipo toEnum
        //Lembrando - Cidade é Entidade então precisamos instanciar também, não é atributo.
        //Cidade cid = cidadeRepository.getOne(objDto.getCidadeId());// Vamos alterar essa linha mas ela também funciona
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null); //Como na de cima basta apenas instanciar o objeto diretamente sem passar pelo repository(desnecessário)
        Endereco end =new Endereco(null,objDto.getLogradouro(),objDto.getNumero(),objDto.getComplemento(),objDto.getBairro(), objDto.getCep(),cid,cli);
        cli.getEnderecos().add(end);// Incluindo a lista de endereços no cliente
        cli.getTelefones().add(objDto.getTelefone1()); // Incluindo telefone no cliente
        if(objDto.getTelefone2() != null){// Regrinha - Testando a validade do telefone opcionalk
             cli.getTelefones().add(objDto.getTelefone2());
        }
        if(objDto.getTelefone3() != null){
             cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    
    
    
}//Fim da classe
