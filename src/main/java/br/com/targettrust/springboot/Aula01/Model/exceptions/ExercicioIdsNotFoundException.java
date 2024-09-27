package br.com.targettrust.springboot.Aula01.Model.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
public class ExercicioIdsNotFoundException extends RuntimeException{
    private final List<Long> ids;
}
