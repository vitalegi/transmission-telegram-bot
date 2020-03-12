package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import java.util.Base64;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.action.RequestFields;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.util.StringUtil;

@Service
public class AddTelegramRequestProcessor extends AbstractTelegramRequestProcessor {

	@Override
	public boolean match(RequestWrapper request) {
		return request.hasDocument();
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

	protected String encodeFile(String fileContent) {
		byte[] bytes = fileContent.getBytes();
		String encoded = Base64.getEncoder().encodeToString(bytes);
		return encoded;
	}
}