package br.com.targettrust.springboot.Aula01.Model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "exercicio_seq", sequenceName = "exercicio_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercicio {

    //UUID id ---- Versões mais usadas do UUID V4 (Random), V1 (Ordenavel, macadress), V7 (Timestampted, Ordenavel)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercicio_seq")
    private Long id;
    @Column(length = 100, unique = true, nullable = false)
    private String nome;
    @Column(length = 150, nullable = false)
    private String parteCorpo;

    @ManyToMany(mappedBy = "exercicios")
    private List<Cliente> clientes;

}
