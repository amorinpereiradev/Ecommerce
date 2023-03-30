package com.ecommerce.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ClienteRequestDto {

	@Size(min = 6, max = 150, message = "Nome do cliente deve ser de 6 a 150 caracteres.")
	@NotBlank(message = "Nome do cliente é obrigatório.")
	private String nome;
	
	@Email(message = "Email de cliente inválido")
	@NotBlank(message = "Email do cliente é obrigatório.")
	private String email;
	
	@Pattern(regexp = "(^$|[0-9]{11})", message = "Telefone deve ter 11 dígitos numéricos.")
	@NotBlank(message = "Telefone do cliente é obrigatório.")
	private String telefone;
	
	@Size(min = 8, max = 20, message = "Senha do cliente é obrigatória.")
	@NotBlank(message = "Senha do cliente é obrigatória.")
	private String senha;
	
}
