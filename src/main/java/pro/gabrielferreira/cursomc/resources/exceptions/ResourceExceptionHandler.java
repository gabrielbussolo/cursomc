package pro.gabrielferreira.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pro.gabrielferreira.cursomc.services.exceptions.DataIntegrityException;
import pro.gabrielferreira.cursomc.services.exceptions.ObjectNotFoundException;

//nao entendi muito bem, mas funciona, faz com que retorne uma msg de erro mais bonitinha.
@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler //tratamento de erro para 404 not found
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler //tratamento de erro para bad request 400
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
