package br.com.gabezk.achadoseperdidos.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    @NotBlank
    private String foundLocation;
    private LocalDate dateFound;
    @NotBlank
    private String city;
}
