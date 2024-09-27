package br.com.targettrust.springboot.Aula01.Service;


import br.com.targettrust.springboot.Aula01.Model.Cliente;
import br.com.targettrust.springboot.Aula01.Model.Exercicio;
import br.com.targettrust.springboot.Aula01.Model.exceptions.ExercicioIdsNotFoundException;
import br.com.targettrust.springboot.Aula01.Model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.Aula01.Repository.ClienteRepository;
import br.com.targettrust.springboot.Aula01.Repository.ExercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;
    private final ClienteRepository clienteRepository;

    public Exercicio criarExercicio(Exercicio exercicio){
        return exercicioRepository.save(exercicio);
    }

    @Transactional
    public void associarExercicios (Long idCliente, List<Long> idExercicios){

        // Todo implementar logica de associação
        // Ir no banco localizar a cliente
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() ->new RegistryNotFoundException(idCliente));

        // localizar o id dos exercicios
        List<Exercicio> exercicios = exercicioRepository.findAllById(idExercicios);
        // regra de negocio: se id nao existir on banco não opera a transação
        var idExerciciosNoBanco = exercicios.stream()
                        .map(Exercicio::getId)
                        .toList();

           List<Long> idsNotExisting = new ArrayList<>();

           for (Long id: idExercicios) {
               if (!idExerciciosNoBanco.contains(id)) {
                   idsNotExisting.add(id);
               }
           }

           if(!idExerciciosNoBanco.containsAll(idExercicios)){
               throw new ExercicioIdsNotFoundException(idsNotExisting);
           }

        // criar cada associação (nova tabela de associação) uma para cada id passado
        cliente.setExercicios(exercicios);
        // Salva a associação
        clienteRepository.update(cliente, idCliente);
    }

    public List<Exercicio> findAllExerciciosByClientId(Long clienteId){
//        return clienteRepository.findById(clienteId)
//                .stream()
//                .map(Cliente::getExercicios)
//                .flatMap(List::stream)
//                .toList();
        return exercicioRepository.findAllByClienteId(clienteId);
    }
}
