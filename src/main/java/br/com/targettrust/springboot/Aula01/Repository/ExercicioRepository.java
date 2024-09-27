package br.com.targettrust.springboot.Aula01.Repository;

import br.com.targettrust.springboot.Aula01.Model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    @Query("select Exercicio e from Cliente c join c.exercicios e where c.id = :clienteId")
    List<Exercicio> findAllByClienteId(@Param("clienteId") Long clienteId);

}
