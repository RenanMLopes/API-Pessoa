package br.com.targettrust.springboot.Aula01.Model.exceptions;

import lombok.Value;

@Value
public class RegistryNotFoundException extends RuntimeException {

    private final Long id;

}
