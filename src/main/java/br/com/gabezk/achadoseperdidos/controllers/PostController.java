package br.com.gabezk.achadoseperdidos.controllers;

import br.com.gabezk.achadoseperdidos.enuns.PostStatus;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.PostRequestDto;
import br.com.gabezk.achadoseperdidos.models.dtos.PostResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.PostUpdateDto;
import br.com.gabezk.achadoseperdidos.services.interfaces.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/post")
@Validated
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    @Operation(summary = "Obter todos os posts", description = "Recupera todos os posts existentes")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/allByStatus")
    @Operation(summary = "Obter posts por status", description = "Recupera todos os posts existentes com um status específico")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByStatus(@Parameter(description = "Status do post", required = true) @RequestHeader("Status") @Valid PostStatus status) {
        return ResponseEntity.ok(postService.getAllPostsByStatus(status));
    }

    @GetMapping("/allApproved")
    @Operation(summary = "Obter todos os posts aprovados", description = "Recupera todos os posts aprovados existentes")
    public ResponseEntity<List<PostResponseDto>> getAllPostsApproved() {
        return ResponseEntity.ok(postService.getAllPostsApproved());
    }

    @GetMapping("/allByUserAndStatus")
    @Operation(summary = "Obter posts por usuário e status", description = "Recupera todos os posts existentes criados por um usuário específico e com um status específico")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByUserAndStatus(@Parameter(description = "ID do usuário", required = true) @RequestHeader("Id") UUID userId, @Parameter(description = "Status do post", required = true) @RequestHeader("Status") @Valid PostStatus status) throws ErrorException {
        return ResponseEntity.ok(postService.getAllPostsByUserAndStatus(userId, status));
    }

    @GetMapping("/allByUserId")
    @Operation(summary = "Obter posts por ID do usuário", description = "Recupera todos os posts existentes criados por um usuário específico")
    public ResponseEntity<List<PostResponseDto>> getAllByUserId(@Parameter(description = "ID do usuário", required = true) @RequestHeader("Id") UUID userId) throws ErrorException {
        return ResponseEntity.ok(postService.getAllByUserId(userId));
    }

    @GetMapping("/allByCity")
    @Operation(summary = "Obter posts por cidade", description = "Recupera todos os posts existentes criados em uma cidade específica")
    public ResponseEntity<List<PostResponseDto>> getAllByCity(@Parameter(description = "Nome da cidade", required = true) @RequestHeader("City") String city) {
        return ResponseEntity.ok(postService.getAllByCity(city));
    }

    @GetMapping("/byId")
    @Operation(summary = "Obter post por ID", description = "Recupera um post com um ID específico")
    public ResponseEntity<PostResponseDto> getPostById(@Parameter(description = "ID do post", required = true) @RequestHeader("Id") UUID id) throws ErrorException {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo post", description = "Cria um novo post com os dados fornecidos")
    public ResponseEntity<PostResponseDto> createPost(@Parameter(description = "ID do usuário", required = true) @RequestHeader("Id") UUID userId, @RequestBody @Valid PostRequestDto postRequestDto) throws ErrorException {
        return new ResponseEntity<>(postService.createPost(userId, postRequestDto), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Atualizar post existente", description = "Atualiza um post existente pelo Id do post e Id do usuário autenticado")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody @Valid PostUpdateDto postUpdateDto, @Parameter(description = "Id do usuário proprietário do post", required = true, in = ParameterIn.HEADER) @RequestHeader("post_id") UUID postId, @Parameter(description = "Id do usuário proprietário do post", required = true, in = ParameterIn.HEADER) @RequestHeader("user_id") UUID userId) throws ErrorException {
        return ResponseEntity.ok(postService.updatePost(postId, userId, postUpdateDto));
    }

    @DeleteMapping
    @Operation(summary = "Deletar post por Id", description = "Deleta um post pelo Id informado.")
    @Parameter(name = "Id", description = "Id do post a ser deletado", required = true)
    public ResponseEntity<String> deletePost(@RequestHeader("Id") UUID id) throws ErrorException {
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PutMapping("/status")
    @Operation(summary = "Atualizar status do post", description = "Atualiza o status de um post pelo Id informado.")
    @Parameter(name = "Id", description = "Id do post a ter o status atualizado", required = true)
    @Parameter(name = "Status", description = "Novo status para o post", required = true)
    public ResponseEntity<String> updateStatus(@RequestHeader("Id") UUID id, @RequestHeader("Status") @Valid PostStatus status) throws ErrorException {
        return ResponseEntity.ok(postService.updateStatus(id, status));
    }

}
