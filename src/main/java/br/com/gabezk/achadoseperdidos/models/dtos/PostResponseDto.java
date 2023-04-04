package br.com.gabezk.achadoseperdidos.models.dtos;

import br.com.gabezk.achadoseperdidos.enuns.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private UUID id;
    private String title;
    private String description;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String foundLocation;
    private LocalDate dateFound;
    private String city;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private PostStatus status;
    private String userFullName;
    private String userPhone;
    //private UUID userId;
}
