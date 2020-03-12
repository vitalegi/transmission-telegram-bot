package it.vitalegi.transmissiontelegrambot.telegram;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientLoggerResponseInterceptor extends AbstractHttpClientLogger implements HttpResponseInterceptor {

	Logger log = LoggerFactory.getLogger(HttpClientLoggerResponseInterceptor.class);

	private String id;

	public HttpClientLoggerResponseInterceptor(String id) {
		super();
		this.id = id;
	}

	@Override
	public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
		log.info("{} ---------------------------", id);
		log.info("{} Status: {}, {}", id, response.getStatusLine().getStatusCode(),
				response.getStatusLine().getReasonPhrase());
		for (Header header : response.getAllHeaders()) {
			log.info("{} Header: {}={}", id, header.getName(), header.getValue());
		}
		log.info("{} Body:\n{}", id, toString(response.getEntity()));
		log.info("{} ---------------------------", id);
	}
}
