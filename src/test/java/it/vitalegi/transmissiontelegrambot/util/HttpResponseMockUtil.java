package it.vitalegi.transmissiontelegrambot.util;

import static it.vitalegi.transmissiontelegrambot.telegram.TelegramCallServiceImpl.X_TRANSMISSION_SESSION_ID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import it.vitalegi.transmissiontelegrambot.telegram.HttpResponseWrapper;

public class HttpResponseMockUtil {

	public static HttpResponseWrapper mockResponseWrongXTransmissionId(String xTransmissionSessionId) {
		return mockResponse(409, xTransmissionSessionId, Json.init().buildString());
	}

	public static HttpResponseWrapper mockResponseOk(String xTransmissionSessionId, JSONObject arguments) {
		return mockResponse(200, xTransmissionSessionId, Json.init()//
				.put("result", "success")//
				.put("arguments", arguments)//
				.buildString());
	}

	public static HttpResponseWrapper mockResponseKo(String xTransmissionSessionId) {
		return mockResponse(200, xTransmissionSessionId, Json.init()//
				.put("result", "fail")//
				.buildString());
	}

	public static HttpResponseWrapper mockResponse(int status, String xTransmissionSessionId, String payload) {
		HttpResponseWrapper response = mock(HttpResponseWrapper.class);
		when(response.getStatus()).thenReturn(status);
		when(response.getPayload()).thenReturn(payload);
		when(response.getHeaderValues(X_TRANSMISSION_SESSION_ID)).thenReturn(Arrays.asList(xTransmissionSessionId));
		return response;
	}
}
