package br.com.targettrust.springboot.Aula01.Service;

import br.com.targettrust.springboot.Aula01.Model.Endereco;
import br.com.targettrust.springboot.Aula01.Model.Exercicio;
import br.com.targettrust.springboot.Aula01.Model.Cliente;
import br.com.targettrust.springboot.Aula01.Model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.Aula01.Repository.ExercicioRepository;
import br.com.targettrust.springboot.Aula01.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ExercicioRepository exercicioRepository;

    public List<Cliente> listarCliente(String nome){
    return clienteRepository.findAll();
    }


    @Transactional
    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.create(cliente);
    }

    public Optional<Cliente> findById(Long id) {
       return clienteRepository.findById(id);
    }

    public Optional<Cliente> editarCliente(Long id, Cliente cliente) {
      return Optional.ofNullable(clienteRepository.update(cliente, id));
    }

    public Optional<Cliente> editarClienteParcial(Long id, Cliente cliente) {
        //Teria que ser uma edição parcial
        return Optional.ofNullable(clienteRepository.update(cliente, id));
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}

