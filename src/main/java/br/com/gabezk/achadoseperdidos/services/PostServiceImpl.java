package br.com.gabezk.achadoseperdidos.services;

import br.com.gabezk.achadoseperdidos.enuns.PostStatus;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.PostRequestDto;
import br.com.gabezk.achadoseperdidos.models.dtos.PostResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.PostUpdateDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserResponseDto;
import br.com.gabezk.achadoseperdidos.models.entity.PostEntity;
import br.com.gabezk.achadoseperdidos.models.entity.UserEntity;
import br.com.gabezk.achadoseperdidos.repositories.PostRepository;
import br.com.gabezk.achadoseperdidos.services.interfaces.PostService;
import br.com.gabezk.achadoseperdidos.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final Type typeToken = new TypeToken<List<PostResponseDto>>() {
    }.getType();
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<PostResponseDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return modelMapper.map(postEntities, new TypeToken<List<PostResponseDto>>(){}.getType());
    }

    @Override
    public List<PostResponseDto> getAllPostsByStatus(PostStatus status) {
        return modelMapper.map(postRepository.findAllByStatus(status), typeToken);
    }
    @Override
    public List<PostResponseDto> getAllPostsApproved() {
        return modelMapper.map(postRepository.findAllByStatus(PostStatus.Approved), typeToken);
    }

    @Override
    public List<PostResponseDto> getAllPostsByUserAndStatus(UUID userId, PostStatus status) throws ErrorException {
        if (!userService.existsById(userId)) throw new ErrorException("Usuário não encontrado com o id:" + userId);
        return modelMapper.map(postRepository.findAllByUserIdAndStatus(userId, status), typeToken);
    }

    @Override
    public List<PostResponseDto> getAllByUserId(UUID userId) throws ErrorException {
        if (!userService.existsById(userId)) throw new ErrorException("Usuário não encontrado com o id:" + userId);
        return modelMapper.map(postRepository.findAllByUserId(userId), typeToken);
    }

    @Override
    public List<PostResponseDto> getAllByCity(String city) {
        return modelMapper.map(postRepository.findAllByCity(city), typeToken);
    }

    @Override
    public PostResponseDto getPostById(UUID id) throws ErrorException {
        var post = postRepository.findById(id).orElseThrow(() -> new ErrorException("Postagem não encontrada com o id: " + id));
        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public PostResponseDto createPost(UUID userId, PostRequestDto postRequestDto) throws ErrorException {
        PostEntity data;
        try {
            if (!userService.existsById(userId)) throw new ErrorException("Usuário não encontrado com o id:" + userId);

            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);

            PostEntity postEntity = modelMapper.map(postRequestDto, PostEntity.class);
            postEntity.setUser(userEntity);

            data = postRepository.save(postEntity);
        }catch (Exception ex){
            throw new ErrorException(ex.getMessage());
        }
        return modelMapper.map(data, PostResponseDto.class);
    }

    @Override
    public PostResponseDto updatePost(UUID idPost, UUID idUser, PostUpdateDto postUpdateDto) throws ErrorException {
        PostEntity post = postRepository.findById(idPost)
                .orElseThrow(() -> new ErrorException("Postagem com id: " + idPost + " não existe!"));

        UserResponseDto user = userService.getUserById(idUser);

        if(!post.getUser().getId().equals(user.id)){
            throw new ErrorException("Id do usuário está incorreto!");
        }

        post.setLastUpdateDate(LocalDateTime.now(ZoneId.systemDefault()));
        post.setStatus(PostStatus.WaitingApproval);

        try{
            modelMapper.map(postUpdateDto, post);
            postRepository.save(post);
        }catch (Exception ex){
            throw new ErrorException(ex.getMessage());
        }

        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public String deletePost(UUID id) throws ErrorException {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ErrorException("Postagem com id: " + id + " não existe!"));
        try {
            postRepository.delete(post);
            return "Postagem deletada com sucesso!";
        }catch (Exception ex){
            throw new ErrorException(ex.getMessage());
        }
    }

    @Override
    public String deletePostByUserId(UUID postId, UUID userId) throws ErrorException {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException("Postagem com id: " + postId + " não existe!"));

        if(!post.getUser().getId().equals(userId)){
            throw new ErrorException("esse post nao pertence a esse usuario!");
        }

        try {
            postRepository.delete(post);
            return "Postagem deletada com sucesso!";
        }catch (Exception ex){
            throw new ErrorException(ex.getMessage());
        }
    }

    @Override
    public String updateStatus(UUID id, PostStatus status) throws ErrorException {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ErrorException("Postagem com id: " + id + " não existe!"));

        post.setStatus(status);
        postRepository.save(post);

        return "Status: "+post.getStatus().toString();
    }
}
