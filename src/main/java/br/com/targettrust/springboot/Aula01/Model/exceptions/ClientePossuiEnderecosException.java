package br.com.targettrust.springboot.Aula01.Model.exceptions;

import lombok.Getter;
import lombok.Value;

@Value
public class ClientePossuiEnderecosException extends RuntimeException{
    private final Long clienteId;


    public ClientePossuiEnderecosException(Long clienteId) {
        this.clienteId = clienteId;
    }
}
