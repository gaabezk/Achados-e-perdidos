package br.com.gabezk.achadoseperdidos.services.interfaces;

import br.com.gabezk.achadoseperdidos.enuns.PostStatus;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.PostRequestDto;
import br.com.gabezk.achadoseperdidos.models.dtos.PostResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.PostUpdateDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDto> getAllPosts();
    List<PostResponseDto> getAllPostsByStatus(PostStatus status);
    List<PostResponseDto> getAllPostsByUserAndStatus(UUID userId, PostStatus status) throws ErrorException;
    List<PostResponseDto> getAllByUserId(UUID userId) throws ErrorException;
    List<PostResponseDto> getAllByCity(String city);
    PostResponseDto getPostById(UUID id) throws ErrorException;
    PostResponseDto createPost(UUID userId, PostRequestDto postRequestDto) throws ErrorException;
    PostResponseDto updatePost(UUID idPost, UUID idUser, PostUpdateDto postUpdateDto) throws ErrorException;
    String deletePost(UUID id) throws ErrorException;
    String updateStatus(UUID id, PostStatus status) throws ErrorException;
}
