package br.com.targettrust.springboot.Aula01.Repository;

import br.com.targettrust.springboot.Aula01.Model.Cliente;
import br.com.targettrust.springboot.Aula01.Model.Endereco;
import br.com.targettrust.springboot.Aula01.Model.exceptions.ClientePossuiEnderecosException;
import br.com.targettrust.springboot.Aula01.Model.exceptions.RegistryNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {
    private final EntityManager entityManager;

    @Transactional
    public Cliente create(Cliente cliente){
        entityManager.persist(cliente);
        cliente.getEnderecos().forEach(endereco -> {
            endereco.setCliente(cliente);
            entityManager.merge(endereco);
        });
        return cliente;
    }

    @Transactional
    public Cliente update(Cliente cliente, Long id){
        Cliente clienteNoBanco = entityManager.find(Cliente.class, id);
        List<Endereco> enderecosNovos = new ArrayList<>();
        if (clienteNoBanco != null){
            clienteNoBanco.setNome(cliente.getNome());
            clienteNoBanco.setCpf(cliente.getCpf());
            clienteNoBanco.setDataNascimento(cliente.getDataNascimento());
            cliente.getEnderecos().forEach(
                    endereco -> {
                        if(endereco.getId() != null){
                            var enderecoNoBanco = entityManager.find(Endereco.class, endereco.getId());
                            if(enderecoNoBanco != null){
                                enderecoNoBanco.setNumero(endereco.getNumero());
                                enderecoNoBanco.setRua(endereco.getRua());
                                enderecoNoBanco.setCliente(clienteNoBanco);
                                enderecosNovos.add(enderecoNoBanco);
                            }else {
                                endereco.setId(null);
                                endereco.setCliente(clienteNoBanco);
                                enderecosNovos.add(endereco);
                            }
                        }else {
                            endereco.setCliente(clienteNoBanco);
                            enderecosNovos.add(endereco);
                        }

                    }
            );
            clienteNoBanco.setEnderecos(enderecosNovos);
            entityManager.merge(clienteNoBanco);
            return clienteNoBanco;
        }else {
            throw new RegistryNotFoundException(id);
        }
    }

    public List<Cliente> findAll(){
        //JPQL
        return entityManager.createQuery("select p from Cliente p", Cliente.class)
                .getResultList();
    }

    public Optional<Cliente> findById(Long id){
        return Optional.ofNullable(entityManager.find(Cliente.class, id));
    }

    @Transactional
    public void deleteById(Long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);

        Long numeroEnderecos = (Long) entityManager.createQuery("select count(e) from Cliente c join c.enderecos e where c.id = :clienteId")
                .setParameter("clienteId", id)
                .getSingleResult();

        if(numeroEnderecos!= null && numeroEnderecos > 0){
            throw new ClientePossuiEnderecosException(id);
        }

        entityManager.remove(cliente);
    }
}
