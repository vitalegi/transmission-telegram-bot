package it.vitalegi.transmissiontelegrambot.telegram.response.processor;

import java.text.MessageFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.telegram.TelegramHandlers;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;

@Service
public class ListTelegramResponseProcessor {

	public static final String CMD = "list";

	@Autowired
	TelegramHandlers telegramHandlers;

	public boolean match(RequestWrapper request, Action action, JSONObject response) {
		return matchFirstTokenCaseInsensitive(request, CMD);
	}

	public String process(JSONObject response) {

		JSONObject args = response.getJSONObject("arguments");
		JSONArray torrents = args.getJSONArray("torrents");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < torrents.length(); i++) {
			JSONObject torrent = torrents.getJSONObject(i);
			sb.append(formatTorrent(torrent));
		}

		String[] values = new String[] { "error", "errorString", "eta", "id", "isFinished", "leftUntilDone", "name",
				"peersGettingFromUs", "peersSendingToUs", "rateDownload", "rateUpload", "sizeWhenDone", "status",
				"uploadRatio" };
		return sb.toString();
	}

	protected boolean matchFirstTokenCaseInsensitive(RequestWrapper request, String expected) {
		List<String> tokenized = request.tokenizeText();
		if (tokenized.isEmpty()) {
			return false;
		}
		String actual = tokenized.get(0);
		return expected.toUpperCase().equals(actual.toUpperCase());
	}

	protected String formatTorrent(JSONObject torrent) {

		StringBuilder sb = new StringBuilder();

		long id = torrent.getLong("id");
		String name = torrent.getString("name");
		double missing = torrent.getDouble("leftUntilDone");
		double size = torrent.getDouble("sizeWhenDone");
		long ratio = Math.round(100 * (size - missing) / size);

		sb.append(formatString("ID %s - %s\n", id, name));
		sb.append(formatString("Ratio %d Size %f Missing %f\n", 100 * (size - missing) / size, size, missing));
//		sb.append(formatString("ETA: %s Up/Down: %d/%d Ratio: %d\n", t, "eta", "rateUpload", "rateDownload",
//				"uploadRatio"));
//		sb.append(formatString("Status: %d\n\n", t, "status"));

		return sb.toString();
	}

	protected String formatString(String pattern, Object... arguments) {
		return MessageFormat.format(pattern, arguments);
	}
}