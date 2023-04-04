package br.com.gabezk.achadoseperdidos.repositories;

import br.com.gabezk.achadoseperdidos.enuns.Role;
import br.com.gabezk.achadoseperdidos.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,UUID> {
    @Query("SELECT u FROM UserEntity u WHERE u.role = :role")
    List<UserEntity> findAllByRole(@Param("role") Role role);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
