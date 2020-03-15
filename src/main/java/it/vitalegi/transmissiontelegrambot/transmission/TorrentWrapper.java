package it.vitalegi.transmissiontelegrambot.transmission;

import org.json.JSONObject;

public class TorrentWrapper {

	JSONObject torrent;

	public TorrentWrapper(JSONObject torrent) {
		super();
		this.torrent = torrent;
	}

	public Long getId() {
		return torrent.getLong("id");
	}

	public String getName() {
		return torrent.getString("name");
	}

	public Long getLeftUntilDone() {
		return torrent.getLong("leftUntilDone");
	}

	public Long getSizeWhenDone() {
		return torrent.getLong("sizeWhenDone");
	}

	public Long getEta() {
		return torrent.getLong("eta");
	}

	public Double getRateUpload() {
		return torrent.getDouble("rateUpload");
	}

	public Double getRateDownload() {
		return torrent.getDouble("rateDownload");
	}

	public Integer getStatus() {
		return torrent.getInt("status");
	}

	public Double getUploadRatio() {
		return torrent.getDouble("uploadRatio");
	}
}
