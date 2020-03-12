package it.vitalegi.transmissiontelegrambot.telegram;

import java.util.List;

public interface HttpResponseWrapper {

	String getPayload();

	int getStatus();

	public String getReasonPhrase();

	List<String> getHeaderValues(String name);

}
