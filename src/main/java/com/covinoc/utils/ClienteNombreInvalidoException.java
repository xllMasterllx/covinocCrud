package com.covinoc.utils;

public class ClienteNombreInvalidoException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClienteNombreInvalidoException() {
        super("El nombre del cliente es obligatorio");
    }

}
