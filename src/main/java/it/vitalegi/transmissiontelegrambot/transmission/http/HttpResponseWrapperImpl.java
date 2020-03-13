package it.vitalegi.transmissiontelegrambot.transmission.http;

import java.util.List;
import java.util.Map;

public class HttpResponseWrapperImpl implements HttpResponseWrapper {

	int status;
	String statusPhrase;
	Map<String, List<String>> headers;
	byte[] body;

	public HttpResponseWrapperImpl(int status, String statusPhrase, Map<String, List<String>> headers, byte[] body) {
		super();
		this.status = status;
		this.statusPhrase = statusPhrase;
		this.headers = headers;
		this.body = body;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	@Override
	public List<String> getHeaderValues(String name) {
		return headers.get(name);
	}

	@Override
	public byte[] getPayload() {
		return body;
//		try {
//			return EntityUtils.toString(response.getEntity(), "UTF-8");
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}

	@Override
	public String getReasonPhrase() {
		return statusPhrase;
	}

	@Override
	public int getStatus() {
		return status;
	}
}
