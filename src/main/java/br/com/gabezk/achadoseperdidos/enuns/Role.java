package br.com.gabezk.achadoseperdidos.enuns;

import jakarta.validation.constraints.NotBlank;

public enum Role {
    @NotBlank
    USER,
    @NotBlank
    ADMIN
}
