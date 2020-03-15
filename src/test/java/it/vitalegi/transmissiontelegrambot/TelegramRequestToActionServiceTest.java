package it.vitalegi.transmissiontelegrambot;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.action.RequestFields;
import it.vitalegi.transmissiontelegrambot.telegram.request.RequestWrapperFactory;
import it.vitalegi.transmissiontelegrambot.util.Json;
import it.vitalegi.transmissiontelegrambot.util.JsonComparator;

@SpringBootTest(classes = SpringTestConfig.class)
public class TelegramRequestToActionServiceTest {

	@Autowired
	TelegramRequestToActionServiceImpl service;

	@Autowired
	RequestWrapperFactory requestFactory;

	@Autowired
	JsonComparator jsonComparator;

	@Test
	void testRequestedAddResultsAdd() throws Exception {
		Action action = service.process(requestFactory.getInstance("filename123.txt", "hello world".getBytes()));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.ADD, action.getMethod());
		assertEquals("aGVsbG8gd29ybGQ", action.getArguments().getString(RequestFields.METAINFO));
	}

	@Test
	void testRequestedDeleteResultsRemove() throws Exception {
		Action action = service.process(requestFactory.getInstance("delete 5"));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.REMOVE, action.getMethod());
		assertEquals(true, action.getArguments().getBoolean(RequestFields.DELETE_LOCAL_DATA));
		assertNotNull(action.getArguments().getJSONArray(RequestFields.IDS));
		assertEquals(1, action.getArguments().getJSONArray(RequestFields.IDS).length());
		assertEquals("5", action.getArguments().getJSONArray(RequestFields.IDS).get(0));
	}

	@Test
	void testRequestedInfoResultsInfo() throws Exception {
		Action action = service.process(requestFactory.getInstance("info 5"));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.GET, action.getMethod());
		assertNotNull(action.getArguments().getJSONArray(RequestFields.IDS));
		assertEquals(1, action.getArguments().getJSONArray(RequestFields.IDS).length());
		assertEquals("5", action.getArguments().getJSONArray(RequestFields.IDS).get(0));

		JSONObject expectedArgs = Json.init()
				.put("fields",
						new String[] { "activityDate", "addedDate", "bandwidthPriority", "comment", "corruptEver",
								"creator", "dateCreated", "desiredAvailable", "doneDate", "downloadDir",
								"downloadedEver", "downloadLimit", "downloadLimited", "error", "errorString", "eta",
								"hashString", "haveUnchecked", "haveValid", "honorsSessionLimits", "id", "isFinished",
								"isPrivate", "leftUntilDone", "magnetLink", "name", "peersConnected",
								"peersGettingFromUs", "peersSendingToUs", "peer-limit", "pieceCount", "pieceSize",
								"rateDownload", "rateUpload", "recheckProgress", "secondsDownloading", "secondsSeeding",
								"seedRatioMode", "seedRatioLimit", "sizeWhenDone", "startDate", "status", "totalSize",
								"uploadedEver", "uploadLimit", "uploadLimited", "webseeds", "webseedsSendingToUs" })
				.putJSONArray("ids", "5")//
				.build();
		jsonComparator.assertEquals(expectedArgs, action.getArguments());
	}

	@Test
	void testRequestedListResultsList() throws Exception {
		Action action = service.process(requestFactory.getInstance("list"));

		assertNotNull(action);
		assertEquals(ActionMethod.GET, action.getMethod());
		assertNotNull(action.getArguments());

		JSONObject expectedArgs = Json.init()
				.put("fields",
						new String[] { "error", "errorString", "eta", "id", "isFinished", "leftUntilDone", "name",
								"peersGettingFromUs", "peersSendingToUs", "rateDownload", "rateUpload", "sizeWhenDone",
								"status", "uploadRatio" })
				.build();
		jsonComparator.assertEquals(expectedArgs, action.getArguments());
	}

	@Test
	void testRequestedListWithSpacesResultsList() throws Exception {
		Action action = service.process(requestFactory.getInstance("   list    "));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.GET, action.getMethod());
		assertFalse(action.getArguments().has(RequestFields.IDS));
	}

	@Test
	void testRequestedRemoveResultsRemove() throws Exception {
		Action action = service.process(requestFactory.getInstance("remove 5"));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.REMOVE, action.getMethod());

		assertEquals(false, action.getArguments().getBoolean(RequestFields.DELETE_LOCAL_DATA));
		assertNotNull(action.getArguments().getJSONArray(RequestFields.IDS));
		assertEquals(1, action.getArguments().getJSONArray(RequestFields.IDS).length());
		assertEquals("5", action.getArguments().getJSONArray(RequestFields.IDS).get(0));
	}

	@Test
	void testRequestedStartResultsStart() throws Exception {
		Action action = service.process(requestFactory.getInstance("start 5"));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.START, action.getMethod());
		assertNotNull(action.getArguments().getJSONArray(RequestFields.IDS));
		assertEquals(1, action.getArguments().getJSONArray(RequestFields.IDS).length());
		assertEquals("5", action.getArguments().getJSONArray(RequestFields.IDS).get(0));
	}

	@Test
	void testRequestedStopResultsStop() throws Exception {
		Action action = service.process(requestFactory.getInstance("stop 5"));

		assertNotNull(action);
		assertNotNull(action.getArguments());
		assertEquals(ActionMethod.STOP, action.getMethod());
		assertNotNull(action.getArguments().getJSONArray(RequestFields.IDS));
		assertEquals(1, action.getArguments().getJSONArray(RequestFields.IDS).length());
		assertEquals("5", action.getArguments().getJSONArray(RequestFields.IDS).get(0));
	}
}
