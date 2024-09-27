package br.com.targettrust.springboot.Aula01.dto.response;

import br.com.targettrust.springboot.Aula01.Model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
public class EnderecoResponse {
    private Long id;
    private String rua;
    private Integer numero;


    public static EnderecoResponse fromModel(Endereco endereco) {
        return EnderecoResponse.builder()
                .id(endereco.getId())
                .numero(endereco.getNumero())
                .rua(endereco.getRua())
                .build();
    }

}
