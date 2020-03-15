package it.vitalegi.transmissiontelegrambot.telegram.request.processor;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

@Service
public class InfoTelegramRequestProcessor extends AbstractTelegramRequestProcessor {

	public static final String CMD = "info";

	@Override
	public boolean match(RequestWrapper request) {
		return matchFirstTokenCaseInsensitive(request, CMD);
	}

	@Override
	public Action process(RequestWrapper request) {
		List<String> tokenized = request.tokenizeText();

		String id = getMandatoryNumberParam(tokenized, 1);
		Action action = new Action();
		action.setMethod(ActionMethod.GET);

		JSONObject json = new JSONObject();
		action.setArguments(json);

		setIds(json, id);

		JSONArray array = new JSONArray();

		String[] values = new String[] { "activityDate", "addedDate", "bandwidthPriority", "comment", "corruptEver",
				"creator", "dateCreated", "desiredAvailable", "doneDate", "downloadDir", "downloadedEver",
				"downloadLimit", "downloadLimited", "error", "errorString", "eta", "hashString", "haveUnchecked",
				"haveValid", "honorsSessionLimits", "id", "isFinished", "isPrivate", "leftUntilDone", "magnetLink",
				"name", "peersConnected", "peersGettingFromUs", "peersSendingToUs", "peer-limit", "pieceCount",
				"pieceSize", "rateDownload", "rateUpload", "recheckProgress", "secondsDownloading", "secondsSeeding",
				"seedRatioMode", "seedRatioLimit", "sizeWhenDone", "startDate", "status", "totalSize", "uploadedEver",
				"uploadLimit", "uploadLimited", "webseeds", "webseedsSendingToUs" };

		for (String value : values) {
			array.put(value);
		}
		json.put("fields", array);
		return action;
	}
}