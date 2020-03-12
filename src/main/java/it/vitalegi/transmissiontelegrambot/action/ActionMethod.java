package it.vitalegi.transmissiontelegrambot.action;

public enum ActionMethod {

	GET("torrent-get"), //
	ADD("torrent-add"), //
	START("torrent-start"), //
	STOP("torrent-stop"), //
	REMOVE("torrent-remove");

	private String action;

	private ActionMethod(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

}
