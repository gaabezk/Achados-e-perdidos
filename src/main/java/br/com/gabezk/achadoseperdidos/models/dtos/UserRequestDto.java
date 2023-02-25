package br.com.gabezk.achadoseperdidos.models.dtos;

import br.com.gabezk.achadoseperdidos.validators.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank
    public String firstName;
    @NotBlank
    public String lastName;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    public String phone;
    @NotBlank
    @Password
    public String password;
}
