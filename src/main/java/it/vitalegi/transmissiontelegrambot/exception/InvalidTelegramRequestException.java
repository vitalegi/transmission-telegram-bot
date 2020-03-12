package it.vitalegi.transmissiontelegrambot.exception;

public class InvalidTelegramRequestException extends RuntimeException {

	public InvalidTelegramRequestException() {
		super();
	}

	public InvalidTelegramRequestException(String msg) {
		super(msg);
	}
}
