package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//intercepta exceções no resource
@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class) //sempre que houver alguma exceção nos controladores rest, o tratamento vais ser direcionado pra cá
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND; //retorna error 404
		err.setTimestamp(Instant.now());
		err.setStatus(status.value()); //o .value converte para inteiro
		err.setError("Resource not Found"); //esse erro irá aparecer quando buscarmos a entidade
		err.setMessage(e.getMessage()); //pega a msnsagem definida la no CategoryService
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class) //sempre que houver alguma exceção nos controladores rest, o tratamento vais ser direcionado pra cá
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST; //retorna error 400
		err.setTimestamp(Instant.now());
		err.setStatus(status.value()); //o .value converte para inteiro
		err.setError("Database Exception"); 
		err.setMessage(e.getMessage()); //pega a msnsagem definida la no CategoryService
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
