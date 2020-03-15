package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

public interface TelegramRequestProcessor {

	public Action process(RequestWrapper request);
}
