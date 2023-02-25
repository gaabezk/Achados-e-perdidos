package br.com.gabezk.achadoseperdidos.models.entity;

import br.com.gabezk.achadoseperdidos.enuns.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "primeiro_nome",nullable = false)
    private String firstName;
    @Column(name = "ultimo_nome",nullable = false)
    private String lastName;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "telefone",unique = true,nullable = false)
    private String phone;
    @Column(name = "senha_encryptada",nullable = false)
    private String hashPassword;
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;
    @Column(name = "codigo_reset_senha")
    private String codeToResetPassword;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

}
