package br.com.targettrust.springboot.Aula01.Controller;

import br.com.targettrust.springboot.Aula01.Service.ExercicioService;
import br.com.targettrust.springboot.Aula01.dto.request.ExercicioRequest;
import br.com.targettrust.springboot.Aula01.dto.response.ExercicioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/exercicios")
@RequiredArgsConstructor
public class ExerciciosController {


    private final ExercicioService exercicioService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExercicioResponse createExercicio(@RequestBody @Valid ExercicioRequest exercicioRequest){
        return ExercicioResponse.fromModel(
                exercicioService.criarExercicio(exercicioRequest.toModel())
        );
    }

}
