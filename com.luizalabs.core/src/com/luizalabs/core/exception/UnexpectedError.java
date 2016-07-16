package com.luizalabs.core.exception;

/**
 * Erro inesperado padrao
 * 
 * @author danielsantalucia
 */
public final class UnexpectedError extends RuntimeException {

	private static final long serialVersionUID = 5045367726763902816L;

	private String message;
	
	public UnexpectedError(Exception error, String message) {
		super.initCause(error);
		this.message = message;
	}
	
	public UnexpectedError(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
