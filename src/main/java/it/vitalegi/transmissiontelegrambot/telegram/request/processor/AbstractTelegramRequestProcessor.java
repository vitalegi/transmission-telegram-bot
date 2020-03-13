package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import it.vitalegi.transmissiontelegrambot.action.RequestFields;
import it.vitalegi.transmissiontelegrambot.exception.InvalidTelegramRequestException;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.util.ListUtil;

public abstract class AbstractTelegramRequestProcessor implements TelegramRequestProcessor {

	protected String getMandatoryNumberParam(List<String> params, int index) {
		String param = getMandatoryParam(params, index);
		try {
			Integer.parseInt(param);
		} catch (NumberFormatException e) {
			throw new InvalidTelegramRequestException("id-is-not-a-number");
		}
		return param;
	}

	protected String getMandatoryParam(List<String> params, int index) {
		String param = ListUtil.getOrNull(params, index);
		if (param == null) {
			throw new InvalidTelegramRequestException("Param is missing");
		}
		return param;
	}

	protected boolean matchFirstTokenCaseInsensitive(RequestWrapper request, String expected) {
		List<String> tokenized = request.tokenizeText();
		if (tokenized.isEmpty()) {
			return false;
		}
		String actual = tokenized.get(0);
		return expected.toUpperCase().equals(actual.toUpperCase());
	}

	protected void setIds(JSONObject json, String id) {
		JSONArray arr = new JSONArray();
		arr.put(id);
		json.put(RequestFields.IDS, arr);
	}
}