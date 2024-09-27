package br.com.targettrust.springboot.Aula01.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", allocationSize = 1)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    private Long id;
    private String rua;
    private Integer numero;


    @ManyToOne()
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_endereco_id"))
    private Cliente cliente;

}
