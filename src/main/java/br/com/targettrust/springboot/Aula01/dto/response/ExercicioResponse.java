package br.com.targettrust.springboot.Aula01.dto.response;

import br.com.targettrust.springboot.Aula01.Model.Exercicio;
import lombok.Builder;

@Builder
public record ExercicioResponse(
        Long id,
        String nome,
        String parteCorpo
) {

    public static ExercicioResponse fromModel(Exercicio exercicio){
        //possibilidade quando tem pouca variavel
        //return new ExercicioResponse(exercicio.getId(), exercicio.getNome(), exercicio.getParteCorpo());
        //mas na maioria das vezes é melhor usar o @Builder

        return ExercicioResponse.builder()
                .id(exercicio.getId())
                .nome(exercicio.getNome())
                .parteCorpo(exercicio.getParteCorpo())
                .build();
    }
}
