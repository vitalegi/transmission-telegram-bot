package it.vitalegi.transmissiontelegrambot.telegram.response.processor;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.telegram.SendMessageWrapper;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapper;
import it.vitalegi.transmissiontelegrambot.transmission.TorrentWrapper;
import it.vitalegi.transmissiontelegrambot.util.BytesUtil;
import it.vitalegi.transmissiontelegrambot.util.TimeUtil;
import it.vitalegi.transmissiontelegrambot.util.TransmissionUtil;

@Service
public class ListTelegramResponseProcessor extends AbstractTelegramResponseProcessor {

	@Autowired
	SendMessageWrapper sendMessageWrapper;

	@Override
	public void process(RequestWrapper request, Action action, JSONObject response) {

		String formattedMessage = produceMessage(response);
		sendMessageWrapper.sendHtmlMessage(request.getChatId(), formattedMessage);
	}

	protected String produceMessage(JSONObject response) {

		JSONObject args = response.getJSONObject("arguments");
		JSONArray torrents = args.getJSONArray("torrents");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < torrents.length(); i++) {
			JSONObject torrent = torrents.getJSONObject(i);
			sb.append(formatTorrent(new TorrentWrapper(torrent)));
		}

		double sumSize = 0;
		double sumUploadRatio = 0;
		double sumDownloadRatio = 0;
		for (int i = 0; i < torrents.length(); i++) {
			TorrentWrapper torrent = new TorrentWrapper(torrents.getJSONObject(i));
			sumSize += torrent.getSizeWhenDone();
			sumUploadRatio += torrent.getRateUpload();
			sumDownloadRatio += torrent.getRateDownload();
		}

		sb.append(beforeTotal());
		sb.append(formatString("Sum: Size %s, Up/Down %s/%s", //
				BytesUtil.bytes(sumSize), BytesUtil.speed(sumUploadRatio), BytesUtil.speed(sumDownloadRatio)));
		sb.append(afterTotal());

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

	protected String formatTorrent(TorrentWrapper torrent) {

		StringBuilder sb = new StringBuilder();

		sb.append(beforeTorrent());

		sb.append(formatString("ID %s - %s\n", torrent.getId(), torrent.getName()));

		sb.append(formatString("Ratio %s Size %s Missing %s Status %s\n", //
				BytesUtil.ratio(torrent.getLeftUntilDone(), torrent.getSizeWhenDone()), //
				BytesUtil.bytes(torrent.getSizeWhenDone()), BytesUtil.bytes(torrent.getLeftUntilDone()), //
				TransmissionUtil.getStatus(torrent)));

		sb.append(formatString("ETA: %s Up/Down: %s/%s Ratio: %s\n", //
				TimeUtil.eta(torrent.getEta()), BytesUtil.speed(torrent.getRateUpload()),
				BytesUtil.speed(torrent.getRateDownload()),
				BytesUtil.ratio(BigDecimal.valueOf(torrent.getUploadRatio()))));

		sb.append(afterTorrent());

		return sb.toString();
	}

	protected String formatString(String pattern, Object... arguments) {
		return String.format(pattern, arguments);
	}

	protected String beforeTorrent() {
		return "<pre>\n";
	}

	protected String afterTorrent() {
		return "</pre>\n";
	}

	protected String beforeTotal() {
		return "<pre>\n";
	}

	protected String afterTotal() {
		return "</pre>";
	}
}