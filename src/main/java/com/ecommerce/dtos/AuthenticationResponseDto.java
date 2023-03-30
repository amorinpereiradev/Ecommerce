package com.ecommerce.dtos;

import com.ecommerce.models.Cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationResponseDto {

	private String message;
	private String accessToken;
	private Cliente data;
	
}
