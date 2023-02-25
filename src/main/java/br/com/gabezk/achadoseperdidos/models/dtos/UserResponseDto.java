package br.com.gabezk.achadoseperdidos.models.dtos;

import java.util.UUID;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto{
    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;

}
