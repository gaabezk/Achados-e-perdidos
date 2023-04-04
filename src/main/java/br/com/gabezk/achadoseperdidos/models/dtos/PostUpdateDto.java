package br.com.gabezk.achadoseperdidos.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl1;
    @NotBlank
    private String imageUrl2;
    @NotBlank
    private String imageUrl3;
    @NotBlank
    private String foundLocation;
    @NotNull
    private LocalDate dateFound;
    @NotBlank
    private String city;
}
