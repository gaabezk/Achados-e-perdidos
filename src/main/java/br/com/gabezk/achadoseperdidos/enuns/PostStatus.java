package br.com.gabezk.achadoseperdidos.enuns;

import jakarta.validation.constraints.NotBlank;

public enum PostStatus {
    @NotBlank
    WaitingApproval,
    @NotBlank
    Approved,
    @NotBlank
    Refused,
    @NotBlank
    Returned
}
