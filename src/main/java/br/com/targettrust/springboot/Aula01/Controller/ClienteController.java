package br.com.targettrust.springboot.Aula01.Controller;

import br.com.targettrust.springboot.Aula01.Model.Exercicio;
import br.com.targettrust.springboot.Aula01.Service.ClienteService;
import br.com.targettrust.springboot.Aula01.Service.ExercicioService;
import br.com.targettrust.springboot.Aula01.dto.request.AssociarExercicioRequest;
import br.com.targettrust.springboot.Aula01.dto.request.ClienteRequest;
import br.com.targettrust.springboot.Aula01.dto.response.ClienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/clientes")
@Slf4j
@RequiredArgsConstructor //cria um construtor com todas as propriedades final
@Tag(name = "Cadastro Academia", description = "Operações relacionadas ao CRUD de cliente no sistema (cliente fisca ou juridica)")
public class ClienteController {

    private final ClienteService clienteService;
    private final ExercicioService exercicioService;




    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Cria um cadastro")
    public ClienteResponse criarCliente(@RequestBody @Valid ClienteRequest cliente){
         return ClienteResponse.fromModel(clienteService.criarCliente(cliente.toModel()));
    }


    /**
     * Todo mais filtros
    **/
    @GetMapping()
    @Operation(description = "Lista os clientes cadastradas no sitema")
    public List<ClienteResponse> listarCliente(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "idade", required = false) Integer idade
    ){
        return clienteService.listarCliente(nome)
                .stream()
                .map(ClienteResponse::fromModel)
                .toList();
    }
    @GetMapping("/{id}")
    @Operation(description = "Localiza um cadastro pelo ID")
    public ResponseEntity<ClienteResponse> findById(@PathVariable(name = "id")Long id) {
        //Cliente cliente = clienteService.findById(id);
        return clienteService.findById(id)
                .map(ClienteResponse::fromModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PatchMapping("/{id}")
    @Operation(description = "Permite editar um cadastro por completo")
    public ResponseEntity<ClienteResponse> editarCliente(@PathVariable(name = "id") Long id, @RequestBody @Valid ClienteRequest cliente){
        return clienteService.editarCliente(id, cliente.toModel())
                .map(ClienteResponse::fromModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "{id}")
    @Operation(description = "Permite editar algum dado do cadastro")
    public ResponseEntity<ClienteResponse> editarClienteParcial(@PathVariable(name = "id") Long id, @RequestBody @Valid ClienteRequest cliente) {
     return clienteService.editarClienteParcial(id, cliente.toModel())
             .map(ClienteResponse::fromModel)
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Deleta um cadastro do sistema")
    public void deletarCliente(@PathVariable (name = "id") Long id){
         clienteService.deletarCliente(id);

    }

    @PostMapping(path = "/{id}/exercicios")
    @Operation(description = "Associa um exercicio ao cadastro de um cliente")
    public void associarExercicios(
        @PathVariable("id") Long idCliente,
        @RequestBody @Valid AssociarExercicioRequest associarExercicioRequest){
        log.info("Associar exercicio para cliente de id" + idCliente);
        log.info(associarExercicioRequest.getExercicios().stream()
               .map(Object::toString)
               .collect(Collectors.joining(",")));
        exercicioService.associarExercicios(idCliente, associarExercicioRequest.getExercicios());
    }
    @GetMapping (path = "/{id}/exercicios")
    @Operation(description = "Lista todos os exercicios cadastrados no sistema")
    public List<Exercicio> listarExercicios(
            @PathVariable("id") Long idCliente)
            {

                return exercicioService.findAllExerciciosByClientId(idCliente);
            }
}
