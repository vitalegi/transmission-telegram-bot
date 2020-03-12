package it.vitalegi.transmissiontelegrambot.exception;

public class InvalidHeaderException extends RuntimeException {

	public InvalidHeaderException() {
		super();
	}

	public InvalidHeaderException(String msg) {
		super(msg);
	}
}