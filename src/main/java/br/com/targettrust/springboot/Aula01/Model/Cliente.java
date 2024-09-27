package br.com.targettrust.springboot.Aula01.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", allocationSize = 1)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    private  Long id;

    @Column(length = 50, nullable = false)
    private  String nome;

    @Column(length = 14, unique = true, nullable = false)
    private  String cpf;

    @Column(nullable = false)
    private  LocalDate dataNascimento;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "cliente")
    private  List<Endereco> enderecos;

    @ManyToMany
    private List<Exercicio> exercicios;

    }



