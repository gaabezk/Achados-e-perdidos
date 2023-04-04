package br.com.gabezk.achadoseperdidos.models.entity;

import br.com.gabezk.achadoseperdidos.enuns.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Entity
@Table(name = "tb_postagem")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "titulo",nullable = false)
    private String title;
    @Column(name = "descricao",nullable = false)
    private String description;
    @Column(name = "imagem_url1",nullable = false)
    private String imageUrl1;
    @Column(name = "imagem_url2")
    private String imageUrl2;
    @Column(name = "imagem_url3")
    private String imageUrl3;
    @Column(name = "local_encontrado",nullable = false)
    private String foundLocation;
    @Column(name = "data_encontrado")
    private LocalDate dateFound;
    @Column(name = "cidade",nullable = false)
    private String city;
    @Column(name = "data_criacao",nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now(ZoneId.systemDefault());
    @Column(name = "data_ultimo_update",nullable = false)
    private LocalDateTime lastUpdateDate = creationDate;
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.WaitingApproval;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
