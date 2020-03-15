package it.vitalegi.transmissiontelegrambot.util;

public class TransmissionUtil {

	public static class Status {
		public static final String SEEDING = "Seeding";
		public static final String DOWNLOADING = "Downloading";
		public static final String QUEUED = "Queued";
		public static final String PAUSED = "Paused";
		public static final String UNKNOWN = "Unknown";
	}

	public static String getStatus(int statusCode) {
		if (statusCode == 6) {
			return Status.SEEDING;
		}
		if (statusCode == 4) {
			return Status.DOWNLOADING;
		}
		if (statusCode == 3) {
			return Status.QUEUED;
		}
		if (statusCode == 0) {
			return Status.PAUSED;
		}
		return Status.UNKNOWN;
	}
}
