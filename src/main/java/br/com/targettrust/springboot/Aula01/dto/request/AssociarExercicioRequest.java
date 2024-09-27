package br.com.targettrust.springboot.Aula01.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class AssociarExercicioRequest {

    @NotNull
    @Size(min = 1)
    private List<Long> exercicios;
}
