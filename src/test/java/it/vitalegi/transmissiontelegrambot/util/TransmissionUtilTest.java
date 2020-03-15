package it.vitalegi.transmissiontelegrambot.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.transmissiontelegrambot.SpringTestConfig;
import it.vitalegi.transmissiontelegrambot.transmission.TorrentWrapper;
import it.vitalegi.transmissiontelegrambot.util.TransmissionUtil.Status;

@SpringBootTest(classes = SpringTestConfig.class)
public class TransmissionUtilTest {

	@Test
	void testSeeding() {
		TorrentWrapper torrent = mock(TorrentWrapper.class);
		when(torrent.getStatus()).thenReturn(6);
		when(torrent.getPeersGettingFromUs()).thenReturn(1);
		assertEquals(Status.SEEDING, TransmissionUtil.getStatus(torrent));
	}

	@Test
	void testIdleSeeding() {
		TorrentWrapper torrent = mock(TorrentWrapper.class);
		when(torrent.getStatus()).thenReturn(6);
		when(torrent.getPeersGettingFromUs()).thenReturn(0);
		assertEquals(Status.IDLE, TransmissionUtil.getStatus(torrent));
	}

	@Test
	void testDownloading() {
		TorrentWrapper torrent = mock(TorrentWrapper.class);
		when(torrent.getStatus()).thenReturn(4);
		when(torrent.getPeersSendingToUs()).thenReturn(1);
		assertEquals(Status.DOWNLOADING, TransmissionUtil.getStatus(torrent));
	}

	@Test
	void testIdleDownloading() {
		TorrentWrapper torrent = mock(TorrentWrapper.class);
		when(torrent.getStatus()).thenReturn(4);
		when(torrent.getPeersSendingToUs()).thenReturn(0);
		assertEquals(Status.IDLE, TransmissionUtil.getStatus(torrent));
	}

	@Test
	void testQueued() {
		TorrentWrapper torrent = mock(TorrentWrapper.class);
		when(torrent.getStatus()).thenReturn(3);
		assertEquals(Status.QUEUED, TransmissionUtil.getStatus(torrent));
	}

	@Test
	void testPaused() {
		TorrentWrapper torrent = mock(TorrentWrapper.class);
		when(torrent.getStatus()).thenReturn(0);
		assertEquals(Status.PAUSED, TransmissionUtil.getStatus(torrent));
	}
}
