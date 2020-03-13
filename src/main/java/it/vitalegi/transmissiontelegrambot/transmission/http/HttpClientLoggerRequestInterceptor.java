package it.vitalegi.transmissiontelegrambot.transmission.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientLoggerRequestInterceptor extends AbstractHttpClientLogger implements HttpRequestInterceptor {

	Logger log = LoggerFactory.getLogger(HttpClientLoggerRequestInterceptor.class);

	private String id;

	public HttpClientLoggerRequestInterceptor(String id) {
		super();
		this.id = id;
	}

	@Override
	public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
		log.info("{} ---------------------------", id);
		log.info("{} URL: {}", id, request.getRequestLine().getUri());
		for (Header header : request.getAllHeaders()) {
			log.info("{} Header: {}={}", id, header.getName(), header.getValue());
		}
		if (request instanceof HttpEntityEnclosingRequest) {
			log.info("{} Body:\n{}", id, toString(((HttpEntityEnclosingRequest) request).getEntity()));
		}
		log.info("{} ---------------------------", id);
	}
}