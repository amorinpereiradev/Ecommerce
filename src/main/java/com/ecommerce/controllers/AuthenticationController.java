package com.ecommerce.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dtos.AuthenticationRequestDto;
import com.ecommerce.dtos.AuthenticationResponseDto;
import com.ecommerce.models.Cliente;
import com.ecommerce.security.TokenAuthenticationService;
import com.ecommerce.services.ClienteServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "")
@RestController
public class AuthenticationController {

	@Autowired
	TokenAuthenticationService authentication;
	
	@Autowired
	ClienteServices clienteServices;
	
	@ApiOperation("Serviço para Autenticação de cliente")
	@PostMapping("v1/auth")
	public ResponseEntity<AuthenticationResponseDto> post(@Valid @RequestBody AuthenticationRequestDto dto ){
		
		AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
		HttpStatus status = null;
		
		try {
			
			Cliente cliente = clienteServices.get(dto.getEmail(), dto.getSenha());
			
			authenticationResponseDto.setMessage("Cliente Autorizado com Sucesso");
			authenticationResponseDto.setAccessToken(authentication.generateToken(cliente.getEmail()));
			authenticationResponseDto.setData(cliente);
			
			status = HttpStatus.OK;
			
	
		} catch (IllegalArgumentException e) {
			
			authenticationResponseDto.setMessage(e.getMessage());
			status = HttpStatus.UNAUTHORIZED;
		}
		
		
		return ResponseEntity.status(status).body(authenticationResponseDto);
	}
	
}
