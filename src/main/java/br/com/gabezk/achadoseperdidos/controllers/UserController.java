package br.com.gabezk.achadoseperdidos.controllers;

import br.com.gabezk.achadoseperdidos.enuns.Role;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserUpdateDto;
import br.com.gabezk.achadoseperdidos.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
@Validated
@SecurityRequirement(name = "Bearer Authentication")
@EnableWebSecurity
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lista todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema.")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/allByRole")
    @Operation(summary = "Lista todos os usuários por perfil",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema que possuem um perfil específico.")
    public ResponseEntity<List<UserResponseDto>> getAllUsersByRole(@Parameter(description = "Perfil do usuário", required = true) @RequestHeader("Role") @Valid Role role) {
        return ResponseEntity.ok(userService.getAllUsersByRole(role));
    }

    @GetMapping("/byId")
    @Operation(summary = "Retorna um usuário pelo ID",
            description = "Retorna as informações de um usuário cadastrado no sistema, com base no seu ID.")
    public ResponseEntity<UserResponseDto> getUserById(@Parameter(description = "ID do usuário", required = true) @RequestHeader("Id") UUID id) throws ErrorException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/byEmail")
    @Operation(summary = "Retorna um usuário pelo e-mail",
            description = "Retorna as informações de um usuário cadastrado no sistema, com base no seu e-mail.")
    public ResponseEntity<UserResponseDto> getUserByEmail(@Parameter(description = "E-mail do usuário", required = true) @RequestHeader("Email") String email) throws ErrorException {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/byPhone")
    @Operation(summary = "Retorna um usuário pelo telefone",
            description = "Retorna as informações de um usuário cadastrado no sistema, com base no seu número de telefone.")
    public ResponseEntity<UserResponseDto> getUserByPhone(@Parameter(description = "Telefone do usuário", required = true) @RequestHeader("Phone") String phone) throws ErrorException {
        return ResponseEntity.ok(userService.getUserByPhone(phone));
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário",
            description = "Registra um novo usuário no sistema, a partir das informações fornecidas no corpo da requisição.")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto requestDto) throws ErrorException {
        return new ResponseEntity<>(userService.createUser(requestDto), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Atualiza informações do usuário",
            description = "Atualiza as informações de um usuário já cadastrado no sistema, a partir das informações fornecidas no corpo da requisição.")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserUpdateDto updateDto, @Parameter(description = "ID do usuário", required = true) @RequestHeader("Id") UUID uuid) throws ErrorException {
        return ResponseEntity.ok(userService.updateUser(uuid, updateDto));
    }

    @PutMapping("/role")
    @Operation(summary = "Atualiza perfil do usuário",
            description = "Atualiza o perfil de um usuário já cadastrado no sistema, a partir do perfil informado no cabeçalho da requisição.")
    public ResponseEntity<String> updateRole(@Parameter(description = "Perfil do usuário", required = true) @RequestHeader("Role") @Valid Role role, @Parameter(description = "ID do usuário", required = true) @RequestHeader("Id") UUID uuid) throws ErrorException {
        return ResponseEntity.ok(userService.updateRole(uuid, role));
    }

    @Operation(summary = "Atualiza a senha de um usuário",
            description = "Este endpoint permite atualizar a senha de um usuário, informando o ID do usuário e a senha antiga e nova senha através dos cabeçalhos da solicitação.")
    @PutMapping("/pass")
    public ResponseEntity<String> updatePassword(
            @Parameter(description = "Senha antiga do usuário") @RequestHeader("old_pass") String oldPass,
            @Parameter(description = "Nova senha do usuário") @RequestHeader("new_pass") String newPass,
            @Parameter(description = "ID do usuário") @RequestHeader("Id") UUID uuid
    ) throws ErrorException {
        return ResponseEntity.ok(userService.updatePassword(uuid, oldPass, newPass));
    }

    @Operation(summary = "Exclui um usuário",
            description = "Este endpoint permite excluir um usuário do sistema, informando o ID do usuário através do cabeçalho da solicitação.")
    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "ID do usuário a ser excluído") @RequestHeader("Id") UUID uuid
    ) throws ErrorException {
        return ResponseEntity.ok(userService.deleteUser(uuid));
    }

    @GetMapping("/exists/id")
    @Operation(summary = "Verifica se existe um usuário com o ID informado")
    public ResponseEntity<Boolean> existsById(
            @Parameter(description = "ID do usuário", required = true)
            @RequestHeader("Id") UUID id) {
        boolean exists = userService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email")
    @Operation(summary = "Verifica se existe um usuário com o email informado")
    public ResponseEntity<Boolean> existsByEmail(
            @Parameter(description = "E-mail do usuário", required = true)
            @RequestHeader("Email") String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/phone")
    @Operation(summary = "Verifica se existe um usuário com o telefone informado")
    public ResponseEntity<Boolean> existsByPhone(
            @Parameter(description = "Telefone do usuário", required = true)
            @RequestHeader("Phone") String phone) {
        boolean exists = userService.existsByPhone(phone);
        return ResponseEntity.ok(exists);
    }
}
