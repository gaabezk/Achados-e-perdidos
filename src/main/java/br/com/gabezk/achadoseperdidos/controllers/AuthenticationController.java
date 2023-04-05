package br.com.gabezk.achadoseperdidos.controllers;

import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.LoginDto;
import br.com.gabezk.achadoseperdidos.services.AuthenticationService;
import br.com.gabezk.achadoseperdidos.models.dtos.AuthResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(
      @RequestBody @Valid UserRequestDto request
  ) throws ErrorException {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponseDto> authenticate(
      @RequestBody LoginDto loginDto
  ) {
    return ResponseEntity.ok(service.authenticate(loginDto));
  }


}
