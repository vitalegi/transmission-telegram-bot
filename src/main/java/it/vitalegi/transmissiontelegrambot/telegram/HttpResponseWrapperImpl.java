package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.util.EntityUtils;

public class HttpResponseWrapperImpl implements HttpResponseWrapper {

	HttpResponse response;

	public HttpResponseWrapperImpl(HttpResponse response) {
		super();
		this.response = response;
	}

	@Override
	public List<String> getHeaderValues(String name) {
		Header[] headers = response.getHeaders(name);
		return Arrays.asList(headers).stream()//
				.map(Header::getValue).collect(Collectors.toList());
	}

	@Override
	public int getStatus() {
		return response.getStatusLine().getStatusCode();
	}

	@Override
	public String getReasonPhrase() {
		return response.getStatusLine().getReasonPhrase();
	}

	@Override
	public String getPayload() {
		try {
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
