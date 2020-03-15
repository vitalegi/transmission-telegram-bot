package it.vitalegi.transmissiontelegrambot.telegram.response.processor;

import org.json.JSONObject;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

public interface TelegramResponseProcessor {

	public void process(RequestWrapper request, Action action, JSONObject response);
}
