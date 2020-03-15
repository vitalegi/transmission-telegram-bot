package it.vitalegi.transmissiontelegrambot.util;

import org.json.JSONObject;

public class TorrentFactory {

	private Json obj;

	public TorrentFactory() {
		obj = Json.init();
	}

	public static TorrentFactory init() {
		return new TorrentFactory();
	}

	public JSONObject build() {
		return obj.build();
	}

	public TorrentFactory id(int id) {
		obj.put("id", id);
		return this;
	}

	public TorrentFactory name(String name) {
		obj.put("name", name);
		return this;
	}

	public TorrentFactory leftUntilDone(long leftUntilDone) {
		obj.put("leftUntilDone", leftUntilDone);
		return this;
	}

	public TorrentFactory sizeWhenDone(long sizeWhenDone) {
		obj.put("sizeWhenDone", sizeWhenDone);
		return this;
	}

	public TorrentFactory eta(int eta) {
		obj.put("eta", eta);
		return this;
	}

	public TorrentFactory rateUpload(Double rateUpload) {
		obj.put("rateUpload", rateUpload);
		return this;
	}

	public TorrentFactory rateUpload(Long rateUpload) {
		obj.put("rateUpload", rateUpload);
		return this;
	}

	public TorrentFactory rateDownload(Double rateDownload) {
		obj.put("rateDownload", rateDownload);
		return this;
	}

	public TorrentFactory rateDownload(Long rateDownload) {
		obj.put("rateDownload", rateDownload);
		return this;
	}

	public TorrentFactory uploadRatio(Double uploadRatio) {
		obj.put("uploadRatio", uploadRatio);
		return this;
	}

	public TorrentFactory status(Integer status) {
		obj.put("status", status);
		return this;
	}

	public TorrentFactory peersGettingFromUs(Integer peersGettingFromUs) {
		obj.put("peersGettingFromUs", peersGettingFromUs);
		return this;
	}

	public TorrentFactory peersSendingToUs(Integer peersSendingToUs) {
		obj.put("peersSendingToUs", peersSendingToUs);
		return this;
	}
}