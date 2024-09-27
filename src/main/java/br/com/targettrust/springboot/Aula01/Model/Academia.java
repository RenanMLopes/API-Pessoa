package br.com.targettrust.springboot.Aula01.Model;



public record Academia(
        Long id,
        String nome,
        Endereco endereco
) {
}
