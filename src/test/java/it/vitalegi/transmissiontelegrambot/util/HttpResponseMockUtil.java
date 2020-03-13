package it.vitalegi.transmissiontelegrambot.util;

import static it.vitalegi.transmissiontelegrambot.transmission.TransmissionCallServiceImpl.X_TRANSMISSION_SESSION_ID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.json.JSONObject;

import it.vitalegi.transmissiontelegrambot.transmission.http.HttpResponseWrapper;

public class HttpResponseMockUtil {

	public static HttpResponseWrapper mockResponse(int status, String xTransmissionSessionId, byte[] payload) {
		HttpResponseWrapper response = mock(HttpResponseWrapper.class);
		when(response.getStatus()).thenReturn(status);
		when(response.getPayload()).thenReturn(payload);
		when(response.getHeaderValues(X_TRANSMISSION_SESSION_ID)).thenReturn(Arrays.asList(xTransmissionSessionId));
		return response;
	}

	public static HttpResponseWrapper mockResponseKo(String xTransmissionSessionId) {
		return mockResponse(200, xTransmissionSessionId, Json.init()//
				.put("result", "fail")//
				.buildString().getBytes());
	}

	public static HttpResponseWrapper mockResponseOk(String xTransmissionSessionId, JSONObject arguments) {
		return mockResponse(200, xTransmissionSessionId, Json.init()//
				.put("result", "success")//
				.put("arguments", arguments)//
				.buildString().getBytes());
	}

	public static HttpResponseWrapper mockResponseWrongXTransmissionId(String xTransmissionSessionId) {
		return mockResponse(409, xTransmissionSessionId, Json.init().buildString().getBytes());
	}
}
