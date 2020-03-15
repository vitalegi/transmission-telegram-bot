package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import java.util.Base64;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.action.RequestFields;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

@Service
public class AddTelegramRequestProcessor extends AbstractTelegramRequestProcessor {

	protected String encodeFile(byte[] fileContent) {
		String encoded = Base64.getEncoder().withoutPadding().encodeToString(fileContent);
		return encoded;
	}

	@Override
	public Action process(RequestWrapper request) {
		Action action = new Action();
		action.setMethod(ActionMethod.ADD);

		JSONObject json = new JSONObject();
		json.put(RequestFields.METAINFO, encodeFile(request.getDocumentContent()));
		action.setArguments(json);

		return action;
	}
}