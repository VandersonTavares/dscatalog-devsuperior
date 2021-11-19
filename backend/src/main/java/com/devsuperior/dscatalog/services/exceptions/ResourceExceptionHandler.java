package com.devsuperior.dscatalog.services.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.resources.exceptions.StandardError;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

//intercepta exceções no resource
@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class) //sempre que houver alguma exceção nos controladores rest, o tratamento vais ser direcionado pra cá
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value()); //o .value converte para inteiro
		err.setError("Resource not Found"); //esse erro irá aparecer quando buscarmos a entidade
		err.setMessage(e.getMessage()); //pega a msnsagem definida la no CategoryService
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
				
		
	}
}
