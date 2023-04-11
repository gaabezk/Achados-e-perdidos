package br.com.gabezk.achadoseperdidos.models.entity;

import br.com.gabezk.achadoseperdidos.enuns.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    private Role role = Role.USER;
    @Column(name = "codigo_reset_senha")
    private String codeToResetPassword;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return hashPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
