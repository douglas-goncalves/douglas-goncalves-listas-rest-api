package br.com.douglasgoncalves.listasrestapi.exceptions;

public class NotFoundBussinessException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public NotFoundBussinessException(String message) {
		super(message);
	}

}
