package it.vitalegi.transmissiontelegrambot.transmission;

import org.json.JSONObject;

public class TorrentWrapper {

	JSONObject torrent;

	public TorrentWrapper(JSONObject torrent) {
		super();
		this.torrent = torrent;
	}

	public Long getId() {
		return torrent.optLong("id");
	}

	public String getName() {
		return torrent.optString("name");
	}

	public Long getLeftUntilDone() {
		return torrent.optLong("leftUntilDone");
	}

	public Long getSizeWhenDone() {
		return torrent.optLong("sizeWhenDone");
	}

	public Long getEta() {
		return torrent.optLong("eta");
	}

	public Double getRateUpload() {
		return torrent.optDouble("rateUpload");
	}

	public Double getRateDownload() {
		return torrent.optDouble("rateDownload");
	}

	public Integer getStatus() {
		return torrent.optInt("status");
	}

	public Double getUploadRatio() {
		return torrent.optDouble("uploadRatio");
	}

	public Integer getPeersGettingFromUs() {
		return torrent.optInt("peersGettingFromUs");
	}

	public Integer getPeersSendingToUs() {
		return torrent.optInt("peersSendingToUs");
	}
}
