package br.com.gabezk.achadoseperdidos.exceptions;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErroPrincipal {

    private ZonedDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
