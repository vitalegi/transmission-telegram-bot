package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

@Service
public class ListTelegramRequestProcessor extends AbstractTelegramRequestProcessor {

	public static final String CMD = "list";

	@Override
	public boolean match(RequestWrapper request) {
		return matchFirstTokenCaseInsensitive(request, CMD);
	}

	@Override
	public Action process(RequestWrapper request) {
		Action action = new Action();
		action.setMethod(ActionMethod.GET);

		JSONObject json = new JSONObject();
		action.setArguments(json);

		JSONArray array = new JSONArray();

		String[] values = new String[] { "error", "errorString", "eta", "id", "isFinished", "leftUntilDone", "name",
				"peersGettingFromUs", "peersSendingToUs", "rateDownload", "rateUpload", "sizeWhenDone", "status",
				"uploadRatio" };

		for (String value : values) {
			array.put(value);
		}
		json.put("fields", array);

		return action;
	}
}