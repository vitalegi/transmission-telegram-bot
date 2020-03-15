package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

@Service
public class StartTelegramRequestProcessor extends AbstractTelegramRequestProcessor {

	@Override
	public Action process(RequestWrapper request) {
		List<String> tokenized = request.tokenizeText();

		String id = getMandatoryNumberParam(tokenized, 1);
		Action action = new Action();
		action.setMethod(ActionMethod.START);

		JSONObject json = new JSONObject();
		setIds(json, id);

		action.setArguments(json);

		return action;
	}
}