package it.vitalegi.transmissiontelegrambot.telegram.matcher;

import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.telegram.request.processor.TelegramRequestProcessor;
import it.vitalegi.transmissiontelegrambot.telegram.response.processor.TelegramResponseProcessor;

public interface TelegramMatcher {

	public boolean match(RequestWrapper request);

	public TelegramRequestProcessor getRequestProcessor();

	public TelegramResponseProcessor getResponseProcessor();
}
