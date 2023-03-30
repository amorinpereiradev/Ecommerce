package com.ecommerce.controllers;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dtos.ClienteRequestDto;
import com.ecommerce.dtos.ClienteResponseDto;
import com.ecommerce.dtos.EmailMessageDto;
import com.ecommerce.helpers.ClienteEmailHelper;
import com.ecommerce.models.Cliente;
import com.ecommerce.producers.EmailMessageProducer;
import com.ecommerce.services.ClienteServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@Api(tags = "Clientes")
@RestController
public class ClienteController {

	@Autowired
	ClienteServices clienteServices;
	
	@Autowired
	private EmailMessageProducer messageProducer;
	
	@Autowired
    private	ObjectMapper objectMapper;
	
	@PostMapping("/v1/clientes")
	public ResponseEntity<ClienteResponseDto> post(@Valid @RequestBody ClienteRequestDto dto){
		
		ClienteResponseDto responseDto = new ClienteResponseDto();
		HttpStatus status = null;
		
		try {
			
			ModelMapper modelMapper = new ModelMapper();
			
			Cliente cliente = clienteServices.save(modelMapper.map(dto, Cliente.class));
			
			responseDto.setMessage("Cliente Cadastrado com Sucesso");
			responseDto.setData(cliente);
			
			status = HttpStatus.CREATED;
			
			EmailMessageDto messageDto = ClienteEmailHelper.gerarMensagemDeCriacaoDeConta(cliente);
			String message = objectMapper.writeValueAsString(messageDto);
			messageProducer.send(message);
			
		} catch (IllegalArgumentException e) {
		
			responseDto.setMessage(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
			
		}
		
		catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(status).body(responseDto);
	}
	
}
