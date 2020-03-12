package it.vitalegi.transmissiontelegrambot.telegram;

import java.util.List;
import java.util.Map;

public class HttpResponseWrapperImpl implements HttpResponseWrapper {

	int status;
	String statusPhrase;
	Map<String, List<String>> headers;
	String body;

	public HttpResponseWrapperImpl(int status, String statusPhrase, Map<String, List<String>> headers, String body) {
		super();
		this.status = status;
		this.statusPhrase = statusPhrase;
		this.headers = headers;
		this.body = body;
	}

	@Override
	public List<String> getHeaderValues(String name) {
		return headers.get(name);
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getReasonPhrase() {
		return statusPhrase;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	@Override
	public String getPayload() {
		return body;
//		try {
//			return EntityUtils.toString(response.getEntity(), "UTF-8");
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}
}
