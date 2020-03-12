package it.vitalegi.transmissiontelegrambot.telegram;

import static it.vitalegi.transmissiontelegrambot.telegram.TelegramCallServiceImpl.X_TRANSMISSION_SESSION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.action.ActionMethod;
import it.vitalegi.transmissiontelegrambot.util.HttpResponseMockUtil;
import it.vitalegi.transmissiontelegrambot.util.Json;

@SpringBootTest
public class TelegramCallServiceTest {

	public static final String X_SESSION_ID_OK = "transmission-session-id-ok";
	public static final String X_SESSION_ID_KO = TelegramCallServiceImpl.X_TRANSMISSION_DEFAULT;

	@MockBean
	HttpClientCaller httpCaller;

	@Autowired
	TelegramCallServiceImpl telegramCallService;

	@Test
	void testWrongXTransmissionSessionIdShouldUpdateId() throws IOException {

		HttpResponseWrapper r1 = HttpResponseMockUtil.mockResponseWrongXTransmissionId(X_SESSION_ID_OK);
		HttpResponseWrapper r2 = HttpResponseMockUtil.mockResponseOk(X_SESSION_ID_OK,
				Json.init().put("torrents", "").build());

		ArgumentCaptor<HttpPost> requestCaptor = ArgumentCaptor.forClass(HttpPost.class);

		when(httpCaller.call(requestCaptor.capture())) //
				.thenReturn(r1).thenReturn(r2);

		telegramCallService.call(action(ActionMethod.GET, Json.init().build()));

		List<HttpPost> requestValues = requestCaptor.getAllValues();

		assertEquals(X_SESSION_ID_KO, getHeaderValue(requestValues.get(0), X_TRANSMISSION_SESSION_ID));
		assertEquals(X_SESSION_ID_OK, getHeaderValue(requestValues.get(1), X_TRANSMISSION_SESSION_ID));
	}

	private String getHeaderValue(HttpPost request, String name) {
		return request.getHeaders(name)[0].getValue();
	}

	private Action action(ActionMethod method, JSONObject arguments) {
		Action action = new Action();
		action.setMethod(method);
		action.setArguments(arguments);
		return action;
	}
}