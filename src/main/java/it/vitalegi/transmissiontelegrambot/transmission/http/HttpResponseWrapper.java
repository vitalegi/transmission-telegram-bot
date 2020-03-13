package it.vitalegi.transmissiontelegrambot.transmission.http;

import java.util.List;
import java.util.Map;

public interface HttpResponseWrapper {

	Map<String, List<String>> getHeaders();

	List<String> getHeaderValues(String name);

	byte[] getPayload();

	String getReasonPhrase();

	int getStatus();
}
