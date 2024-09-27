package br.com.targettrust.springboot.Aula01.Repository;

import br.com.targettrust.springboot.Aula01.Model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
