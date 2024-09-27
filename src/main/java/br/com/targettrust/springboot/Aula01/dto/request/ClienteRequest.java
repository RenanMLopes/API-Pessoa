package br.com.targettrust.springboot.Aula01.dto.request;

import br.com.targettrust.springboot.Aula01.Model.Cliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

    @NotBlank
    private  String nome;

    @NotBlank
    @CPF
    private  String cpf;

    @NotNull
    private LocalDate dataNascimento;

    @Size(min = 1)
    @NotNull
    @Valid
    private List<EnderecoRequest> enderecos;

    public Cliente toModel(){
        return  Cliente.builder()
                .nome(nome)
                .cpf(cpf)
                .dataNascimento(dataNascimento)
                .enderecos(
                        enderecos.stream()
                                .map(EnderecoRequest:: toModel)
                                .collect(Collectors.toList())
                )
                .build();
    }

}
