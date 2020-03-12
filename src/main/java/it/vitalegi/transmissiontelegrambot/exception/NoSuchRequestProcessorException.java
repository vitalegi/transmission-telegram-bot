package it.vitalegi.transmissiontelegrambot.exception;

import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

public class NoSuchRequestProcessorException extends RuntimeException {

	public NoSuchRequestProcessorException(RequestWrapper requestWrapper) {
		this(requestWrapper, "");
	}

	public NoSuchRequestProcessorException(RequestWrapper requestWrapper, String msg) {
		super(prepareMessage(requestWrapper, msg));
	}

	protected static String prepareMessage(RequestWrapper requestWrapper, String msg) {
		return msg + " Request: " + requestWrapper.toString();
	}
}
