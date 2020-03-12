package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.vitalegi.transmissiontelegrambot.action.Action;
import it.vitalegi.transmissiontelegrambot.exception.InvalidHeaderException;
import it.vitalegi.transmissiontelegrambot.util.ListUtil;

@Service
public class TelegramCallServiceImpl {

	Logger log = LoggerFactory.getLogger(TelegramCallServiceImpl.class);

	public final static String X_TRANSMISSION_SESSION_ID = "X-Transmission-Session-Id";
	public final static String X_TRANSMISSION_DEFAULT = "x-transmission-session-id-missing";

	@Value("${telegram.url}")
	protected String url;

	protected String xTransmissionSessionId = X_TRANSMISSION_DEFAULT;

	@Autowired
	protected HttpClientCaller httpClientCaller;

	public JSONObject call(Action action) {
		try {
			return doCall(action);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected JSONObject doCall(Action action) throws IOException {

		HttpResponseWrapper response = null;
		try {
			HttpPost request = createRequest(action, xTransmissionSessionId);
			response = httpClientCaller.call(request);
			return processResponse(response);
		} catch (InvalidHeaderException e) {
			String newXTransmissionSessionId = updateXTransmission(response);
			HttpPost request = createRequest(action, newXTransmissionSessionId);
			response = httpClientCaller.call(request);
			return processResponse(response);
		}
	}

	protected HttpPost createRequest(Action action, String xTransmissionSessionId) throws IOException {
		HttpPost request = new HttpPost(url);
		request.addHeader("accept", "application/json");
		request.addHeader(X_TRANSMISSION_SESSION_ID, xTransmissionSessionId);
		request.setEntity(new StringEntity(createBody(action).toString()));
		return request;
	}

	protected JSONObject createBody(Action action) {
		JSONObject body = new JSONObject();
		body.put("method", action.getMethod().getAction());
		body.put("arguments", action.getArguments());
		return body;
	}

	protected String updateXTransmission(HttpResponseWrapper response) {
		log.info("Wrong {} header.", X_TRANSMISSION_SESSION_ID);
		String newXTransmissionSessionId = ListUtil.getOrNull(//
				response.getHeaderValues(X_TRANSMISSION_SESSION_ID), 0);
		log.info("New value: {}", newXTransmissionSessionId);
		this.xTransmissionSessionId = newXTransmissionSessionId;
		return newXTransmissionSessionId;
	}

	protected JSONObject processResponse(HttpResponseWrapper response) {
		if (response.getStatus() == 409) {
			throw new InvalidHeaderException(X_TRANSMISSION_SESSION_ID + " is not valid");
		}
		if (response.getStatus() != 200) {
			throw new RuntimeException("Error " + response.getStatus() + ": " + response.getReasonPhrase());
		}
		JSONObject payload = new JSONObject(response.getPayload());

		String result = payload.getString("result");
		if (!"success".equals(result)) {
			throw new RuntimeException("Error " + result);
		}
		return payload;
	}
}