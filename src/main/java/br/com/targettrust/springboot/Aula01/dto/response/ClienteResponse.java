package br.com.targettrust.springboot.Aula01.dto.response;

import br.com.targettrust.springboot.Aula01.Model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class ClienteResponse {
    private Long id;
    private  String nome;
    private  String cpf;
    private LocalDate dataNascimento;

    private List<EnderecoResponse> enderecos;
    public static ClienteResponse fromModel(Cliente cliente){
        return  ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .enderecos(
                        cliente.getEnderecos().stream()
                                .map(EnderecoResponse::fromModel)
                                .toList()
                )
                .build();

    }
}
