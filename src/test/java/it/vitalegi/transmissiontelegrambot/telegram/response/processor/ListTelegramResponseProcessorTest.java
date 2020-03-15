package it.vitalegi.transmissiontelegrambot.telegram.response.processor;

import static it.vitalegi.transmissiontelegrambot.util.BytesUtil.KB;
import static it.vitalegi.transmissiontelegrambot.util.BytesUtil.MB;
import static it.vitalegi.transmissiontelegrambot.util.TimeUtil.HOUR;
import static it.vitalegi.transmissiontelegrambot.util.TimeUtil.MINUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.transmissiontelegrambot.SpringTestConfig;
import it.vitalegi.transmissiontelegrambot.util.Json;
import it.vitalegi.transmissiontelegrambot.util.TimeUtil;
import it.vitalegi.transmissiontelegrambot.util.TorrentFactory;
import it.vitalegi.transmissiontelegrambot.util.TransmissionUtil.Status;

@SpringBootTest(classes = SpringTestConfig.class)
public class ListTelegramResponseProcessorTest {

	Logger log = LoggerFactory.getLogger(ListTelegramResponseProcessorTest.class);

	@Autowired
	ListTelegramResponseProcessor processor;

	@Test
	public void testOneTorrent() {

		JSONObject obj = Json.init()//
				.put("arguments", Json.init()//
						.putJSONArray("torrents", TorrentFactory.init().id(5).name("torrent.1")//
								.leftUntilDone(5 * KB).sizeWhenDone(1 * MB + 100 * KB)//
								.eta(6 * HOUR + 59 * MINUTE).rateUpload(5 * KB).rateDownload(10 * MB)//
								.uploadRatio(1.6).status(4).build()

						).build())//
				.build();

		StringBuilder expected = new StringBuilder();

		expected.append(beforeTorrent());
		expected.append(nameRow("5", "torrent.1"));
		expected.append(sizeRow("99.56", "1.10MB", "5.00KB", Status.DOWNLOADING));
		expected.append(etaRow(TimeUtil.eta(6 * HOUR + 59 * MINUTE), "5.00KBs", "10.00MBs", "1.60"));
		expected.append(afterTorrent());

		expected.append(beforeTotal());
		expected.append(totalRow("1.10MB", "5.00KBs", "10.00MBs"));
		expected.append(afterTotal());

		String actual = processor.produceMessage(obj);

		assertEqualsWithLogs(expected.toString(), actual);
	}

	@Test
	public void testTwoTorrents() {

		JSONObject obj = Json.init()//
				.put("arguments", Json.init()//
						.putJSONArray("torrents", //
								TorrentFactory.init().id(5).name("torrent.1")//
										.leftUntilDone(5 * KB).sizeWhenDone(1 * MB + 100 * KB)//
										.eta(6 * HOUR + 59 * MINUTE).rateUpload(50 * KB).rateDownload(10 * MB)//
										.uploadRatio(1.6).status(4).build(),
								TorrentFactory.init().id(6).name("torrent.2")//
										.leftUntilDone(5 * KB).sizeWhenDone(1 * MB + 100 * KB)//
										.eta(7 * HOUR + 59 * MINUTE).rateUpload(5 * KB).rateDownload(10 * MB)//
										.uploadRatio(1.6).status(6).build())
						.build())//
				.build();

		StringBuilder expected = new StringBuilder();

		expected.append(beforeTorrent());
		expected.append(nameRow("5", "torrent.1"));
		expected.append(sizeRow("99.56", "1.10MB", "5.00KB", Status.DOWNLOADING));
		expected.append(etaRow(TimeUtil.eta(6 * HOUR + 59 * MINUTE), "50.00KBs", "10.00MBs", "1.60"));
		expected.append(afterTorrent());

		expected.append(beforeTorrent());
		expected.append(nameRow("6", "torrent.2"));
		expected.append(sizeRow("99.56", "1.10MB", "5.00KB", Status.SEEDING));
		expected.append(etaRow(TimeUtil.eta(7 * HOUR + 59 * MINUTE), "5.00KBs", "10.00MBs", "1.60"));
		expected.append(afterTorrent());

		expected.append(beforeTotal());
		expected.append(totalRow("2.20MB", "55.00KBs", "20.00MBs"));
		expected.append(afterTotal());

		String actual = processor.produceMessage(obj);

		assertEqualsWithLogs(expected.toString(), actual);
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

	protected String nameRow(String id, String name) {
		return String.format("ID %s - %s\n", id, name);
	}

	protected String sizeRow(String ratio, String size, String missing, String status) {
		return String.format("Ratio %s Size %s Missing %s Status %s\n", ratio, size, missing, status);
	}

	protected String etaRow(String eta, String rateUpload, String rateDownload, String uploadRatio) {
		return String.format("ETA: %s Up/Down: %s/%s Ratio: %s\n", eta, rateUpload, rateDownload, uploadRatio);
	}

	protected String totalRow(String size, String rateUpload, String rateDownload) {
		return String.format("Sum: Size %s, Up/Down %s/%s", size, rateUpload, rateDownload);
	}

	protected void assertEqualsWithLogs(String expected, String actual) {
		String[] expectedLines = expected.split("\\n");
		String[] actualLines = actual.split("\\n");

		for (int i = 0; i < expectedLines.length; i++) {
			log.debug("Expected: {}", expectedLines[i]);
			log.debug("Actual:   {}", actualLines[i]);
			assertEquals(expectedLines[i], actualLines[i]);
		}
		assertEquals(expectedLines.length, actualLines.length);
	}
}
