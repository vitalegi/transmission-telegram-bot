package it.vitalegi.transmissiontelegrambot.telegram;

import java.util.List;
import java.util.Map;

public interface HttpResponseWrapper {

	String getPayload();

	int getStatus();

	String getReasonPhrase();

	List<String> getHeaderValues(String name);

	Map<String, List<String>> getHeaders();
}
