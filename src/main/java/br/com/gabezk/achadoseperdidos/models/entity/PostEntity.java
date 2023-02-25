package br.com.gabezk.achadoseperdidos.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_postagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo")
    private String title;
    @Column(name = "descricao")
    private String description;
    @Column(name = "imagem_url1")
    private String imagemUrl1;
    @Column(name = "imagem_url2")
    private String imagemUrl2;
    @Column(name = "imagem_url3")
    private String imagemUrl3;
    @Column(name = "local_encontrado")
    private String foundLocation;
    @Column(name = "data_encontrado")
    private LocalDate dateFound;
    @Column(name = "cidade")
    private String city;
    @Column(name = "data_criacao")
    private LocalDateTime creationDate;
    @Column(name = "data_ultimo_update")
    private LocalDateTime lasUpdateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


}
