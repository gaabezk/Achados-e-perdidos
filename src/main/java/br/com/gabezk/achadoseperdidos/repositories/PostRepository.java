package br.com.gabezk.achadoseperdidos.repositories;

import br.com.gabezk.achadoseperdidos.enuns.PostStatus;
import br.com.gabezk.achadoseperdidos.models.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,UUID> {
    List<PostEntity> findAllByStatus(PostStatus status);
    List<PostEntity> findAllByCity(String city);
    List<PostEntity> findAllByUserIdAndStatus(UUID userId, PostStatus status);
    List<PostEntity> findAllByUserId(UUID userId);
}
