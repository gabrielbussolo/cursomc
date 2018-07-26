package pro.gabrielferreira.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//extendo do modelo de ero que criei
public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();



	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message)); // adiciona um erro, uso o metodo pra percorrer a lista e
															// adicionar os erros
	}

}
