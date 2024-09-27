package br.com.targettrust.springboot.Aula01.dto.request;

import br.com.targettrust.springboot.Aula01.Model.Exercicio;
import br.com.targettrust.springboot.Aula01.dto.response.ExercicioResponse;
import jakarta.validation.constraints.NotBlank;

public record ExercicioRequest (
    @NotBlank
    String nome,
    @NotBlank
    String parteCorpo
) {
    public Exercicio toModel(){
        return Exercicio.builder()
                .nome(nome)
                .parteCorpo(parteCorpo)
                .build();
    }

}
