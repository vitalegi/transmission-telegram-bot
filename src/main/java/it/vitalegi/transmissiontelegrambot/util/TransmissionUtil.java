package it.vitalegi.transmissiontelegrambot.util;

import it.vitalegi.transmissiontelegrambot.transmission.TorrentWrapper;

public class TransmissionUtil {

	public static class Status {
		public static final String SEEDING = "Seeding";
		public static final String IDLE = "Idle";
		public static final String DOWNLOADING = "Downloading";
		public static final String QUEUED = "Queued";
		public static final String PAUSED = "Paused";
		public static final String UNKNOWN = "Unknown";
	}

	public static String getStatus(TorrentWrapper torrent) {
		if (torrent.getStatus() == 6) {
			if (torrent.getPeersGettingFromUs() > 0) {
				return Status.SEEDING;
			}
			return Status.IDLE;
		}
		if (torrent.getStatus() == 4) {
			if (torrent.getPeersSendingToUs() > 0) {
				return Status.DOWNLOADING;
			}
			return Status.IDLE;
		}
		if (torrent.getStatus() == 3) {
			return Status.QUEUED;
		}
		if (torrent.getStatus() == 0) {
			return Status.PAUSED;
		}
		return Status.UNKNOWN;
	}
}
